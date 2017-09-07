package ua.rd.ioc;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public class ApplicationContextTest {
    @Test(expected = NoSuchBeanException.class)
    public void getBeanWithEmptyContext() throws Exception {
        Context context = new ApplicationContext();
        context.getBean("abc");
    }

    @Test
    public void getBeanDefinitionNamesWithEmptyContext() throws Exception {
        //given
        Context context = new ApplicationContext();
        //when
        String[] actual = context.getBeanDefinitionNames();
        //then
        String[] expected ={};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getBeandefinitionWith_ONE_BeanDefinition() throws Exception {
        //given
        String beanName = "FirstBean";
        Class<TestBean> beanType = TestBean.class;
        Map<String, Class<?>> beandescription  = new HashMap<String, Class<?>>(){
            {put(beanName,beanType);}
        };
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);
        //whenthen
        TestBean bean = (TestBean) context.getBean(beanName);

    }
    @Test
    public void getBeanDefinitionNamesWith_ONE_BeanDefinitionContext_IS_NOT_NULL() throws Exception {
        //given
        String beanName = "FirstBean";
        Class<TestBean> beanType = TestBean.class;
        Map<String, Class<?>> beandescription  = new HashMap<String, Class<?>>(){
            {put(beanName,beanType);}
        };
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        //when
        Object actual = context.getBean(beanName);

        //then
        assertNotNull(actual);
    }

    @Test
    public void getBeandefinitionWith_ZERO_BeanDefinition() throws Exception {
        //given
        Map<String, Class<?>> beandescription  = new HashMap<String, Class<?>>();
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        //when
        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        //then
        String[] expected  = {};
        assertArrayEquals(beanDefinitionNames,expected);
    }

    @Test
    public void getBeandefinitionWith_SEVERAL_BeanDefinition() throws Exception {
        //given
        String beanName1 = "FirstBean";
        String beanName = "SecondBean";
        Class<TestBean> beanType = TestBean.class;
        Map<String, Class<?>> beandescription  = new HashMap<String, Class<?>>(){
            {put(beanName1,beanType);}
            {put(beanName,beanType);}
        };
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        //when
        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        //then
        String[] expected  = {beanName1,beanName};
        assertArrayEquals(beanDefinitionNames,expected);
    }

    @Test(expected = UnableCreateBeanException.class)
    public void getBeanOneBeanCannotCreateException() throws Exception {
        String beanName = "FirstBean";
        Class<UnavalibleTestBean> uncreatable = UnavalibleTestBean.class;
        Map<String, Class<?>> beandescription  = new HashMap<String, Class<?>>(){
            {put(beanName,uncreatable);}
        };
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        //when
        Object actual = context.getBean(beanName);

        //then
        assertNotNull(actual);
    }

    public static class TestBean {
    }

    private static class UnavalibleTestBean {
    }
}