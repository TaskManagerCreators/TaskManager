package com.reaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = MailSender.class)
public class MailSender implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    public MailSender() {
    }

    @Override
    public void perform() {
        throw new UnsupportedOperationException("MailSender not...");
    }

    @Override
    public Object getValue() {
        return "contacts";
    }

    @Override
    public ReactionType getType() {
        return ReactionType.SENDER;
    }
}
