package cz.educanet.minesweeper.logic;

public class Minesweeper {

    private int rowsCount;
    private int columnsCount;

    public Minesweeper(int rows, int columns) { // generate bombs here
        this.rowsCount = rows;
        this.columnsCount = columns;
    }

    /**
     * 0 - Hidden
     * 1 - Visible
     * 2 - Flag
     * 3 - Question mark
     *
     * @param x X
     * @param y Y
     * @return field type
     */
    public int getField(int x, int y) {
        if (x == rowsCount && y == columnsCount) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Toggles the field state, ie.
     * 0 -> 1,
     * 1 -> 2,
     * 2 -> 3 and
     * 3 -> 0
     *
     * @param x X
     * @param y Y
     */
    public void toggleFieldState(int x, int y) {
        getField(1,1);
    }

    /**
     * Reveals the field and all fields adjacent (with 0 adjacent bombs) and all fields adjacent to the adjacent fields... ect.
     *
     * @param x X
     * @param y Y
     */
    public void reveal(int x, int y) {

    }

    /**
     * Returns the amount of adjacent bombs
     *
     * @param x X
     * @param y Y
     * @return number of adjacent bombs
     */
    public int getAdjacentBombCount(int x, int y) {
        return 0;
    }

    /**
     * Checks if there is a bomb on the current position
     *
     * @param x X
     * @param y Y
     * @return true if bomb on position
     */
    public boolean isBombOnPosition(int x, int y) {
        return false;
    }

    /**
     * Returns the amount of bombs on the field
     *
     * @return bomb count
     */
    public int getBombCount() {
        return 0;
    }

    /**
     * total bombs - number of flags
     *
     * @return remaining bomb count
     */
    public int getRemainingBombCount() {

        return 0;
    }

    /**
     * returns true if every flag is on a bomb, else false
     *
     * @return if player won
     */
    public boolean didWin() {
        //return getRemainingBombCount() <= 0;
        return false;
    }

    /**
     * returns true if player revealed a bomb, else false
     *
     * @return if player lost
     */
    public boolean didLose() {
        return false; //isBombOnPosition() == true;
    }

    public int getRows() {
        return rowsCount;
    }

    public int getColumns() {
        return columnsCount;
    }

}
