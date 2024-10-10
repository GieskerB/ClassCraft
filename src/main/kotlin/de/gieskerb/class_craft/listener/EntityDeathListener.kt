package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.classes.BaseClass
import de.gieskerb.class_craft.classes.ClassSpecificLUT.EntityPair
import de.gieskerb.class_craft.classes.ClassSpecificLUT.farmerEntities
import de.gieskerb.class_craft.classes.ClassSpecificLUT.warriorEntities
import de.gieskerb.class_craft.classes.FarmerClass
import de.gieskerb.class_craft.classes.WarriorClass
import de.gieskerb.class_craft.commands.HorseCommand
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class EntityDeathListener : Listener {
    @EventHandler
    fun onEntityAttack(event: EntityDeathEvent) {
        if (event.damageSource.causingEntity !is Player) {
            return
        }

        if (event.entityType == EntityType.HORSE && event.entity.getMetadata(HorseCommand.METADATA_IDENTIFIER)
                .isNotEmpty()
        ) {
            event.droppedExp = 0
            event.drops.clear()
            return
        }

        val player: Player = event.damageSource.causingEntity as Player
        val playerClass: BaseClass? = PlayerData.getPlayerData(player.name)?.activeClass
            when (playerClass) {
                is WarriorClass -> {
                    if (warriorEntities!!.contains(EntityPair(event.entity.type))) {
                        player.sendMessage("You have killed the right Entity!")
                    }
                }

                is FarmerClass -> {
                    if (farmerEntities!!.contains(EntityPair(event.entity.type))) {
                        player.sendMessage("You have killed the right Entity!")
                    }
                }

                else -> {}

        }
    }
}