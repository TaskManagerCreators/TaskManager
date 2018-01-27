package com.reaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Sleep.class)
public class Sleep implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    public void setMillis(long millis) {
        this.value = millis;
    }

    private long value;

    public Sleep(){}

    public Sleep(long millis) {
        System.out.println(millis);
        this.value = millis;
    }

    @Override
    public void perform() {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ReactionType getType() {
        return ReactionType.SLEEP;
    }
}
