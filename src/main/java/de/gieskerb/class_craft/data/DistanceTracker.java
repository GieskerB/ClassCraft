package de.gieskerb.class_craft.data;

import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class DistanceTracker {

    private final Player player;
    private final Horse horse;

    public DistanceTracker(Player player, Horse horse) {
        this.player = player;
        this.horse = horse;
    }

    private double getDistance() {
        final Location playerLocation = player.getLocation();
        final Location horseLocation = horse.getLocation();
        if (playerLocation.getWorld().getEnvironment() != horseLocation.getWorld().getEnvironment()) {
            return -1;
        }
        return player.getLocation().distance(horse.getLocation());
    }

    public void despawnHorse() {
        this.horse.remove();
    }

    public boolean trackDistance() {
        final double distance = getDistance();
        if (distance > 16 || distance == -1) {
            this.despawnHorse();
            return true;
        }
        return false;
    }

}
