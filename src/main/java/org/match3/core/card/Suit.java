package org.match3.core.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Suit {
    DIAMONDS('\u2666'),
    HEARTS('\u2764'),
    SPADES('\u2660'),
    CLUBS('\u2663');

    private final char symbol;

    public static Suit getSuitBySymbol(char symbol) {
        for (Suit suit : Suit.values()) {
            if (suit.getSymbol() == symbol) {
                return suit;
            }
        }
        throw new IllegalArgumentException("No suit with symbol " + symbol);
    }
}
