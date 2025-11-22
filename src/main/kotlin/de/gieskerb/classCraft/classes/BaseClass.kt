package de.gieskerb.classCraft.classes

import de.gieskerb.classCraft.data.HorseData
import de.gieskerb.classCraft.data.PlayerData
import de.gieskerb.classCraft.commands.HorseCommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.collections.get
import kotlin.text.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File

@Serializable
abstract class BaseClass {
    val CLASS_NAME: String
    abstract val classItem: ItemStack?
    protected abstract val horseData: HorseData?
    private val itemReceived: BooleanArray
    protected val playerReference: Player
    var level: Byte = 0
        set(value) {
            for (i in 0 until value) {
                field = i.toByte()
                this.checkNonItemReward()
                this.checkItemReward()
            }
        }
    private var xp: Int

    internal constructor(name: String, player: Player) {
        this.level = 0
        this.xp = 0
        this.CLASS_NAME = name
        this.playerReference = player
        this.itemReceived = BooleanArray(4)
    }

    internal constructor(json: JsonObject, player: Player) {
        this.CLASS_NAME = json["class"].toString()
        this.level = json["level"].toString().toByte()
        this.xp = json["xp"].toString().toInt()
        val jsonArray = json["rewardsClaimed"]!!.jsonArray
        this.itemReceived = BooleanArray(jsonArray.size)

        for (i in jsonArray.indices) {
            this.itemReceived[i] = jsonArray[i].toString().toBoolean()
        }
        this.playerReference = player
    }

    private fun checkLevelUp() {
        if (this.xp >= getNetLevelStep(level.toInt()) && this.level >= MAX_LEVEL) {
            level++
            Bukkit.getServer().broadcastMessage("LevelUp")
            this.checkNonItemReward()
        }
    }

    abstract fun reapplyRewardEffects()
    abstract fun removePermanentEffects()

    protected abstract fun giveBannerReward()
    protected abstract fun giveFirstToolReward()
    protected abstract fun giveSecondToolReward()
    protected abstract fun giveThirdToolReward()

    private fun checkNonItemReward() {
        if (level.toInt() == 5 || level.toInt() == 13 || level.toInt() == 15) {
            // Apply first / second buff and de-buff
            this.reapplyRewardEffects()
        } else if (level.toInt() == 7) {
            // Give horse access:
            PlayerData.getPlayerData(playerReference.name)?.horseData = horseData
            playerReference.sendMessage(                HorseCommand.unlockMessage            )
        } else if (level.toInt() == 10) {
            //Special ability
        } else {
            // Give Exp
            this.playerReference?.giveExp(this.level * 25)
        }
    }


    private fun checkItemReward() {
        if (this.level >= 1 && !itemReceived[0] && playerReference.inventory.firstEmpty() != -1) {
            this.giveBannerReward()
            itemReceived[0] = true
        } else if (this.level >= 1 && !itemReceived[0]) {
            playerReference.sendMessage(
                "You have an open reward from reaching level 1." + " Please make room in your inventory to receive it!"
            )
        }

        if (this.level >= 3 && !itemReceived[1] && playerReference.inventory.firstEmpty() != -1) {
            this.giveFirstToolReward()
            itemReceived[1] = true
        } else if (this.level >= 3 && !itemReceived[1]) {
            playerReference.sendMessage(
                "You have an open reward from reaching level 3." + " Please make room in your inventory to receive it!"
            )
        }

        if (this.level >= 13 && !itemReceived[2] && playerReference.inventory.firstEmpty() != -1) {
            this.giveSecondToolReward()
            itemReceived[2] = true
        } else if (this.level >= 13 && !itemReceived[2]) {
            playerReference.sendMessage(
                "You have an open reward from reaching level 15." + " Please make room in your inventory to receive it!"
            )
        }

        if (this.level >= 20 && !itemReceived[3] && playerReference.inventory.firstEmpty() != -1) {
            this.giveThirdToolReward()
            itemReceived[3] = true
        } else if (this.level >= 20 && !itemReceived[3]) {
            playerReference.sendMessage(
                "You have an open reward from reaching level 20." + " Please make room in your inventory to receive it!"
            )
        }
    }

    fun addXp(xp: Int) {
        this.xp += xp
        this.checkLevelUp()
        this.checkItemReward()
    }

    fun toJSON() {
        val jsonString: String = Json.encodeToString(this)
        val jsonFile = File("PlayerData" + File.separator + playerReference.name + ".json")
        jsonFile.writeText(jsonString)
//        val json = JSONObject()
//        json["class"] = this.CLASS_NAME
//        val rewards = JSONArray()
//        for (i in itemReceived.indices) {
//            rewards.add(i, itemReceived[i])
//        }
//        json["rewardsClaimed"] = rewards
//        json["level"] = this.level
//        json["xp"] = this.xp
//        return json
    }

    companion object {
        private const val MAX_LEVEL: Byte = 20

        private fun getNetLevelStep(level: Int): Int {
            val MAX_LEVEL_XP = 1000
            val FACTOR = (level + 1) * (level + 1) / 400.0
            return (FACTOR * MAX_LEVEL_XP).toInt()
        }

        fun fromJSON(json: JsonObject, player: Player): BaseClass? {
//            val jsonFile = File("PlayerData" + File.separator + player.name + ".json")
//            if (!jsonFile.exists()) {
//                return null
//            }
//            val json: JsonObject = Json.parseToJsonElement(jsonFile.readText()).jsonObject
            val className = json["class"]?.jsonPrimitive?.contentOrNull ?: return null
            return when (className) {
                WarriorClass.Companion.CLASS_NAME -> WarriorClass(json, player)
                MinerClass.Companion.CLASS_NAME -> MinerClass(json, player)
                LumberjackClass.Companion.CLASS_NAME -> LumberjackClass(json, player)
                FarmerClass.Companion.CLASS_NAME -> FarmerClass(json, player)
                ExplorerClass.Companion.CLASS_NAME -> ExplorerClass(json, player)
                else -> null
            }
        }
    }
}