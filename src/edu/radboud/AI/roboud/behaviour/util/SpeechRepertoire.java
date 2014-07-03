package edu.radboud.ai.roboud.behaviour.util;

import java.util.Random;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class SpeechRepertoire {

    public static final String[] questionName = {"What is your name?", "How shall I call you?"};
    public static final String[] questionAge = {"What is your age?", "How old are you?"};
    public static final String[] questionSex = {"Are you male or female?"};
    public static final String[] textGreetingStart = {"Hi there!", "Hello you!", "Hi!", "Hello", "Good day"};
    public static final String[] shakeHands = {"Let's shake hands", "Will you shake my hand?", "You can shake my hand?"};
    public static final String[] knowYou = {"Do we know each other?", "Do I know you from something?"};
    public static final String[] textGreetingEnd = {"Bye", "Bye now", "Goodbye", "It was a pleasure seeing you", "See you later"};
    public static final String[] questionAskUserReady = {"Are you ready for your next assignment?", "Your next job is ready, are you ready?"};
    public static final String[] textAskToCount = {"You are about to go to a conference. I am curious to know how many people are present at the conference, so I can post a tweet about it. So your assignment is, count the number of people at the conference."};
    public static final String[] questionUnderstand = {"I hope I was clear", "I hope you understand the assignment"};
    public static final String[] textEnding = {"That was it", "That's all"};
    public static final String[] knowEnough = {"Oké, now I know enough about you", "That was it for now", "Thank you for this information"};
    public static final String[] AskUserSucceeded = {"Did you complete your assignment?", "Did you count the number of people?"};
    public static final String[] AskNrOfPeople = {"How many people were present?", "What amount of people we at the conference?"};
    public static final String[] ConfirmNrOfPeople = {"Thank you for completing your assignment.", "Good job!"};
    public static final String[] ConfirmPostTweet = {"I've posted a tweet on my Twitter account."};
    private static final String name = "Roboud";
    public static final String[] textIntroduceMyself = {"I am " + name + " and I am your companion for the next few" +
            "days", "My name is " + name, "You can call me " + name};
    public static final String[] myTweet1 = {"The conference I attended was visited by ", "I've got some cool news. I visited a conference with ", "Today, I visited a conference with "};
    public static final String[] myTweet2 = {" people. It was a fascinating experience!", " people. It was breathtaking.", " people. It was rather stimulating and inspiring.", " people. It was a superb adventure."};
    public static final String[] drive = {"Can I drive sometimes?", "May I drive sometimes?", "Is it allowed for me to drive sometimes"};
    public static final String[] turn = {"Can I turn my body sometimes?", "May I turn my body sometimes?", "Is it allowed for me to turn my body sometimes?"};

    public static String randomChoice(String[] list) {
        double rand = new Random().nextDouble() * list.length;
        for (int i = 0; i < list.length; i++)
            if (rand < (i + 1))
                return list[i];
        return null;
    }
}
