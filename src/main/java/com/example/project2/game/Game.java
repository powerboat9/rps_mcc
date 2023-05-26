package com.example.project2.game;

import com.example.project2.Context;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// Handles persistent game state (javafx nodes)
//     -Owen Avery
public class Game {
    private static final String IMG_ROOT = "file:src/main/resources/com/example/project2/";

    private static final Image IMG_ROCK = new Image(
            IMG_ROOT + "rock.jpg", 100, 100, false, true, true
    );
    private static final Image IMG_PAPER = new Image(
            IMG_ROOT + "paper.jpg", 100, 100, false, true, true
    );
    private static final Image IMG_SCISSOR = new Image(
            IMG_ROOT + "scissor.jpg", 100, 100, false, true, true
    );

    private final Scene scene;

    private final StringProperty rounds;
    private final StringProperty wins;
    private final StringProperty losses;
    private final StringProperty ties;
    public final StringProperty infoText;

    private final Scene scenePie;
    private final ObjectProperty<ObservableList<PieChart.Data>> pieData;

    public void bindGameState(GameState state) {
        rounds.bind(Bindings.concat("Rounds: ", state.getRounds()));
        wins.bind(Bindings.concat("Wins: ", state.getWins()));
        losses.bind(Bindings.concat("Losses: ", state.getLosses()));
        ties.bind(Bindings.concat("Ties: ", state.getTies()));
        pieData.set(state.getPieData());
    }

    public void setInfo(String txt) {
        infoText.set(txt);
    }

    private static void styleLabel(Label label) {
        label.setFont(new Font("Ariel", 14));
        label.setPrefWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.WHITE);
    }

    private static void styleButton(Button button) {
        button.setFont(new Font("Ariel", 18));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(
                new BackgroundFill(Color.DARKGREEN.darker(), null, null)
        ));
    }

    public Game(Context ctx) {
        ImageView rock = new ImageView(IMG_ROCK);
        ImageView paper = new ImageView(IMG_PAPER);
        ImageView scissor = new ImageView(IMG_SCISSOR);
        HBox imgBox = new HBox(rock, paper, scissor);
        imgBox.setSpacing(10);
        imgBox.setPadding(new Insets(20));
        imgBox.setAlignment(Pos.CENTER);
        imgBox.setBackground(
                new Background(
                        new BackgroundFill(Color.DARKGREEN, null, null)
                )
        );

        Label info = new Label();
        styleLabel(info);
        infoText = info.textProperty();

        Label roundLabel = new Label();
        styleLabel(roundLabel);
        rounds = roundLabel.textProperty();

        Label winLabel = new Label();
        styleLabel(winLabel);
        wins = winLabel.textProperty();

        Label lossLabel = new Label();
        styleLabel(lossLabel);
        losses = lossLabel.textProperty();

        Label tieLabel = new Label();
        styleLabel(tieLabel);
        ties = tieLabel.textProperty();

        GridPane statPane = new GridPane();
        statPane.addRow(0, roundLabel, winLabel, lossLabel, tieLabel);
        for (int i = 0; i < 4; i++) {
            ColumnConstraints c = new ColumnConstraints();
            c.setPercentWidth(25);
            statPane.getColumnConstraints().add(c);
        }

        rock.setOnMouseClicked(e -> ctx.onPlayerMove(Move.ROCK));
        paper.setOnMouseClicked(e -> ctx.onPlayerMove(Move.PAPER));
        scissor.setOnMouseClicked(e -> ctx.onPlayerMove(Move.SCISSOR));

        Button buttonReset = new Button("Reset Game");
        styleButton(buttonReset);
        buttonReset.setBackground(new Background(
                new BackgroundFill(Color.DARKGREEN.darker(), null, null)
        ));
        buttonReset.setOnAction(e -> ctx.resetGame());

        Button buttonMainMenu = new Button("Main Menu");
        styleButton(buttonMainMenu);
        buttonMainMenu.setBackground(new Background(
                new BackgroundFill(Color.DARKGREEN.darker(), null, null)
        ));
        buttonMainMenu.setOnAction(e -> ctx.displayMainMenu());

        Button buttonPie = new Button("See Chart");
        styleButton(buttonPie);
        buttonPie.setBackground(new Background(
                new BackgroundFill(Color.DARKGREEN.darker(), null, null)
        ));
        buttonPie.setOnAction(e -> ctx.startPie());

        HBox buttonBox = new HBox(buttonReset, buttonMainMenu, buttonPie);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefWidth(Double.MAX_VALUE);

        VBox rootBox = new VBox(imgBox, info, statPane, buttonBox);
        rootBox.setPadding(new Insets(20));
        rootBox.setSpacing(10);
        rootBox.setBackground(
                new Background(
                        new BackgroundFill(Color.GREEN, null, null)
                )
        );
        scene = new Scene(rootBox, 600, 300);

        PieChart pieChart = new PieChart();
        pieChart.setLabelsVisible(false);
        pieData = pieChart.dataProperty();
        StackPane piePane = new StackPane(pieChart);

        scenePie = new Scene(piePane, 300, 300);
    }

    public void display(Stage stage) {
        stage.setScene(scene);
    }

    public void displayPie(Stage stage) {
        stage.setScene(scenePie);
    }
}
