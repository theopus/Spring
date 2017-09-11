package ua.rd.ioc;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public class ApplicationContext implements Context {

    private List<BeanDefinition> beanDefinitions;

    private Map<String, Object> cache = new HashMap<>();

    public ApplicationContext(Config config) {
         this.beanDefinitions = Arrays.asList(config.beanDefinitions());
    }

    public ApplicationContext() {
        beanDefinitions = Arrays.asList(Config.EMPTY_BEAN_DEFINIION);
    }

    @Override
    public Object getBean(String beanName) {
        if (cache.containsKey(beanName))
            return cache.get(beanName);
        if (beanDefinitions.stream().map(BeanDefinition::getBeanName).anyMatch(n -> n.equals(beanName))){
            BeanDefinition beanDefinition = beanDefinitions.stream().filter(bD -> bD.getBeanName().equals(beanName)).findFirst().orElse(null);
            Object nonArgBean = createBean(beanDefinition);
            return cache.put(beanName, nonArgBean);
        }
        else
            throw new NoSuchBeanException();
    }


    private Object createBean(BeanDefinition beanDefinition){
        Class<?> type = beanDefinition.getBeanType();
        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object newBean = null;
        if (parameterTypes.length == 0){
            newBean = createBeanWithDeafultConstructor(beanDefinition.getBeanType());
        }
        else {
            newBean = createBeanWithContructor(type);
        }
        return newBean;
    }

    private Object createBeanWithContructor(Class<?> type) {
        Constructor<?> s = type.getDeclaredConstructors()[0];
        return null;
    }

    private Object createBeanWithDeafultConstructor(Class<?> type){
        Object newBean = null;
        try {
            newBean = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return newBean;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        String[] beanDefinitionNames = beanDefinitions
                .stream()
                .map(BeanDefinition::getBeanName)
                .toArray(String[]::new);
        return beanDefinitionNames;
    }

}
