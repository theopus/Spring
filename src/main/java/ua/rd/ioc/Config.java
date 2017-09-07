package ua.rd.ioc;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public interface Config {
    BeanDefinition[] EMPTY_BEAN_DEFINIION = new BeanDefinition[0];

    BeanDefinition[] beanDefinitions();

}
