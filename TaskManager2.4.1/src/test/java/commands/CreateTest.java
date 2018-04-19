package commands;

import com.InterAction;
import com.commands.Create;
import com.reaction.Output;
import com.reaction.Reaction;
import com.reaction.ReactionResolver;
import com.reaction.ReactionType;
import com.taskmanagerlogic.CommandResolver;
import com.taskmanagerlogic.Journal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Executable;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateTest {
    private ApplicationContext context;
    private Journal journal;
    private Create create;
    private String[] validCommand;
    private String[] wrongCommand;
    ReactionResolver reactionResolver;
    @BeforeAll
    void setUp(){
        context = SpringApplication.run(InterAction.class);
        journal = context.getBean(Journal.class);
        reactionResolver = context.getBean(ReactionResolver.class);
        create = context.getBean(Create.class);
        validCommand = "create task , desc , 20:42 26.02.2019 , output - 1000 , archac3@gmail.com".split(",");
        wrongCommand = "createew task , desc , 20:42 26.02.2019 , outputs -- 1000 , archac3@gmail.com".split(",");
    }
    @Test
    void execute() {
        assertThrows(NullPointerException.class , ()->{
            create.execute(wrongCommand);
        });
    }

    @Test
    void parseReaction() {
        assertEquals(ReactionType.OUTPUT , ReactionResolver.create(validCommand[3]).getType());
        assertEquals(ReactionType.SENDER , ReactionResolver.create("sender - me").getType());
        assertEquals(ReactionType.SLEEP , ReactionResolver.create("sleep - 1209").getType());
        assertThrows(NullPointerException.class , ()->{
            ReactionResolver.create(wrongCommand[3]);
        });
    }

    @Test
    void dateMatcher() {
        Date validDate = Date.from(Instant.now().plusMillis(1000 * 1000));
        Date wrongDate = Date.from(Instant.now().minusMillis(1000 * 1000));
        assertTrue(create.dateMatcher(validDate));
        assertFalse(create.dateMatcher(wrongDate));

    }
}