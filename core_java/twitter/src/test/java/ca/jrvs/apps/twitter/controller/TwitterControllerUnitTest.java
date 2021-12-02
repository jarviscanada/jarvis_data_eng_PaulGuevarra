package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import oauth.signpost.exception.OAuthException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
    @Mock
    Service service;

    @InjectMocks
    TwitterController controller;

    String hashTag = "#tacotuesday";
    String text = "@paulwguevarra guess what day it is? It really really is "+ hashTag;
    float lon = 10f;
    float lat = 10f;

    @Before
    public void setUp() throws Exception {
    controller = new TwitterController(service);
    }

    @Test
    public void postTweet() throws IOException, OAuthException {
        Tweet tweet = new Tweet();

        Coordinates coordinates = new Coordinates();
        float[] coords = {lon, lat};
        coordinates.setCoordinates(coords);
        tweet.setCoordinates(coordinates);
        tweet.setText(text);

        String [] missingArgs = {"post", text};
        String [] invalidLatArgs = {"post", text, "ABC:10"};
        String [] missingLatArgs = {"post", text, "10"};

        when(service.postTweet(any())).thenReturn(tweet);

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

        String newText = text + System.currentTimeMillis();
        String[] validArgs = {"post", "@paulwguevarra guess what day it is? It really really is #tacotuesday", "10:10"};

        float lon = 10f;
        float lat = 10f;
        Tweet validTweet = controller.postTweet(validArgs);

        assertEquals(validArgs[1], validTweet.getText());
        assertEquals(lon, validTweet.getCoordinates().getCoordinates()[0],1e-8);
        assertEquals(lat, validTweet.getCoordinates().getCoordinates()[1], 1e-8);


    }

    @Test
    public void showTweet() {
        Tweet tweet = new Tweet();

        Coordinates coordinates = new Coordinates();
        float[] coords = {lon, lat};
        coordinates.setCoordinates(coords);
        tweet.setCoordinates(coordinates);
        tweet.setText(text);
        tweet.setId_str("1465764728005967875");
        String [] invalidArgs = {"show","1465764728005967875" , "someText", "someArgument"};

        when(service.showTweet(any(), any())).thenReturn(tweet);

        try{
            controller.showTweet(invalidArgs);
            fail();
        }catch(IllegalArgumentException e){
            assertEquals("Invalid/Missing arguments.\nUSAGE: TwitterCLIApp show tweet_id \"id1,id2,...\"",e.getMessage());
        }

        String [] validArgs = {"show", "1465764728005967875", "id,text,coordinates"};
        Tweet validTweet = controller.showTweet(validArgs);

        String validText = "@paulwguevarra guess what day it is? It really really is #tacotuesday";
        float longi = 10f;
        float latid = 10f;

        assertEquals(validText, validTweet.getText());
        assertEquals(longi, validTweet.getCoordinates().getCoordinates()[0], 1e-8);
        assertEquals(latid, validTweet.getCoordinates().getCoordinates()[1], 1e-8);


    }

    @Test
    public void deleteTweet() throws OAuthException, IOException {
        Tweet tweet = new Tweet();

        Coordinates coordinates = new Coordinates();
        float[] coords = {lon, lat};
        coordinates.setCoordinates(coords);
        tweet.setCoordinates(coordinates);
        tweet.setText(text);
        tweet.setId_str("1465764728005967875");

        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);

        String [] missingArgs = {"delete"};

        when(service.deleteTweets(any())).thenReturn(tweets);

        try {
            controller.deleteTweet(missingArgs);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Invalid/Missing arguments.\nUSAGE: TwitterCLIApp delete \"id1,id2,...\"", e.getMessage());
        }




        String [] validArgs = {"delete", "1446179603752669195"};
        List<Tweet> deleteTweets = controller.deleteTweet(validArgs);


        for(Tweet validTweet: deleteTweets)
        {
            assertEquals("@paulwguevarra guess what day it is? It really really is "+ hashTag,deleteTweets.get(0).getText());
            assertEquals(lon, validTweet.getCoordinates().getCoordinates()[0], 1e-8);
            assertEquals(lat, validTweet.getCoordinates().getCoordinates()[1], 1e-8);

        }


    }
}
