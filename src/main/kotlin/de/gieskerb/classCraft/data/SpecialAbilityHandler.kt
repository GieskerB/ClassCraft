package de.gieskerb.classCraft.data

import de.gieskerb.classCraft.classes.BaseClass
import de.gieskerb.classCraft.classes.ClassSpecificLUT
import de.gieskerb.classCraft.classes.ExplorerClass
import de.gieskerb.classCraft.classes.FarmerClass
import de.gieskerb.classCraft.classes.LumberjackClass
import de.gieskerb.classCraft.classes.MinerClass
import de.gieskerb.classCraft.classes.WarriorClass
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class SpecialAbilityHandler : BukkitRunnable() {

    companion object {
        private val BLOODLUST_RANGE = 15
        private val BLOODLUST_THRESHHOLD = 10
        private val BLOODLUST_KILLS = 5

        private val MINER_DEPTH = arrayOf(16, 0, -16)

        private val LUMBERJACK_SAPLING_DISTANCE = 5
    }

    private fun warriorHandler(activeClass: WarriorClass, player: Player) {
        // Bloodlust Ability
        val chunkOffset = arrayOf(-1, 0, 1)
        val playerChunk = player.location.chunk
        val surroundedChunk = ArrayList<Chunk>()
        for (deltaX in chunkOffset) {
            for (deltaZ in chunkOffset) {
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

        if (entityCounter >= BLOODLUST_THRESHHOLD && activeClass.killStreak >= BLOODLUST_KILLS) {
            player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 5, 1))
            player.addPotionEffect(PotionEffect(PotionEffectType.LUCK, 5, 4))
            player.addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION, 5, 3))
        }
    }

    private fun minerHandler(activeClass: MinerClass, player: Player) {
        val depth = player.location.y

        if (depth < MINER_DEPTH[0]) player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 5, 1))
        if (depth < MINER_DEPTH[1]) player.addPotionEffect(PotionEffect(PotionEffectType.LUCK, 5, 2))
        if (depth < MINER_DEPTH[2]) player.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 5, 1))
    }

    private fun lumberjackHandler(activeClass: LumberjackClass, player: Player) {
        activeClass.surroundedSaplings.clear()
        val playerLocation = player.location
        val range = -LUMBERJACK_SAPLING_DISTANCE..LUMBERJACK_SAPLING_DISTANCE
        for (deltaX in range) {
            for (deltaY in range) {
                for (deltaZ in range) {
                    val block: Block = player.world.getBlockAt(
                        (playerLocation.x + deltaX).toInt(),
                        (playerLocation.y + deltaY).toInt(),
                        (playerLocation.z + deltaZ).toInt()
                    )

                    if (block.type == Material.OAK_SAPLING || block.type == Material.BIRCH_SAPLING || block.type == Material.SPRUCE_SAPLING || block.type == Material.JUNGLE_SAPLING || block.type == Material.ACACIA_SAPLING || block.type == Material.DARK_OAK_SAPLING || block.type == Material.MANGROVE_PROPAGULE || block.type == Material.CHERRY_SAPLING || block.type == Material.CRIMSON_FUNGUS || block.type == Material.WARPED_FUNGUS) {
                        activeClass.surroundedSaplings.add(block)
                    }
                }
            }
        }
    }

    private fun farmerHandler(activeClass: FarmerClass, player: Player) {

    }

    private fun explorerHandler(activeClass: ExplorerClass, player: Player) {

    }

    override fun run() {
        for (playerData in PlayerData.Companion.allPlayerData) {
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
