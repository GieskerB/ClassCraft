package de.gieskerb.class_craft.data;

import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class HorseData  {

    public Horse.Color color;
    public Horse.Style style;
    public double speed, jump, health;
    public String name;
    public Material armor;

    public HorseData(Horse.Color color, Horse.Style style, double speed, double jump, double health, String name, Material armor) {
        this.color = color;
        this.style = style;
        this.speed = speed;
        this.jump = jump;
        this.health = health;
        this.name = name;
        this.armor = armor;
    }

    public HorseData(JSONObject json) {
        this.color = Horse.Color.values()[Integer.parseInt(json.get("color").toString())];
        this.style = Horse.Style.values()[Integer.parseInt( json.get("style").toString())];
        this.speed = Double.parseDouble(json.get("speed").toString());
        this.jump = Double.parseDouble(json.get("jump").toString());
        this.health = Double.parseDouble(json.get("health").toString());
        this.name = json.get("name").toString();
        this.armor = Material.valueOf(json.get("armor").toString());
    }

    public static String getHorseName(Player player, String className) {
        String playerName = player.getName();
        if (playerName.charAt(playerName.length() - 1) == 's' || playerName.charAt(playerName.length() - 1) == 'x' ||
                playerName.charAt(playerName.length() - 1) == 'ß') {
            playerName += '\'';
        } else {
            playerName += 's';
        }
        return playerName + " " + className + "-Horse";
    }

    public static HorseData fromJSON(JSONObject json) {
        if (json.isEmpty()) return null;
        return new HorseData(json);
    }


    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("color", color.ordinal());
        json.put("style", style.ordinal());
        json.put("speed", speed);
        json.put("jump", jump);
        json.put("health", health);
        json.put("name", name);
        json.put("armor", armor.toString());
        return json;
    }
}
