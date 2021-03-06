package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.*;

@Repository
public class TwitterDao implements CrdDao<Tweet, String>{

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;
    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper){this.httpHelper = httpHelper;}

    @Override
    public Tweet create(Tweet tweet) {
        URI uri;
        try {
            uri = getPostUri(tweet);
        }
        catch(URISyntaxException e){
            throw new IllegalArgumentException("Invalid tweet input",e);

        }
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response,HTTP_OK);
    }


    private URI getPostUri(Tweet tweet) throws URISyntaxException {
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        String longitude = String.valueOf(tweet.getCoordinates().getCoordinates()[0]);
        String latitude = String.valueOf(tweet.getCoordinates().getCoordinates()[1]);
        String text = tweet.getText();

        return new URI(API_BASE_URI+POST_PATH+QUERY_SYM+"status"+EQUAL
                +percentEscaper.escape(text)+AMPERSAND+"long"+EQUAL+longitude+AMPERSAND+
                "lat"+EQUAL+latitude);

    }

    @Override
    public Tweet findById(String s) {
        URI uri;
        try{
           uri =  getShowUri(s);
        }catch(URISyntaxException e)
        {
            throw new IllegalArgumentException("Invalid tweet input",e);
        }
        HttpResponse response = httpHelper.httpGet(uri);
        return parseResponseBody(response,HTTP_OK);
    }

    private URI getShowUri(String s) throws URISyntaxException{
        return new URI(API_BASE_URI+SHOW_PATH+QUERY_SYM+"id"+EQUAL+s);
    }

    @Override
    public Tweet deleteById(String s) {
        URI uri;
        try{
            uri = getDeleteUri(s);
        }catch(URISyntaxException e)
        {
            throw new IllegalArgumentException("Invalid tweet input",e);
        }
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response,HTTP_OK);
    }

    private URI getDeleteUri(String s) throws URISyntaxException{
        return new URI(API_BASE_URI+DELETE_PATH+"/"+s+".json");
    }
    Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
    Tweet tweet = null;
    int status = response.getStatusLine().getStatusCode();
    if(status != expectedStatusCode){
        try{
            System.out.println(EntityUtils.toString(response.getEntity()));
            }catch(IOException e){
            System.out.println("Response has no entity");

        }
        throw new RuntimeException("Unexpected HTTP status: "+ status);

    }

    if(response.getEntity()==null)
    {
    throw new RuntimeException("Empty response body");

    }
    String jsonStr;
    try{
        jsonStr = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
        throw new RuntimeException("Failed to convert entity to String",e);
    }
    try{
        tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
    }catch(IOException e){
        throw new RuntimeException("Unable to convert JSON str to Object", e);
        }

        return tweet;
    }


}

