package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.omg.SendingContext.RunTime;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {
    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    String tweetJsonStr = "{\n"
            + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
            + "   \"id\":1097607853932564480,\n"
            + "   \"id_str\":\"1097607853932564480\",\n"
            + "   \"text\":\"test with loc223\",\n"
            + "   \"entities\":{\n"
            + "      \"hashtags\":[],"
            + "      \"user_mentions\":[]"
            + "   },\n"
            + "   \"coordinates\": {"
            + "           \"coordinates\" : [ 10.1, 10.1 ],\n"
            + "           \"type\" : \"Point\"\n"
            + "   },\n"
            + "   \"retweet_count\":0,\n"
            + "   \"favorite_count\":0,\n"
            + "   \"favorited\":false,\n"
            + "   \"retweeted\":false\n"
            + "}";
    @Test
    public void showTweet() throws Exception {
        String hashTag = "tacotuesday";
        String text = "@paulwguevarra guess what day it is? It really really is "+ hashTag + " " + System.currentTimeMillis();
        float lat = -10f;
        float lon = 10f;

        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.create((TweetUtil.buildTweet(text,lon, lat)));
            fail();
        }catch(RuntimeException e){
            assertTrue(true);
        }

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr,Tweet.class);

         doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.create(TweetUtil.buildTweet(text, lon,lat));
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void postTweet() throws Exception
    {
        String hashTag = "tacotuesday";
        String text = "@paulwguevarra guess what day it is? It really really is "+ hashTag + " " + System.currentTimeMillis();
        float lat = -10f;
        float lon = 10f;

        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.create(TweetUtil.buildTweet(text, lon, lat));
        }
        catch(RuntimeException e){
            assertTrue(true);
        }

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);

        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.create(TweetUtil.buildTweet(text, lon, lat));
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }


    @Test
    public void deleteTweet() throws Exception
    {
        String id = "1465773211329646596";
        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.findById(id);
        }
        catch(RuntimeException e){
            assertTrue(true);
        }

        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);

        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet = spyDao.findById(id);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }


}
