package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.classes.BaseClass
import de.gieskerb.class_craft.classes.LumberjackClass
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

class ToggleSneakListener : Listener {

    companion object {
        val SAPLING_GROW_CHANCE: Double = 0.33
    }

    @EventHandler
    fun onToggleSneak(event: PlayerToggleSneakEvent) {
        val player: Player = event.player
        val activeClass: BaseClass? = PlayerData.getPlayerData(player.name)!!.activeClass
        if (player.isSneaking && activeClass != null && activeClass is LumberjackClass) {
            if (activeClass.surroundedSaplings.isEmpty()) {
                return
            }
            val randomSapling: Block =
                activeClass.surroundedSaplings[(Math.random() * activeClass.surroundedSaplings.size).toInt()]
            if (Math.random() < SAPLING_GROW_CHANCE) {
                randomSapling.applyBoneMeal(BlockFace.UP)
            }

        }
    }

}