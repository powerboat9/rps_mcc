package com.example.project2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

// Main JavaFX application
// note: has difficulty selector
//     -Owen Avery
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Context ctx = new Context(stage);
        stage.setTitle("Rock, Paper, Scissors");
        ctx.displayMainMenu();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}