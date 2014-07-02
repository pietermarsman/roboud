package edu.radboud.ai.roboud.action;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.*;
import edu.radboud.ai.roboud.action.pools.*;
import edu.radboud.ai.roboud.action.util.*;

import java.util.List;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class ActionFactory {
    private final static String TAG = "ActionFactory";
    private static ActionFactory instance = null;
    private RoboudController controller;


    //all the pools
    private ChoiceActionPool choiceActionPool;
    private CombineActionPool combineActionPool;
    private ConfirmationActionPool confirmationActionPool;
    private ExpressEmotionActionPool expressEmotionActionPool;
    private LedActionPool ledActionPool;
    private ListenActionPool listenActionPool;
    private HeadActionPool headActionPool;
    private MotorActionPool motorActionPool;
    private ReadTextActionPool readTextActionPool;
    private ShowTextActionPool showTextActionPool;
    private SleepActionPool sleepActionPool;
    private SpeakActionPool speakActionPool;


    private ActionFactory(RoboudController controller) {
        this.controller = controller;
    }

    public synchronized static ActionFactory getInstance(RoboudController controller) {
        if (instance == null)
            instance = new ActionFactory(controller);
        return instance;
    }

    public ChoiceAction getChoiceAction(List<String> options) {
        if (choiceActionPool == null) {
            choiceActionPool = ChoiceActionPool.getInstance(controller);
        }
        return choiceActionPool.acquire(options);
    }

    public CombineAction getCombineAction(AbstractAction a, AbstractAction b) {
        if (combineActionPool == null) {
            combineActionPool = CombineActionPool.getInstance(controller);
        }
        return combineActionPool.acquire(a, b);
    }

    public ConfirmationAction getConfirmationAction(String question) {
        if (confirmationActionPool == null) {
            confirmationActionPool = ConfirmationActionPool.getInstance(controller);
        }
        return confirmationActionPool.acquire(question);
    }

    public ExpressEmotionAction getExpressEmotionAction(FaceExpression expression) {
        if (expressEmotionActionPool == null) {
            expressEmotionActionPool = ExpressEmotionActionPool.getInstance(controller);
        }
        return expressEmotionActionPool.acquire(expression);
    }

    public LedAction getLedAction(LedColor color) {
        if (ledActionPool == null) {
            ledActionPool = LedActionPool.getInstance(controller);
        }
        return ledActionPool.acquire(color);
    }

    public ListenAction getListenAction() {
        if (listenActionPool == null) {
            listenActionPool = ListenActionPool.getInstance(controller);
        }
        return listenActionPool.acquire();
    }

    public HeadAction getHeadAction(HeadDirection headDirection) {
        if (headActionPool == null) {
            headActionPool = HeadActionPool.getInstance(controller);
        }
        return headActionPool.acquire(headDirection);
    }

    public MotorAction getMotorAction(RobotDirection dir, RobotSpeed speed) {
        if (motorActionPool == null) {
            motorActionPool = MotorActionPool.getInstance(controller);
        }
        return motorActionPool.acquire(dir, speed);
    }

    public ReadTextAction getReadTextAction(String question) {
        if (readTextActionPool == null) {
            readTextActionPool = ReadTextActionPool.getInstance(controller);
        }
        return readTextActionPool.acquire(question);
    }

    public ReadTextAction getReadTextAction(String[] questions) {
        if (readTextActionPool == null) {
            readTextActionPool = ReadTextActionPool.getInstance(controller);
        }
        return readTextActionPool.acquire(questions);
    }

    public ShowTextAction getShowTextAction(String text) {
        if (showTextActionPool == null) {
            showTextActionPool = ShowTextActionPool.getInstance(controller);
        }
        return showTextActionPool.acquire(text);
    }

    public ShowTextAction getShowTextAction(String[] texts) {
        if (showTextActionPool == null) {
            showTextActionPool = ShowTextActionPool.getInstance(controller);
        }
        return showTextActionPool.acquire(texts);
    }

    public SleepAction getSleepAction(long time) {
        if (sleepActionPool == null) {
            sleepActionPool = SleepActionPool.getInstance(controller);
        }
        return sleepActionPool.acquire(time);
    }

    public SpeakAction getSpeakAction(String text) {
        if (speakActionPool == null) {
            speakActionPool = SpeakActionPool.getInstance(controller);
        }
        return speakActionPool.acquire(text);
    }

    public SpeakAction getSpeakAction(String[] texts) {
        if (speakActionPool == null) {
            speakActionPool = SpeakActionPool.getInstance(controller);
        }
        return speakActionPool.acquire(texts);
    }

    public void releaseAction(AbstractAction action) {
        try {
            if (action instanceof ChoiceAction) {
                choiceActionPool.release((ChoiceAction) action);
            } else if (action instanceof CombineAction) {
                combineActionPool.release((CombineAction) action);
            } else if (action instanceof ConfirmationAction) {
                confirmationActionPool.release((ConfirmationAction) action);
            } else if (action instanceof ExpressEmotionAction) {
                expressEmotionActionPool.release((ExpressEmotionAction) action);
            } else if (action instanceof LedAction) {
                ledActionPool.release((LedAction) action);
            } else if (action instanceof HeadAction) {
                headActionPool.release((HeadAction) action);
            } else if (action instanceof MotorAction) {
                motorActionPool.release((MotorAction) action);
            } else if (action instanceof ReadTextAction) {
                readTextActionPool.release((ReadTextAction) action);
            } else if (action instanceof ShowTextAction) {
                showTextActionPool.release((ShowTextAction) action);
            } else if (action instanceof SleepAction) {
                sleepActionPool.release((SleepAction) action);
            } else if (action instanceof SpeakAction) {
                speakActionPool.release((SpeakAction) action);
            } else {
                if (action == null) {
                    Log.w(TAG, "cannot release a null action");
                }
                Log.e(TAG, "trying to release unknown action " + action.toString());
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "An ActionPool cannot be null");
        }
    }

}
