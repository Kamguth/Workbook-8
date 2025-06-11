package com.pluralsight;

public class Actor {
    private int actorId;
    private String firstName;
    private String lastName;

    public Actor(int actorId, String firstName, String lastName) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return actorId + ": " + firstName + " " + lastName;
    }
}
