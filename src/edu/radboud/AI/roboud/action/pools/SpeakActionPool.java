package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.SpeakAction;


/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class SpeakActionPool extends ActionPool<SpeakAction> {

    private static SpeakActionPool instance = null;

    private SpeakActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized SpeakActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new SpeakActionPool(controller);
        return instance;
    }

    public SpeakAction acquire(String text) {
        SpeakAction speakAction = acquire();
        speakAction.setText(text);
        return speakAction;
    }

    public SpeakAction acquire(String[] texts) {
        SpeakAction speakAction = acquire();
        speakAction.setText(texts);
        return speakAction;
    }

    @Override
    protected SpeakAction create() {
        return new SpeakAction(controller);
    }
}
