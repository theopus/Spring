package ua.rd.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ApplicationContext implements Context {

    private List<BeanDefinition> beanDefinitions;
    private Map<String, Object> beans = new HashMap<>();

    public ApplicationContext(Config config) {
        beanDefinitions = Arrays.asList(config.beanDefinitions());
    }

    public ApplicationContext() {
        beanDefinitions = Arrays.asList(Config.EMPTY_BEANDEFINITION);//new BeanDefinition[0];
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = getBeanDefinitionByName(beanName);
        Object bean = beans.get(beanName);
        if(bean == null) {
            bean = createNewBean(beanDefinition);
            if (!beanDefinition.isPrototype()) {
                beans.put(beanName, bean);
            }
        }
        return bean;
    }

    private Object createNewBean(BeanDefinition beanDefinition) {
        Object bean = createNewBeanInstance(beanDefinition);
        return bean;
    }

    private BeanDefinition getBeanDefinitionByName(String beanName) {
        return beanDefinitions.stream()
                .filter(bd -> Objects.equals(bd.getBeanName(), beanName))
                .findAny().orElseThrow(NoSuchBeanException::new);
    }

    private Object createNewBeanInstance(BeanDefinition bd) {
        Class<?> type = bd.getBeanType();
        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        Object newBean = null;
        if(constructor.getParameterCount() == 0) {
            newBean = createBeanWithDefaultConstructor(type);
        } else {
            newBean = createBeanWithConstructorWithParams(type);
        }
        return newBean;
    }

    private Object createBeanWithConstructorWithParams(Class<?> type) {
        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        Set<Object> objects = new LinkedHashSet<>();
        for (Class<?> parameterType : parameterTypes) {
            String simpleName = parameterType.getSimpleName();
            String firstLeterLow = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
            System.out.println(firstLeterLow);
            objects.add(this.getBean(firstLeterLow));
        }

        Object o = null;
        try {
            o = constructor.newInstance(objects.toArray(new Object[objects.size()]));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
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

    public String[] getBeanDefinitionNames() {
        return beanDefinitions.stream()
                .map(BeanDefinition::getBeanName)
                .toArray(String[]::new);
    }
}
