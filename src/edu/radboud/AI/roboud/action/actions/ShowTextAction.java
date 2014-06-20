package edu.radboud.ai.roboud.action.actions;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class ShowTextAction extends AbstractAction {

    private String text;

    public ShowTextAction(RoboudController controller, String text) {
        super(controller);
        this.text = text;
    }

    public ShowTextAction(RoboudController controller, String[] texts) {
        this(controller, SpeechRepertoire.randomChoice(texts));
    }

    @Override
    public void doActions() {
        controller.showText(text);
        setChanged();
        notifyObservers();
    }

    public String getText() {
        return text;
    }
}
