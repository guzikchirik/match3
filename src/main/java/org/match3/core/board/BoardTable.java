package org.match3.core.board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.match3.core.card.Card;
import org.match3.core.card.Suit;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class BoardTable {

    private static final int ROWS = 5;
    private static final int COLUMNS = 8;
    private static BoardTable instance;

    public static BoardTable getInstance() {
        if (instance == null) {
            instance = new BoardTable();
        }
        return instance;
    }

    private final Card[][] board = new Card[ROWS][COLUMNS];
    private final List<Card> cardsOnTable = new ArrayList<>();
    private final List<Card> cardsInHand = new ArrayList<>();
    private final List<Position> emptyPositions = new ArrayList<>();

    public void init(List<Card> cards) {
        if (cards.size() != 4) {
            throw new IllegalArgumentException("The number of cards must be 4");
        }
        board[1][2] = cards.get(0);
        board[1][3] = cards.get(1);
        board[2][2] = cards.get(2);
        board[2][3] = cards.get(3);

        cardsOnTable.add(cards.get(0));
        cardsOnTable.add(cards.get(1));
        cardsOnTable.add(cards.get(2));
        cardsOnTable.add(cards.get(3));

        for (int i = 1; i <= ROWS; i++) {
            for (int j = 1; j <= COLUMNS; j++) {
                emptyPositions.add(new Position(i, j));
            }
        }
        emptyPositions.remove(new Position(2, 3));
        emptyPositions.remove(new Position(2, 4));
        emptyPositions.remove(new Position(3, 3));
        emptyPositions.remove(new Position(3, 4));
    }

    public boolean isCardOnTable(Card card) {
        return cardsOnTable.contains(card);
    }

    public boolean areCardsOnTable(List<Card> cards) {
        return cardsOnTable.stream().anyMatch(cards::contains);
    }

    public Card getCard(final int row, final int column) {
        return board[row][column];
    }

    public Card getCard(final Position position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    public void setCard(Card card, Position position) {
        board[position.getRow() - 1][position.getColumn() - 1] = card;
        cardsInHand.remove(card);
        cardsOnTable.add(card);
        emptyPositions.remove(position);
    }

    public void removeCard(Card card) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Card currentCard = board[i][j];
                if (currentCard != null && currentCard.equals(card)) {
                    board[i][j] = null;
                    emptyPositions.add(new Position(i + 1, j + 1));
                }
            }
        }
        cardsOnTable.remove(card);
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void addCardToHand(Card card) {
        cardsInHand.add(card);
    }

    public void addCardsToHand(List<Card> card) {
        cardsInHand.addAll(card);
    }

    public List<Position> getEmptyPositions() {
        return emptyPositions;
    }

    public Card[][] getBoard() {
        return board;
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public void parseBoardFromFile(final String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            final int[] row = {1};
            stream.forEach(line -> {
                if (line.startsWith("-")) {
                    return;
                }
                String[] split = line.split("\\|");
                for (int i = 1; i <= 8; i++) {
                    char[] charArray = split[i].trim()
                                               .toUpperCase()
                                               .toCharArray();
                    if (charArray.length == 0) {
                        emptyPositions.add(new Position(row[0], i));
                        continue;
                    }
                    String cardName;
                    if (charArray.length == 3) {
                        cardName = String.valueOf(charArray[0]) + charArray[1] + Suit.getSuitBySymbol(charArray[2])
                                                                                     .name()
                                                                                     .substring(0, 1);
                    } else {
                        cardName = charArray[0] + Suit.getSuitBySymbol(charArray[1])
                                                             .name()
                                                             .substring(0, 1);
                    }
                    Card card = Card.getCardByName(cardName);
                    setCard(card, new Position(row[0], i));
                }
                row[0]++;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
