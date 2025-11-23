package de.gieskerb.classCraft.classes

import de.gieskerb.classCraft.commands.HorseCommand
import de.gieskerb.classCraft.data.HorseData
import de.gieskerb.classCraft.data.PlayerData
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File

@Serializable
sealed class BaseClass {
    val className: String
    abstract val classItem: ItemStack
    protected abstract val horseData: HorseData?
    private val itemReceived: BooleanArray

    @Transient
    protected var playerReference: Player? = null

    var level: Byte = 0
        set(value) {
            for (i in 0 until value) {
                field = i.toByte()
                this.checkNonItemReward()
                this.checkItemReward()
            }
        }
    private var xp: Int

    protected constructor(name: String, player: Player) {
        this.level = 0
        this.xp = 0
        this.className = name
        this.playerReference = player
        this.itemReceived = BooleanArray(4)
    }

    private fun checkLevelUp() {
        if (this.xp >= getNetLevelStep(level.toInt()) && this.level >= MAX_LEVEL) {
            level++
//            Bukkit.getServer().broadcastMessage("LevelUp")
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
            PlayerData.getPlayerData(playerReference?.name)?.horseData = horseData
            playerReference?.sendMessage(HorseCommand.unlockMessage)
        } else if (level.toInt() == 10) {
            //Special ability
        } else {
            // Give Exp
            this.playerReference?.giveExp(this.level * 25)
        }
    }

    private fun checkItemReward() {
        if (this.level >= 1 && !itemReceived[0] && playerReference?.inventory?.firstEmpty() != -1) {
            this.giveBannerReward()
            itemReceived[0] = true
        } else if (this.level >= 1 && !itemReceived[0]) {
            playerReference?.sendMessage(
                "You have an open reward from reaching level 1." + " Please make room in your inventory to receive it!"
            )
        }

        if (this.level >= 3 && !itemReceived[1] && playerReference?.inventory?.firstEmpty() != -1) {
            this.giveFirstToolReward()
            itemReceived[1] = true
        } else if (this.level >= 3 && !itemReceived[1]) {
            playerReference?.sendMessage(
                "You have an open reward from reaching level 3." + " Please make room in your inventory to receive it!"
            )
        }

        if (this.level >= 13 && !itemReceived[2] && playerReference?.inventory?.firstEmpty() != -1) {
            this.giveSecondToolReward()
            itemReceived[2] = true
        } else if (this.level >= 13 && !itemReceived[2]) {
            playerReference?.sendMessage(
                "You have an open reward from reaching level 15." + " Please make room in your inventory to receive it!"
            )
        }

        if (this.level >= 20 && !itemReceived[3] && playerReference?.inventory?.firstEmpty() != -1) {
            this.giveThirdToolReward()
            itemReceived[3] = true
        } else if (this.level >= 20 && !itemReceived[3]) {
            playerReference?.sendMessage(
                "You have an open reward from reaching level 20." + " Please make room in your inventory to receive it!"
            )
        }
    }

    fun addXp(xp: Int) {
        this.xp += xp
        this.checkLevelUp()
        this.checkItemReward()
    }

    fun addPlayerReference(playerName: String) {
        this.playerReference = Bukkit.getPlayer(playerName)
    }

    companion object {
        private const val MAX_LEVEL: Byte = 20

        private fun getNetLevelStep(level: Int): Int {
            val maxLevelEXP = 1000
            val factor = (level + 1) * (level + 1) / 400.0
            return (factor * maxLevelEXP).toInt()
        }
    }
}