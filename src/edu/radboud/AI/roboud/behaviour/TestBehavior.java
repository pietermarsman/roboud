package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.PostTweetAction;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public TestBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);

        PostTweetAction postTweetAction = null;
        try {
            postTweetAction = new PostTweetAction(controller, "This is my test tweet");
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        blocks.add(postTweetAction);
    }
}
