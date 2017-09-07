package ua.rd.ioc;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public class ApplicationContext implements Context {

    private BeanDefinition[] beanDefinitions;

    public ApplicationContext(Config config) {
         this.beanDefinitions = config.beanDefinitions();
    }

    public ApplicationContext() {
        beanDefinitions = Config.EMPTY_BEAN_DEFINIION;
    }

    @Override
    public Object getBean(String beanName) {
        List<BeanDefinition> beanDefinitions = Arrays.asList(this.beanDefinitions);
        if (beanDefinitions
                .stream()
                .map(BeanDefinition::getBeanName)
                .anyMatch(n -> n.equals(beanName))){
            BeanDefinition beanDefinition;
            return beanDefinition.getBeanType().newInstance();
        }
        else
            throw new NoSuchBeanException();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        String[] beanDefinitionNames = Arrays
                .stream(beanDefinitions)
                .map(BeanDefinition::getBeanName)
                .toArray(String[]::new);
        return beanDefinitionNames;
    }
}
