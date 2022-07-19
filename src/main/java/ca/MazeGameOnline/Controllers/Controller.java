package ca.MazeGameOnline.Controllers;

import ca.MazeGameOnline.API.ApiBoardWrapper;
import ca.MazeGameOnline.API.ApiGameWrapper;
import ca.MazeGameOnline.Model.Game;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Game NUmber Not Found.")
class ResourceNotFoundException extends RuntimeException {
}

@RestController
public class Controller {
    private List<ApiGameWrapper> ApiGames = new ArrayList<>();
    private List<Game> games = new ArrayList<>();

    @GetMapping("/api/about")
    public String getAbout() {
        return "Yangxin Ma";
    }

    @GetMapping("/api/games")
    public List<ApiGameWrapper> getGames() {
        return ApiGames;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createNewGame() {
        Game game = new Game();
        games.add(game);
        int gameNumber = games.size();
        ApiGameWrapper temp = ApiGameWrapper.makeFromGame(game, gameNumber);
        ApiGames.add(temp);
        return temp;
    }

    @GetMapping("/api/games/{id}")
    public ApiGameWrapper getGameId(@PathVariable("id") int gameNumber) {
        for (ApiGameWrapper game : ApiGames) {
            if (game.gameNumber == gameNumber) {
                return game;
            }
        }
        throw new ResourceNotFoundException();
    }

    @GetMapping("/api/games/{id}/board")
    public ApiBoardWrapper getGameBoard(@PathVariable("id") int gameNumber) {
        for (ApiGameWrapper game : ApiGames) {
            if (game.gameNumber == gameNumber) {
                return ApiBoardWrapper.makeFromGame(games.get(gameNumber - 1));
            }
        }
        throw new ResourceNotFoundException();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("api/games/{id}/moves")
    public void makeGameMoves(@PathVariable("id") int gameNumber,
                                     @RequestBody String movement) {
        boolean checkMove = true;

        gameNumber = gameNumber - 1;

        if(gameNumber < 0 || gameNumber >= games.size())
            throw new ResourceNotFoundException();

        switch (movement) {
            case "MOVE_UP":
                checkMove = games.get(gameNumber).processUserInput("w");
                break;
            case "MOVE_DOWN":
                checkMove = games.get(gameNumber).processUserInput("s");
                break;
            case "MOVE_LEFT":
                checkMove = games.get(gameNumber).processUserInput("a");
                break;
            case "MOVE_RIGHT":
                checkMove = games.get(gameNumber).processUserInput("d");
                break;
            case "MOVE_CATS":
                games.get(gameNumber).moveCat();
                break;
            default:
                checkMove = false;
        }
        if(!checkMove){
            throw new IllegalArgumentException();
        }
        ApiGameWrapper temp = ApiGameWrapper.makeFromGame(games.get(gameNumber), gameNumber + 1);
        ApiGames.set(gameNumber, temp);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("api/games/{id}/cheatstate")
    public void makeCheat(@PathVariable("id") int gameNumber, @RequestBody String cheat)
    {
        gameNumber = gameNumber - 1;
        if(gameNumber < 0 || gameNumber >= games.size())
            throw new ResourceNotFoundException();
        switch (cheat)
        {
            case "1_CHEESE":
                games.get(gameNumber).setMaxScore(1);
                break;
            case "SHOW_ALL":
                games.get(gameNumber).processUserInput("m");
                break;
            default:
                throw new IllegalArgumentException();
        }
        ApiGameWrapper temp = ApiGameWrapper.makeFromGame(games.get(gameNumber), gameNumber + 1);
        ApiGames.set(gameNumber, temp);
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
    }
}
