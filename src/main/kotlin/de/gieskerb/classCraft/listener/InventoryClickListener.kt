package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.classes.ExplorerClass
import de.gieskerb.classCraft.classes.FarmerClass
import de.gieskerb.classCraft.classes.LumberjackClass
import de.gieskerb.classCraft.classes.MinerClass
import de.gieskerb.classCraft.classes.WarriorClass
import de.gieskerb.classCraft.commands.ClassCommand
import de.gieskerb.classCraft.commands.HorseCommand
import de.gieskerb.classCraft.data.HorseData
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

class InventoryClickListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.clickedInventory == ClassCommand.selectInventory) {
            event.isCancelled = true
            val clickedItem = Objects.requireNonNull(event.clickedInventory)?.getItem(event.slot)

            val player = event.whoClicked
            checkNotNull(clickedItem)
            val newClass = if (clickedItem == WarriorClass.displayItem) {
                WarriorClass(event.whoClicked as Player)
            } else if (clickedItem == MinerClass.displayItem) {
                MinerClass(event.whoClicked as Player)
            } else if (clickedItem == LumberjackClass.displayItem) {
                LumberjackClass(event.whoClicked as Player)
            } else if (clickedItem == FarmerClass.displayItem) {
                FarmerClass(event.whoClicked as Player)
            } else if (clickedItem == ExplorerClass.displayItem) {
                ExplorerClass(event.whoClicked as Player)
            } else {
                return
            }
            player.closeInventory()
            PlayerData.getPlayerData(player.name)!!.changeClass(newClass)
        } else if (event.clickedInventory == ClassCommand.getLevelMenu(event.whoClicked as Player)) {
            event.isCancelled = true
        } else if (event.clickedInventory == HorseCommand.getCustomizeMenu(event.whoClicked as Player)) {
            event.isCancelled = true
            val clickedSlot = event.slot
            val horseData: HorseData? = PlayerData.getPlayerData((event.whoClicked.name))?.horseData
            if (clickedSlot in 10..16) {
                // Select Color
                if (horseData != null) {
                    horseData.color = Horse.Color.entries.toTypedArray()[clickedSlot - 10]
                    PlayerData.getPlayerData((event.whoClicked.name))?.horseData = horseData
                    HorseCommand.selectColor(event.clickedInventory!!, horseData.color)
                }
            } else if (clickedSlot in 29..33) {
                // Select Style
                if (horseData != null) {
                    horseData.style = Horse.Style.entries.toTypedArray()[clickedSlot - 29]
                    PlayerData.getPlayerData((event.whoClicked.name))?.horseData = horseData
                    HorseCommand.selectStyle(event.clickedInventory!!, horseData.style)
                }
            }
        }
    }
}