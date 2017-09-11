package ua.rd.repository;

import ua.rd.domain.Tweet;

/**
 * Created by Oleksandr_Tkachov on 9/11/2017.
 */
public interface TweetRepository {

    Iterable<Tweet> allTweets();

}
