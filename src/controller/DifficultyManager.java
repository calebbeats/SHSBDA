package controller;

public class DifficultyManager {

    private static Difficulty difficulty;

    public enum Difficulty {
        Easy, Normal, Hard
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(Difficulty difficulty) {
        DifficultyManager.difficulty = difficulty;
    }

    public static double getShooterDamageMultiplier() {
        switch (difficulty) {
            case Easy:
                return 3;
            case Normal:
                return 2;
            case Hard:
                return 1;
            default:
                return 2;
        }
    }

    public static double getEnemyDamagerMultiplier() {
        switch (difficulty) {
            case Easy:
                return 0.5;
            case Normal:
                return 1;
            case Hard:
                return 2;
            default:
                return 1;
        }
    }
}
