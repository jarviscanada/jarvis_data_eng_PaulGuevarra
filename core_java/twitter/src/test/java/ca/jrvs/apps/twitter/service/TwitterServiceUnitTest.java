package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import oauth.signpost.exception.OAuthException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    String hashTag = "#tacotuesday";
    float lon = 10f;
    float lat = -10f;
    String text ="test with loc223";
    String tweetJsonStr = "{\n"
            + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
            + "   \"id\":1097607853932564480,\n"
            + "   \"id_str\":\"1097607853932564480\",\n"
            + "   \"text\":\"test with loc223\",\n"
            + "   \"entities\":{\n"
            + "      \"hashtags\":[],"
            + "      \"user_mentions\":[]"
            + "   },\n"
            + "   \"coordinates\":null,"
            + "   \"retweet_count\":0,\n"
            + "   \"favorite_count\":0,\n"
            + "   \"favorited\":false,\n"
            + "   \"retweeted\":false\n"
            + "}";

    @Before
    public void setUp() throws Exception{
        service = new TwitterService(dao);
    }

    @Test
    public void postTweet() throws OAuthException, IOException {

        String invalidText = "I hate the way you talk to me\n" +
                "And the way you cut your hair\n" +
                "I hate the way you drive my car\n" +
                "I hate it when you stare\n" +
                "\n" +
                "I hate your big dumb combat boots\n" +
                "And the way you read my mind\n" +
                "I hate you so much that it makes me sick\n" +
                "It even makes me rhyme\n" +
                "\n" +
                "I hate the way you're always right\n" +
                "I hate it when you lie\n" +
                "I hate it when you make me laugh\n" +
                "Even worse when you make me cry\n" +
                "\n" +
                "I hate the way you're not around\n" +
                "And the fact that you didn't call\n" +
                "But mostly I hate the way I don't hate you\n" +
                "Not even close, not even a little bit, not even at all.";
        float invalidLon = 500f;
        float invalidLat = -500f;

        Tweet invalidTextTweet = TweetUtil.buildTweet(invalidText,lon, lat);
        Tweet invalidLonTweet = TweetUtil.buildTweet(text, invalidLon,lat);
        Tweet invalidLatTweet = TweetUtil.buildTweet(text,lon,invalidLat);

        try{
            service.postTweet(invalidTextTweet);
            fail();
        }catch(IllegalArgumentException | OAuthException | IOException e){
            assertEquals("Tweet must be a max of 280 characters or less.", e.getMessage());
        }

        try{
            service.postTweet(invalidLonTweet);
            fail();
        }catch(IllegalArgumentException | OAuthException | IOException e){
            assertEquals("Coordinates are out of range.", e.getMessage());
        }

        try{
            service.postTweet(invalidLatTweet);
            fail();
        }catch(IllegalArgumentException | OAuthException | IOException e){
            assertEquals("Coordinates are out of range.", e.getMessage());
        }

        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(dao).create(any());
        Tweet postTweet = TweetUtil.buildTweet("test with loc223",lon,lat);
        Tweet validTweet = service.postTweet(postTweet);

        assertEquals("test with loc223", validTweet.getText());
        assertEquals(null, validTweet.getCoordinates());
    }

    @Test
    public void showTweet() throws IOException {
        String id = "1465757997402107906";
        String invalidId = "ABC123";
        String[] validFields = {
                "created_at",
                "id",
                "id_str",
                "text",
                "entities",
                "coordinates",
                "retweet_count",
                "favorite_count",
                "favorited",
                "retweeted"
        };
        String[] invalidFields = {
                "Create",
                "Identification",
                "IdentificationString",
                "Ent"
        };

        try{
            service.showTweet(invalidId, validFields);
            fail();
        }catch (IllegalArgumentException e){

            assertEquals("ID must be all numerical characters.",e.getMessage());
        }

        try{
            service.showTweet(id, invalidFields);
            fail();
        }catch (IllegalArgumentException e){
            //ID must be all numerical characters.
            assertEquals("Invalid or Missing Field(s): Create Identification IdentificationString Ent ",e.getMessage());
        }
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(dao).findById(any());
        Tweet tweet = service.showTweet(id, validFields);

        assertEquals("test with loc223", tweet.getText());
        assertEquals(null,tweet.getCoordinates());
    }

    @Test
    public void deleteTweets() throws OAuthException, IOException {
        String [] invalidIds = {"ABC", "DEF", "GHI"};
        String text = "test with loc223";
        String text2 = "@tos test5 ";
        try{
            service.deleteTweets(invalidIds);
        }catch (IllegalArgumentException e){
            assertEquals("ID must be all numerical characters.", e.getMessage());
        }

        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(dao).deleteById(any());

        String [] validIds = {"1445454948389429253", "1445455964530823179"};
        List<Tweet> deletedTweets = service.deleteTweets(validIds);

        for(Tweet tweet : deletedTweets)
        {
            assertTrue(text.equals(tweet.getText()) || text2.equals(tweet.getText()));
            assertEquals(null, tweet.getCoordinates());
        }
    }
}
