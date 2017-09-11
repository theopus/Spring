package ua.rd.service;

import ua.rd.domain.Tweet;

/**
 * Created by Oleksandr_Tkachov on 9/11/2017.
 */
public interface TweetService {
    Iterable<Tweet> allTweets();
}
