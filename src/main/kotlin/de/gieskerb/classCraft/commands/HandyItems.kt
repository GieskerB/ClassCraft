package de.gieskerb.classCraft.commands

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object HandyItems {

    private var backgroundItem: ItemStack? = null
    var notSelectedItem: ItemStack? = null
        get() {
            if (field == null) {
                field = ItemStack(Material.RED_STAINED_GLASS_PANE)
                val notSelectedItemMeta = field!!.itemMeta
                notSelectedItemMeta!!.setDisplayName("§r§cNot Selected")
                field!!.itemMeta = notSelectedItemMeta
            }
            return field
        }
        private set
    var selectedItem: ItemStack? = null
        get() {
            if (field == null) {
                field = ItemStack(Material.LIME_STAINED_GLASS_PANE)
                val selectedItemMeta = field!!.itemMeta
                selectedItemMeta!!.setDisplayName("§r§aSelected")
                field!!.itemMeta = selectedItemMeta
            }
            return field
        }
        private set

    val backGroundItem: ItemStack?
        get() {
            if (backgroundItem == null) {
                backgroundItem = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
                val backgroundItemMeta = backgroundItem!!.itemMeta
                backgroundItemMeta!!.isHideTooltip = true
                backgroundItem!!.itemMeta = backgroundItemMeta
            }
            return backgroundItem
        }
}