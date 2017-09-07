package ua.rd.ioc;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public class JavaMapConfig implements Config {

    private List<String> beanDescriptions;

    public JavaMapConfig(List<String> beanDescriptions) {
        this.beanDescriptions = beanDescriptions;

    }



    @Override
    public BeanDefinition[] beanDefinitions() {
        BeanDefinition[] beanDefinitions = beanDescriptions
                .stream().map(name -> (BeanDefinition) () -> name)
                .toArray(BeanDefinition[]::new);
        return beanDefinitions;
    }
}
