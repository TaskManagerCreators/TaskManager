package com.reaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.taskmanagerlogic.Task;

@JsonDeserialize(as = Output.class)
public class Output implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    @Override
    public void setValue(Object outgoing) {
        this.value = (String) outgoing;
    }
    
    private String value;

    public Output(String outgoing) {
        this.value =  outgoing;
    }

    public Output(){}

    @Override
    public void perform(Task task) {
        System.out.println(task.getReaction().getValue());
    }


    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ReactionType getType() {
        return ReactionType.OUTPUT;
    }

}
