package ua.rd.ioc;

import java.util.Map;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public class JavaMapConfig implements Config {

    private  Map<String, Map<String, Object>> beanDescriptions;

    public JavaMapConfig(Map<String, Map<String, Object>> beanDescriptions) {
        this.beanDescriptions = beanDescriptions;

    }



    @Override
    public BeanDefinition[] beanDefinitions() {
        BeanDefinition[] beanDefinitions = beanDescriptions
                .keySet()
                .stream()
                .map(key -> getBeanDefinition(key, beanDescriptions.get(key)))
                .toArray(BeanDefinition[]::new);
        return beanDefinitions;
    }

    private BeanDefinition getBeanDefinition(String name, Map<String, Object> entry) {
        return new SimpleBeanDefinition(name,
                (Class<?>) entry.get("type"),
                (boolean) entry.getOrDefault("isPrototype",false));

    }
}
