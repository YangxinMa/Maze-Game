package ca.MazeGameOnline.API;

import ca.MazeGameOnline.Model.*;
import java.util.List;

public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiBoardWrapper makeFromGame(Game game) {
        ApiBoardWrapper wrapper = new ApiBoardWrapper();

        // Populate this object!
        wrapper.boardWidth = game.getCOLUMN();
        wrapper.boardHeight = game.getROW();
        wrapper.mouseLocation = ApiLocationWrapper.makeFromCellLocation(game.getMouse().getPosX(),game.getMouse().getPosY());
        wrapper.cheeseLocation = ApiLocationWrapper.makeFromCellLocation(game.getCheese().getPosX(),game.getCheese().getPosY());
        wrapper.catLocations = ApiLocationWrapper.makeFromCellLocations(game);
        wrapper.hasWalls = game.getMazeWallInfo();
        wrapper.isVisible = game.getVisibilityInfo();

        return wrapper;
    }
}