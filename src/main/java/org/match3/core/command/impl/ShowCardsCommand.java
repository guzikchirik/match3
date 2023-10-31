package org.match3.core.command.impl;

import org.match3.core.board.BoardTable;
import org.match3.core.card.Card;
import org.match3.core.command.Command;

public class ShowCardsCommand implements Command {

    @Override
    public String execute(String request) {
        BoardTable boardTable = BoardTable.getInstance();
        return boardTable.getCardsInHand().stream().map(Card::prettyPrint).toList().toString();
    }
}
