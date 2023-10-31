package org.match3.core.command.impl;

import static java.lang.String.format;

import org.match3.core.board.BoardTable;
import org.match3.core.card.Card;
import org.match3.core.board.Position;
import org.match3.core.command.Command;

public class AddCardCommand implements Command {

    @Override
    public String execute(String request) {
        BoardTable boardTable = BoardTable.getInstance();

        String[] requestArgs = request.split(" ");
        Card card = Card.getCardByName(requestArgs[1].toUpperCase());
        if (boardTable.isCardOnTable(card)) {
            return "Card is already on the table";
        }

        Position position = Position.getPositionFromString(requestArgs[2]);
        boardTable.setCard(card, position);
        return format("Card [%s] is added on the table SUCCESSFULLY!", card.prettyPrint());
    }
}
