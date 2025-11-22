package de.gieskerb.classCraft.data

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.classes.BaseClass
import de.gieskerb.classCraft.listener.HungerChangeListener
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import kotlin.text.get

class PlayerData {
    private val assignedPlayer: String
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

    constructor(json: JsonObject) {
        this.assignedPlayer = json.get("player").toString()
        this.activeClass = Bukkit.getPlayer(assignedPlayer)?.let { BaseClass.fromJSON(json["class"] as JsonObject, it) }
        this.horseData = HorseData.fromJSON(json["horse"] as JsonObject)
    }

    fun toJSON(): JsonObject {
        val json = Json { prettyPrint = true }
        val jsonObject: JsonObject = json.encodeToJsonElement(this).jsonObject
        return jsonObject
//        val json = JSONObject()
//        json["player"] = assignedPlayer
//        if (this.activeClass != null) {
//            json["class"] = activeClass!!.toJSON()
//        } else {
//            json["class"] = JSONObject()
//        }
//        if (this.horseData != null) {
//            json["horse"] = horseData!!.toJSON()
//        } else {
//            json["horse"] = JSONObject()
//        }
//        return json
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
        private val allPlayerData = ArrayList<PlayerData>()
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