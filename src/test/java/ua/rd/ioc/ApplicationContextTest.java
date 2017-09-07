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
        //map instead list for beanName - beanType
//        List<String> beandescription = Arrays.asList(beanName);
//ToDo: make it work(create beans)

        Map<String, Class<?>> beandescription  = new HashMap<String, Class<?>>(){
            {put(beanName,beanType);}
        };
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        TestBean bean = (TestBean) context.getBean(beanName);


    }

    @Test
    public void getBeandefinitionWith_ZERO_BeanDefinition() throws Exception {
        //given
        List<String> beandescription = Collections.emptyList();
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
        List<String> beandescription = Arrays.asList(beanName1, beanName);
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        //when
        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        //then
        String[] expected  = {beanName1,beanName};
        assertArrayEquals(beanDefinitionNames,expected);
    }

    @Test
    public void getBeanDefinitionNamesWith_ONE_BeanDefinitionContext_IS_NOT_NULL() throws Exception {
        //given
        String beanName = "FirstBean";
        List<String> beandescription = Arrays.asList(beanName);
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        //when
        Object actual = context.getBean(beanName);

        //then
        assertNotNull(actual);
    }

    private class TestBean {
    }
}