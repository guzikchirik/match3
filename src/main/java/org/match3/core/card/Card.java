package org.match3.core.card;

import static org.match3.core.card.Suit.CLUBS;
import static org.match3.core.card.Suit.DIAMONDS;
import static org.match3.core.card.Suit.HEARTS;
import static org.match3.core.card.Suit.SPADES;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Card {
    DA("AD", DIAMONDS, 14), HA("AH", HEARTS, 14), SA("AS", SPADES, 14), CA("AC", CLUBS, 14),
    DK("KD", DIAMONDS, 13), HK("KH", HEARTS, 13), SK("KS", SPADES, 13), CK("KC", CLUBS, 13),
    DQ("QD", DIAMONDS, 12), HQ("QH", HEARTS, 12), SQ("QS", SPADES, 12), CQ("QC", CLUBS, 12),
    DJ("JD", DIAMONDS, 11), HJ("JH", HEARTS, 11), SJ("JS", SPADES, 11), CJ("JC", CLUBS, 11),
    D10("10D", DIAMONDS, 10), H10("10H", HEARTS, 10), S10("10S", SPADES, 10), C10("10C", CLUBS, 10),
    D9("9D", DIAMONDS, 9), H9("9H", HEARTS, 9), S9("9S", SPADES, 9), C9("9C", CLUBS, 9),
    D8("8D", DIAMONDS, 8), H8("8H", HEARTS, 8), S8("8S", SPADES, 8), C8("8C", CLUBS, 8),
    D7("7D", DIAMONDS, 7), H7("7H", HEARTS, 7), S7("7S", SPADES, 7), C7("7C", CLUBS, 7),
    D6("6D", DIAMONDS, 6), H6("6H", HEARTS, 6), S6("6S", SPADES, 6), C6("6C", CLUBS, 6),
    D5("5D", DIAMONDS, 5), H5("5H", HEARTS, 5), S5("5S", SPADES, 5), C5("5C", CLUBS, 5),
    D4("4D", DIAMONDS, 4), H4("4H", HEARTS, 4), S4("4S", SPADES, 4), C4("4C", CLUBS, 4),
    D3("3D", DIAMONDS, 3), H3("3H", HEARTS, 3), S3("3S", SPADES, 3), C3("3C", CLUBS, 3),
    D2("2D", DIAMONDS, 2), H2("2H", HEARTS, 2), S2("2S", SPADES, 2), C2("2C", CLUBS, 2);

    private final String name;
    private final Suit suit;
    private final int value;

    public static Card getCardByName(final String name) {
        for (Card card : Card.values()) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public String prettyPrint() {
        return name().substring(1) + getSuit().getSymbol();
    }

    public boolean isSameSuit(final Card card) {
        return suit == card.getSuit();
    }

    public boolean isSameValue(final Card card) {
        return value == card.getValue();
    }

    public boolean isAce() {
        return value == 14;
    }

    public boolean isClose(final Card card) {
        if (isAce() && card.isAce()) {
            return false;
        }
        if (isAce() && (card.getValue() == 2 || card.getValue() == 13)) {
            return true;
        }
        if (card.isAce() && (value == 2 || value == 13)) {
            return true;
        }
        return value == card.value + 1 || value == card.value - 1;
    }
}
