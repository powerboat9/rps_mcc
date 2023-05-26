package com.example.project2.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.chart.PieChart;

import java.util.function.Consumer;
import java.util.function.Supplier;

// Handles non-persistent game state (statistics, username)
//     -Owen Avery
public class GameState {
    private final String name;

    private final int totalRounds;
    private final Supplier<Result> diff;
    private final IntegerProperty rounds = new SimpleIntegerProperty(0);
    private final IntegerProperty wins = new SimpleIntegerProperty(0);
    private final IntegerProperty losses = new SimpleIntegerProperty(0);
    private final IntegerProperty ties = new SimpleIntegerProperty(0);
    private final Consumer<String> updateInfo;
    private final ObservableList<PieChart.Data> pieData = new ObservableListBase<>() {
        private final PieChart.Data wins = new PieChart.Data("Wins", 0);
        private final PieChart.Data losses = new PieChart.Data("Losses", 0);
        private final PieChart.Data ties = new PieChart.Data("Ties", 0);

        {
            wins.pieValueProperty().bind(GameState.this.wins);
            losses.pieValueProperty().bind(GameState.this.losses);
            ties.pieValueProperty().bind(GameState.this.ties);
        }

        @Override
        public PieChart.Data get(int i) {
            switch (i) {
                case 0:
                    return wins;
                case 1:
                    return losses;
                case 2:
                    return ties;
                default:
                    throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };

    public GameState(Game game, String name, int totalRounds, Supplier<Result> diff) {
        this.name = name;
        this.totalRounds = totalRounds;
        this.diff = diff;
        updateInfo = game::setInfo;
        reset();
        game.bindGameState(this);
    }

    public ObservableIntegerValue getRounds() {
        return rounds;
    }

    public ObservableIntegerValue getWins() {
        return wins;
    }

    public ObservableIntegerValue getLosses() {
        return losses;
    }

    public ObservableIntegerValue getTies() {
        return ties;
    }

    public ObservableList<PieChart.Data> getPieData() {
        return pieData;
    }

    public void onPlayerMove(Move m) {
        if (rounds.get() == totalRounds) {
            updateInfo.accept("Game Over!");
            return;
        }
        rounds.set(rounds.get() + 1);
        switch (diff.get()) {
            case WIN:
                wins.set(wins.get() + 1);
                updateInfo.accept("You chose " + m + ". Computer chose " + m.getWorse() + ". You win!");
                break;
            case LOSE:
                losses.set(losses.get() + 1);
                updateInfo.accept("You chose " + m + ". Computer chose " + m.getBetter() + ". You lose!");
                break;
            case TIE:
                ties.set(ties.get() + 1);
                updateInfo.accept("You both chose " + m + ". It's a tie!");
        }
    }

    public void reset() {
        rounds.set(0);
        wins.set(0);
        losses.set(0);
        ties.set(0);
        updateInfo.accept(name + ", click an image to begin");
    }
}
