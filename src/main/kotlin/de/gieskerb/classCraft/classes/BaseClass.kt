package de.gieskerb.classCraft.classes

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.data.HorseData
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask
import java.util.Queue

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
            for (i in 0..value) {
                field = i.toByte()
                this.grantLevelReward()
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

    private fun givePlayerItems(items: Queue<ItemStack>) {
        val player = this.playerReference ?: return

        var itemGiverRetry: BukkitTask? = null

        itemGiverRetry = Bukkit.getScheduler().runTaskTimer(Main.plugin, Runnable {

            if (items.isEmpty()) {
                itemGiverRetry?.cancel()
                return@Runnable
            }

            if (player.inventory.firstEmpty() != -1) {
                val itemToGive = items.poll()
                player.inventory.addItem(itemToGive)
            } else {
                player.sendMessage("§cCan not deliver Item reward. Please make place in your inventory")
            }

        }, 0, 100)
    }

    /*
     * level rewards:
     * 1. iconic ARMOR-SET
     * 2. unique BANNER
     * 3. access HORSE
     * 4. access TITLE
     * 5. access UPGRADE
     * 6. material IRON
     * 7. access COMPASS LOCATION
     * 8. special TELEKINESIS
     * 9. custom PARTICLES
     * 10. access SPECIALIZER
     * 11. access HORSE CUSTOM
     * 12. passive POS+
     * 13. material DIAMOND
     * 14. access BAGPACK
     * 15. passive NEG-
     * 16. access PET
     * 17. special EXTRA EXP
     * 18. passive POS++
     * 19. material NETHERITE
     * 20. special DOUBLE DROPS
     */

    protected abstract fun classWeapon(): Queue<ItemStack> // Level 0
    protected abstract fun classArmorSet(): Queue<ItemStack> // Level 1
    protected abstract fun classBanner(): Queue<ItemStack> // Level 2

    private fun grantLevelReward() {
        println("Granting Level Reward: $level")
        when (this.level) {
            0.toByte() -> {
                this.givePlayerItems(this.classWeapon())
            }

            1.toByte() -> {
                this.givePlayerItems(this.classArmorSet())
            }

            2.toByte() -> {
                this.givePlayerItems(this.classBanner())
            }

            3.toByte() -> {
            }

            4.toByte() -> {
            }

            5.toByte() -> {
            }

            6.toByte() -> {
            }

            7.toByte() -> {
            }

            8.toByte() -> {
            }

            9.toByte() -> {
            }

            10.toByte() -> {
            }

            11.toByte() -> {
            }

            12.toByte() -> {
            }

            13.toByte() -> {
            }

            14.toByte() -> {
            }

            15.toByte() -> {
            }

            16.toByte() -> {
            }

            17.toByte() -> {
            }

            18.toByte() -> {
            }

            19.toByte() -> {
            }

            20.toByte() -> {
            }
        }
    }

    private fun checkLevelUp() {
        if (this.xp >= getNetLevelStep(level.toInt()) && this.level < MAX_LEVEL) {
            ++this.level
            this.xp = 0;
            this.grantLevelReward()
        }
    }

//    private fun checkNonItemReward() {
//        if (level.toInt() == 5 || level.toInt() == 13 || level.toInt() == 15) {
//            // Apply first / second buff and de-buff
//            this.reapplyRewardEffects()
//        } else if (level.toInt() == 7) {
//            // Give horse access:
//            PlayerData.getPlayerData(playerReference?.name)?.horseData = horseData
//            playerReference?.sendMessage(HorseCommand.unlockMessage)
//        } else if (level.toInt() == 10) {
//            //Special ability
//        } else {
//            // Give Exp
//            this.playerReference?.giveExp(this.level * 25)
//        }
//    }
//
//    private fun checkItemReward() {
//        if (this.level >= 1 && !itemReceived[0] && playerReference?.inventory?.firstEmpty() != -1) {
//            this.giveBannerReward()
//            itemReceived[0] = true
//        } else if (this.level >= 1 && !itemReceived[0]) {
//            playerReference?.sendMessage(
//                "You have an open reward from reaching level 1." + " Please make room in your inventory to receive it!"
//            )
//        }
//
//        if (this.level >= 3 && !itemReceived[1] && playerReference?.inventory?.firstEmpty() != -1) {
//            this.giveFirstToolReward()
//            itemReceived[1] = true
//        } else if (this.level >= 3 && !itemReceived[1]) {
//            playerReference?.sendMessage(
//                "You have an open reward from reaching level 3." + " Please make room in your inventory to receive it!"
//            )
//        }
//
//        if (this.level >= 13 && !itemReceived[2] && playerReference?.inventory?.firstEmpty() != -1) {
//            this.giveSecondToolReward()
//            itemReceived[2] = true
//        } else if (this.level >= 13 && !itemReceived[2]) {
//            playerReference?.sendMessage(
//                "You have an open reward from reaching level 15." + " Please make room in your inventory to receive it!"
//            )
//        }
//
//        if (this.level >= 20 && !itemReceived[3] && playerReference?.inventory?.firstEmpty() != -1) {
//            this.giveThirdToolReward()
//            itemReceived[3] = true
//        } else if (this.level >= 20 && !itemReceived[3]) {
//            playerReference?.sendMessage(
//                "You have an open reward from reaching level 20." + " Please make room in your inventory to receive it!"
//            )
//        }
//    }

    fun addXp(xp: Int) {
        this.xp += xp
        this.checkLevelUp()
    }

    fun addPlayerReference(playerName: String) {
        this.playerReference = Bukkit.getPlayer(playerName)
        println("Set Player Reference $playerName")
        this.grantLevelReward();
    }

    companion object {
        val classWeaponIdentifierKey = NamespacedKey(Main.plugin,"CLASS_WEAPON")
        private const val MAX_LEVEL: Byte = 20

        private fun getNetLevelStep(level: Int): Int {
            val maxLevelEXP = 1000
            val factor = (level + 1) * (level + 1) / 400.0
            return (factor * maxLevelEXP).toInt()
        }
    }
}