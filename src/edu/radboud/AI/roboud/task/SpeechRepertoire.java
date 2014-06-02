package edu.radboud.ai.roboud.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class SpeechRepertoire {

    private static final String name = "Roboud";

    public static final String[] questionName = {"What is your name?", "How shall I call you?"};
    public static final String[] questionAge = {"What is your age?"};
    public static final String[] questionSex = {"Are you male or female?"};

    public static final String[] textGreetingStart = {"Hi there!", "Hello you!", "Hi!", "Hello", "Good day"};
    public static final String[] textGreetingEnd = {"Bye", "Bye now", "Goodbye", "It was a pleasure seeing you"
            , "See you later"};
    public static final String[] textIntroduceMyself = {"I am " + name + " and I am your companion for the next few" +
            "days", "My name is " + name, "You can call me " + name};

    public static String randomChoice(String[] list) {
        double rand = new Random().nextDouble() * list.length;
        for (int i = 0; i < list.length; i++)
            if (rand < (i+1))
                return list[i];
        return null;
    }
}
