package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.classes.*;
import de.gieskerb.class_craft.commands.ClassCommand;
import de.gieskerb.class_craft.commands.HorseCommand;
import de.gieskerb.class_craft.data.HorseData;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
//        if (event == null || event.getClickedInventory() == null) {
//            return;
//        }

        if (event.getClickedInventory().equals(ClassCommand.getSelectInventory())) {
            event.setCancelled(true);
            var clickedItem = Objects.requireNonNull(event.getClickedInventory()).getItem(event.getSlot());

            var player = event.getWhoClicked();
            BaseClass newClass;
            assert clickedItem != null;
            if (clickedItem.equals(WarriorClass.getDisplayItem())) {
                newClass = new WarriorClass((Player) event.getWhoClicked());
            } else if (clickedItem.equals(MinerClass.getDisplayItem())) {
                newClass = new MinerClass((Player) event.getWhoClicked());
            } else if (clickedItem.equals(LumberjackClass.getDisplayItem())) {
                newClass = new LumberjackClass((Player) event.getWhoClicked());
            } else if (clickedItem.equals(FarmerClass.getDisplayItem())) {
                newClass = new FarmerClass((Player) event.getWhoClicked());
            } else if (clickedItem.equals(ExplorerClass.getDisplayItem())) {
                newClass = new ExplorerClass((Player) event.getWhoClicked());
            } else {
                return;
            }
            player.closeInventory();
            PlayerData.getPlayerData(player.getName()).changeClass(newClass);

        } else if (event.getClickedInventory().equals(ClassCommand.getLevelMenu((Player) event.getWhoClicked()))) {
            event.setCancelled(true);
        } else if (event.getClickedInventory().equals(HorseCommand.getCustomizeMenu((Player) event.getWhoClicked()))) {
            event.setCancelled(true);
            int clickedSlot = event.getSlot();
            HorseData horseData = PlayerData.getPlayerData((event.getWhoClicked().getName())).getHorseData();
            if (clickedSlot >= 10 && clickedSlot <= 16) {
                // Select Color
                horseData.color = Horse.Color.values()[clickedSlot - 10];
                PlayerData.getPlayerData((event.getWhoClicked().getName())).setHorseData(horseData);
                HorseCommand.selectColor(event.getClickedInventory(), horseData.color);
            } else if (clickedSlot >= 29 && clickedSlot <= 33) {
                // Select Style
                horseData.style = Horse.Style.values()[clickedSlot - 29];
                PlayerData.getPlayerData((event.getWhoClicked().getName())).setHorseData(horseData);
                HorseCommand.selectStyle(event.getClickedInventory(), horseData.style);
            }
        }
    }
}
