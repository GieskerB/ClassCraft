package de.gieskerb.classCraft.classes

import de.gieskerb.classCraft.classes.WarriorClass.Companion.CLASS_ID
import de.gieskerb.classCraft.data.HorseData
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
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
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.LinkedList
import java.util.Queue

@Serializable
class HunterClass : BaseClass {
    constructor(player: Player) : super(CLASS_IDENTIFIER, player)

    override val classItem: ItemStack
        get() = displayItem

    override val horseData: HorseData
        get() = HorseData(
            Horse.Color.entries.toTypedArray()[(Math.random() * Horse.Color.entries.toTypedArray().size).toInt()],
            Horse.Style.entries.toTypedArray()[(Math.random() * Horse.Style.entries.toTypedArray().size).toInt()],
            0.3,
            0.6,
            60.0,
            HorseData.horseNameByPlayer(super.playerReference!!, super.className),
            Material.DIAMOND_HORSE_ARMOR
        )

    override fun classWeapon(): Queue<ItemStack> {
        val item = ItemStack(Material.BOW)
        val meta: ItemMeta = item.itemMeta!!

        meta.itemName(
            Component.text(
                "Mighty Hunter Bow",
                TextColor.color(0xAA0000)
            )
        )
        val loreList: MutableList<Component> = ArrayList()
        loreList.add(Component.text("This is a lore line!", TextColor.color(0x00AA00)))
        loreList.add(Component.text("And this is a lore line, too!", TextColor.color(0x00AA00)))
        meta.lore(loreList)
        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        item.setItemMeta(meta)
        item.editPersistentDataContainer { pdc ->
            pdc.set(
                classWeaponIdentifierKey,
                PersistentDataType.BYTE,
                ((CLASS_ID shl 4 or this.level.toInt()).toByte())
            )
        }
        item.setItemMeta(meta)
        val queue = LinkedList<ItemStack>()
        queue.add(item)
        return queue
    }

    override fun classBanner() :Queue<ItemStack> {
        val item = ItemStack(Material.GRAY_BANNER)
        val meta = item.itemMeta as BannerMeta

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

        meta.patterns = patterns

        item.setItemMeta(meta)
        val queue = LinkedList<ItemStack>()
        queue.add(item)
        return queue
    }

    override fun classArmorSet(): Queue<ItemStack> {
        // TODO("Not yet implemented")
        return LinkedList<ItemStack>()
    }

    companion object {
        const val CLASS_IDENTIFIER: String = "Hunter"

        var displayItem: ItemStack = ItemStack(Material.BOW)
            get() {
                if (!field.itemMeta.itemName().toString().startsWith(CLASS_IDENTIFIER)) {
                    val itemMeta = checkNotNull(field.itemMeta)
                    itemMeta.itemName(
                        Component.text(
                            "$CLASS_IDENTIFIER-Class",
                            TextColor.color(0xFFAA00),
                            TextDecoration.BOLD
                        )
                    )
                    val loreList: MutableList<Component> = ArrayList()
                    loreList.add(Component.text("This is a lore line!", TextColor.color(0x00AA00)))
                    loreList.add(Component.text("And this is a lore line, too!", TextColor.color(0x00AA00)))
                    itemMeta.lore(loreList)
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    field.setItemMeta(itemMeta)
                }
                return field
            }
            private set
    }
}