package ua.rd;

import ua.rd.ioc.ApplicationContext;
import ua.rd.ioc.Config;
import ua.rd.ioc.Context;
import ua.rd.ioc.JavaMapConfig;
import ua.rd.repository.InMemTweetRepositoryImpl;
import ua.rd.repository.TweetRepository;

import java.util.HashMap;
import java.util.Map;

public class IoCRunner
{
    public static void main(String[] args) {
        String beanName1 = "reopistory";
        Class<InMemTweetRepositoryImpl> beanType = InMemTweetRepositoryImpl.class;
        HashMap<String, Map<String, Object>> hashMap = new HashMap<String, Map<String, Object>>() {
            {
                put(beanName1, new HashMap<String, Object>() {
                    {
                        put("type", beanType);
                    }
                });
            }
        };

        Config config = new JavaMapConfig(hashMap);
        Context context = new ApplicationContext(config);

        TweetRepository tw = (TweetRepository) context.getBean(beanName1);
        System.out.println(tw.allTweets().toString());


    }
}
