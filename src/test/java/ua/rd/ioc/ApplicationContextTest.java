package ua.rd.ioc;

import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

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
    public void getBeandefinitionWithOneBeanDefinition() throws Exception {
        String beanName = "FirstBean";
        List<String> beandescription = Arrays.asList(beanName);
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        String[] expected  = {beanName};
        assertArrayEquals(beanDefinitionNames,expected);


    }
}