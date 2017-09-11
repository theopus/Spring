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
        Map<String, Map<String, Object>> beandescription  = new HashMap<String, Map<String, Object>>(){
            {
                put(beanName, new HashMap<String, Object>(){
                    {
                        put("type", beanType);
                    }
                });
            }
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
        Map<String, Map<String, Object>> beandescription  = new HashMap<String, Map<String, Object>>(){
            {
                put(beanName, new HashMap<String, Object>(){
                    {
                        put("type", beanType);
                    }
                });
            }
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
        Map<String, Map<String, Object>> beandescription  = new HashMap<String, Map<String, Object>>(){};
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

        Map<String, Map<String, Object>> beandescription = get2DiffBD(beanName1, beanName, beanType);
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        //when
        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        //then
        String[] expected  = {beanName1,beanName};
        assertArrayEquals(beanDefinitionNames,expected);
    }

    private Map<String, Map<String, Object>> get2DiffBD(final String beanName1, final String beanName, final Class<TestBean> beanType) {
        return new HashMap<String, Map<String, Object>>(){
                {
                    put(beanName1, new HashMap<String, Object>(){
                        {
                            put("type", beanType);
                        }
                    });
                    put(beanName, new HashMap<String, Object>(){
                        {
                            put("type", beanType);
                        }
                    });
                }
            };
    }

    @Test
    public void getBeanIsSingletone() throws Exception {
        String beanName = "FirstBean";
        Class<TestBean> beanType = TestBean.class;
        Map<String, Map<String, Object>> beandescription  = new HashMap<String, Map<String, Object>>(){
            {
                put(beanName, new HashMap<String, Object>(){
                    {
                        put("type", beanType);
                    }
                });
            }
        };
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);



        TestBean bean1 = (TestBean) context.getBean(beanName);
        TestBean bean2 = (TestBean) context.getBean(beanName);

        assertSame(bean1,bean2);


    }

    @Test
    public void getBeanIsPrototype() throws Exception {
        String beanName = "FirstBean";
        Class<TestBean> beanType = TestBean.class;
        Map<String, Map<String, Object>> beandescription  = new HashMap<String, Map<String, Object>>(){
            {
                put(beanName, new HashMap<String, Object>(){
                    {
                        put("type", beanType);
                        put("isPrototype", true);
                    }
                });
            }
        };
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);

        TestBean bean1 = (TestBean) context.getBean(beanName);
        TestBean bean2 = (TestBean) context.getBean(beanName);

        assertNotSame(bean1,bean2);


    }


    private Map<String, Map<String, Object>> getStringMapMap(final String beanName, final Class<TestBean> beanType) {
        return new HashMap<String, Map<String, Object>>(){
                {
                    put(beanName, new HashMap<String, Object>(){
                        {
                            put("type", beanType);
                        }
                    });
                }
            };
    }

    @Test
    public void getBeanWithContructorAnotherBean() throws Exception {
        //given
        String dependent = "InnerBean";
        String beanName = "Bean";
        Class<TestBean> dependentType = TestBean.class;
        Class<TestBeanWithContructor> beanType = TestBeanWithContructor.class;
        Map<String, Map<String, Object>> beandescription  = new HashMap<String, Map<String, Object>>(){
            {
                put(dependent, new HashMap<String, Object>(){
                    {
                        put("type", dependentType);
                    }
                });
                put(beanName, new HashMap<String, Object>(){
                    {
                        put("type", beanType);
                    }
                });
            }
        };
        Config config = new JavaMapConfig(beandescription);
        Context context = new ApplicationContext(config);
        //whenthen
        TestBeanWithContructor beanWithContructor = (TestBeanWithContructor) context.getBean(beanName);

        assertNotNull(beanWithContructor);

    }

    static class TestBean {
    }

    static class TestBeanWithContructor{
        private TestBean bean;

        public TestBeanWithContructor(TestBean bean) {
            this.bean = bean;
        }
    }

}