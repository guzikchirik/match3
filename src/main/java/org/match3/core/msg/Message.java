package org.match3.core.msg;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Message {
    public static final String SEPARATOR = " ";
    public static final String WELCOME_MSG = "Welcome to Match3 game!";
    public static final String INITIAL_MSG = "Enter initial cards separated by space. Example: ad kh 3s 4c\n";
    public static final String RULES_MSG = "Welcome to Match3 game!";
    public static final String COMMANDS_MSG = """
            Enter command:
            1. add card position: add da 11
            2. hand cards: hand qd kh 5s 6c
            3. find best card and add: find
            4. remove card from the table: remove ad
            5. show cards in hand: show
            6. exit
            """;
}
