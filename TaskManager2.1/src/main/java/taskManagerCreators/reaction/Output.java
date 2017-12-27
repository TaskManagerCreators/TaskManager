package taskManagerCreators.reaction;

/**
 * Prints a notification.
 * @see taskmanagerlogic.Task
 * @version 1.0
 */
public class Output implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    private String outgoing;

    public Output(String outgoing) {
        this.outgoing = outgoing;
    }

    @Override
    public void perform() {
        System.out.println(outgoing);
    }

    @Override
    public Object getValue() {
        return outgoing;
    }

    @Override
    public ReactionType getType() {
        return ReactionType.OUTPUT;
    }

}
