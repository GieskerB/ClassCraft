package de.gieskerb.class_craft.data

import de.gieskerb.class_craft.classes.*
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class SpecialAbilityHandler : BukkitRunnable() {

    private val BLOODLUST_RANGE = 10

    private fun warriorHandler(activeClass: WarriorClass, player: Player) {
        // Bloodlust Ability
        var offset = arrayOf(-1, 0, 1)
        val playerChunk = player.location.chunk
        var surroundedChunk = ArrayList<Chunk>()
        for (deltaX in offset) {
            for (deltaZ in offset) {
                surroundedChunk.add(player.world.getChunkAt(playerChunk.x + deltaX, playerChunk.z + deltaZ))
            }
        }
        activeClass.subsideStreak()
        var entityCounter: Int = 0
        for (chunk in surroundedChunk) {
            for (entity in chunk.entities) {
                if (entity.location.distanceSquared(player.location) < BLOODLUST_RANGE * BLOODLUST_RANGE && ClassSpecificLUT.warriorEntities!!.contains(
                        ClassSpecificLUT.EntityPair(entity.type)
                    )
                ) {
                    entityCounter++
                }
            }
        }

        // TODO buff player now
        player.sendMessage(activeClass.killStreak.toString() + " " + entityCounter)

    }

    private fun minerHandler(activeClass: MinerClass, player: Player) {

    }

    private fun lumberjackHandler(activeClass: LumberjackClass, player: Player) {

    }

    private fun farmerHandler(activeClass: FarmerClass, player: Player) {

    }

    private fun explorerHandler(activeClass: ExplorerClass, player: Player) {

    }

    override fun run() {
        for (playerData in PlayerData.allPlayerData) {
            if (playerData.activeClass == null || playerData.activeClass!!.level < 10) {
                continue
            }
            val player: Player = Bukkit.getPlayer(playerData.assignedPlayer) ?: continue

            when (val baseClass: BaseClass = playerData.activeClass!!) {
                is WarriorClass -> warriorHandler(baseClass, player)
                is MinerClass -> minerHandler(baseClass, player)
                is LumberjackClass -> lumberjackHandler(baseClass, player)
                is FarmerClass -> farmerHandler(baseClass, player)
                is ExplorerClass -> explorerHandler(baseClass, player)
            }
        }
    }
}