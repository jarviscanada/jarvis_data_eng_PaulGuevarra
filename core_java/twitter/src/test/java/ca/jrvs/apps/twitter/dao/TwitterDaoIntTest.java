package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    public String hashTag = "#tacotuesday";
    float longitude = 10f;
    float latitude = -10f;
    TwitterDao twitterDao;
    String text = "@paulwguevarra guess what day it is? It really really is "+ hashTag + " " + System.currentTimeMillis();
    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
        this.twitterDao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() {

        Tweet postTweet = TweetUtil.buildTweet(text, longitude, latitude);
        Tweet tweet = twitterDao.create(postTweet);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(longitude, tweet.getCoordinates().getCoordinates()[0], 1e-8);
        assertEquals(latitude, tweet.getCoordinates().getCoordinates()[1], 1e-8);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));


    }

    @Test
    public void findById() {
        String id = "1465754182586097666";
        String expectedText = "@paulwguevarra guess what day it is? It's #tacotuesday";
        Tweet tweet = twitterDao.findById(id);

        assertEquals(expectedText, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(longitude, tweet.getCoordinates().getCoordinates()[0], 1e-8);
        assertEquals(latitude, tweet.getCoordinates().getCoordinates()[1], 1e-8);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    }

    @Test
    public void deleteById() {
        Tweet postTweet = twitterDao.create(TweetUtil.buildTweet(text+" COPY", longitude, latitude));
        Tweet tweet = twitterDao.deleteById(postTweet.getId_str());

        assertEquals(text+" COPY", tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(longitude, tweet.getCoordinates().getCoordinates()[0],1e-8);
        assertEquals(latitude, tweet.getCoordinates().getCoordinates()[1], 1e-8);
        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    }
}