package Minesweeper;

import java.util.Random;

public class Minefield {
    private Cell[][] board;
    private int mines;
    private boolean gameDone = false; // toggle to true when entire board is revealed
    private int blanks;
    private int blankCount; // keeping track

    // constructor

    public Minefield(int sizeX) {
        this.board = new Cell[sizeX][sizeX]; // creating square board
        this.mines = sizeX * sizeX / 8; // want mines to take up 1/5 of board
        // create blank cells

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = new Cell(i, j, Type.BLANK);
            }
        }
        // fill up board with mines

        Random rand = new Random();

        // randomly fill with mines

        for (int i = 0; i < this.mines; i++) {
        // generate random x and y coordinates
            int x = rand.nextInt(this.board.length - 1);
            int y = rand.nextInt(this.board[x].length - 1);

            if (this.board[x][y].getType() == Type.MINE) {
                i -= 1;
                continue;
            } else {
                this.board[x][y].changeType(Type.MINE);
            }
        }

        int numberCount = 0;

        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                if (cell.getType() != Type.MINE) { // skip if already a mine
                    cell.setNumber(checkMines(cell));
                    if (cell.getType() == Type.NUMBER) {
                        numberCount++;
                    }
                }
            }
        }
        this.blanks = sizeX * sizeX - this.mines - numberCount;
    }

    private int checkMines(Cell cell) {
        int mineCount = 0;
        int x = cell.getxCoord();
        int y = cell.getyCoord();

        int otherX = x - 1;
        for (int i = 0; i < 3; i++) {
            // start with upper-left, and go right
            int otherY = y - 1;
            for (int j = 0; j < 3; j++) {
                if (otherX + i < 0) {
                    continue;
                    }
                if (otherY + j < 0) {
                    continue;
                }

                if (otherX + i > this.board.length - 1) {
                    continue;
                }

                if (otherY + j > this.board[i].length - 1) {
                    continue;
                }
                if (this.board[otherX + i][otherY + j].getType() == Type.MINE) {
                    mineCount += 1;
                }
            }
        }
        if (mineCount == 0) {
            cell.changeType(Type.BLANK); // not touching any mines
        } else {
            cell.changeType(Type.NUMBER); // touching at least one mine
        }
        return mineCount;
    }

    // when player inputs coordinate to uncover

    public void play(int x, int y) {
        // check if coordinates are within board dimensions
        if (x < 0 || x > board.length-1 || y < 0 || y > board.length-1) {
            return;
        }
        // check if all blank cells have been found
        checkEnd();
        Cell cell = board[x][y]; // selected cell
        // if already uncovered, pass
        if (cell.isUncovered()) {
            return;
        }
        switch (cell.getType()) {
            case MINE:
                this.gameDone = true; // ends game
                System.out.println("You found a mine!");
                break;
            case NUMBER:
                cell.uncover();
                break;
            case BLANK:
                cell.uncover();
                this.blankCount++;
                // safe to call play on all 8 adjacent cells
                play(x, y-1);
                play(x, y+1);
                play(x-1, y);
                play(x+1, y);
                play(x-1, y-1);
                play(x-1, y+1);
                play(x+1, y-1);
                play(x+1, y+1);
                break;
        }
    }

    private void checkEnd() {
        // when all cells that don't have mines are uncovered
        if (this.blanks == this.blankCount) {
            this.gameDone = true;
            System.out.println("Field cleared!");
        }
    }

    private void updateScore() {
        // corresponds to number of uncovered cells that do not have mines
    }

    // getters

    public boolean isGameDone() {
        return this.gameDone;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public int getBlanks() { return this.blanks - this.blankCount; }
}
