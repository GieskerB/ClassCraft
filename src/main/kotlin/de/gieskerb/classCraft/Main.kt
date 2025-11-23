package de.gieskerb.classCraft

import de.gieskerb.classCraft.listener.BlockBreakListener
import de.gieskerb.classCraft.listener.BlockPlaceListener
import de.gieskerb.classCraft.listener.EntityBreedListener
import de.gieskerb.classCraft.listener.EntityDeathListener
import de.gieskerb.classCraft.listener.HarvestBlockListener
import de.gieskerb.classCraft.listener.HungerChangeListener
import de.gieskerb.classCraft.listener.InventoryClickListener
import de.gieskerb.classCraft.listener.InventoryOpenListener
import de.gieskerb.classCraft.listener.PlayerItemConsumeListener
import de.gieskerb.classCraft.listener.PlayerJoinListener
import de.gieskerb.classCraft.listener.PlayerMoveListener
import de.gieskerb.classCraft.listener.PlayerQuitListener
import de.gieskerb.classCraft.listener.RespawnListener
import de.gieskerb.classCraft.listener.WorldSaveListener
import de.gieskerb.classCraft.commands.ClassCommand
import de.gieskerb.classCraft.commands.HorseCommand
import de.gieskerb.classCraft.listener.ChunkLoadListener
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    // TODO in general: more and colored text!

    companion object {
        lateinit var plugin: JavaPlugin
            private set
    }

    init {
        plugin = this
    }

    override fun onEnable() {
        // Plugin startup logic
        plugin.registerCommand("class",ClassCommand())
        plugin.registerCommand("horse",HorseCommand())

        PlayerJoinListener.loadAllPlayersData()

        val pluginManager: PluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(BlockBreakListener(), this)
        pluginManager.registerEvents(BlockPlaceListener(), this)
        pluginManager.registerEvents(ChunkLoadListener(), this)
        pluginManager.registerEvents(EntityBreedListener(), this)
        pluginManager.registerEvents(EntityDeathListener(), this)
        pluginManager.registerEvents(HarvestBlockListener(), this)
        pluginManager.registerEvents(HungerChangeListener(), this)
        pluginManager.registerEvents(InventoryClickListener(), this)
        pluginManager.registerEvents(InventoryOpenListener(), this)
        pluginManager.registerEvents(PlayerItemConsumeListener(), this)
        pluginManager.registerEvents(PlayerJoinListener(), this)
        pluginManager.registerEvents(PlayerMoveListener(), this)
        pluginManager.registerEvents(PlayerQuitListener(), this)
        pluginManager.registerEvents(RespawnListener(), this)
        pluginManager.registerEvents(WorldSaveListener(), this)

    }

    override fun onDisable() {
        // Plugin shutdown logic
        PlayerQuitListener.saveAllPlayersData()
    }
}