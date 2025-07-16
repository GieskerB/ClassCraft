package de.gieskerb.classCraft.classes

import de.gieskerb.classCraft.data.HorseData
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.Block
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
@SerialName("LumberjackClass")
class LumberjackClass : BaseClass {

    constructor(player: Player) : super(CLASS_NAME,0, 0, BooleanArray(4))
    {
        this.playerReference = player
    }

    override fun reapplyRewardEffects() {
        if (super.level >= 17) {
            super.playerReference.addPotionEffect(PotionEffect(PotionEffectType.STRENGTH, -1, 1, false, false))
        } else if (super.level >= 5) {
            super.playerReference.addPotionEffect(PotionEffect(PotionEffectType.STRENGTH, -1, 0, false, false))
        }
        if (super.level >= 15) {
            super.playerReference.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, -1, 0, false, false))
        }
    }

    override val classItem: ItemStack?
        get() = displayItem

    override fun getInitialHorseData(): HorseData = HorseData(
        Horse.Color.entries.toTypedArray()[(Math.random() * Horse.Color.entries.toTypedArray().size).toInt()],
        Horse.Style.entries.toTypedArray()[(Math.random() * Horse.Style.entries.toTypedArray().size).toInt()],
        0.3,
        0.6,
        60.0,
        HorseData.horseNameByPlayer(super.playerReference, super.className),
        Material.DIAMOND_HORSE_ARMOR
    )

    override fun removePermanentEffects() {
        super.playerReference.removePotionEffect(PotionEffectType.RESISTANCE)
        super.playerReference.removePotionEffect(PotionEffectType.SLOWNESS)
    }

    val surroundedSaplings: ArrayList<Block> = ArrayList()

    override fun giveBannerReward() {
        val item = ItemStack(Material.WHITE_BANNER)
        val m = item.itemMeta as BannerMeta

        val patterns: MutableList<Pattern> = java.util.ArrayList()
        patterns.add(Pattern(DyeColor.GRAY, PatternType.CROSS))
        patterns.add(Pattern(DyeColor.WHITE, PatternType.CIRCLE))
        patterns.add(Pattern(DyeColor.WHITE, PatternType.RHOMBUS))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.TRIANGLE_BOTTOM))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.TRIANGLE_TOP))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.SQUARE_BOTTOM_LEFT))
        patterns.add(Pattern(DyeColor.BROWN, PatternType.STRAIGHT_CROSS))
        patterns.add(Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.STRIPE_RIGHT))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.STRIPE_TOP))

        m.patterns = patterns

        item.setItemMeta(m)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveFirstToolReward() {
        val item = ItemStack(Material.STONE_AXE)
        item.addEnchantment(Enchantment.EFFICIENCY, 2)
        item.addEnchantment(Enchantment.UNBREAKING, 2)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveSecondToolReward() {
        val item = ItemStack(Material.IRON_AXE)
        item.addEnchantment(Enchantment.EFFICIENCY, 5)
        item.addEnchantment(Enchantment.UNBREAKING, 3)
        item.addEnchantment(Enchantment.FORTUNE, 3)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveThirdToolReward() {
        val item = ItemStack(Material.NETHERITE_AXE)
        item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5)
        item.addUnsafeEnchantment(Enchantment.FORTUNE, 5)
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 10)
        super.playerReference.inventory.addItem(item)
    }

    companion object {
        const val CLASS_NAME: String = "Lumberjack"
        var displayItem: ItemStack? = null
            get() {
                if (field == null) {
                    field = ItemStack(Material.NETHERITE_AXE)
                    val itemMeta = checkNotNull(field!!.itemMeta)
                    itemMeta.setItemName("§6§lLumberjack-Class")
                    val loreList: MutableList<String> = ArrayList()
                    loreList.add("§r§2super is a lore line!")
                    loreList.add("§r§2And super is anoter one.")
                    itemMeta.lore = loreList
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    field!!.setItemMeta(itemMeta)
                }
                return field
            }
            private set
    }
}