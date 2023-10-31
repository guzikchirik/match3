package org.match3.core.card;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardValue implements Comparable<CardValue>{
    private Card card;
    private int value;

    @Override
    public int compareTo(CardValue cardValue) {
        return this.value - cardValue.value;
    }
}
