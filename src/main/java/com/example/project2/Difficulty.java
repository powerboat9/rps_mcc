package com.example.project2;

import com.example.project2.game.Result;

import java.util.Random;
import java.util.function.Supplier;

// Game Difficulty
//     -Owen Avery
public class Difficulty {
    public static final Random RAND = new Random();

    public static final Supplier<Result> EASY = new DifficultyStandard(4.0 / 9, 0.6);
    public static final Supplier<Result> NORMAL = new DifficultyStandard(1.0 / 3, 0.5);
    public static final Supplier<Result> HARD = new DifficultyStandard(2.0 / 9, 0.4);
    public static final Supplier<Result> LUNATIC = () -> Result.LOSE;

    private static class DifficultyStandard implements Supplier<Result> {
        private final double cpuLooseChance;
        private final double cpuTieSubChance;

        public DifficultyStandard(double cpuLooseChance, double cpuTieSubChance) {
            this.cpuLooseChance = cpuLooseChance;
            this.cpuTieSubChance = cpuTieSubChance;
        }

        @Override
        public Result get() {
            if (Difficulty.RAND.nextDouble() < cpuLooseChance) {
                return Result.WIN;
            } else if (Difficulty.RAND.nextDouble() < cpuTieSubChance) {
                return Result.TIE;
            } else {
                return Result.LOSE;
            }
        }
    }
}
