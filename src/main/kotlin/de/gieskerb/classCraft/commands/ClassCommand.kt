package de.gieskerb.classCraft.commands

import de.gieskerb.classCraft.classes.BaseClass
import de.gieskerb.classCraft.classes.ExplorerClass
import de.gieskerb.classCraft.classes.FarmerClass
import de.gieskerb.classCraft.classes.LumberjackClass
import de.gieskerb.classCraft.classes.MinerClass
import de.gieskerb.classCraft.classes.WarriorClass
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class ClassCommand : CommandExecutor {
    private fun getHelpMessage(isOp: Boolean): String {
        var helpMessage = """
            §e§lClass Command Help
            
            §r§6/class help§e: Shows this help message.
            §r§6/class select§e: Select your class via a menu.
            §r§6/class select §o[class]§r§e: Selects your class by text. Possible classes are: §9Warrior§e, §9Miner§e, §9Lumberjack§e, §9Farmer§e and §9Explorer§e.
            §r§6/class remove§e: Removes your current class.
            §r§6/class level§e: Shows your current class level.
            
            """.trimIndent()
        if (isOp) {
            helpMessage += "§r§6/class level §o[class]§r§e: §l§c!Requires OP permission!§r§e. Sets your current class level directly.\n"
        }
        return helpMessage
    }

    override fun onCommand(sender: CommandSender, command: Command, string: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("You must be a player to use this command.")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("Missing arguments. Please use /class help to see more details!")
        } else if (args.size == 1) {
            when (args[0]) {
                "help" -> sender.sendMessage(getHelpMessage(sender.isOp()))
                "select" -> sender.openInventory(selectInventory!!)
                "remove" -> {
                    PlayerData.getPlayerData(sender.getName())!!.resetClass()
                    sender.sendMessage("You have removed your class.")
                }

                "level" -> if (PlayerData.getPlayerData(sender.getName())?.activeClass != null) {
                    sender.openInventory(getLevelMenu(sender)!!)
                }

                else -> sender.sendMessage("Unknown command. Please use /class help to see more details!")
            }
        } else if (args.size == 2) {
            when (args[0]) {
                "select" -> when (args[1]) {
                    "Warrior", "warrior" -> PlayerData.getPlayerData(sender.getName())!!
                        .changeClass(WarriorClass(sender))

                    "Miner", "miner" -> PlayerData.getPlayerData(sender.getName())!!.changeClass(MinerClass(sender))
                    "Lumberjack", "lumberjack" -> PlayerData.getPlayerData(sender.getName())!!
                        .changeClass(LumberjackClass(sender))

                    "Farmer", "farmer" -> PlayerData.getPlayerData(sender.getName())!!.changeClass(FarmerClass(sender))
                    "Explorer", "explorer" -> PlayerData.getPlayerData(sender.getName())!!
                        .changeClass(ExplorerClass(sender))

                    else -> sender.sendMessage("Unknown class. Please use /class help to see more details")
                }

                "level" -> {
                    if (!sender.isOp()) {
                        sender.sendMessage("You dont have permission to use this command.")

                    } else {
                        try {
                            val level = args[1].toInt()
                            if (level < 0 || level > 20) {
                                throw NumberFormatException()
                            }
                            val activeClass: BaseClass? = PlayerData.getPlayerData(sender.getName())?.activeClass
                            if (activeClass != null) {
                                activeClass.level = level.toByte()
                            } else {
                                sender.sendMessage("You dont have a class selected right now.")
                            }
                        } catch (e: NumberFormatException) {
                            sender.sendMessage("Invalid level. Value must be an integer between 0 and 20!")
                        }
                    }
                }

                else -> sender.sendMessage("Unknown command. Please use /class help to see more details!")
            }
        } else {
            sender.sendMessage("Too many arguments. Please use /class help to see more details!")
        }

        return true
    }

    companion object {
        private var selectMenu: Inventory? = null
        private var levelMenu: Inventory? = null

        val selectInventory: Inventory?
            get() {
                if (selectMenu == null) {
                    val INVENTORY_SIZE = 27
                    selectMenu = Bukkit.createInventory(null, INVENTORY_SIZE, "Select your class")

                    for (i in 0 until INVENTORY_SIZE) {
                        if (i != 9 && i != 11 && i != 13 && i != 15 && i != 17) {
                            selectMenu!!.setItem(i, HandyItems.backGroundItem)
                        }
                    }

                    selectMenu!!.setItem(9, WarriorClass.displayItem)
                    selectMenu!!.setItem(11, MinerClass.displayItem)
                    selectMenu!!.setItem(13, LumberjackClass.displayItem)
                    selectMenu!!.setItem(15, FarmerClass.displayItem)
                    selectMenu!!.setItem(17, ExplorerClass.displayItem)
                }
                return selectMenu
            }

        fun getLevelMenu(player: Player): Inventory? {
            val INVENTORY_SIZE = 45
            if (levelMenu == null) {
                levelMenu = Bukkit.createInventory(null, INVENTORY_SIZE, "Level up your class")

                for (i in 0 until INVENTORY_SIZE) {
                    if (i <= 9 || i >= 35 || i % 9 == 0 || i % 9 == 8) {
                        levelMenu!!.setItem(i, HandyItems.backGroundItem)
                    }
                }
            }

            val playerData = checkNotNull(PlayerData.getPlayerData(player.name))
            val playerLevel = if (playerData.activeClass == null) 0 else playerData.activeClass!!.level

            var levelPaneIndex = 1

            for (i in 0 until INVENTORY_SIZE) {
                if (!(i <= 9 || i >= 35 || i % 9 == 0 || i % 9 == 8)) {
                    if (i == INVENTORY_SIZE / 2) {
                        levelMenu!!.setItem(
                            i,
                            if (playerData.activeClass == null) null else playerData.activeClass!!.classItem
                        )
                        continue
                    }
                    val levelPane = ItemStack(
                        if (levelPaneIndex <= playerLevel) Material.LIME_STAINED_GLASS_PANE else (if ((playerLevel + 1) == levelPaneIndex) Material.YELLOW_STAINED_GLASS_PANE else Material.RED_STAINED_GLASS_PANE)
                    )

                    val levelPaneItemMeta = levelPane.itemMeta
                    levelPaneItemMeta!!.setDisplayName("§rLevel: $levelPaneIndex")
                    levelPane.setItemMeta(levelPaneItemMeta)
                    levelMenu!!.setItem(i, levelPane)
                    levelPaneIndex++
                }
            }

            return levelMenu
        }
    }
}