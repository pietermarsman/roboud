package edu.radboud.ai.roboud.task;

import android.os.StrictMode;
import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class PostTweetTask extends AbstractTask {
    String text;
    public static final String TAG = "PostTweetTask";
    Twitter twitter;

    public PostTweetTask(RoboudController controller, String text) throws TwitterException, IOException {
        super(controller);
        if (text == null)
            this.text = "This is a default text to post on Twitter";
        else
            this.text = text;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initialize();
        postTweet();
        Log.i(TAG,"Done posting tweet, now setchanges and notifyobservers");
        //Mike: This looks strange here:
        setChanged();
        notifyObservers();
    }

    public void initialize()
    {
        Log.i(TAG,"initializing twitter settings");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        // the secret key should not be human-readable. Later, we could build in some security measures.
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("bK5U6eeRXZ4jUacXPEl3olhRO")
                .setOAuthConsumerSecret("nLc2YC6R9aJxwpSBC3lGBAFQjIIKkMzdRMDTKRdzzyxV6MK5bW")
                .setOAuthAccessToken("2513363683-tavASPWcLfsuC3ULNcSgwWulfxEdRJOqNonWAGG")
                .setOAuthAccessTokenSecret("W9Ik7JX0XvMgNc6SQXaNZZTzeCfYDVQoJKWvhBXmVkY3D");
        Log.i(TAG,"Done with keys");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public void postTweet() {
        Log.i(TAG,"in postTweet()");
        try {
//            twitter = new TwitterFactory().getInstance();
            try {
                Log.i(TAG,"try it");
                // get request token.
                // this will throw IllegalStateException if access token is already available
                RequestToken requestToken = twitter.getOAuthRequestToken();
                Log.i(TAG, "Got request token.");
                Log.i(TAG, "Request token: " + requestToken.getToken());
                Log.i(TAG,"Request token secret: " + requestToken.getTokenSecret());
                AccessToken accessToken = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken) {
                    Log.i(TAG,"Open the following URL and grant access to your account:");
                    Log.i(TAG, requestToken.getAuthorizationURL());
                    Log.i(TAG, "Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = br.readLine();
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            Log.i(TAG, "Unable to get the access token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                Log.i(TAG, "Got access token.");
                Log.i(TAG,"Access token: " + accessToken.getToken());
                Log.i(TAG, "Access token secret: " + accessToken.getTokenSecret());
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    Log.i(TAG, "OAuth consumer key/secret is not set.");
                    System.exit(-1);
                }
            }
            Status status = twitter.updateStatus(text);
            Log.i(TAG, "Successfully updated the status to [" + status.getText() + "].");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            Log.i(TAG, "Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            Log.i(TAG, "Failed to read the system input.");
            System.exit(-1);
        }
    }

    @Override
    public void releaseActions() {

    }

    @Override
    protected Object processActionInformation() {
        return null;
    }

    @Override
    protected void processTaskInformation(Object information) {

    }
}
