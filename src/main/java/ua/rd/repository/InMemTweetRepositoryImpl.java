package ua.rd.repository;

import ua.rd.domain.Tweet;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Oleksandr_Tkachov on 9/11/2017.
 */
public class InMemTweetRepositoryImpl implements TweetRepository {

    private List<Tweet> tweetList;
    {
        tweetList = Arrays.asList(
          new Tweet(1L, "Msg", null),
          new Tweet(2L, "Msg", null),
          new Tweet(3L, "Msg", null)
        );
    }


    @Override
    public Iterable<Tweet> allTweets() {
        return tweetList;
    }
}
