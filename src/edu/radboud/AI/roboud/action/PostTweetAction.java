package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observer;


/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class PostTweetAction extends AbstractAction {
    String text;
    public PostTweetAction(RoboudController controller1, String text) throws TwitterException, IOException {
        super(controller1);
        if(text == null)
            this.text = "This is a default text to post on Twitter";
        else
            this.text = text;
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            try {
                // get request token.
                // this will throw IllegalStateException if access token is already available
                RequestToken requestToken = twitter.getOAuthRequestToken();
                controller.showText("Got request token.");
                controller.showText("Request token: " + requestToken.getToken());
                controller.showText("Request token secret: " + requestToken.getTokenSecret());
                AccessToken accessToken = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken) {
                    controller.showText("Open the following URL and grant access to your account:");
                    controller.showText(requestToken.getAuthorizationURL());
                    controller.showText("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = br.readLine();
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            System.out.println("Unable to get the access token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                controller.showText("Got access token.");
                controller.showText("Access token: " + accessToken.getToken());
                controller.showText("Access token secret: " + accessToken.getTokenSecret());
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    controller.showText("OAuth consumer key/secret is not set.");
                    System.exit(-1);
                }
            }
            Status status = twitter.updateStatus(text);
            controller.showText("Successfully updated the status to [" + status.getText() + "].");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            controller.showText("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            controller.showText("Failed to read the system input.");
            System.exit(-1);
        }
    }
}
