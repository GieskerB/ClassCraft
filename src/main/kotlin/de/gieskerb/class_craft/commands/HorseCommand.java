package de.gieskerb.class_craft.commands;

import de.gieskerb.class_craft.Main;
import de.gieskerb.class_craft.data.DistanceTracker;
import de.gieskerb.class_craft.data.HorseData;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class HorseCommand implements CommandExecutor {

    public final static String METADATA_IDENTIFIER = "CLASS_HORSE";

    private static Inventory customizeMenu = null;

    private static ItemStack generateItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§7§l" + name);
        item.setItemMeta(meta);
        return item;
    }

    public static Inventory getCustomizeMenu(Player player) {
        if (customizeMenu == null) {
            final int INVENTORY_SIZE = 45;
            customizeMenu = Bukkit.createInventory(null, INVENTORY_SIZE, "Customize your Horse");

            for (int i = 0; i < INVENTORY_SIZE; i++) {
                if (i % 9 == 0 || i % 9 == 8 || (i % 9 == 1 && i > 10) || (i % 9 == 7 && i > 16) || i / 9 == 2) {
                    customizeMenu.setItem(i, HandyItems.getBackGroundItem());
                }
            }

            customizeMenu.setItem(10, generateItem(Material.WHITE_TERRACOTTA, "White"));
            customizeMenu.setItem(11, generateItem(Material.ORANGE_TERRACOTTA, "Creamy"));
            customizeMenu.setItem(12, generateItem(Material.BROWN_CONCRETE, "Chestnut"));
            customizeMenu.setItem(13, generateItem(Material.BROWN_TERRACOTTA, "Brown"));
            customizeMenu.setItem(14, generateItem(Material.BLACK_TERRACOTTA, "Black"));
            customizeMenu.setItem(15, generateItem(Material.CYAN_TERRACOTTA, "Gray"));
            customizeMenu.setItem(16, generateItem(Material.GRAY_TERRACOTTA, "Dark Brown"));

            customizeMenu.setItem(29, generateItem(Material.BARRIER, "None"));
            customizeMenu.setItem(30, generateItem(Material.IRON_BOOTS, "White Stockings and Blaze"));
            customizeMenu.setItem(31, generateItem(Material.PAPER, "White Field"));
            customizeMenu.setItem(32, generateItem(Material.PUMPKIN_SEEDS, "White Spots"));
            customizeMenu.setItem(33, generateItem(Material.MELON_SEEDS, "Black Dots"));

        }
        final PlayerData playerData = PlayerData.getPlayerData(player.getName());
        final HorseData horseData = playerData.getHorseData();
        if(horseData != null) {
            selectColor(customizeMenu, horseData.color);
            selectStyle(customizeMenu, horseData.style);
        }
        return customizeMenu;
    }

    public static void selectColor(Inventory inventory, Horse.Color color) {
        if (inventory != customizeMenu && customizeMenu != null) {
            return;
        }

        inventory.setItem(1,
                          color == Horse.Color.WHITE ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(2,
                          color == Horse.Color.CREAMY ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(3,
                          color ==
                                  Horse.Color.CHESTNUT ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(4,
                          color == Horse.Color.BROWN ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(5,
                          color == Horse.Color.BLACK ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(6,
                          color == Horse.Color.GRAY ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(7,
                          color ==
                                  Horse.Color.DARK_BROWN ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
    }

    public static void selectStyle(Inventory inventory, Horse.Style style) {
        if (inventory != customizeMenu && customizeMenu != null) {
            return;
        }

        inventory.setItem(38,
                          style == Horse.Style.NONE ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(39,
                          style == Horse.Style.WHITE ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(40,
                          style ==
                                  Horse.Style.WHITEFIELD ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(41,
                          style ==
                                  Horse.Style.WHITE_DOTS ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
        inventory.setItem(42,
                          style ==
                                  Horse.Style.BLACK_DOTS ? HandyItems.getSelectedItem() : HandyItems.getNotSelectedItem());
    }

    public Horse spawnHorse(Player player) {
        Horse horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
        HorseData horseData = PlayerData.getPlayerData(player.getName()).getHorseData();

        horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horseData.speed);
        horse.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(horseData.jump);
        horse.setAdult();
        horse.setMetadata(METADATA_IDENTIFIER, new FixedMetadataValue(Main.getPlugin(), true));
        horse.setAgeLock(true);
        horse.setLootTable(null);
        horse.setColor(horseData.color);
        horse.setStyle(horseData.style);
        horse.setMaxHealth(horseData.health);
        horse.setHealth(horseData.health);
        horse.setCustomName(horseData.name);
        horse.setCustomNameVisible(true);
        horse.getInventory().setArmor(new ItemStack(horseData.armor));
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setOwner(player);

        PlayerData.getPlayerData(player.getName()).setDistanceTracker(new DistanceTracker(player, horse));

        return horse;
    }

    private String getHelpMessage() {
        return "§e§lHorse Command Help\n" + "\n" + "§r§6/horse help§e: Shows this help message.\n" +
                "§r§6/horse spawn§e: Spawns your own class specific horse at your position.\n" +
                "§r§6/horse mount§e: Spawns your horse and mounts you directly.\n" +
                "§r§6/horse customize§e: Gives you the ability to change §9Color§e and §9Style§e of the horse.\n";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        if (playerData == null || playerData.getHorseData() == null) {
            player.sendMessage("You dont have permission to use this command yet.");
            return true;
        }

        if (args.length == 1 && args[0].equals("mount")) {
            Horse horse = spawnHorse(player);
            horse.setPassenger(player);
        } else if (args.length == 1 && args[0].equals("help")) {
            player.sendMessage(getHelpMessage());
        } else if (args.length == 1 && args[0].equals("spawn")) {
            spawnHorse(player);
        } else if (args.length == 1 && args[0].equals("customize")) {
            player.openInventory(getCustomizeMenu(player));
        } else {
            if (args.length == 2) {
                Horse horse = (Horse) player.getVehicle();
                if (args[0].equals("speed")) {
                    horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(Double.parseDouble(args[1]));
                } else if (args[0].equals("jump")) {
                    horse.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(Double.parseDouble(args[1]));
                }
            }
        }

        return true;
    }
}
