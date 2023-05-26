package com.example.project2.menu;

import com.example.project2.Context;
import com.example.project2.Difficulty;
import com.example.project2.game.Result;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.function.Supplier;

// Handles main menu state
//     -Owen Avery
public class MainMenu {
    private final Scene scene;
    private final Context ctx;
    private final StringProperty name;
    private final StringProperty rounds;
    private final Runnable setDefaultDiff;
    private final Runnable setDefaultFocus;
    private final Supplier<Supplier<Result>> getDiff;

    public void clear() {
        name.set("");
        rounds.set("");
    }

    private void onBegin() {
        String name = this.name.get();
        String err = null;

        if (name.length() == 0) {
            err = "Name cannot be blank";
        }
        try {
            int totalRounds = Integer.parseInt(rounds.get());
            if (totalRounds < 1) {
                throw new NumberFormatException();
            }

            if (err == null) {
                ctx.startGame(name, totalRounds, getDiff.get());
                return;
            }
        } catch (NumberFormatException ex) {
            err = (err == null) ? "" : (err + "\n");
            err += "Number of games must be a positive integer";
        }

        Alert alert = new Alert(Alert.AlertType.ERROR, err);
        alert.show();
    }

    public MainMenu(Context ctx) {
        this.ctx = ctx;
        Label headerLabel = new Label("Rock, Paper, Scissors");
        headerLabel.setFont(new Font("Ariel", 28));
        headerLabel.setTextFill(Color.WHITE);
        headerLabel.setPrefWidth(Double.MAX_VALUE);
        headerLabel.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        nameField.setFont(new Font("Ariel", 20));
        nameField.setPromptText("Name");
        name = nameField.textProperty();

        TextField roundField = new TextField();
        roundField.setFont(new Font("Ariel", 20));
        roundField.setPromptText("Number of Rounds");
        rounds = roundField.textProperty();

        RadioButton[] diffButtons = new RadioButton[4];
        diffButtons[0] = new RadioButton("Easy");
        diffButtons[0].setUserData(Difficulty.EASY);
        diffButtons[1] = new RadioButton("Normal");
        diffButtons[1].setUserData(Difficulty.NORMAL);
        diffButtons[2] = new RadioButton("Hard");
        diffButtons[2].setUserData(Difficulty.HARD);
        diffButtons[3] = new RadioButton("Lunatic");
        diffButtons[3].setUserData(Difficulty.LUNATIC);

        ToggleGroup diffGroup = new ToggleGroup();
        for (int i = 0; i < diffButtons.length; i++) {
            diffButtons[i].setToggleGroup(diffGroup);
            diffButtons[i].setFont(new Font("Ariel", 16));
            diffButtons[i].setTextFill(Color.WHITE);
        }
        getDiff = () -> (Supplier<Result>) diffGroup.getSelectedToggle().getUserData();
        setDefaultDiff = () -> diffButtons[1].setSelected(true);
        VBox diffBox = new VBox(diffButtons);
        diffBox.setSpacing(10);

        Button begin = new Button("Click to Begin");
        begin.setFont(new Font("Ariel", 20));

        begin.setOnMouseClicked(e -> onBegin());
        begin.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onBegin();
            }
        });
        setDefaultFocus = begin::requestFocus;

        VBox mainBox = new VBox(
                headerLabel, nameField, roundField, diffBox, begin
        );
        mainBox.setBackground(
                new Background(
                        new BackgroundFill(Color.DARKGREEN, null, null)
                )
        );
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(20);
        mainBox.setPadding(new Insets(10));

        scene = new Scene(mainBox, 400, 400);
    }

    public void display(Stage stage) {
        name.set("");
        rounds.set("");
        setDefaultDiff.run();
        stage.setScene(scene);
        setDefaultFocus.run();
    }
}
