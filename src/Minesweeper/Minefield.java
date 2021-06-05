package Minesweeper;

import java.util.Random;

public class Minefield {
    private Cell[][] board;
    static String name = "Minesweeper";
    private int mines;
    private boolean gameDone = false; // toggle to true when entire board is revealed
    private int score;

    // constructor

    public Minefield(int sizeX, int sizeY) {
        this.board = new Cell[sizeX][sizeY]; // creating square board
        this.mines = sizeX * sizeY / 8; // wants mines to take up 1/5 of board
        this.score = 0;

        // create blank cells

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = new Cell(i, j, Type.BLANK);
            }
        }
        // fill up board with mines

        Random rand = new Random(1);

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

        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                if (cell.getType() != Type.MINE) { // skip if already a mine
                    cell.setNumber(checkMines(cell));
                }
            }
        }
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
        Cell cell = board[x][y]; // selected cell
        switch (cell.getType()) {
            case MINE:
                gameDone = true; // ends game
                System.out.println("You found a mine!");
            case NUMBER:
                cell.uncover();
            case BLANK:
                cell.uncover();
                // uncoverNearby(cell);
        }
    }

    // when player chooses blank cell

    private void uncoverNearby(Cell cell) {
        int x = cell.getxCoord();
        int y = cell.getyCoord();
        int up;
        int down;
        int left;
        int right;

        // traverse in the 8 directions

        // up

        up = x - 1; // row above

        while (up > 0) {
            board[up][y].uncover();
            if (board[up][y].getType() == Type.NUMBER) {
                break;
            }
            up--;
        }

        // down

        down = x + 1;

        while (down < board.length) {
            board[down][y].uncover();
            if (board[down][y].getType() == Type.NUMBER) {
                break;
            }
            down++;
        }

        // left

        left = y - 1;

        while (left > 0) {
            board[x][left].uncover();
            if (board[x][left].getType() == Type.NUMBER) {
                break;
            }
            left--;
        }

        // right

        right = y + 1;

        while (right < board[x].length) {
            board[x][right].uncover();
            if (board[x][right].getType() == Type.NUMBER) {
                break;
            }
            right++;
        }

        up = x - 1;
        left = y - 1;

        while (up > 0 && left > 0) {
            board[up][left].uncover();
            if (board[up][left].getType() == Type.NUMBER) {
                break;
            }
        }

        up = x - 1;
        right = y + 1;

        while (up > 0 && right < board.length) {
            board[up][right].uncover();
            if (board[up][right].getType() == Type.NUMBER) {
                break;
            }
        }

        down = x + 1;
        left = y - 1;

        while (down < board.length && left > 0) {
            board[down][left].uncover();
            if (board[down][left].getType() == Type.NUMBER) {
                break;
            }
        }

        down = x + 1;
        right = y + 1;

        while (down < board.length && right < board.length) {
            board[down][right].uncover();
            if (board[down][right].getType() == Type.NUMBER) {
                break;
            }
        }
    }

    private void checkEnd() {
        // when all cells that don't have mines are uncovered

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

    public int getScore() {
        return score;
    }

    public int getMines() {
        return this.mines;
    }
}
