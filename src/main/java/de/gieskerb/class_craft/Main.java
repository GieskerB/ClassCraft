package de.gieskerb.class_craft;

import de.gieskerb.class_craft.commands.ClassCommand;
import de.gieskerb.class_craft.commands.HorseCommand;
import de.gieskerb.class_craft.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    // TODO in general: more and colored text!

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        Objects.requireNonNull(getCommand("class")).setExecutor(new ClassCommand());
        Objects.requireNonNull(getCommand("horse")).setExecutor(new HorseCommand());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new EntityBreedListener(), this);
        pluginManager.registerEvents(new EntityDeathListener(), this);
        pluginManager.registerEvents(new HarvestBlockListener(), this);
        pluginManager.registerEvents(new HungerChangeListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new RespawnListener(), this);
        pluginManager.registerEvents(new WorldSaveListener(), this);

        PlayerJoinListener.loadAllPlayersData();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PlayerQuitListener.saveAllPlayersData();
    }

}
