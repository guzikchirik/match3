package org.match3.core.command;

import static org.match3.core.command.CommandName.*;

import java.util.HashMap;
import java.util.Map;

import org.match3.core.command.impl.*;

public class CommandProvider {

    private final Map<String, Command> repository = new HashMap<>();

    public CommandProvider() {
        repository.put(ADD_CARD.getCommand(), new AddCardCommand());
        repository.put(FIND_CARD.getCommand(), new FindCardCommand());
        repository.put(HAND_CARD.getCommand(), new AddCardsToHandCommand());
        repository.put(SHOW_CARDS.getCommand(), new ShowCardsCommand());
        repository.put(REMOVE_CARD.getCommand(), new RemoveCardCommand());
        repository.put(WRONG_COMMAND.getCommand(), new AddCardCommand());
    }

    public Command getCommand(String request){
        String name = request.split(" ")[0].toLowerCase();
        Command command = repository.get(name);
        if(command == null){
            command = repository.get("Wrong_Request");
        }
        return command;
    }
}
