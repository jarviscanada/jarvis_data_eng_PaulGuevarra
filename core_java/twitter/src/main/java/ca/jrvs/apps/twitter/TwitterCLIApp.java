package ca.jrvs.apps.twitter;


import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import oauth.signpost.exception.OAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class TwitterCLIApp {
private Controller controller;

@Autowired
    public TwitterCLIApp(Controller controller){
    this.controller = controller;
}

    public static void main(String[]args) throws OAuthException, IOException {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
    TwitterCLIApp app = new TwitterCLIApp(new TwitterController(new TwitterService(new TwitterDao(httpHelper))));
    app.run(args);
}

    public void run(String[] args) throws OAuthException, IOException {

    if(args.length==0)
    {
        throw new IllegalArgumentException("Invalid/Missing Arguments.\nUSAGE: TwitterApp post|show|delete [options]");
    }
    String input = args[0].toLowerCase();

    switch(input){
            case "post":
                result(controller.postTweet(args));
                break;
            case "show":
                result(controller.showTweet(args));
                break;
            case "delete":
                List<Tweet> tweets = controller.deleteTweet(args);
                for(Tweet tweet: tweets)
                {
                    result(tweet);
                }
                break;

    }
    }

    public void result(Tweet tweet){
    try{
        System.out.println(JsonUtil.toJson(tweet, true, false));

    }
    catch (JsonProcessingException e){
        throw new RuntimeException("Unable to convert Tweet to String", e);
    }
    }
}
