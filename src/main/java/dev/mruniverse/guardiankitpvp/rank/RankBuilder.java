package dev.mruniverse.guardiankitpvp.rank;

import dev.mruniverse.guardiankitpvp.interfaces.rank.Rank;

import java.util.List;

public class RankBuilder implements Rank {

    private final int RequiredExp;

    private final List<String> commands;

    private final String name;

    private final String prefix;

    public RankBuilder(String rankName, String rankPrefix, int rankRequiredExp, List<String> rankCommands) {
        this.RequiredExp = rankRequiredExp;
        this.commands = rankCommands;
        this.name = rankName;
        this.prefix = rankPrefix;
    }

    public int getRequiredExp() {
        return this.RequiredExp;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

}
