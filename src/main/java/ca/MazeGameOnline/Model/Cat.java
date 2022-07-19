package ca.MazeGameOnline.Model;

import java.util.Random;

/*
    A prototype "Cat" definition class.
    A cat has its own position info, and it moves with pseudorandom direction after checking the current maze status.
*/

public class Cat {
    // 0:UP; 1:DOWN; 2:LEFT; 3:RIGHT
    private final int ROW = 15;
    private final int COLUMN = 20;
    private final int MAX_DIRECTION = 4;
    private final int MIN_DIRECTION = 0;

    private int posX;
    private int posY;
    private int previousStep = -1;

    public Cat(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    private int randomDirection() {
        Random rand = new Random();
        int randomNum = rand.nextInt(MAX_DIRECTION - MIN_DIRECTION);
        return randomNum;
    }

    private int checkMove(Maze maze) {
        int tempPosX = posX;
        int tempPosY = posY;
        int randDir;
        do {
            randDir = randomDirection();
            tempPosX = posX;
            tempPosY = posY;
            switch (randDir) {
                case 0:
                    tempPosY = posY - 1;
                    break;
                case 1:
                    tempPosY = posY + 1;
                    break;
                case 2:
                    tempPosX = posX - 1;
                    break;
                case 3:
                    tempPosX = posX + 1;
                    break;
            }
        } while (tempPosX < 0 || tempPosY < 0 || tempPosX > ROW - 1 || tempPosY > COLUMN - 1
                || maze.getMaze()[tempPosX][tempPosY].getItemOnBoard() == 0);
        return randDir;
    }

    public void move(Maze maze) {
        int limit = 0;
        int randDir = checkMove(maze);
        if (previousStep != -1) {
            while ((randDir == 0 && previousStep == 1)
                    || (randDir == 1 && previousStep == 0)
                    || (randDir == 2 && previousStep == 3)
                    || (randDir == 3 && previousStep == 2)) {
                randDir = checkMove(maze);
                limit++;
                if (limit == 30) {
                    if (previousStep == 0) {
                        randDir = 1;
                    } else if (previousStep == 1) {
                        randDir = 0;
                    } else if (previousStep == 2) {
                        randDir = 3;
                    } else {
                        randDir = 2;
                    }
                    break;
                }
            }
        }

        previousStep = randDir;

        switch (randDir) {
            case 0:
                posY -= 1;
                break;
            case 1:
                posY += 1;
                break;
            case 2:
                posX -= 1;
                break;
            case 3:
                posX += 1;
                break;
        }
    }
}
