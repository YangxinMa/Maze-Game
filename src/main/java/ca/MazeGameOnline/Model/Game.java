package ca.MazeGameOnline.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int ROW = 15;
    private static final int COLUMN = 20;
    private static final int invalidMoveTypeOne = 1;
    private static final int invalidMoveTypeTwo = 2;

    private int maxScore = 5;
    private int currentScore = 0;

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    private Maze maze = new Maze();
    private Cat cat1 = new Cat(ROW - 2, 1);
    private Cat cat2 = new Cat(1, COLUMN - 2);
    private Cat cat3 = new Cat(ROW - 2, COLUMN - 2);
    private Mouse mouse = new Mouse(1, 1);



    public int getMaxScore() {
        return maxScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public Maze getMaze() {
        return maze;
    }

    public boolean isGameWon(){
        return currentScore == maxScore;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Cheese getCheese() {
        return cheese;
    }

    public List<Cat> getCats() {
        return cats;
    }

    private  Cheese cheese = new Cheese(maze);
    private List<Cat> cats = new ArrayList<>();

    public Game(){
        cats.add(cat1);
        cats.add(cat2);
        cats.add(cat3);
        addCat();
        addMouse();
        updateCheese(cheese);
        maze.revealMap(mouse.getPosX(), mouse.getPosY());
    }

    public boolean processUserInput(final String userInput) {
        boolean isSuccessfulMove = true;
        if (userInput.equalsIgnoreCase("c")) {
            maxScore = 1;

        } else if (userInput.equalsIgnoreCase("m")) {
            maze.revealAllTiles();
        } else if (userInput.equalsIgnoreCase("w")
                || userInput.equalsIgnoreCase("s")
                || userInput.equalsIgnoreCase("a")
                || userInput.equalsIgnoreCase("d")) {
            if (!mouse.checkMove(maze, userInput)) {
                return false;
            }
            moveMouse(userInput);



            if (mouse.getPosX() == cheese.getPosX() && mouse.getPosY() == cheese.getPosY()) {
                currentScore++;

                cheese = new Cheese(maze);
                updateCheese(cheese);
            } else if ((cat1.getPosX() == cheese.getPosX() && cat1.getPosY() == cheese.getPosY())
                    || (cat2.getPosX() == cheese.getPosX() && cat2.getPosY() == cheese.getPosY())
                    || (cat3.getPosX() == cheese.getPosX() && cat3.getPosY() == cheese.getPosY())) {
                // Currently nothing happens if one of cats is on the cheese.
            }
            else {
                updateCheese(cheese);
            }
            maze.revealMap(mouse.getPosX(), mouse.getPosY());
        } else {
            throw new IllegalArgumentException("Invalid action type:" + userInput);
        }
        return isSuccessfulMove;
    }

    public boolean[][] getVisibilityInfo() {
        boolean[][] visibilityInfo = new boolean[ROW][COLUMN];
        for(int i =0; i< ROW; i++){
            for(int j = 0; j<COLUMN; j++){
                visibilityInfo[i][j] = maze.getMaze()[i][j].isRevealed();
            }
        }
        return visibilityInfo;
    }

    public boolean[][] getMazeWallInfo(){
        boolean[][] wallInfo = new boolean[ROW][COLUMN];
        for(int i =0; i< ROW; i++){
            for(int j = 0; j<COLUMN; j++){
                wallInfo[i][j] = maze.getMaze()[i][j].getItemOnBoard() == 0;
            }
        }
        return wallInfo;
    }

    public static int getROW() {
        return ROW;
    }

    public static int getCOLUMN() {
        return COLUMN;
    }

    public void addCat() {
        maze.getMaze()[cat1.getPosX()][cat1.getPosY()].setItemOnBoard(2);
        maze.getMaze()[cat2.getPosX()][cat2.getPosY()].setItemOnBoard(2);
        maze.getMaze()[cat3.getPosX()][cat3.getPosY()].setItemOnBoard(2);
    }

    public void moveCat() {
        maze.getMaze()[cat1.getPosX()][cat1.getPosY()].setItemOnBoard(1);
        maze.getMaze()[cat2.getPosX()][cat2.getPosY()].setItemOnBoard(1);
        maze.getMaze()[cat3.getPosX()][cat3.getPosY()].setItemOnBoard(1);
        cat1.move(maze);
        cat2.move(maze);
        cat3.move(maze);
        maze.getMaze()[cat1.getPosX()][cat1.getPosY()].setItemOnBoard(2);
        maze.getMaze()[cat2.getPosX()][cat2.getPosY()].setItemOnBoard(2);
        maze.getMaze()[cat3.getPosX()][cat3.getPosY()].setItemOnBoard(2);
    }

    public void addMouse() {
        maze.getMaze()[mouse.getPosX()][mouse.getPosY()].setItemOnBoard(3);
    }

    public void moveMouse(String direction) {
        maze.getMaze()[mouse.getPosX()][mouse.getPosY()].setItemOnBoard(1);
        mouse.move(maze, direction);
        maze.getMaze()[mouse.getPosX()][mouse.getPosY()].setItemOnBoard(3);
    }

    public void updateCheese(Cheese cheese) {
        maze.getMaze()[cheese.getPosX()][cheese.getPosY()].setItemOnBoard(4);
    }

    public boolean isEatenByCat() {
        return (cat1.getPosX() == mouse.getPosX() && cat1.getPosY() == mouse.getPosY())
                || (cat2.getPosX() == mouse.getPosX() && cat2.getPosY() == mouse.getPosY())
                || (cat3.getPosX() == mouse.getPosX() && cat3.getPosY() == mouse.getPosY());
    }
}
