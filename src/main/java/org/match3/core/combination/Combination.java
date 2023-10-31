package org.match3.core.combination;

import static java.util.Arrays.asList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.match3.core.card.Card;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Combination {
    private Card first;
    private Card second;
    private Card third;

    public boolean isPair() {
        return first.isSameValue(second) || second.isSameValue(third) || first.isSameValue(third);
    }

    public boolean isTrio() {
        return first.isSameValue(second) && second.isSameValue(third);
    }

    public boolean isColor() {
        return first.isSameSuit(second) && second.isSameSuit(third);
    }

    public boolean isSequence() {
        List<Card> cards = asList(first, second, third);
        cards.sort(Comparator.comparingInt(Card::getValue));

        if (cards.get(0).getValue() == 2 && cards.get(1).getValue() == 3 && cards.get(2).getValue() == 14) {
            return true;
        }
        return cards.get(1).getValue() == cards.get(0).getValue() + 1
                && cards.get(1).getValue() == cards.get(2).getValue() - 1;
    }

    public boolean isPureSequence() {
        return isSequence() && isColor();
    }
}
