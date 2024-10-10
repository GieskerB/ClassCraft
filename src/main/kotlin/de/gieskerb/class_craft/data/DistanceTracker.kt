package de.gieskerb.class_craft.data

import org.bukkit.entity.Horse
import org.bukkit.entity.Player

class DistanceTracker(private val player: Player, private val horse: Horse) {
    private val distance: Double
        get() {
            val playerLocation = player.location
            val horseLocation = horse.location
            if (playerLocation.world!!.environment != horseLocation.world!!.environment) {
                return (-1).toDouble()
            }
            return player.location.distance(horse.location)
        }

    fun despawnHorse() {
        horse.remove()
    }

    fun trackDistance(): Boolean {
        val distance = distance
        if (distance > 16 || distance == -1.0) {
            this.despawnHorse()
            return true
        }
        return false
    }
}