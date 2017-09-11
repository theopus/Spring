package ua.rd.domain;

/**
 * Created by Oleksandr_Tkachov on 9/11/2017.
 */
public class Tweet {

    private Long tweetId;
    private String txt;
    private User user;

    public Tweet() {
    }

    public Tweet(String txt, User user) {
        this.txt = txt;
        this.user = user;
    }

    public Tweet(Long tweetId, String txt, User user) {
        this.tweetId = tweetId;
        this.txt = txt;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetId=" + tweetId +
                ", txt='" + txt + '\'' +
                ", user=" + user +
                '}';
    }
}
