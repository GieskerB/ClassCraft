package de.gieskerb.classCraft.classes

import de.gieskerb.classCraft.data.HorseData
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
@SerialName("WarriorClass")
class WarriorClass : BaseClass {

    constructor(player: Player) : super(CLASS_NAME,0, 0, BooleanArray(4))
    {
        this.playerReference = player
    }

    var killStreak: Double = 0.0
        private set


    fun addKill() {
        killStreak += 1
    }

    fun subsideStreak() {
        killStreak *=.95

        if (killStreak < 0.01)
            killStreak = 0.0
    }

    override fun getInitialHorseData(): HorseData = HorseData(
        Horse.Color.entries.toTypedArray()[(Math.random() * Horse.Color.entries.toTypedArray().size).toInt()],
        Horse.Style.entries.toTypedArray()[(Math.random() * Horse.Style.entries.toTypedArray().size).toInt()],
        0.3,
        0.6,
        60.0,
        HorseData.horseNameByPlayer(super.playerReference, super.className),
        Material.DIAMOND_HORSE_ARMOR
    )

    override fun reapplyRewardEffects() {
        if (super.level >= 17) {
            super.playerReference.addPotionEffect(PotionEffect(PotionEffectType.RESISTANCE, -1, 1, false, false))
        } else if (super.level >= 5) {
            super.playerReference.addPotionEffect(PotionEffect(PotionEffectType.RESISTANCE, -1, 0, false, false))
        }
        if (super.level >= 15) {
            super.playerReference.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, -1, 0, false, false))
        }
    }

    override fun removePermanentEffects() {
        super.playerReference.removePotionEffect(PotionEffectType.RESISTANCE)
        super.playerReference.removePotionEffect(PotionEffectType.SLOWNESS)
    }

    override val classItem: ItemStack?
        get() = displayItem

    override fun giveBannerReward() {
        val item = ItemStack(Material.GRAY_BANNER)
        val m = item.itemMeta as BannerMeta

        val patterns: MutableList<Pattern> = ArrayList()
        patterns.add(Pattern(DyeColor.LIGHT_GRAY, PatternType.FLOWER))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.HALF_HORIZONTAL_BOTTOM))
        patterns.add(Pattern(DyeColor.BLACK, PatternType.STRIPE_CENTER))
        patterns.add(Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER))
        patterns.add(Pattern(DyeColor.LIGHT_GRAY, PatternType.TRIANGLE_TOP))
        patterns.add(Pattern(DyeColor.ORANGE, PatternType.STRIPE_TOP))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.SQUARE_TOP_LEFT))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.SQUARE_TOP_RIGHT))
        patterns.add(Pattern(DyeColor.LIGHT_GRAY, PatternType.TRIANGLES_TOP))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.CURLY_BORDER))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.CURLY_BORDER))

        m.patterns = patterns

        item.itemMeta = m
        super.playerReference.inventory.addItem(item)
    }

    override fun giveFirstToolReward() {
        val item = ItemStack(Material.STONE_SWORD)
        item.addEnchantment(Enchantment.SHARPNESS, 2)
        item.addEnchantment(Enchantment.UNBREAKING, 2)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveSecondToolReward() {
        val item = ItemStack(Material.IRON_SWORD)
        item.addEnchantment(Enchantment.SHARPNESS, 5)
        item.addEnchantment(Enchantment.UNBREAKING, 3)
        item.addEnchantment(Enchantment.LOOTING, 3)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveThirdToolReward() {
        val item = ItemStack(Material.NETHERITE_SWORD)
        item.addUnsafeEnchantment(Enchantment.SHARPNESS, 5)
        item.addUnsafeEnchantment(Enchantment.SMITE, 5)
        item.addUnsafeEnchantment(Enchantment.BANE_OF_ARTHROPODS, 5)
        item.addUnsafeEnchantment(Enchantment.LOOTING, 5)
        item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 5)
        item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 3)
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3)
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 10)
        super.playerReference.inventory.addItem(item)
    }

    companion object {
        const val CLASS_NAME: String = "Warrior"
        var displayItem: ItemStack? = null
            get() {
                if (field == null) {
                    field = ItemStack(Material.NETHERITE_SWORD)
                    val itemMeta = checkNotNull(field!!.itemMeta)
                    itemMeta.setItemName("§6§lWarrior-Class")
                    val loreList: MutableList<String> = ArrayList()
                    loreList.add("§r§2super is a lore line!")
                    loreList.add("§r§2And super is another one.")
                    itemMeta.lore = loreList
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    field!!.setItemMeta(itemMeta)
                }
                return field
            }
            private set
    }
}