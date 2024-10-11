package de.gieskerb.class_craft.classes

import de.gieskerb.class_craft.data.HorseData
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
import org.json.simple.JSONObject

class ExplorerClass : BaseClass {
    constructor(player: Player) : super(Companion.CLASS_NAME, player)

    constructor(json: JSONObject, player: Player) : super(json, player)

    override fun reapplyRewardEffects() {
    }

    override fun removePermanentEffects() {
    }

    override val classItem: ItemStack?
        get() = displayItem

    override val horseData: HorseData
        get() = HorseData(
            Horse.Color.entries.toTypedArray()[(Math.random() * Horse.Color.entries.toTypedArray().size).toInt()],
            Horse.Style.entries.toTypedArray()[(Math.random() * Horse.Style.entries.toTypedArray().size).toInt()],
            0.3,
            0.6,
            60.0,
            HorseData.horseNameByPlayer(super.playerReference, super.CLASS_NAME),
            Material.DIAMOND_HORSE_ARMOR
        )

    override fun giveFirstToolReward() {
        val item = ItemStack(Material.STONE_SWORD)
        item.addUnsafeEnchantment(Enchantment.SHARPNESS, 1)
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 1)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveSecondToolReward() {
        val item = ItemStack(Material.DIAMOND_SWORD)
        item.addUnsafeEnchantment(Enchantment.SHARPNESS, 3)
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 2)
        item.addUnsafeEnchantment(Enchantment.LOOTING, 1)
        super.playerReference.inventory.addItem(item)
    }

    override fun giveThirdToolReward() {
        val item = ItemStack(Material.NETHERITE_SWORD)
        item.addUnsafeEnchantment(Enchantment.SHARPNESS, 5)
        item.addUnsafeEnchantment(Enchantment.SMITE, 5)
        item.addUnsafeEnchantment(Enchantment.BANE_OF_ARTHROPODS, 5)
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

        item.setItemMeta(m)
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
                    field!!.setItemMeta(itemMeta)
                }
                return field
            }
            private set
    }
}