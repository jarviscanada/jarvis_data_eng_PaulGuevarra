package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import oauth.signpost.exception.OAuthException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerTest {

    public TwitterController controller;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
        controller = new TwitterController(new TwitterService(new TwitterDao(httpHelper)));
    }

    @Test
    public void postTweet() throws OAuthException, IOException {


        String [] missingArgs = {"post", "Taco"};
        String [] invalidLatArgs = {"post", "Taco", "ABC:10"};
        String [] missingLatArgs = {"post", "Taco", "10"};

        try{
            controller.postTweet(missingArgs);
            fail();
        }catch(IllegalArgumentException | OAuthException | IOException e)
        {
            assertEquals("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"", e.getMessage());
        }
        try{
            controller.postTweet(invalidLatArgs);
            fail();

        }catch(IllegalArgumentException | OAuthException | IOException e)
        {
            assertEquals("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"",e.getMessage());
        }
        try{
            controller.postTweet(missingLatArgs);
            fail();
        }catch (IllegalArgumentException | OAuthException | IOException e){
            assertEquals("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"",e.getMessage());
        }
        String hashTag = "#tacotuesday";
        String text = "@paulwguevarra guess what day it is? It really really is "+ hashTag + " " + System.currentTimeMillis();

        String[] validArgs = {"post", text, "10:10"};

        float lon = 10f;
        float lat = 10f;
        Tweet tweet = controller.postTweet(validArgs);

        assertEquals(validArgs[1], tweet.getText());
        assertEquals(lon, tweet.getCoordinates().getCoordinates()[0],1e-8);
        assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 1e-8);
    }

    @Test
    public void showTweet() {
        String [] invalidArgs = {"show","1465764728005967875" , "someText", "someArgument"};

        try{
            controller.showTweet(invalidArgs);
            fail();
        }catch(IllegalArgumentException e){
            assertEquals("Invalid/Missing arguments.\nUSAGE: TwitterCLIApp show tweet_id \"id1,id2,...\"",e.getMessage());
        }

        String [] validArgs = {"show", "1465764728005967875", "id,text,coordinates"};
        Tweet tweet = controller.showTweet(validArgs);

       String text = "@paulwguevarra guess what day it is? It really really is #tacotuesday 1638300517519";
       float lon = 10f;
       float lat = -10f;

       assertEquals(text, tweet.getText());
       assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 1e-8);
       assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 1e-8);


    }

    @Test
    public void deleteTweet() throws OAuthException, IOException {
        String [] missingArgs= {"delete"};
        try {
            controller.deleteTweet(missingArgs);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Invalid/Missing arguments.\nUSAGE: TwitterCLIApp delete \"id1,id2,...\"", e.getMessage());
        }
        String hashTag = "tacotuesday";
        String text = "@paulwguevarra guess what day it is? It really really is "+ hashTag + " " + System.currentTimeMillis();
        float lat = 10f;
        float lon = 10f;

        String[] tweet1Args = {"post", text, "10:10"};
        String[] tweet2Args = {"post", text + "COPY", "10:10"};
        Tweet tweet1 = controller.postTweet(tweet1Args);
        Tweet tweet2 = controller.postTweet(tweet2Args);

        String[] ids = {tweet1.getId_str(), tweet2.getId_str()};

        List<Tweet> deleteTweets = controller.deleteTweet(ids);

        for(Tweet tweet: deleteTweets)
        {
            assertTrue(((text).equals(tweet.getText())) || (text+"COPY").equals(tweet.getText()));
            assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 1e-8);
            assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 1e-8);

        }

    }
}