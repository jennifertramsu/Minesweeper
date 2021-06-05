package Minesweeper;

import java.util.Scanner;

public class MineSweeperApp {

    public static void main(String[] args) {
        int x;
        int y;
        Scanner in = new Scanner(System.in);

        Minefield game = new Minefield(15, 15);

        while (!game.isGameDone()) {
            displayBoard(game);

            System.out.println("X coordinate: ");
            x = in.nextInt();
            System.out.println("Y coordinate: ");
            y = in.nextInt();

            game.play(x, y);
            }

        displayAll(game);

        System.out.println("Game over!");
        }

    public static void displayBoard(Minefield field) {
        // print header row
        System.out.println("Mines Left: " + field.getMines());
        System.out.print("      ");
        for (int i = 0; i < field.getBoard()[0].length; i++) {
            if (i >= 10) {
                System.out.print(i);
                System.out.print("   ");
            } else {
                System.out.print(i);
                System.out.print("    ");
            }
        }
        System.out.println();
        // print side numbers, then Minefield row
        for (int i = 0; i < field.getBoard().length; i++) {
            System.out.print(i);
            if (i < 10) {
                System.out.print(" ");
            }
            for (int j = 0; j < field.getBoard()[0].length; j++) {
                System.out.print(" || ");
                if (!field.getBoard()[i][j].isUncovered()) {
                    System.out.print('-');
                    continue;
                }
                switch (field.getBoard()[i][j].getType()) {
                    case MINE -> System.out.print('M');
                    case BLANK -> System.out.print(' ');
                    case NUMBER -> System.out.print(field.getBoard()[i][j].getNumber());
                }
            }
            System.out.print(" ||");
            System.out.println();
        }
        System.out.println("Score: \t" + field.getScore());
    }

    public static void displayAll(Minefield field) {
        // make all cells uncovered
        // call displayBoard()

        for (Cell[] row : field.getBoard()) {
            for (Cell cell : row) {
                cell.uncover();
            }
        }
        displayBoard(field);
    }
}

