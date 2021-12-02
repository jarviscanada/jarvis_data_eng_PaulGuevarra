package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import oauth.signpost.exception.OAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static ca.jrvs.apps.twitter.util.TweetUtil.buildTweet;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller{

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service){this.service = service;}


    @Override
    public Tweet postTweet(String[] args) throws OAuthException, IOException {
        if(args.length!=3){
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        String tweet_txt = args[1];
        String coord = args[2];
        String [] coordArray = coord.split(COORD_SEP);
        if(coordArray.length!= 2 || StringUtils.isEmpty(tweet_txt)) {
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        float lat = -500;
        float lon = -500;

        try{
            lat = Float.parseFloat(coordArray[0]);
            lon = Float.parseFloat(coordArray[1]);

        }catch(Exception e)
        {
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        Tweet postTweet = buildTweet(tweet_txt,lon, lat);
        return service.postTweet(postTweet);
    }

    @Override
    public Tweet showTweet(String[] args) {
        if (args.length>3){
            throw new IllegalArgumentException("Invalid/Missing arguments.\nUSAGE: TwitterCLIApp show tweet_id \"id1,id2,...\"");
        }
        String id = args[1];
        if(args.length==2){
            return service.showTweet(id, null);
        }
        List<String> listOfFields =  Arrays.stream(args[2].split(","))
                .map(s -> s.toLowerCase())
                .map(s -> s.replaceAll("\\s+",""))
                .collect(Collectors.toList());;
                String [] fields = listOfFields.toArray(new String[listOfFields.size()]);
                return service.showTweet(id, fields);

    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if(args.length!=2)
        {
            throw new IllegalArgumentException("Invalid/Missing arguments.\nUSAGE: TwitterCLIApp delete \"id1,id2,...\"");
        }
        String [] ids = args[1].split(",");
        return service.deleteTweets(ids);
    }
}
