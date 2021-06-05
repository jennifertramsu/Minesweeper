package Minesweeper;

public class Cell {
    private boolean uncovered;
    private int xCoord;
    private int yCoord;
    private Type type;
    private int number; // can only access if type is number

    public Cell(int x, int y, Type type) {
        this.xCoord = x;
        this.yCoord = y;
        this.type = type;
        this.uncovered = false;
    }

    // setters

    public void setNumber(int number) {
        this.number = number;
    }

    public void uncover() {
        this.uncovered = true;
    }

    public void changeType(Type type) {
        this.type = type;
    }

    // getters

    public Type getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public boolean isUncovered() {
        return uncovered;
    }
}
