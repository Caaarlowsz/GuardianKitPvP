package dev.mruniverse.guardiankitpvp.enums;

public enum TopHologram {
    KILL_STREAKS(10),
    KILLS(0),
    DEATHS(1),
    WINS(6),
    COINS(2),
    EXPERIENCE(4);

    private final int id;

    TopHologram(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public String getName() {
        switch (this) {
            case COINS:
                return "Coins";
            case WINS:
                return "Wins";
            default:
            case KILLS:
                return "Kills";
            case DEATHS:
                return "Deaths";
            case EXPERIENCE:
                return "Experience";
            case KILL_STREAKS:
                return "Kill Streaks";
        }
    }

    public String getReplace() {
        switch (this) {
            case COINS:
                return "%coins%";
            case WINS:
                return "%wins%";
            default:
            case KILLS:
                return "%kills%";
            case DEATHS:
                return "%deaths%";
            case EXPERIENCE:
                return "%experience%";
            case KILL_STREAKS:
                return "%ks%";
        }
    }

    public GuardianHolograms getGuardian() {
        switch (this) {
            case COINS:
                return GuardianHolograms.TOP_COINS;
            case WINS:
                return GuardianHolograms.TOP_WINS;
            default:
            case KILLS:
                return GuardianHolograms.TOP_KILLS;
            case DEATHS:
                return GuardianHolograms.TOP_DEATHS;
            case EXPERIENCE:
                return GuardianHolograms.TOP_EXPERIENCE;
            case KILL_STREAKS:
                return GuardianHolograms.TOP_KILL_STREAK;
        }
    }
}
