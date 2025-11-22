package de.gieskerb.classCraft.commands

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.data.DistanceTracker
import de.gieskerb.classCraft.data.HorseData
import de.gieskerb.classCraft.data.PlayerData
import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue

class HorseCommand : BasicCommand {
    private fun spawnHorse(player: Player): Horse {
        val horse = player.world.spawnEntity(player.location, EntityType.HORSE) as Horse
        val horseData: HorseData? = PlayerData.getPlayerData(player.name)?.horseData

        if (horseData != null) {
            horse.getAttribute(Attribute.MOVEMENT_SPEED)!!.baseValue = horseData.speed
            horse.getAttribute(Attribute.JUMP_STRENGTH)!!.baseValue = horseData.jump
            horse.setAdult()
            horse.setMetadata(METADATA_IDENTIFIER, FixedMetadataValue(Main.plugin, true))
            horse.ageLock = true
            horse.lootTable = null
            horse.color = horseData.color
            horse.style = horseData.style
            horse.getAttribute(Attribute.MAX_HEALTH)!!.baseValue = horseData.health
            horse.health = horseData.health
            horse.customName = horseData.name
            horse.isCustomNameVisible = true
            horse.inventory.armor = ItemStack(horseData.armor)
            horse.inventory.saddle = ItemStack(Material.SADDLE)
            horse.owner = player
        }

        PlayerData.getPlayerData(player.name)!!.setDistanceTracker(DistanceTracker(player, horse))

        return horse
    }

    private val helpMessage: String
        get() = """
            §e§lHorse Command Help
            
            §r§6/horse help§e: Shows this help message.
            §r§6/horse spawn§e: Spawns your own class specific horse at your position.
            §r§6/horse mount§e: Spawns your horse and mounts you directly.
            §r§6/horse customize§e: Gives you the ability to change §9Color§e and §9Style§e of the horse.
            
            """.trimIndent()

    override fun execute(commandSourceStack: CommandSourceStack, args: Array<out String>) {
        val sender = commandSourceStack.sender
        if (sender !is Player) {
            sender.sendMessage("You must be a player to use this command.")
            return
        }

        val playerData = PlayerData.getPlayerData(sender.name)
        if (playerData?.horseData == null) {
            sender.sendMessage("You dont have permission to use this command yet.")
            return
        }

        if (args.size == 1 && args[0] == "mount") {
            val horse = spawnHorse(sender)
            horse.addPassenger(sender)
        } else if (args.size == 1 && args[0] == "help") {
            sender.sendMessage(helpMessage)
        } else if (args.size == 1 && args[0] == "spawn") {
            spawnHorse(sender)
        } else if (args.size == 1 && args[0] == "customize") {
            sender.openInventory(getCustomizeMenu(sender)!!)
        } else {
            if (args.size == 2) {
                val horse = sender.vehicle as Horse?
                if (args[0] == "speed") {
                    horse!!.getAttribute(Attribute.MOVEMENT_SPEED)!!.baseValue = args[1].toDouble()
                } else if (args[0] == "jump") {
                    horse!!.getAttribute(Attribute.JUMP_STRENGTH)!!.baseValue = args[1].toDouble()
                }
            }
        }
    }

    companion object {
        const val METADATA_IDENTIFIER: String = "CLASS_HORSE"

        val unlockMessage: String
            get() = "$6You have now unlocked your class specific horse! Use /horse for more details."
        

        private var customizeMenu: Inventory? = null

        private fun generateItem(material: Material, name: String): ItemStack {
            val item = ItemStack(material)
            val meta = item.itemMeta
            meta!!.setDisplayName("§r§7§l$name")
            item.setItemMeta(meta)
            return item
        }

        fun getCustomizeMenu(player: Player): Inventory? {
            if (customizeMenu == null) {
                val invSize = 45
                customizeMenu = Bukkit.createInventory(null, invSize, "Customize your Horse")

                for (i in 0 until invSize) {
                    if (i % 9 == 0 || i % 9 == 8 || (i % 9 == 1 && i > 10) || (i % 9 == 7 && i > 16) || i / 9 == 2) {
                        customizeMenu!!.setItem(i, HandyItems.backGroundItem)
                    }
                }

                customizeMenu!!.setItem(10, generateItem(Material.WHITE_TERRACOTTA, "White"))
                customizeMenu!!.setItem(11, generateItem(Material.ORANGE_TERRACOTTA, "Creamy"))
                customizeMenu!!.setItem(12, generateItem(Material.BROWN_CONCRETE, "Chestnut"))
                customizeMenu!!.setItem(13, generateItem(Material.BROWN_TERRACOTTA, "Brown"))
                customizeMenu!!.setItem(14, generateItem(Material.BLACK_TERRACOTTA, "Black"))
                customizeMenu!!.setItem(15, generateItem(Material.CYAN_TERRACOTTA, "Gray"))
                customizeMenu!!.setItem(16, generateItem(Material.GRAY_TERRACOTTA, "Dark Brown"))

                customizeMenu!!.setItem(29, generateItem(Material.BARRIER, "None"))
                customizeMenu!!.setItem(30, generateItem(Material.IRON_BOOTS, "White Stockings and Blaze"))
                customizeMenu!!.setItem(31, generateItem(Material.PAPER, "White Field"))
                customizeMenu!!.setItem(32, generateItem(Material.PUMPKIN_SEEDS, "White Spots"))
                customizeMenu!!.setItem(33, generateItem(Material.MELON_SEEDS, "Black Dots"))
            }
            val playerData = PlayerData.getPlayerData(player.name)
            val horseData = playerData!!.horseData
            if (horseData != null) {
                selectColor(customizeMenu!!, horseData.color)
                selectStyle(customizeMenu!!, horseData.style)
            }
            return customizeMenu
        }

        fun selectColor(inventory: Inventory, color: Horse.Color) {
            if (inventory !== customizeMenu && customizeMenu != null) {
                return
            }

            inventory.setItem(
                1, if (color == Horse.Color.WHITE) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                2, if (color == Horse.Color.CREAMY) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                3, if (color == Horse.Color.CHESTNUT) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                4, if (color == Horse.Color.BROWN) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                5, if (color == Horse.Color.BLACK) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                6, if (color == Horse.Color.GRAY) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                7, if (color == Horse.Color.DARK_BROWN) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
        }

        fun selectStyle(inventory: Inventory, style: Horse.Style) {
            if (inventory !== customizeMenu && customizeMenu != null) {
                return
            }

            inventory.setItem(
                38, if (style == Horse.Style.NONE) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                39, if (style == Horse.Style.WHITE) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                40, if (style == Horse.Style.WHITEFIELD) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                41, if (style == Horse.Style.WHITE_DOTS) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
            inventory.setItem(
                42, if (style == Horse.Style.BLACK_DOTS) HandyItems.selectedItem else HandyItems.notSelectedItem
            )
        }
    }
}