package com.reaction;

public class ReactionResolver {


    /*private  static Map<String, Reaction> keeper = new HashMap<String, Reaction>() {{
        put("sleep", new Sleep());
        put("output", new Output());
        put("send", new MailSender());

    }};*/

    public static Reaction create(String args) {
        String key = args.split("-")[0].trim();

       /* for (Map.Entry<String, Reaction> pair : keeper.entrySet()) {
            if (key == pair.getKey())
                return pair.getValue();
            else throw new IllegalArgumentException("Arguments exception.");
       */
        return new MailSender();
    }

}
