package org.match3.core.combination;

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
        if (first.isClose(second) && second.isClose(third) && !(first.isClose(third) || first.isSameValue(third))) {
            return true;
        } else if (first.isClose(third) && second.isClose(third) && !(first.isClose(second) || first.isSameValue(second))) {
            return true;
        } else return first.isClose(second) && first.isClose(third) && !(second.isClose(third) || second.isSameValue(third));
    }

    public boolean isPureSequence() {
        return isSequence() && isColor();
    }
}
