package edu.radboud.ai.roboud.action.actions;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class ShowTextAction extends AbstractAction {

    private String text;

    @Deprecated
    public ShowTextAction(RoboudController controller, String text) {
        super(controller);
        this.text = text;
    }

    @Deprecated
    public ShowTextAction(RoboudController controller, String[] texts) {
        this(controller, SpeechRepertoire.randomChoice(texts));
    }

    public ShowTextAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions() {
        if (text == null) {
            throw new NullPointerException("text cannot be null");
        }
        controller.showText(text);
        setChanged();
        notifyObservers();
    }

    public String getText() {
        return text;
    }

    public void setText(String[] texts) {
        this.text = SpeechRepertoire.randomChoice(texts);
    }

    public void setText(String text) {
        this.text = text;
    }
}
