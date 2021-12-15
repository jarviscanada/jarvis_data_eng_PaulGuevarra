package ca.jrvs.apps.twitter.spring;


import ca.jrvs.apps.twitter.TwitterCLIApp;
import oauth.signpost.exception.OAuthException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ComponentScan("ca.jrvs.apps.twitter")
public class TwitterCLIComponentScan{

    public static void main(String[]args) throws OAuthException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                TwitterCLIComponentScan.class);
        TwitterCLIApp app = context.getBean(TwitterCLIApp.class);
        app.run(args);
    }

}