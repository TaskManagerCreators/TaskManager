package reaction;

import java.io.Serializable;


public interface Reaction extends Serializable {

    void perform();

    Object getValue();

    ReactionType getType();
}
