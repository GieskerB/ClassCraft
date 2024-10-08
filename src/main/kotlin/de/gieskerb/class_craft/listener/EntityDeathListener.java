package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.classes.BaseClass;
import de.gieskerb.class_craft.classes.ClassSpecificLUT;
import de.gieskerb.class_craft.classes.FarmerClass;
import de.gieskerb.class_craft.classes.WarriorClass;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static de.gieskerb.class_craft.commands.HorseCommand.METADATA_IDENTIFIER;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityAttack(EntityDeathEvent event) {
        if (!(event.getDamageSource().getCausingEntity() instanceof Player player)) {
            return;
        }

        if (event.getEntityType() == EntityType.HORSE &&
                !event.getEntity().getMetadata(METADATA_IDENTIFIER).isEmpty()) {
            event.setDroppedExp(0);
            event.getDrops().clear();
            return;
        }

        BaseClass playerClass = PlayerData.getPlayerData(player.getName()).getActiveClass();
        switch (playerClass) {
            case WarriorClass warriorClass -> {
                if (ClassSpecificLUT.getWarriorEntities().contains(new ClassSpecificLUT.EntityPair(event.getEntity().getType()))) {
                    player.sendMessage("You have killed the right Entity!");
                }
            }
            case FarmerClass farmerClass -> {
                if (ClassSpecificLUT.getFarmerEntities().contains(new ClassSpecificLUT.EntityPair(event.getEntity().getType()))) {
                    player.sendMessage("You have killed the right Entity!");
                }
            }
            default -> {
            }
        }
    }
}
