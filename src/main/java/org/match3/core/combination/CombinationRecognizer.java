package org.match3.core.combination;

import java.util.List;

public class CombinationRecognizer {

    public int getCombinationValue(final Combination combination) {
        if (combination.isTrio()) {
            return CombinationName.TRIO.getPoints();
        }
        if (combination.isPureSequence()) {
            return CombinationName.PURE_SEQUENCE.getPoints();
        }
        if (combination.isSequence()) {
            return CombinationName.SEQUENCE.getPoints();
        }
        if (combination.isColor()) {
            return CombinationName.COLOR.getPoints();
        }
        if (combination.isPair()) {
            return CombinationName.PAIR.getPoints();
        }
        return 0;
    }

    public int getCombinationValue(final List<Combination> combinations) {
        int value = 0;
        int combo = 0;
        for (Combination combination : combinations) {
            int combinationValue = getCombinationValue(combination);
            if (combinationValue > 0) {
                value += combinationValue;
                combo++;
            }
        }
        return combo <= 2 ? value * combo : value * combo + value;
    }
}
