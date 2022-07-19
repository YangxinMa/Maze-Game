package ca.MazeGameOnline.API;


import ca.MazeGameOnline.Model.Game;

import java.util.ArrayList;
import java.util.List;

public class ApiLocationWrapper {
    public int x;
    public int y;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiLocationWrapper makeFromCellLocation(int x, int y) {
        ApiLocationWrapper location = new ApiLocationWrapper();
        location.x = y;
        location.y = x;


        return location;
    }
    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static List<ApiLocationWrapper> makeFromCellLocations(Game game) {
        List<ApiLocationWrapper> locations = new ArrayList<>();

        // Populate this object!
        for(int i = 0; i < game.getCats().size();i++) {
            ApiLocationWrapper cat = ApiLocationWrapper.makeFromCellLocation(game.getCats().get(i).getPosX(), game.getCats().get(i).getPosY());
            locations.add(cat);
        }
        return locations;
    }
}