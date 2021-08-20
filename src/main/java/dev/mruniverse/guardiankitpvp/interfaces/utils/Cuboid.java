package dev.mruniverse.guardiankitpvp.interfaces.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Iterator;

public interface Cuboid {
    Iterator<Block> blockList();

    Location getCenter();

    double getDistance();

    double getDistanceSquared();

    int getHeight();

    Location getPoint1();

    Location getPoint2();

    Location getRandomLocation();

    int getTotalBlockSize();

    int getXWidth();

    int getZWidth();

    boolean isIn(final Location loc);

    boolean isIn(final Player player);

    boolean isInWithMarge(final Location loc, final double marge);
}
