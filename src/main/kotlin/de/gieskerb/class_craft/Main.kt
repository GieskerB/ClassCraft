package de.gieskerb.class_craft

import de.gieskerb.class_craft.commands.ClassCommand
import de.gieskerb.class_craft.commands.HorseCommand
import de.gieskerb.class_craft.listener.*
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
        getCommand("class")?.setExecutor(ClassCommand())
        getCommand("horse")?.setExecutor(HorseCommand())

        val pluginManager: PluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(BlockBreakListener(), this)
        pluginManager.registerEvents(BlockPlaceListener(), this)
        pluginManager.registerEvents(EntityBreedListener(), this)
        pluginManager.registerEvents(EntityDeathListener(), this)
        pluginManager.registerEvents(HarvestBlockListener(), this)
        pluginManager.registerEvents(HungerChangeListener(), this)
        pluginManager.registerEvents(InventoryClickListener(), this)
        pluginManager.registerEvents(InventoryOpenListener(), this)
        pluginManager.registerEvents(PlayerJoinListener(), this)
        pluginManager.registerEvents(PlayerMoveListener(), this)
        pluginManager.registerEvents(PlayerQuitListener(), this)
        pluginManager.registerEvents(RespawnListener(), this)
        pluginManager.registerEvents(WorldSaveListener(), this)

        PlayerJoinListener.loadAllPlayersData()
    }

    override fun onDisable() {
        // Plugin shutdown logic
        PlayerQuitListener.saveAllPlayersData()
    }
}