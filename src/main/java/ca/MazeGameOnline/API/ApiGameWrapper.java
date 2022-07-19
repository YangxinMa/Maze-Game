package ca.MazeGameOnline.API;

import ca.MazeGameOnline.Model.*;

public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiGameWrapper makeFromGame(Game game, int id) {
        ApiGameWrapper wrapper = new ApiGameWrapper();
        wrapper.gameNumber = id;

        // Populate this object!
        wrapper.isGameWon = game.isGameWon();
        wrapper.isGameLost = game.isEatenByCat();
        wrapper.numCheeseFound = game.getCurrentScore();
        wrapper.numCheeseGoal = game.getMaxScore();

        return wrapper;

    }
}