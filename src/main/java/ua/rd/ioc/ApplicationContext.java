package ua.rd.ioc;

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
        throw new NoSuchBeanException();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        String[] beanDefinitionNames = new String[beanDefinitions.length];
        for (int i = 0; i < beanDefinitions.length; i++) {
            beanDefinitionNames[i] = beanDefinitions[i].getBeanName();
        }
        return beanDefinitionNames;
    }
}
