package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCLIApp;
import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import oauth.signpost.exception.OAuthException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

//@Configuration
public class TwitterCLIBean {
    public static void main(String[]args) throws OAuthException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(TwitterCLIBean.class);
        TwitterCLIApp app = context.getBean(TwitterCLIApp.class);
        app.run(args);
    }

    @Bean
    public TwitterCLIApp twitterCLIApp(Controller controller){
        return new TwitterCLIApp(controller);
    }

    @Bean
    public Controller twitterController(Service service){
        return new TwitterController(service);
    }

    @Bean
    public Service twitterService(CrdDao dao){
        return new TwitterService(dao);
    }

    @Bean
    public CrdDao twitterDao(HttpHelper helper){
        return new TwitterDao(helper);
    }

    @Bean
    HttpHelper helper(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        return new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    }
}
