package com.reaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Sleep.class)
public class Sleep implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    public void setMillis(long millis) {
        this.millis = millis;
    }

    private long millis;

    public Sleep(){}

    public Sleep(long millis) {
        System.out.println(millis);
        this.millis = millis;
    }

    @Override
    public void perform() {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getValue() {
        return millis;
    }

    @Override
    public ReactionType getType() {
        return ReactionType.SLEEP;
    }
}
