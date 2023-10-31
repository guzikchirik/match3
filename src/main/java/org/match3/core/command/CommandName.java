package org.match3.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandName {
    ADD_CARD("add"),
    FIND_CARD("find"),
    HAND_CARD("hand"),
    SHOW_CARDS("show"),
    REMOVE_CARD("remove"),
    WRONG_COMMAND("Wrong_Request");

    private final String command;
}
