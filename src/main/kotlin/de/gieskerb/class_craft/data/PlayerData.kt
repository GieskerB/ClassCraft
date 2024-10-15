package de.gieskerb.class_craft.data

import de.gieskerb.class_craft.Main
import de.gieskerb.class_craft.classes.BaseClass
import de.gieskerb.class_craft.listener.HungerChangeListener
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import org.json.simple.JSONObject

class PlayerData {
    val assignedPlayer: String
    var activeClass: BaseClass?
        private set

    var horseData: HorseData?

    @Transient
    private var distanceTracker: DistanceTracker? = null

    @Transient
    private var distanceTrackerTask: BukkitTask? = null

    constructor(playerName: String) {
        this.assignedPlayer = playerName
        this.activeClass = null
        this.horseData = null
        this.distanceTracker = null
    }

    constructor(json: JSONObject) {
        this.assignedPlayer = json.get("player").toString()
        this.activeClass = Bukkit.getPlayer(assignedPlayer)?.let { BaseClass.fromJSON(json["class"] as JSONObject, it) }
        this.horseData = HorseData.fromJSON(json["horse"] as JSONObject)
    }

    fun toJSON(): JSONObject {
        val json = JSONObject()
        json["player"] = assignedPlayer
        if (this.activeClass != null) {
            json["class"] = activeClass!!.toJSON()
        } else {
            json["class"] = JSONObject()
        }
        if (this.horseData != null) {
            json["horse"] = horseData!!.toJSON()
        } else {
            json["horse"] = JSONObject()
        }
        return json
    }

    fun getDistanceTracker(): DistanceTracker? {
        return this.distanceTracker
    }

    fun setDistanceTracker(distanceTracker: DistanceTracker?) {
        if (distanceTrackerTask != null) {
            distanceTrackerTask!!.cancel()
        }
        if (this.distanceTracker != null) {
            this.distanceTracker!!.despawnHorse()
        }

        this.distanceTracker = distanceTracker
        this.distanceTrackerTask = Bukkit.getScheduler().runTaskTimer(
            Main.plugin, Runnable {
                if (this.distanceTracker!!.trackDistance()) {
                    distanceTrackerTask!!.cancel()
                    this.distanceTracker = null
                    this.distanceTrackerTask = null
                }
            }, 0L, 20L
        )
    }

    fun changeClass(activeBaseClass: BaseClass) {
        this.resetClass()
        this.activeClass = activeBaseClass
        Bukkit.getPlayer(assignedPlayer)?.sendMessage(
            "§aYou selected the §6" + activeClass!!.CLASS_NAME + " Class"
        )
    }

    fun resetClass() {
        activeClass?.removePermanentEffects()
        HungerChangeListener.removePlayer(assignedPlayer)
        this.activeClass = null
        this.horseData = null
    }

    companion object {
        val allPlayerData = ArrayList<PlayerData>()
        fun addPlayerData(playerData: PlayerData) {
            allPlayerData.add(playerData)
        }

        fun getPlayerData(name: String?): PlayerData? {
            if (name == null) return null
            for (playerData in allPlayerData) {
                if (name == playerData.assignedPlayer) return playerData
            }
            return null
        }

        fun removePlayerData(name: String?) {
            if (name == null) return
            allPlayerData.removeIf { playerData: PlayerData -> name == playerData.assignedPlayer }
        }
    }
}