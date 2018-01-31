package com.reaction;

import java.util.HashMap;
import java.util.Map;

public class ReactionResolver {

    private Map<String, Reaction> keeper = new HashMap<String, Reaction>() {{
        put("sleep", new Sleep());
        put("output", new Output());
        put("send", new MailSender());

    }};

    public static Reaction create(String args) {
        String key = args.split("-")[0].trim();
        Reaction reaction = new ReactionResolver().keeper.get(key);
        reaction.setValue(args.substring(args.indexOf('-') + 1, args.length()).trim());
        return reaction;
    }

}
