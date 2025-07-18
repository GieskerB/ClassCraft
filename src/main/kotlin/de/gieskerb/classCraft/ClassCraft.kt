package de.gieskerb.classCraft

import de.gieskerb.classCraft.commands.ClassCommand
import de.gieskerb.classCraft.commands.HorseCommand
import de.gieskerb.classCraft.data.SpecialAbilityHandler
import de.gieskerb.classCraft.listener.*
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class ClassCraft : JavaPlugin() {
    // TODO in general: more and colored text!


    companion object {
        lateinit var plugin: JavaPlugin
            private set
        private lateinit var specialAbilityHandler: SpecialAbilityHandler
    }

    init {
        plugin = this
    }

    override fun onEnable() {
        // Plugin startup logic
        getCommand("class")?.setExecutor(ClassCommand())
        getCommand("horse")?.setExecutor(HorseCommand())

        PlayerJoinListener.loadAllPlayersData()

        val pluginManager: PluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(BlockBreakListener(), this)
        pluginManager.registerEvents(BlockPlaceListener(), this)
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
        pluginManager.registerEvents(ToggleSneakListener(),this)
        pluginManager.registerEvents(WorldSaveListener(), this)

        specialAbilityHandler = SpecialAbilityHandler()
        specialAbilityHandler.runTaskTimer(this, 0L, 20L)

    }

    override fun onDisable() {
        // Plugin shutdown logic
        PlayerQuitListener.saveAllPlayersData()
    }
}