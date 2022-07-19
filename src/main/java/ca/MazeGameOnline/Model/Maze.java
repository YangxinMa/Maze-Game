package ca.MazeGameOnline.Model;

import java.util.Random;

/*
    A prototype "Maze" definition class.
    A maze is a 2D array in boardTile data type with fixed size.
    A maze is generated by depth first search algorithm.
*/

public class Maze {
    private final int ROW = 15;
    private final int COLUMN = 20;
    private BoardTile[][] maze = new BoardTile[ROW][COLUMN];

    public Maze() {
        int[][] mazeMapOnly = createMazeMapOnly(ROW-2, COLUMN-2);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                BoardTile newTile;
                if(i == 0 || i == ROW - 1 || j == 0 || j == COLUMN - 1){
                    newTile = new BoardTile(0);
                    newTile.setToRevealed();
                }
                else{
                    newTile = new BoardTile(mazeMapOnly[i-1][j-1]);
                }
                maze[i][j] = newTile;
            }
        }
    }

    public BoardTile[][] getMaze() {
        return maze;
    }

    private int returnSurroundingTiles(int[][] arr, int x, int y, int xBound, int yBound) {
        int position;
        int tempX;
        int tempY;
        int[] list = {0, 0, 0, 0};
        int count = 0;
        if (x - 1 >= 0 && arr[x - 1][y] != 1) {
            tempX = x - 1;
            tempY = y;
            if (checkSurroundingTiles(arr, tempX, tempY, xBound, yBound)) {
                list[0] = 1;
                count += 1;
            }
        }
        if (x + 1 < xBound && arr[x + 1][y] != 1) {
            tempX = x + 1;
            tempY = y;
            if (checkSurroundingTiles(arr, tempX, tempY, xBound, yBound)) {
                list[3] = 1;
                count += 1;
            }
        }
        if (y - 1 >= 0 && arr[x][y - 1] != 1) {
            tempX = x;
            tempY = y - 1;
            if (checkSurroundingTiles(arr, tempX, tempY, xBound, yBound)) {
                list[1] = 1;
                count += 1;
            }
        }
        if (y + 1 < yBound && arr[x][y + 1] != 1) {
            tempX = x;
            tempY = y + 1;
            if (checkSurroundingTiles(arr, tempX, tempY, xBound, yBound)) {
                list[2] = 1;
                count += 1;
            }
        }

        if (count == 0) {
            return -1;
        }
        Random random = new Random();
        int p = random.nextInt(count);
        count = 0;
        int index = 0;
        while (true) {
            if (list[index] == 1) {
                if (count == p) {
                    position = index;
                    break;
                }
                count++;
            }
            index++;
        }
        return position;
    }

    private boolean checkSurroundingTiles(int[][] arr, int x, int y, int xBound, int yBound) {
        int count = 0;
        if (x - 1 >= 0) {
            if (arr[x - 1][y] == 1) {
                count += 1;
            }
        }
        if (x + 1 < xBound) {
            if (arr[x + 1][y] == 1) {
                count += 1;
            }
        }
        if (y - 1 >= 0) {
            if (arr[x][y - 1] == 1) {
                count += 1;
            }
        }
        if (y + 1 < yBound) {
            if (arr[x][y + 1] == 1) {
                count += 1;
            }
        }
        return count == 1;
    }

    private boolean checkLoop(int[][] arr, int x, int y, int xBound, int yBound) {
        int count = 0;
        if (x - 1 >= 0 && y - 1 >= 0) {
            if (arr[x - 1][y - 1] == 1) {
                count += 1;
            }
            if (arr[x][y - 1] == 1) {
                count += 1;
            }
            if (arr[x - 1][y] == 1) {
                count += 1;
            }
            if (count == 3) {
                return false;
            }
            count = 0;
        }
        if (x - 1 >= 0 && y + 1 < yBound) {
            if (arr[x - 1][y] == 1) {
                count += 1;
            }
            if (arr[x - 1][y + 1] == 1) {
                count += 1;
            }
            if (arr[x][y + 1] == 1) {
                count += 1;
            }
            if (count == 3) {
                return false;
            }
            count = 0;
        }
        if (y - 1 >= 0 && x + 1 < xBound) {
            if (arr[x][y - 1] == 1) {
                count += 1;
            }
            if (arr[x + 1][y - 1] == 1) {
                count += 1;
            }
            if (arr[x + 1][y] == 1) {
                count += 1;
            }
            if (count == 3) {
                return false;
            }
            count = 0;
        }
        if (x + 1 < xBound && y + 1 < yBound) {
            if (arr[x][y + 1] == 1) {
                count += 1;
            }
            if (arr[x + 1][y] == 1) {
                count += 1;
            }
            if (arr[x + 1][y + 1] == 1) {
                count += 1;
            }
            return count != 3;
        }
        return true;
    }

    private int[][] initMazeMap(int row, int column) {
        int[][] arr = new int[row][column];
        arr[0][0] = 1;
        int[] branch = new int[row * column * 2];
        int branchIndex = 0;

        int x = 0;
        int y = 0;

        while (true) {
            branch[branchIndex] = x;
            branch[branchIndex + 1] = y;
            int next = returnSurroundingTiles(arr, x, y, row, column);
            if (next == -1) {
                x = branch[branchIndex - 2];
                y = branch[branchIndex - 1];
                branch[branchIndex] = 0;
                branch[branchIndex + 1] = 0;
                branchIndex -= 2;
                if (branchIndex == 0) {
                    break;
                }
            } else {
                if (next == 0) {
                    x = x - 1;
                }
                if (next == 1) {
                    y = y - 1;
                }
                if (next == 2) {
                    y = y + 1;
                }
                if (next == 3) {
                    x = x + 1;
                }
                arr[x][y] = 1;
                branchIndex += 2;
            }
        }
        return arr;
    }

    private int[][] createMazeMapOnly(int row, int column) {
        int[][] arr = initMazeMap(row, column);

        while (arr[0][column - 1] != 1 || arr[row - 1][0] != 1 || arr[row - 1][column - 1] != 1) {
            arr = initMazeMap(row, column);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (arr[i][j] == 0 && checkLoop(arr, i, j, row, column)) {
                    arr[i][j] = 1;
                }
            }
        }
        return arr;
    }

    public void revealMap(int posX, int posY) {
        maze[posX][posY].setToRevealed();
        if (posX > 0) {
            maze[posX - 1][posY].setToRevealed();
            if (posY > 0) {
                maze[posX][posY - 1].setToRevealed();
                maze[posX - 1][posY - 1].setToRevealed();
            }
            if (posY < COLUMN - 1) {
                maze[posX][posY + 1].setToRevealed();
                maze[posX - 1][posY + 1].setToRevealed();
            }
        }
        if (posX < ROW - 1) {
            maze[posX + 1][posY].setToRevealed();
            if (posY > 0) {
                maze[posX][posY - 1].setToRevealed();
                maze[posX + 1][posY - 1].setToRevealed();
            }
            if (posY < COLUMN - 1) {
                maze[posX][posY + 1].setToRevealed();
                maze[posX + 1][posY + 1].setToRevealed();
            }
        }
    }

    public void revealAllTiles() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                maze[i][j].setToRevealed();
            }
        }
    }
}