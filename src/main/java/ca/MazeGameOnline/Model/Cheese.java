package ca.MazeGameOnline.Model;

import java.util.Random;

/*
    A prototype "Cheese" definition class.
    A cheese has its own position info.
*/

public class Cheese {
    private final int ROW = 15;
    private final int COLUMN = 20;

    private int posX;
    private int posY;

    public Cheese(Maze maze) {
        Random random = new Random();
        int x = random.nextInt(15);
        int y = random.nextInt(20);
        while (maze.getMaze()[x][y].getItemOnBoard() == 0
                || (x == 1 || y == 1 || x == ROW - 2 || y == COLUMN - 2)) {
            x = random.nextInt(15);
            y = random.nextInt(20);
        }
        this.posX = x;
        this.posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
