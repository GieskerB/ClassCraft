package de.gieskerb.class_craft.commands;

import de.gieskerb.class_craft.classes.*;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ClassCommand implements CommandExecutor {

    private static Inventory selectMenu = null, levelMenu = null;

    public static Inventory getSelectInventory() {
        if (selectMenu == null) {
            final int INVENTORY_SIZE = 27;
            selectMenu = Bukkit.createInventory(null, INVENTORY_SIZE, "Select your class");

            for (int i = 0; i < INVENTORY_SIZE; i++) {
                if (i != 9 && i != 11 && i != 13 && i != 15 && i != 17) {
                    selectMenu.setItem(i, HandyItems.getBackGroundItem());
                }
            }

            selectMenu.setItem(9, WarriorClass.getDisplayItem());
            selectMenu.setItem(11, MinerClass.getDisplayItem());
            selectMenu.setItem(13, LumberjackClass.getDisplayItem());
            selectMenu.setItem(15, FarmerClass.getDisplayItem());
            selectMenu.setItem(17, ExplorerClass.getDisplayItem());
        }
        return selectMenu;
    }

    public static Inventory getLevelMenu(Player player) {
        final int INVENTORY_SIZE = 45;
        if (levelMenu == null) {
            levelMenu = Bukkit.createInventory(null, INVENTORY_SIZE, "Level up your class");

            for (int i = 0; i < INVENTORY_SIZE; i++) {
                if (i <= 9 || i >= 35 || i % 9 == 0 || i % 9 == 8) {
                    levelMenu.setItem(i, HandyItems.getBackGroundItem());
                }
            }
        }

        final PlayerData playerData = PlayerData.getPlayerData(player.getName());
        assert playerData != null;
        int playerLevel = playerData.getActiveClass() == null ? 0 : playerData.getActiveClass().getLevel();

        int levelPaneIndex = 1;

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (!(i <= 9 || i >= 35 || i % 9 == 0 || i % 9 == 8)) {
                if (i == INVENTORY_SIZE / 2) {
                    levelMenu.setItem(i,
                                      playerData.getActiveClass() ==
                                              null ? null : playerData.getActiveClass().getClassItem());
                    continue;
                }
                ItemStack levelPane = new ItemStack(levelPaneIndex <= playerLevel ? Material.LIME_STAINED_GLASS_PANE : (
                        (playerLevel + 1) ==
                                levelPaneIndex ? Material.YELLOW_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE));

                ItemMeta levelPaneItemMeta = levelPane.getItemMeta();
                levelPaneItemMeta.setDisplayName("§rLevel: " + levelPaneIndex);
                levelPane.setItemMeta(levelPaneItemMeta);
                levelMenu.setItem(i, levelPane);
                levelPaneIndex++;
            }
        }

        return levelMenu;
    }

    private String getHelpMessage(boolean isOp) {
        String helpMessage = "§e§lClass Command Help\n" + "\n" + "§r§6/class help§e: Shows this help message.\n" +
                "§r§6/class select§e: Select your class via a menu.\n" +
                "§r§6/class select §o[class]§r§e: Selects your class by text. Possible classes are: §9Warrior§e, §9Miner§e, §9Lumberjack§e, §9Farmer§e and §9Explorer§e.\n" +
                "§r§6/class remove§e: Removes your current class.\n" +
                "§r§6/class level§e: Shows your current class level.\n";
        if (isOp) {
            helpMessage += "§r§6/class level §o[class]§r§e: §l§c!Requires OP permission!§r§e. Sets your current class level directly.\n";
        }
        return helpMessage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("Missing arguments. Please use /class help to see more details!");
        } else if (args.length == 1) {
            switch (args[0]) {
                case "help":
                    player.sendMessage(getHelpMessage(player.isOp()));
                    break;
                case "select":
                    player.openInventory(getSelectInventory());
                    break;
                case "remove":
                    PlayerData.getPlayerData(player.getName()).resetClass();
                    player.sendMessage("You have removed your class.");
                    break;
                case "level":
                    if (PlayerData.getPlayerData(player.getName()).getActiveClass() != null) {
                        player.openInventory(getLevelMenu(player));
                    }
                    break;
                default:
                    player.sendMessage("Unknown command. Please use /class help to see more details!");
            }
        } else if (args.length == 2) {
            switch (args[0]) {
                case "select":
                    switch (args[1]) {
                        case "Warrior":
                        case "warrior":
                            PlayerData.getPlayerData(player.getName()).changeClass(new WarriorClass(player));
                            break;
                        case "Miner":
                        case "miner":
                            PlayerData.getPlayerData(player.getName()).changeClass(new MinerClass(player));
                            break;
                        case "Lumberjack":
                        case "lumberjack":
                            PlayerData.getPlayerData(player.getName()).changeClass(new LumberjackClass(player));
                            break;
                        case "Farmer":
                        case "farmer":
                            PlayerData.getPlayerData(player.getName()).changeClass(new FarmerClass(player));
                            break;
                        case "Explorer":
                        case "explorer":
                            PlayerData.getPlayerData(player.getName()).changeClass(new ExplorerClass(player));
                            break;
                        default:
                            player.sendMessage("Unknown class. Please use /class help to see more details");
                    }
                    break;
                case "level":
                    if (!player.isOp()) {
                        player.sendMessage("You dont have permission to use this command.");
                        break;
                    }
                    try {
                        int level = Integer.parseInt(args[1]);
                        if (level < 0 || level > 20) {
                            throw new NumberFormatException();
                        }
                        BaseClass activeClass = PlayerData.getPlayerData(player.getName()).getActiveClass();
                        if (activeClass != null) {
                            activeClass.setLevel(level);
                        } else {
                            player.sendMessage("You dont have a class selected right now.");
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage("Invalid level. Value must be an integer between 0 and 20!");
                    }
                    break;
                default:
                    player.sendMessage("Unknown command. Please use /class help to see more details!");
            }
        } else {
            player.sendMessage("Too many arguments. Please use /class help to see more details!");
        }

        return true;
    }

}
