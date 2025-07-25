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
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
@SerialName("ExplorerClass")
class ExplorerClass : BaseClass {

    constructor(player: Player) : super(CLASS_NAME,0, 0, BooleanArray(4))
    {
        this.playerReference = player
    }

    override fun reapplyRewardEffects() {
    }

    override fun removePermanentEffects() {
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

    override fun giveFirstToolReward() {
        val item = ItemStack(Material.CHAINMAIL_BOOTS)
        item.addEnchantment(Enchantment.PROTECTION, 2)
        item.addEnchantment(Enchantment.UNBREAKING, 2)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveSecondToolReward() {
        val item = ItemStack(Material.IRON_BOOTS)
        item.addEnchantment(Enchantment.PROTECTION, 4)
        item.addEnchantment(Enchantment.UNBREAKING, 3)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveThirdToolReward() {
        val item = ItemStack(Material.NETHERITE_HOE)
        item.addUnsafeEnchantment(Enchantment.PROTECTION, 5)
        item.addUnsafeEnchantment(Enchantment.FORTUNE, 5)
        item.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 5)
        item.addUnsafeEnchantment(Enchantment.FEATHER_FALLING, 5)
        item.addUnsafeEnchantment(Enchantment.FROST_WALKER, 3)
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 10)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveBannerReward() {
        val item = ItemStack(Material.GRAY_BANNER)
        val m = item.itemMeta as BannerMeta

        val patterns: MutableList<Pattern> = java.util.ArrayList()
        patterns.add(Pattern(DyeColor.GRAY, PatternType.HALF_HORIZONTAL))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.HALF_HORIZONTAL_BOTTOM))
        patterns.add(Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT))
        patterns.add(Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.STRIPE_CENTER))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.BORDER))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.HALF_HORIZONTAL_BOTTOM))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.CURLY_BORDER))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.STRIPE_TOP))

        m.patterns = patterns

        item.itemMeta = m
        super.playerReference.inventory.addItem(item)
    }

    companion object {
        const val CLASS_NAME: String = "Explorer"
        var displayItem: ItemStack? = null
            get() {
                if (field == null) {
                    field = ItemStack(Material.NETHERITE_BOOTS)
                    val itemMeta = checkNotNull(field!!.itemMeta)
                    itemMeta.setItemName("§6§lExplorer-Class")
                    val loreList: MutableList<String> = ArrayList()
                    loreList.add("§r§2super is a lore line!")
                    loreList.add("§r§2And super is anoter one.")
                    itemMeta.lore = loreList
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    field!!.itemMeta = itemMeta
                }
                return field
            }
            private set
    }
}