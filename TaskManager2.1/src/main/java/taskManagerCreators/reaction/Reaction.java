package taskManagerCreators.reaction;

import java.io.Serializable;

/**
 * An interface for each reaction.
 * @see MailSender
 * @see Output
 * @see ReactionType
 * @see Sleep
 * @version 1.0
 */
public interface Reaction extends Serializable {

    void perform();

    Object getValue();

    ReactionType getType();
}
