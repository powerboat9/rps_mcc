package com.example.project2;

import com.example.project2.game.Game;
import com.example.project2.game.GameState;
import com.example.project2.game.Move;
import com.example.project2.game.Result;
import com.example.project2.menu.MainMenu;
import javafx.stage.Stage;

import java.util.function.Supplier;

// Combines program state
//     -Owen Avery
public class Context {
    private final Stage stage;
    private final Stage pieStage;
    private final MainMenu mainMenu;
    private final Game game;
    private GameState gameState;

    public Context(Stage stage) {
        this.stage = stage;
        pieStage = new Stage();
        pieStage.setAlwaysOnTop(true);
        mainMenu = new MainMenu(this);
        game = new Game(this);
    }

    public void onPlayerMove(Move m) {
        gameState.onPlayerMove(m);
    }

    public void displayMainMenu() {
        pieStage.close();
        mainMenu.display(stage);
        gameState = null;
    }

    public void startGame(String name, int totalRounds, Supplier<Result> diff) {
        gameState = new GameState(game, name, totalRounds, diff);
        game.display(stage);
    }

    public void startPie() {
        game.displayPie(pieStage);
        pieStage.show();
    }

    public void resetGame() {
        gameState.reset();
    }
}
