package com.reaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Output.class)
public class Output implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    public void setValue(String outgoing) {
        this.value = outgoing;
    }
    
    private String value;

    public Output(String outgoing) {
        this.value = outgoing;
    }

    public Output(){}

    @Override
    public void perform() {
        System.out.println(value);
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
