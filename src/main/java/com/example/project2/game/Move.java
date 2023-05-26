package com.example.project2.game;

// Possible RPS moves
//     -Owen Avery
public enum Move {
    ROCK,
    PAPER,
    SCISSOR;

    public Move getBetter() {
        switch (this) {
            case ROCK:
                return PAPER;
            case PAPER:
                return SCISSOR;
            case SCISSOR:
                return ROCK;
        }
        return null;
    }

    public Move getWorse() {
        switch (this) {
            case ROCK:
                return SCISSOR;
            case PAPER:
                return ROCK;
            case SCISSOR:
                return PAPER;
        }
        return null;
    }

    @Override
    public String toString() {
        switch (this) {
            case ROCK:
                return "Rock";
            case PAPER:
                return "Paper";
            case SCISSOR:
                return "Scissor";
        }
        return null;
    }
}
