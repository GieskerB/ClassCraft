package de.gieskerb.class_craft.classes

import de.gieskerb.class_craft.data.HorseData
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.json.simple.JSONArray
import org.json.simple.JSONObject

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

    internal constructor(json: JSONObject, player: Player) {
        this.CLASS_NAME = json["class"].toString()
        this.level = json["level"].toString().toByte()
        this.xp = json["xp"].toString().toInt()
        val jsonArray: JSONArray = json.get("rewardsClaimed") as JSONArray
        this.itemReceived = BooleanArray(jsonArray.size)
        for (i in 0 until jsonArray.size) {
            itemReceived[i] = jsonArray[i] as Boolean
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

    // TODO Milk
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
            playerReference.sendMessage(
                "You have now unlocked your class specific horse! Use /horse for more details."
            )
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

    fun toJSON(): JSONObject {
        val json = JSONObject()
        json["class"] = this.CLASS_NAME
        val rewards = JSONArray()
        for (i in itemReceived.indices) {
            rewards.add(i, itemReceived[i])
        }
        json["rewardsClaimed"] = rewards
        json["level"] = this.level
        json["xp"] = this.xp
        return json
    }

    companion object {
        private const val MAX_LEVEL: Byte = 20

        private fun getNetLevelStep(level: Int): Int {
            val MAX_LEVEL_XP = 1000
            val FACTOR = (level + 1) * (level + 1) / 400.0
            return (FACTOR * MAX_LEVEL_XP).toInt()
        }

        fun fromJSON(json: JSONObject, player: Player): BaseClass? {
            if (json.isEmpty()) {
                return null
            }
            return when (json["class"]) {
                WarriorClass.CLASS_NAME -> WarriorClass(json, player)
                MinerClass.CLASS_NAME -> MinerClass(json, player)
                LumberjackClass.CLASS_NAME -> LumberjackClass(json, player)
                FarmerClass.CLASS_NAME -> FarmerClass(json, player)
                ExplorerClass.CLASS_NAME -> ExplorerClass(json, player)
                else -> null
            }
        }
    }
}