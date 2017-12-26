package reaction;

public class ReactionResolver {
    public static Reaction create(String data) {
        String values[] = data.split("-");
        switch (values[0].trim()) {
            case "sleep":
                return new Sleep(Long.valueOf(values[1].trim()));
            case "output":
                return new Output(values[1].trim());
            case "send":
                return new MailSender();
            default:
                throw new IllegalArgumentException("Arguments exception.");
        }
    }
}
