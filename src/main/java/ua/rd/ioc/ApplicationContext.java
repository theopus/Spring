package ua.rd.ioc;

import java.lang.reflect.*;
import java.util.*;

public class ApplicationContext implements Context {

    private List<BeanDefinition> beanDefinitions;
    private Map<String, Object> beans = new HashMap<>();

    public ApplicationContext(Config config) {
        beanDefinitions = Arrays.asList(config.beanDefinitions());
        initContext(beanDefinitions);
    }

    private void initContext(List<BeanDefinition> beanDefinitions) {
        beanDefinitions.forEach(bd -> getBean(bd.getBeanName()));
    }

    public ApplicationContext() {
        beanDefinitions = Arrays.asList(Config.EMPTY_BEANDEFINITION);//new BeanDefinition[0];
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = getBeanDefinitionByName(beanName);
        Object bean = beans.get(beanName);
        if (bean != null){
            return bean;
        }
        bean = createNewBean(beanDefinition);
        if (!beanDefinition.isPrototype()) {
            beans.put(beanName, bean);
        }
        return bean;
    }

    private Object createNewBean(BeanDefinition beanDefinition) {
        BeanBuilder beanBuilder = new BeanBuilder(beanDefinition);
        beanBuilder.createNewBeanInstance();
        beanBuilder.callPostConstructAnnotatedMethod();
        beanBuilder.callInitInstance();
//TODO        beanBuilder.createBenchmarkProxy();

        Object bean = beanBuilder.build();
        return bean;
    }

    private BeanDefinition getBeanDefinitionByName(String beanName) {
        return beanDefinitions.stream()
                .filter(bd -> Objects.equals(bd.getBeanName(), beanName))
                .findAny().orElseThrow(NoSuchBeanException::new);
    }

    public String[] getBeanDefinitionNames() {
        return beanDefinitions.stream()
                .map(BeanDefinition::getBeanName)
                .toArray(String[]::new);
    }

    private class BeanBuilder{
        private BeanDefinition beanDefinition;
        private Object bean;

        public BeanBuilder(BeanDefinition beanDefinition) {
            this.beanDefinition = beanDefinition;
        }

        private Object createBeanWithDefaultConstructor(Class<?> type) {
            Object newBean;
            try {
                newBean = type.newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
            return newBean;
        }

        private Object createBeanWithConstructorWithParams(Class<?> type) {
            Constructor<?> constructor = type.getDeclaredConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            List<Object> objects = new ArrayList<>();
            for (Class<?> parameterType : parameterTypes) {
                String simpleName = parameterType.getSimpleName();
                String firstLeterLow = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
                objects.add(getBean(firstLeterLow));
            }

            Object o = null;
            try {
                o = constructor.newInstance(objects.toArray(new Object[objects.size()]));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return o;
        }

        private void createNewBeanInstance() {
            Class<?> type = beanDefinition.getBeanType();
            Constructor<?> constructor = type.getDeclaredConstructors()[0];
            if(constructor.getParameterCount() == 0) {
                bean = createBeanWithDefaultConstructor(type);
            } else {
                bean = createBeanWithConstructorWithParams(type);
            }
        }

        public void callPostConstructAnnotatedMethod() {
            Arrays.stream(bean.getClass().getMethods()).forEach(method -> {
                if (method.getAnnotation(MyPostConstruct.class) != null)
                    try {
                        method.invoke(bean);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
            });
        }

        public void callInitInstance() {
            try {
                bean.getClass().getMethod("init").invoke(bean);
            } catch (NoSuchMethodException ignored){
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public void createBenchmarkProxy() {
            Class<?> beanClass = bean.getClass();
            bean = Proxy.newProxyInstance(
                    ClassLoader.getSystemClassLoader(),
                    beanClass.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println(method.getName());
                            return method.invoke(bean, args);
                        }
                    });
        }

        public Object build() {
            return bean;
        }
    }
}
