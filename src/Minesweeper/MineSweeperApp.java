package Minesweeper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MineSweeperApp {

    public static void main(String[] args) {
        int x;
        int y;
        int size;
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome! How large would you like the field to be? ");
        size = in.nextInt();

        Minefield game = new Minefield(size);
        long start = System.nanoTime();
        while (!game.isGameDone()) {
            displayBoard(game);

            System.out.println("X coordinate: ");
            while (true) {
                try {
                    x = in.nextInt();
                    if (x < 0 || x > size - 1) {
                        throw new Exception("Out of range!");
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Only integer values are valid. Try again: ");
                    in.next();
                } catch (Exception e) {
                    in.next();
                }
            }
            System.out.println("Y coordinate: ");
            while (true) {
                try {
                    y = in.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Only integer values are valid. Try again: ");
                    in.next();
                }
            }
            game.play(x, y);
        }
        long end = System.nanoTime();
        displayAll(game);

        System.out.println("Time elapsed: " + (end - start)/1000000000 + " seconds");
        System.out.println("Game over!");
        }

    public static void displayBoard(Minefield field) {
        // print header row
        System.out.println("Blanks Left: " + field.getBlanks());
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

