package org.match3.core.command.impl;

import static java.lang.String.format;

import org.match3.core.board.BoardTable;
import org.match3.core.card.Card;
import org.match3.core.board.Position;
import org.match3.core.command.Command;

public class RemoveCardCommand implements Command {
    @Override
    public String execute(String request) {
        BoardTable boardTable = BoardTable.getInstance();

        String[] requestArgs = request.split(" ");
        Card card = Card.getCardByName(requestArgs[1].toUpperCase());
        if (!boardTable.isCardOnTable(card)) {
            return format("There is no card [%s] on the table", card.prettyPrint());
        }

        boardTable.removeCard(card);
        return format("Card [%s] removed successfully", card.prettyPrint());
    }
}
