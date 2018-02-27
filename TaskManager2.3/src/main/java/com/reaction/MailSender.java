package com.reaction;

import com.InterAction;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mailservice.EmailService;
import com.taskmanagerlogic.Task;

@JsonDeserialize(as = MailSender.class)
public class MailSender implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    private EmailService emailService;

    public MailSender() {
    }

    private String msg;

    @Override
    public void perform(Task task) {
        emailService = (EmailService) InterAction.applicationContext.getBean("emailService");
            emailService.sendSimpleMessage(task.getEmail()/*task.getUser().getEmail().trim()*/, task.getDescribe() , task.getReaction().getValue().toString());

    }

    @Override
    public Object getValue() {
        return msg;
    }

    @Override
    public void setValue(Object data) {
        msg = (String) data;
    }

    @Override
    public ReactionType getType() {
        return ReactionType.SENDER;
    }
}
