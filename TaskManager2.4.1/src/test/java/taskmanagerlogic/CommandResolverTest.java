package taskmanagerlogic;

import com.commands.*;
import com.taskmanagerlogic.CommandResolver;
import com.taskmanagerlogic.History;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a command resolver test. It just creates each command and checks that type.
 * @version 1.0
 * @see com.taskmanagerlogic.CommandResolver
 */
class CommandResolverTest {
    Command command;
    private final String[] create = {"create"};
    private final String[] delete = {"delete"};
    private final String[] help = {"help"};
    private final String[] show = {"show"};
    private final String[] exit = {"exit"};
    private final String[] history = {"history"};
    private final String[] clean = {"clean"};
    @Test
    void createCommand() {
        command = CommandResolver.createCommand(create);
        assertEquals(command.getClass() , Create.class);
        command = CommandResolver.createCommand(delete);
        assertEquals(command.getClass() , Delete.class);
        command = CommandResolver.createCommand(show);
        assertEquals(command.getClass() , Show.class);
        command = CommandResolver.createCommand(help);
        assertEquals(command.getClass() , Help.class);
        command = CommandResolver.createCommand(exit);
        assertEquals(command.getClass() , Exit.class);
        command = CommandResolver.createCommand(history);
        assertEquals(command.getClass() , ShowHistory.class);
        command = CommandResolver.createCommand(clean);
        assertEquals(command.getClass() , Clean.class);

    }
}