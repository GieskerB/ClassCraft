package de.gieskerb.classCraft.classes

import de.gieskerb.classCraft.data.HorseData
import kotlinx.serialization.json.JsonObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
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
import java.util.Objects

class MinerClass : BaseClass {
    constructor(player: Player) : super(CLASS_IDENTIFIER, player)

    constructor(json: JsonObject, player: Player) : super(json, player)

    override fun reapplyRewardEffects() {
        if (super.level >= 17) {
            super.playerReference.addPotionEffect(PotionEffect(PotionEffectType.HASTE, -1, 1, false, false))
        } else if (super.level >= 5) {
            super.playerReference.addPotionEffect(PotionEffect(PotionEffectType.HASTE, -1, 0, false, false))
        }
        if (super.level >= 15) {
            super.playerReference.getAttribute(Attribute.MAX_HEALTH)?.baseValue =
                15.0
        }
    }

    override fun removePermanentEffects() {
        super.playerReference.removePotionEffect(PotionEffectType.HASTE)
        Objects.requireNonNull(super.playerReference.getAttribute(Attribute.MAX_HEALTH))?.baseValue =
            20.0
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
            HorseData.horseNameByPlayer(super.playerReference, super.className),
            Material.DIAMOND_HORSE_ARMOR
        )

        override fun giveFirstToolReward() {
            val item = ItemStack(Material.STONE_PICKAXE)
            item.addEnchantment(Enchantment.EFFICIENCY, 2)
            item.addEnchantment(Enchantment.UNBREAKING, 2)
            super.playerReference.inventory.addItem(item)
        }
    
        override fun giveSecondToolReward() {
            val item = ItemStack(Material.IRON_PICKAXE)
            item.addEnchantment(Enchantment.EFFICIENCY, 5)
            item.addEnchantment(Enchantment.UNBREAKING, 3)
            item.addEnchantment(Enchantment.FORTUNE, 3)
            super.playerReference.inventory.addItem(item)
        }
    
        override fun giveThirdToolReward() {
            val item = ItemStack(Material.NETHERITE_PICKAXE)
            item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5)
            item.addUnsafeEnchantment(Enchantment.FORTUNE, 5)
            item.addUnsafeEnchantment(Enchantment.UNBREAKING, 10)
            super.playerReference.inventory.addItem(item)
        }

    override fun giveBannerReward() {
        val item = ItemStack(Material.WHITE_BANNER)
        val m = item.itemMeta as BannerMeta

        val patterns: MutableList<Pattern> = ArrayList()
        patterns.add(Pattern(DyeColor.GRAY, PatternType.CROSS))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.STRIPE_BOTTOM))
        patterns.add(Pattern(DyeColor.BROWN, PatternType.STRAIGHT_CROSS))
        patterns.add(Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.HALF_HORIZONTAL))
        patterns.add(Pattern(DyeColor.GRAY, PatternType.BORDER))

        m.patterns = patterns

        item.setItemMeta(m)
        super.playerReference.inventory.addItem(item)
    }

    companion object {
        const val CLASS_IDENTIFIER: String = "Miner"
        var displayItem: ItemStack? = null
            get() {
                if (field == null) {
                    field = ItemStack(Material.NETHERITE_PICKAXE)
                    val itemMeta = checkNotNull(field!!.itemMeta)
                    itemMeta.itemName(Component.text("$CLASS_IDENTIFIER-Class", TextColor.color(0xFFAA00), TextDecoration.BOLD))
                    val loreList: MutableList<Component> = ArrayList()
                    loreList.add(Component.text("This is a lore line!", TextColor.color(0x00AA00)))
                    loreList.add(Component.text("And this is a lore line, too!", TextColor.color(0x00AA00)))
                    itemMeta.lore(loreList)
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    field!!.setItemMeta(itemMeta)
                }
                return field
            }
            private set
    }
}