package dev.mruniverse.guardiankitpvp.interfaces.rank;

import java.util.List;

public interface Rank {

    int getRequiredExp();

    List<String> getCommands();

    String getName();

    String getPrefix();

}
