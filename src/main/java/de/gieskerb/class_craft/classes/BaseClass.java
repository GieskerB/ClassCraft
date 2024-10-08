package de.gieskerb.class_craft.classes;

import de.gieskerb.class_craft.data.HorseData;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class BaseClass {

    private static final byte MAX_LEVEL = 20;

    public final String CLASS_NAME;
    protected final boolean[] itemReceived;
    protected Player playerReference;
    private byte level;
    private int xp;

    BaseClass(String name, Player player) {
        this.level = 0;
        this.CLASS_NAME = name;
        this.playerReference = player;
        this.itemReceived = new boolean[4];
    }

    BaseClass(JSONObject json, Player player) {
        this.CLASS_NAME = (String) json.get("class");
        this.level = Byte.parseByte(json.get("level").toString());
        this.xp = Integer.parseInt(json.get("xp").toString());
        JSONArray jsonArray = (JSONArray) json.get("rewardsClaimed");
        this.itemReceived = new boolean[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            this.itemReceived[i] = (boolean) jsonArray.get(i);
        }
        this.playerReference = player;
    }

    private static int getNetLevelStep(int level) {
        final int MAX_LEVEL_XP = 1000;
        final float FACTOR = (level + 1) * (level + 1) / 400f;
        return (int) (FACTOR * MAX_LEVEL_XP);

    }

    public static BaseClass fromJSON(JSONObject json, Player player) {
        if (json.isEmpty()) {
            return null;
        }
        return switch ((String) json.get("class")) {
            case WarriorClass.CLASS_NAME -> new WarriorClass(json, player);
            case MinerClass.CLASS_NAME -> new MinerClass(json, player);
            case LumberjackClass.CLASS_NAME -> new LumberjackClass(json, player);
            case FarmerClass.CLASS_NAME -> new FarmerClass(json, player);
            case ExplorerClass.CLASS_NAME -> new ExplorerClass(json, player);
            default -> null;
        };
    }

    private void checkLevelUp() {
        if (this.xp >= getNetLevelStep(this.level) && this.level >= MAX_LEVEL) {
            this.level++;
            Bukkit.getServer().broadcastMessage("LevelUp");
            this.checkNonItemReward();
        }
    }

    // TODO Milk
    public abstract void reapplyRewardEffects();

    public abstract void removePermanentEffects();

    public abstract ItemStack getClassItem();

    protected abstract HorseData getHorseData();

    protected abstract void giveBannerReward();

    protected abstract void giveFirstToolReward();

    protected abstract void giveSecondToolReward();

    protected abstract void giveThirdToolReward();

    private void checkNonItemReward() {

        if(this.level == 5 || this.level == 13 || this.level == 15) {
            // Apply first / second buff and de-buff
            this.reapplyRewardEffects();
        } else if(this.level == 7) {
            // Give horse access:
            PlayerData.getPlayerData(playerReference.getName()).setHorseData(getHorseData());
            playerReference.sendMessage(
                    "You have now unlocked your class specific horse! Use /horse for more details.");
        } else if(this.level == 10) {
            //Special ability
        } else {
            // Give Exp
            this.playerReference.giveExp(this.level * 25);
        }
    }

    private void checkItemReward() {
        if (this.level >= 1 && !this.itemReceived[0] && this.playerReference.getInventory().firstEmpty() != -1) {
            this.giveBannerReward();
            this.itemReceived[0] = true;
        } else if (this.level >= 1 && !this.itemReceived[0]) {
            this.playerReference.sendMessage("You have an open reward from reaching level 1." +
                                                     " Please make room in your inventory to receive it!");
        }

        if (this.level >= 3 && !this.itemReceived[1] && this.playerReference.getInventory().firstEmpty() != -1) {
            this.giveFirstToolReward();
            this.itemReceived[1] = true;
        } else if (this.level >= 3 && !this.itemReceived[1]) {
            this.playerReference.sendMessage("You have an open reward from reaching level 3." +
                                                     " Please make room in your inventory to receive it!");
        }

        if (this.level >= 13 && !this.itemReceived[2] && this.playerReference.getInventory().firstEmpty() != -1) {
            this.giveSecondToolReward();
            this.itemReceived[2] = true;
        } else if (this.level >= 13 && !this.itemReceived[2]) {
            this.playerReference.sendMessage("You have an open reward from reaching level 15." +
                                                     " Please make room in your inventory to receive it!");
        }

        if (this.level >= 20 && !this.itemReceived[3] && this.playerReference.getInventory().firstEmpty() != -1) {
            this.giveThirdToolReward();
            this.itemReceived[3] = true;
        } else if (this.level >= 20 && !this.itemReceived[3]) {
            this.playerReference.sendMessage("You have an open reward from reaching level 20." +
                                                     " Please make room in your inventory to receive it!");
        }
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        for (byte i = 0; i < level; i++) {
            this.level = i;
            this.checkNonItemReward();
            this.checkItemReward();
        }
    }

    public void addXp(int xp) {
        this.xp += xp;
        this.checkLevelUp();
        this.checkItemReward();
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("class", this.CLASS_NAME);
        JSONArray rewards = new JSONArray();
        for (byte i = 0; i < this.itemReceived.length; i++) {
            rewards.add(i, this.itemReceived[i]);
        }
        json.put("rewardsClaimed", rewards);
        json.put("level", this.level);
        json.put("xp", this.xp);
        return json;
    }
}
