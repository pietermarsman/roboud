package edu.radboud.ai.roboud.module.util;

/**
 * Created by Pieter Marsman on 3-7-2014.
 */
public class RoboudUser {

    public Integer age;
    public Boolean isMan;
    private String name;

    public RoboudUser(String name) {
        this.name = name;
        age = null;
        isMan = null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String man;
        if (isMan != null)
            man = isMan ? " is man" : " is female";
        else
            man = "";
        String ageString;
        if (age != null)
            ageString = " (" + String.valueOf(age) + ")";
        else
            ageString = "";
        return "User " + name + ageString + man;
    }
}
