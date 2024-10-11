package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.classes.FarmerClass
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class HungerChangeListener : Listener {
    @EventHandler
    fun onHunger(e: FoodLevelChangeEvent) {
        val player = e.entity as Player
        val playerData = PlayerData.getPlayerData(player.name)

        if (playerData!!.activeClass != null && playerData.activeClass is FarmerClass) {
            val farmerClass = playerData.activeClass as FarmerClass
            if ( farmerClass.level >= 15) {
                val newFoodLevel = e.foodLevel
                if (newFoodLevel < getLastHunger(player.name)) {
                    e.foodLevel -= 1
                }
                lastHungerValue[player.name] = e.foodLevel
                player.sendMessage(e.foodLevel.toString() + "")
            }
        }
    }

    companion object {
        private val lastHungerValue: MutableMap<String, Int> = HashMap()

        private fun getLastHunger(playerName: String): Int {
            if (lastHungerValue.containsKey(playerName)) {
                return lastHungerValue[playerName]!!
            }
            return 20
        }

        fun removePlayer(playerName: String) {
            lastHungerValue.remove(playerName)
        }
    }
}