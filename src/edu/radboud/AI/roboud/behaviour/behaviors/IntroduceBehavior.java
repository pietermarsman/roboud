package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.util.LedColor;
import edu.radboud.ai.roboud.behaviour.util.SpeechRepertoire;
import edu.radboud.ai.roboud.module.util.RoboudUser;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class IntroduceBehavior extends AbstractBehavior {

    public static final String TAG = "IntroduceBehavior";

    private AbstractAction askName, askAge, askSex;
    private String name;
    private RoboudModel model;

    public IntroduceBehavior(ActionFactory actionFactory, Scenario scenario, RoboudModel model) {
        super(actionFactory, scenario);
        this.model = model;

        //These need to be consistent in both text and speech
        String introduceMySelf = SpeechRepertoire.randomChoice(SpeechRepertoire.textIntroduceMyself);
        String explanation = "I'm going to ask you some questions in order to get to know you. I will not share this information without your explicit confirmation.";
        String yourName = SpeechRepertoire.randomChoice(SpeechRepertoire.questionName);
        String yourAge = SpeechRepertoire.randomChoice(SpeechRepertoire.questionAge);
        String yourSex = SpeechRepertoire.randomChoice(SpeechRepertoire.questionSex);

        List<String> sex = new ArrayList<String>();
        sex.add("Male");
        sex.add("Female");
        sex.add("Robot");
        sex.add("Other");
        String enough = SpeechRepertoire.randomChoice(SpeechRepertoire.knowEnough);

        actions.add(actionFactory.getLedAction(LedColor.GREEN));
        //Greetings and introducing myself
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(introduceMySelf));
            actions.add(actionFactory.getSpeakAction(introduceMySelf));
            actions.add(actionFactory.getShowTextAction(explanation));
            actions.add(actionFactory.getSpeakAction(explanation));

        } else {
            actions.add(actionFactory.getShowTextAction(introduceMySelf));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
            actions.add(actionFactory.getShowTextAction(explanation));
            actions.add(actionFactory.getSleepAction(5000)); //this should be in ShowTextAction
        }

        //Ask name
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(yourName));
        }
        askName = actionFactory.getReadTextAction(yourName);
        actions.add(askName);

        //Ask age
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(yourAge));
        }
        askAge = actionFactory.getReadTextAction(yourAge);
        actions.add(askAge);

        //Ask sex
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(yourSex));
        }
        askSex = actionFactory.getChoiceAction(sex, yourSex);
        actions.add(askSex);

        //End introduce
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(enough));
            actions.add(actionFactory.getSpeakAction(enough));
        } else {
            actions.add(actionFactory.getShowTextAction(enough));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }
    }

    @Override
    protected Object processInformation(AbstractAction currentAction) {
        if (currentAction == askName) {
            name = (String) currentAction.getInformation();
            RoboudUser user = new RoboudUser(name);
            model.addUser(user);
        } else if (currentAction == askAge) {
            RoboudUser user = model.getUser(name);
            user.age = Integer.getInteger((String) currentAction.getInformation());
            model.addUser(user);
        } else if (currentAction == askSex) {
            RoboudUser user = model.getUser(name);
            String res = (String) currentAction.getInformation();
            res = res.toLowerCase();
            if (res.contains("m"))
                user.isMan = true;
            else
                user.isMan = false;
            model.addUser(user);
        }
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
