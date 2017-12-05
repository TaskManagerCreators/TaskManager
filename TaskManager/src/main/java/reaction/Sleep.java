package reaction;

public class Sleep implements Reaction {

    private static final long serialVersionUID = 12321312324L;

    private long millis;

    public Sleep(long millis) {
        this.millis = millis;
    }

    @Override
    public void perform() {
        try {
            Thread.sleep(millis);
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
