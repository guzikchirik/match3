package org.match3.core.controller;

import static org.match3.core.msg.Message.COMMANDS_MSG;
import static org.match3.core.msg.Message.INITIAL_MSG;
import static org.match3.core.msg.Message.WELCOME_MSG;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.match3.core.board.BoardTable;
import org.match3.core.card.Card;
import org.match3.core.command.CommandProvider;

public class GameController {

    private final CommandProvider commandProvider = new CommandProvider();

    public void startGame() {

        Scanner scanner = new Scanner(System.in);
        System.out.println(WELCOME_MSG);
        System.out.print(INITIAL_MSG);
//        String cardsLine = scanner.nextLine().trim();

//        List<Card> cards = parseCards(cardsLine);
        BoardTable boardTable = BoardTable.getInstance();

        boardTable.parseBoardFromFile("src/main/resources/board/startBoard.txt");
//        printBoard(boardTable);
//        boardTable.init(cards);

        int finalScore = 0;

        printBoard(boardTable);

        System.out.print(COMMANDS_MSG);
        do {
            String commandLine = scanner.nextLine().trim();
            if (commandLine.equals("exit")) {
                break;
            }
            String execute = commandProvider.getCommand(commandLine)
                                            .execute(commandLine);
            if (execute != null && execute.contains("error")) {
                System.out.printf("Error: %s%n", execute);
            }
            System.out.printf("%s%n", execute);
            if (execute.contains("points")) {
                String[] split = execute.split("points:");
                finalScore += Integer.parseInt(split[split.length - 1]);
            }
            printBoard(boardTable);
        } while (true);
        System.out.printf("Final score: %d%n", finalScore);
        // Close the Scanner when done
        scanner.close();
    }

    private void printBoard(BoardTable boardTable) {
        System.out.println("-------------------------------------------------");
        for (int row = 0; row < boardTable.getRows(); row++) {
            List<Card> cardsRow = new ArrayList<>();
            for (int column = 0; column < boardTable.getColumns(); column++) {
                Card card = boardTable.getCard(row, column);
                cardsRow.add(card);
            }
            StringBuilder rowBuilder = new StringBuilder("|");
            cardsRow.forEach(card -> {
                String value = card == null ? "  " : card.prettyPrint();
                if (value.contains("10")) {
                    rowBuilder.append(" ");
                } else {
                    rowBuilder.append("  ");
                }
                rowBuilder.append(value).append(" |");
            });
            System.out.println(rowBuilder);
        }
        System.out.println("-------------------------------------------------");
    }

    private List<Card> parseCards(String cards) {
        String[] cardsArray = cards.split(" ");
        if (cardsArray.length != 4) {
            throw new IllegalArgumentException("The number of cards must be 4");
        }
        return List.of(
            Card.getCardByName(cardsArray[0].toUpperCase()),
            Card.getCardByName(cardsArray[1].toUpperCase()),
            Card.getCardByName(cardsArray[2].toUpperCase()),
            Card.getCardByName(cardsArray[3].toUpperCase())
        );
    }
}
