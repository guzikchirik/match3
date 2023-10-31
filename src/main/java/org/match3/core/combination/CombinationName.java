package org.match3.core.combination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CombinationName {
    PAIR(10),
    COLOR(20),
    SEQUENCE(40),
    PURE_SEQUENCE(60),
    TRIO(100);

    private final int points;
}
