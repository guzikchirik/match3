package org.match3.core.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    private int row;
    private int column;

    public static Position getPositionFromString(String position) {
        return new Position(Integer.parseInt(position.substring(0, 1)), Integer.parseInt(position.substring(1, 2)));
    }
}
