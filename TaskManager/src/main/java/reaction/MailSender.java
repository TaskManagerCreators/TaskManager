package reaction;

public class MailSender implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    @Override
    public void perform() {
        throw new UnsupportedOperationException("MAilSender not...");
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public ReactionType getType() {
        return ReactionType.SENDER;
    }
}
