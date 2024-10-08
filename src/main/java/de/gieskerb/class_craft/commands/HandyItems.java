package de.gieskerb.class_craft.commands;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HandyItems {

    private static ItemStack backgroundItem = null;
    private static ItemStack notSelectedItem = null;
    private static ItemStack selectedItem = null;

    public static ItemStack getBackGroundItem() {
        if (backgroundItem == null) {
            backgroundItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta backgroundItemMeta = backgroundItem.getItemMeta();
            backgroundItemMeta.setHideTooltip(true);
            backgroundItem.setItemMeta(backgroundItemMeta);
        }
        return backgroundItem;
    }

    public static ItemStack getNotSelectedItem() {
        if (notSelectedItem == null) {
            notSelectedItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta notSelectedItemMeta = notSelectedItem.getItemMeta();
            notSelectedItemMeta.setDisplayName("§r§cNot Selected");
            notSelectedItem.setItemMeta(notSelectedItemMeta);
        }
        return notSelectedItem;
    }

    public static ItemStack getSelectedItem() {
        if (selectedItem == null) {
            selectedItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            ItemMeta selectedItemMeta = selectedItem.getItemMeta();
            selectedItemMeta.setDisplayName("§r§aSelected");
            selectedItem.setItemMeta(selectedItemMeta);
        }
        return selectedItem;
    }

}
