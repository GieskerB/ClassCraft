package de.gieskerb.classCraft.classes

import de.gieskerb.classCraft.data.HorseData
import de.gieskerb.classCraft.data.PlayerData
import de.gieskerb.classCraft.commands.HorseCommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlin.collections.get
import kotlin.text.get

@Serializable
@SerialName("BaseClass")
abstract class BaseClass(
    val className: String,
    var level: Byte = 0,
    var xp: Int = 0,
    var itemReceived: BooleanArray = BooleanArray(4)
) {

    @Transient
    protected lateinit var playerReference: Player

    @Transient
    abstract val classItem: ItemStack?

    internal constructor(name: String, player: Player) : this(name) {
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

    protected abstract fun getInitialHorseData(): HorseData
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
            PlayerData.getPlayerData(playerReference.name)?.horseData = getInitialHorseData()
            playerReference.sendMessage(HorseCommand.unlockMessage)
        } else if (level.toInt() == 10) {
            //Special ability
        } else {
            // Give Exp
            this.playerReference.giveExp(this.level * 25)
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

    companion object {

        private const val MAX_LEVEL: Byte = 20

        private fun getNetLevelStep(level: Int): Int {
            val MAX_LEVEL_XP = 1000
            val FACTOR = (level + 1) * (level + 1) / 400.0
            return (FACTOR * MAX_LEVEL_XP).toInt()
        }

        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                polymorphic(BaseClass::class) {
                    subclass(ExplorerClass::class, ExplorerClass.serializer())
                    subclass(FarmerClass::class, FarmerClass.serializer())
                    subclass(LumberjackClass::class, LumberjackClass.serializer())
                    subclass(MinerClass::class, MinerClass.serializer())
                    subclass(WarriorClass::class, WarriorClass.serializer())
                }
            }
        }

        fun serialize(baseClass: BaseClass): String {
            return json.encodeToString(baseClass)
        }

        fun deserialize(jsonString: String, player: Player): BaseClass? {
            return try {
                if (jsonString.isBlank()) {
                    null
                } else {
                    val deserializedClass = json.decodeFromString<BaseClass>(jsonString)
                    deserializedClass.playerReference = player
                    deserializedClass
                }
            } catch (e: Exception) {
                println("Error deserializing BaseClass from JSON: $jsonString - ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }
}