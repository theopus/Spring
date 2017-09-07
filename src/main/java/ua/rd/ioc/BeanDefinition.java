package ua.rd.ioc;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public interface BeanDefinition {
    String getBeanName();
    Class<?> getBeanType();
}
