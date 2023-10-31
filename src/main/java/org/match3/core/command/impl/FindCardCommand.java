package org.match3.core.command.impl;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.match3.core.board.BoardTable;
import org.match3.core.board.Position;
import org.match3.core.card.Card;
import org.match3.core.combination.Combination;
import org.match3.core.combination.CombinationRecognizer;
import org.match3.core.command.Command;
import org.match3.core.card.CardValue;

public class FindCardCommand implements Command {

    private Map<Position, CardValue> bestCombinations = new HashMap<>();
    private BoardTable boardTable;

    @Override
    public String execute(String request) {
        bestCombinations.clear();
        boardTable = BoardTable.getInstance();
        List<Card> cards = boardTable.getCardsInHand();
        boolean isCardOnTable = cards.stream().anyMatch(boardTable::isCardOnTable);
        if (isCardOnTable) {
            return format("Some of the card [%s] is already on the table", cards.stream()
                                                                                 .filter(boardTable::isCardOnTable)
                                                                                 .map(Card::prettyPrint)
                                                                                 .toList());
        }

        List<Position> emptyPositions = boardTable.getEmptyPositions();
        Card[][] board = boardTable.getBoard();

        int bestCombinationValue = 0;
        for (Card card : cards) {
            for (Position position : emptyPositions) {
                int combinationValue = getBestCombination(board, card, position);
                if (combinationValue > bestCombinationValue) {
                    bestCombinationValue = combinationValue;
                    bestCombinations.put(position, new CardValue(card, combinationValue));
                }
            }
        }

        return getResultCardValue(cards, emptyPositions);
    }

    private String getResultCardValue(List<Card> cards, List<Position> emptyPositions) {
        if (bestCombinations.isEmpty()) {
            Position position = emptyPositions.get(0);
            Card card = cards.get(0);
            boardTable.setCard(card, position);
            return format("add %s %s%s points:0", card.prettyPrint(), position.getRow(), position.getColumn());
        }

        Map.Entry<Position, CardValue> positionCardValueEntry = bestCombinations.entrySet()
                                                             .stream()
                                                             .max(Map.Entry.comparingByValue())
                                                             .get();
        Position position = positionCardValueEntry.getKey();
        CardValue value = positionCardValueEntry.getValue();
        boardTable.setCard(value.getCard(), position);
        bestCombinations.entrySet().forEach(System.out::println);
        return format("add %s %s%s points:%s",
                      value.getCard().prettyPrint(),
                      position.getRow(),
                      position.getColumn(),
                      value.getValue()
        );
    }

    private int getBestCombination(Card[][] board, Card card, Position position) {
        int row = position.getRow() -1;
        int column = position.getColumn() -1;
        if (board[row][column] != null) {
            return 0;
        }

        List<Combination> allPossibleCombinations = new ArrayList<>();
        allPossibleCombinations.addAll(getHorizonCombination(board, card, position));
        allPossibleCombinations.addAll(getVerticalCombination(board, card, position));
        allPossibleCombinations.addAll(getDiagonalUpCombination(board, card, position));
        allPossibleCombinations.addAll(getDiagonalDownCombination(board, card, position));

        CombinationRecognizer combinationRecognizer = new CombinationRecognizer();

        return combinationRecognizer.getCombinationValue(allPossibleCombinations);
    }

    private List<Combination> getHorizonCombination(Card[][] board, Card card, Position position) {
        int row = position.getRow() -1;
        int column = position.getColumn() -1;

        Card card1 = getCard(board, row, column - 2);
        Card card2 = getCard(board, row, column - 1);
        Card card4 = getCard(board, row, column + 1);
        Card card5 = getCard(board, row, column + 2);
        return getListCombinations(card1, card2, card, card4, card5);
    }

    private List<Combination> getVerticalCombination(Card[][] board, Card card, Position position) {
        int row = position.getRow() -1;
        int column = position.getColumn() -1;

        Card card1 = getCard(board, row - 2, column);
        Card card2 = getCard(board, row - 1, column);
        Card card4 = getCard(board, row + 1, column);
        Card card5 = getCard(board, row + 2, column);
        return getListCombinations(card1, card2, card, card4, card5);
    }

    private List<Combination> getDiagonalUpCombination(Card[][] board, Card card, Position position) {
        int row = position.getRow() -1;
        int column = position.getColumn() -1;

        Card card1 = getCard(board, row + 2, column - 2);
        Card card2 = getCard(board, row + 1, column - 1);
        Card card4 = getCard(board, row - 1, column + 1);
        Card card5 = getCard(board, row - 2, column + 2);
        return getListCombinations(card1, card2, card, card4, card5);
    }

    private List<Combination> getDiagonalDownCombination(Card[][] board, Card card, Position position) {
        int row = position.getRow() -1;
        int column = position.getColumn() -1;

        Card card1 = getCard(board, row - 2, column - 2);
        Card card2 = getCard(board, row - 1, column - 1);
        Card card4 = getCard(board, row + 1, column + 1);
        Card card5 = getCard(board, row + 2, column + 2);
        return getListCombinations(card1, card2, card, card4, card5);
    }

    private List<Combination> getListCombinations(Card... cards) {
        List<Combination> resultCombinations = new ArrayList<>();
        Card card1 = cards[0];
        Card card2 = cards[1];
        Card card3 = cards[2];
        Card card4 = cards[3];
        Card card5 = cards[4];
        if (card1 != null && card2 != null && card3 != null) {
            resultCombinations.add(new Combination(card1, card2, card3));
        }
        if (card2 != null && card3 != null && card4 != null) {
            resultCombinations.add(new Combination(card2, card3, card4));
        }
        if (card3 != null && card4 != null && card5 != null) {
            resultCombinations.add(new Combination(card3, card4, card5));
        }
        return resultCombinations;
    }

    private Card getCard(Card[][] board, int row, int column) {
        try {
            return board[row][column];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
