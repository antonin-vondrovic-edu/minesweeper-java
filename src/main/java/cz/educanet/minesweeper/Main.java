package cz.educanet.minesweeper;

import cz.educanet.minesweeper.logic.Minesweeper;
import cz.educanet.minesweeper.presentation.Cell;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static JButton[][] cells;

    public static void main(String[] args) {
        Minesweeper logic = new Minesweeper(20, 28);
        cells = new JButton[logic.getRows()][logic.getColumns()];

        JFrame frame = new JFrame("Minesweeper");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(logic.getRows(), logic.getColumns(), 1, 1));
        for (int i = 0; i < logic.getRows(); i++) {
            for (int j = 0; j < logic.getColumns(); j++) {
                Cell btn = new Cell();
                cells[i][j] = btn;
                panel.add(btn);
                int finalI = i;
                int finalJ = j;
                btn.setMouseClick(() -> {
                    logic.reveal(finalJ, finalI);
                    redraw(logic);
                });
                btn.setContextClick(() -> {
                    logic.toggleFieldState(finalJ, finalI);
                    redraw(logic);
                });
            }
        }
        redraw(logic);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void redraw(Minesweeper logic) {
        if(logic.didWin())
            JOptionPane.showMessageDialog(null, "You won the game!");
        else if(logic.didLose())
            JOptionPane.showMessageDialog(null, "You lost the game!");

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                int field = logic.getField(j, i);

                cells[i][j].setBackground(Color.darkGray);
                cells[i][j].setForeground(Color.white);

                if(field == 0) {
                    cells[i][j].setText("");
                }
                else if(field == 1) {
                    cells[i][j].setBackground(Color.white);
                    cells[i][j].setForeground(Color.darkGray);

                    int bombCount = logic.getAdjacentBombCount(j, i);
                    if(bombCount > 0) {
                        cells[i][j].setForeground(new Color(bombCount / 8f, 0, 0));
                        cells[i][j].setText(bombCount + "");
                    }
                    else
                        cells[i][j].setText("");
                }
                else if(field == 2) {
                    cells[i][j].setText("\uD83D\uDEA9");
                }
                else if(field == 3) {
                    cells[i][j].setText("?");
                }
            }
        }
    }
}
