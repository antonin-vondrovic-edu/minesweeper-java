package cz.educanet.minesweeper.logic;

import java.util.Random;

public class Minesweeper {

    private int blockAmount;
    private final int bombAmount = 25;
    private int flagAmount = bombAmount;
    private int remainingBombAmount = bombAmount - flagAmount;
    private boolean isBomb;

    private final Grid[][] grid;
    private final int rowsCount;
    private final int columnsCount;

    public Minesweeper(int rows, int columns) { // generate bombs here
        isBomb = false;
        this.rowsCount = rows;
        this.columnsCount = columns;
        grid = new Grid[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[j][i] = new Grid();
            }
        }
        mineFieldTime(rows, columns);
        blockAmount = (rows * columns) - bombAmount;
    }

    public Grid[][] mineFieldTime(int rows, int columns) {
        Random rnd = new Random();
        for (int i = 0; i < bombAmount; i++) {
            int x = rnd.nextInt(columns);
            int y = rnd.nextInt(rows);
            if(grid[x][y].getBomb()) {
                i--;
            }
            else {
                grid[x][y].setBomb(true);
            }
        }
        return grid;
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
        return grid[x][y].getFieldState();
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
    // where i right clicked
    public void toggleFieldState(int x, int y) {
        if(grid[x][y].getFieldState() == 2) {
            grid[x][y].setFieldState(0);
        }
        else {
            grid[x][y].setFieldState(2);
        }
    }

    /**
     * Reveals the field and all fields adjacent (with 0 adjacent bombs) and all fields adjacent to the adjacent fields... ect.
     *
     * @param x X
     * @param y Y
     */
    public void reveal(int x, int y) { // where i left clicked
        if(grid[x][y].getBomb()) {
            isBomb = true;
        }
        recursiveReveal(x, y);
        blockAmount--;
    }

    public void recursiveReveal(int x, int y) {
        grid[x][y].setFieldState(1);
        if(getAdjacentBombCount(x, y) == 0) {
            boolean topleft = (x != 0) && (y != 0);
            boolean topright = (x != columnsCount - 1) && (y != 0);
            boolean botleft = (x != 0) && (y != rowsCount - 1);
            boolean botright = (x != columnsCount - 1) && (y != rowsCount - 1);

            if (topleft && !grid[x - 1][y - 1].getBomb() && grid[x - 1][y - 1].getFieldState() != 1){recursiveReveal(x - 1, y - 1); blockAmount--; }// top left
            if (topright && !grid[x + 1][y - 1].getBomb() && grid[x + 1][y - 1].getFieldState() != 1){blockAmount--; recursiveReveal(x + 1, y - 1); } // top right
            if (botright && !grid[x + 1][y + 1].getBomb() && grid[x + 1][y + 1].getFieldState() != 1){blockAmount--; recursiveReveal(x + 1, y + 1); } // bot right
            if (botleft && !grid[x - 1][y + 1].getBomb() && grid[x - 1][y + 1].getFieldState() != 1){blockAmount--; recursiveReveal(x - 1, y + 1); }// bot left

            if ((topleft || topright) && !grid[x][y - 1].getBomb() && grid[x][y - 1].getFieldState() != 1){blockAmount--; recursiveReveal(x, y - 1); } // top
            if ((botright || botleft) && !grid[x][y + 1].getBomb() && grid[x][y + 1].getFieldState() != 1){blockAmount--; recursiveReveal(x, y + 1); } // bot
            if ((botleft || topleft) && !grid[x - 1][y].getBomb() && grid[x - 1][y].getFieldState() != 1){blockAmount--; recursiveReveal(x - 1, y); }// left
            if ((topright || botright) && !grid[x + 1][y].getBomb() && grid[x + 1][y].getFieldState() != 1) {blockAmount--; recursiveReveal(x + 1, y); }// right
        }
    }

    /**
     * Returns the amount of adjacent bombs
     *
     * @param x X
     * @param y Y
     * @return number of adjacent bombs
     */
    public int getAdjacentBombCount(int x, int y) {
        int bombcount = 0;

        boolean topleft = (x != 0) && (y != 0);
        boolean topright = (x != columnsCount - 1) && (y != 0);
        boolean botleft = (x != 0) && (y != rowsCount - 1);
        boolean botright = (x != columnsCount - 1) && (y != rowsCount - 1);

        if(topleft && grid[x - 1][y - 1].getBomb()) bombcount++; // top left
        if(topright && grid[x + 1][y - 1].getBomb()) bombcount++; // top right
        if(botright && grid[x + 1][y + 1].getBomb()) bombcount++; // bot right
        if(botleft && grid[x - 1][y + 1].getBomb()) bombcount++; // bot left

        if((topleft || topright) && grid[x][y - 1].getBomb()) bombcount++; // top
        if((botright || botleft) && grid[x][y + 1].getBomb()) bombcount++; // bot
        if((botleft || topleft) && grid[x - 1][y].getBomb()) bombcount++; // left
        if((topright || botright) && grid[x + 1][y].getBomb()) bombcount++; // right


        return bombcount;
    }

    /**
     * Checks if there is a bomb on the current position
     *
     * @param x X
     * @param y Y
     * @return true if bomb on position
     */
    public boolean isBombOnPosition(int x, int y) {
        return (grid[x][y].getBomb());
    }

    /**
     * Returns the amount of bombs on the field
     *
     * @return bomb count
     */
    public int getBombCount() {
        return bombAmount;
    }

    /**
     * total bombs - number of flags
     *
     * @return remaining bomb count
     */
    public int getRemainingBombCount() {
        return remainingBombAmount;
    }

    /**
     * returns true if every flag is on a bomb, else false
     *
     * @return if player won
     */
    public boolean didWin() {
        return (blockAmount == 0);
    }

    /**
     * returns true if player revealed a bomb, else false
     *
     * @return if player lost
     */
    public boolean didLose() {
        return isBomb;
    }

    public int getRows() {
        return rowsCount;
    }

    public int getColumns() {
        return columnsCount;
    }
}
