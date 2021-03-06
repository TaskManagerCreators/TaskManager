package com.reaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.taskmanagerlogic.Task;

@JsonDeserialize(as = Sleep.class)
public class Sleep implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    public void setValue(Object millis) {
        this.millis = Long.valueOf(String.valueOf(millis));
    }

    private long millis;

    public Sleep() {
    }

    public Sleep(long millis) {
        this.millis = millis;
    }

    @Override
    public void perform(Task task) {
        try {
            Thread.sleep((Long) task.getReaction().getValue());
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
