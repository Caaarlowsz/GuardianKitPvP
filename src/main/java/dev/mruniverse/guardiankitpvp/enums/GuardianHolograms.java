package dev.mruniverse.guardiankitpvp.enums;

public enum GuardianHolograms {
    STATICS,
    TOP_KILLS,
    TOP_COINS,
    TOP_DEATHS,
    TOP_EXPERIENCE,
    TOP_WINS,
    TOP_KILL_STREAK,
    SPAWN_LOCATION;

    public String getPath() {
        switch (this) {
            case TOP_COINS:
                return "holograms.tops.coins";
            case TOP_KILLS:
                return "holograms.tops.kills";
            case TOP_DEATHS:
                return "holograms.tops.deaths";
            case TOP_WINS:
                return "holograms.tops.wins";
            case TOP_EXPERIENCE:
                return "holograms.tops.exp";
            case TOP_KILL_STREAK:
                return "holograms.tops.kill-streak";
            case SPAWN_LOCATION:
                return "holograms.spawn-location";
            default:
            case STATICS:
                return "holograms.stats";
        }
    }

    public static String getSizePath() {
        return "holograms.tops.topSize";
    }

    public String getPath(HoloPath holoPath) {
        switch (this) {
            case TOP_EXPERIENCE:
                return "holograms.tops.exp" + holoPath.getPath();
            case TOP_KILL_STREAK:
                return "holograms.tops.kill-streak" + holoPath.getPath();
            case TOP_WINS:
                return "holograms.tops.wins" + holoPath.getPath();
            case TOP_COINS:
                return "holograms.tops.coins" + holoPath.getPath();
            case TOP_KILLS:
                return "holograms.tops.kills" + holoPath.getPath();
            case TOP_DEATHS:
                return "holograms.tops.deaths" + holoPath.getPath();
            case SPAWN_LOCATION:
                return "holograms.spawn-location" + holoPath.getPath();
            default:
            case STATICS:
                return "holograms.stats" + holoPath.getPath();
        }
    }

}