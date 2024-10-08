package de.gieskerb.class_craft.data;

import de.gieskerb.class_craft.Main;
import de.gieskerb.class_craft.classes.BaseClass;
import de.gieskerb.class_craft.listener.HungerChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerData {


    private final static ArrayList<PlayerData> allPlayerData = new ArrayList<>();
    private final String assignedPlayer;
    private BaseClass activeBaseClass;
    private HorseData horseData;
    private transient DistanceTracker distanceTracker;
    private transient BukkitTask distanceTrackerTask;

    public PlayerData(String playerName) {
        this.assignedPlayer = playerName;
        this.activeBaseClass = null;
        this.horseData = null;
        this.distanceTracker = null;
    }

    public PlayerData(JSONObject json) {
        this.assignedPlayer = json.get("player").toString();
        this.activeBaseClass = BaseClass.fromJSON((JSONObject) json.get("class"), Bukkit.getPlayer(assignedPlayer));
        this.horseData = HorseData.fromJSON((JSONObject) json.get("horse"));
    }

    public static void addPlayerData(PlayerData playerData) {
        allPlayerData.add(playerData);
    }

    public static PlayerData getPlayerData(String name) {
        if (name == null) return null;
        for (PlayerData playerData : allPlayerData) {
            if (name.equals(playerData.assignedPlayer)) return playerData;
        }
        return null;
    }

    public static void removePlayerData(String name) {
        if (name == null) return;
        allPlayerData.removeIf(playerData -> name.equals(playerData.assignedPlayer));
    }


    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("player", assignedPlayer);
        if (this.activeBaseClass != null) {
            json.put("class", activeBaseClass.toJSON());
        } else {
            json.put("class", new JSONObject());
        }
        if (this.horseData != null) {
            json.put("horse", horseData.toJSON());
        } else {
            json.put("horse", new JSONObject());
        }
        return json;
    }

    public DistanceTracker getDistanceTracker() {
        return this.distanceTracker;
    }

    public void setDistanceTracker(DistanceTracker distanceTracker) {
        if (distanceTrackerTask != null) {
            this.distanceTrackerTask.cancel();
        }
        if (this.distanceTracker != null) {
            this.distanceTracker.despawnHorse();
        }

        this.distanceTracker = distanceTracker;
        this.distanceTrackerTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
            if (this.distanceTracker.trackDistance()) {
                this.distanceTrackerTask.cancel();
                this.distanceTracker = null;
                this.distanceTrackerTask = null;
            }
        }, 0L, 20L);
    }

    public void changeClass(BaseClass activeBaseClass) {
        if (activeBaseClass == null) {
            throw new NullPointerException("activeBaseClass is null -> use resetClass instead!");
        }
        if (this.activeBaseClass != null) {
            this.resetClass();
        }
        this.activeBaseClass = activeBaseClass;
        Objects.requireNonNull(Bukkit.getPlayer(assignedPlayer)).sendMessage(
                "§aYou selected the §6" + this.activeBaseClass.CLASS_NAME + " Class");
    }

    public void resetClass() {
        if (this.activeBaseClass != null) {
            this.activeBaseClass.removePermanentEffects();
        }
        HungerChangeListener.removePlayer(assignedPlayer);
        this.activeBaseClass = null;
        this.horseData = null;
    }

    public BaseClass getActiveClass() {
        return this.activeBaseClass;
    }

    public HorseData getHorseData() {
        return this.horseData;
    }

    public void setHorseData(HorseData horseData) {
        this.horseData = horseData;
    }

}
