package org.match3.core.command.impl;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.List;

import org.match3.core.board.BoardTable;
import org.match3.core.card.Card;
import org.match3.core.command.Command;

public class AddCardsToHandCommand implements Command {

    @Override
    public String execute(String request) {
        BoardTable boardTable = BoardTable.getInstance();

        String[] requestArgs = request.split(" ");
        List<Card> cards = Arrays.stream(requestArgs)
                                 .skip(1)
                                 .map(String::toUpperCase)
                                 .map(Card::getCardByName)
                                 .toList();
        if (boardTable.areCardsOnTable(cards)) {
            return format("Some cards [%s] are already on the table", cards.stream()
                                                                .filter(boardTable::isCardOnTable)
                                                                .map(Card::prettyPrint)
                                                                .toList());
        }
        if (boardTable.getCardsInHand().stream().anyMatch(cards::contains)) {
            return format("Some cards are already in HAND! HAND [%s] and CARDS [%s]", boardTable.getCardsInHand(), cards);
        }
        boardTable.addCardsToHand(cards);
        return format("Cards %s added to hand", cards.stream()
                                                     .map(Card::prettyPrint)
                                                     .toList());
    }
}
