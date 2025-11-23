package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.classes.BaseClass
import de.gieskerb.classCraft.classes.ClassSpecificLUT.EntityPair
import de.gieskerb.classCraft.classes.ClassSpecificLUT.farmerEntities
import de.gieskerb.classCraft.classes.ClassSpecificLUT.warriorEntities
import de.gieskerb.classCraft.classes.FarmerClass
import de.gieskerb.classCraft.classes.WarriorClass
import de.gieskerb.classCraft.commands.HorseCommand
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.persistence.PersistentDataType

class EntityDeathListener : Listener {
    @EventHandler
    fun onEntityAttack(event: EntityDeathEvent) {
        if (event.damageSource.causingEntity !is Player) {
            return
        }

        if (event.entityType == EntityType.HORSE && event.entity.persistentDataContainer.getOrDefault(
                HorseCommand.identifierKey,
                PersistentDataType.BOOLEAN,
                false
            )
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