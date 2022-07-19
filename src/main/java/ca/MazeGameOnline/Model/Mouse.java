package ca.MazeGameOnline.Model;

/*
    A prototype "Mouse" definition class.
    A mouse has its own position info, and it moves with player's input directions after checking the current maze
        status and the input correctness.
*/

public class Mouse {
    // 0:UP; 1:DOWN; 2:LEFT; 3:RIGHT
    private final int ROW = 15;
    private final int COLUMN = 20;

    private int posX;
    private int posY;

    public Mouse(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean checkMove(Maze maze, String direction) {
        String dir = direction.toLowerCase();
        int tempPosX = posX;
        int tempPosY = posY;
        switch (dir) {
            case "w":
                tempPosX = posX - 1;
                break;
            case "s":
                tempPosX = posX + 1;
                break;
            case "a":
                tempPosY = posY - 1;
                break;
            case "d":
                tempPosY = posY + 1;
                break;
        }
        return tempPosX >= 0 && tempPosY >= 0 && tempPosX <= ROW - 1 && tempPosY <= COLUMN - 1
                && maze.getMaze()[tempPosX][tempPosY].getItemOnBoard() != 0;
    }

    public void move(Maze maze, String direction) {
        String dir = direction.toLowerCase();
        switch (dir) {
            case "w":
                posX -= 1;
                break;
            case "s":
                posX += 1;
                break;
            case "a":
                posY -= 1;
                break;
            case "d":
                posY += 1;
                break;
        }
    }
}