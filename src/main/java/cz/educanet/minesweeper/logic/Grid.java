package cz.educanet.minesweeper.logic;

public class Grid {
    private int fieldState;
    private boolean bomb;

    public int getFieldState() {
        return fieldState;
    }

    public boolean getBomb() {
        return  bomb;
    }

    public void setFieldState(int fieldState) {
        this.fieldState = fieldState;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }
}
