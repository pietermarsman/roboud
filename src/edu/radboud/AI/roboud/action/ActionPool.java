package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;

/**
 * Created by mikel_000 on 20-6-2014.
 */
public class ActionPool {

    private static ActionPool instance = null;
    private RoboudController controller;

    private ActionPool(RoboudController controller){
        this.controller = controller;
    }

    public synchronized static ActionPool getInstance(RoboudController controller){
       if (instance == null){
           instance = new ActionPool(controller);
       }
       return instance;
    }

}
