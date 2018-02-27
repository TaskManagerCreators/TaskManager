package com.reaction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.taskmanagerlogic.Task;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY , property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Sleep.class , name = "SLEEP"),
        @JsonSubTypes.Type(value = Output.class , name = "OUTPUT"),
        @JsonSubTypes.Type(value = MailSender.class , name = "SENDER"),
})
public interface Reaction extends Serializable {


    void perform(Task task);

    Object getValue();

    void setValue(Object data);

    ReactionType getType();
}
