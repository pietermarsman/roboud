package edu.radboud.ai.roboud.module.util;

/**
 * Created by Pieter Marsman on 3-7-2014.
 */
public class RoboudUser {

    private String name;
    public Integer age;
    public Boolean isMan;

    public RoboudUser(String name) {
        this.name = name;
        age = null;
        isMan = null;
    }

    public String getName() {
        return name;
    }
}
