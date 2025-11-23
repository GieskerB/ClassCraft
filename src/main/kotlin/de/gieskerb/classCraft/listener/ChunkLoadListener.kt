package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.Main
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import java.io.File
import java.io.FileWriter
import kotlin.math.abs

class ChunkLoadListener : Listener {

    // 60 mio World size -> 60 Mio : 16 Chunk size -> log_2 22 Bits pro coordinate
    // 22: x, 22: y, 2: World  -> 46 Bits
    // Map Long -> MyContainer

    @Serializable
    class ChunkData {

        val key: Long
        val blobBitMaps: Array<Long>

        constructor(key: Long) {
            this.key = key;
            val isOverworld: Boolean = ((this.key shr 44) and 0b11) != 0L
            // 4 blobs per layer (y-coordinate)
            this.blobBitMaps = Array(4 * if (isOverworld) OVERWORLD_CHUNK_HEIGHT else OTHERWORLD_CHUNK_HEIGHT) { 0 }
        }

        fun get(x: Int, y: Int, z: Int): Boolean {
            val index = calcIndex(x, y, z)
            val blobBitMap: Long = this.blobBitMaps[index]
            val matchBitMap: Long = calcBitMap(x,z)
            return (blobBitMap and matchBitMap) != 0L
        }


        fun set(x: Int, y: Int, z: Int, bool: Boolean) {
            val index = calcIndex(x, y, z)
            val matchBitMap: Long = calcBitMap(x,z)
            if (bool) {
                // set bit to 1
                blobBitMaps[index] = this.blobBitMaps[index] or matchBitMap
            } else {
                // set bit to 0
                blobBitMaps[index] = this.blobBitMaps[index] and matchBitMap.inv()
            }
        }

        companion object {
            const val OVERWORLD_CHUNK_HEIGHT = 320
            const val OTHERWORLD_CHUNK_HEIGHT = 256

            fun generateKey(world: World, x: Int, z: Int): Long {
                val worldID: Long = when (world.environment) {
                    World.Environment.NORMAL -> 0.toLong();
                    World.Environment.NETHER -> 1.toLong();
                    World.Environment.THE_END -> 2.toLong();
                    else -> throw Exception("Unknown world environment")
                }
                val xNew = if (x < 0) (1L shl 21) or abs(x).toLong() else x.toLong()
                val zNew = if (z < 0) (1L shl 21) or abs(z).toLong() else z.toLong()
                return (worldID shl 44) or (xNew shl 22) or zNew
            }

            private fun calcIndex (x: Int, y: Int, z: Int): Int {
                val chunkX = abs(x % 16)
                val chunkZ = abs(z % 16)
                // Divide 16x16 chunk into 4 8x8 blobs:
                // blob 0 (0b00) := x < 8 and z < 8; ...; blob 3 (0b11) x >= 8 and z >= 8
                // Binary representation
                val index = y * 4 * (((chunkX / 8) shl 1) or chunkZ / 8)
                return index
            }

            private fun calcBitMap (x: Int, z: Int): Long {
                val blobX = abs(x / 8)
                val blobZ = abs(z / 8)
                // Blob stored as a 2d 8x8 grid: Convert two indexes into one with knows grid size
                val index = blobX * 8 + blobZ
                return 1L shl (index - 1)
            }
        }
    }

    var loadedChunksCount = 0

    private fun createFolder() {
        var folder = File(Main.plugin.dataFolder.absolutePath)

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcast(
                    Component.text(
                        "Could not create Folder for ClassCraft-Plugin",
                        TextColor.color(0xAA0000)
                    )
                )
            }
        }

        folder = File(Main.plugin.dataFolder.absolutePath + File.separator + "ChunkData")

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcast(
                    Component.text(
                        "Could not create ChunkData Folder for ClassCraft-Plugin",
                        TextColor.color(0xAA0000)
                    )
                )
            }
        }
    }

    var chunkDataMap: HashMap<Long, ChunkData> = HashMap()

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onChunkLoad(event: ChunkLoadEvent) {
        val chunk = event.chunk

        val worldChar: Char = when (chunk.world.environment) {
            World.Environment.NORMAL -> 'C';
            World.Environment.NETHER -> 'N';
            World.Environment.THE_END -> 'E';
            else -> throw Exception("Unknown world environment")
        }
        val chunkX = chunk.x
        val chunkZ = chunk.z

        val chunkFile = File(Main.plugin.dataFolder.absolutePath + File.separator + "ChunkData" + File.separator + "$worldChar$chunkX$chunkZ.json")

        val key = ChunkData.generateKey(chunk.world, chunkX, chunkZ)
        if (chunkDataMap.containsKey(key)) {
            // TODO error messages
            chunkFile.createNewFile()
            val chunkData = chunkDataMap[key]
            FileWriter(chunkFile).use { file ->
                file.write(Json.encodeToString(chunkData))
            }
        }

        println("Loading chunk ${event.chunk} - $loadedChunksCount")
        ++loadedChunksCount
    }
    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onChunkUnload(event: ChunkUnloadEvent) {
        createFolder()

        val chunk = event.chunk

        val worldChar: Char = when (chunk.world.environment) {
            World.Environment.NORMAL -> 'C';
            World.Environment.NETHER -> 'N';
            World.Environment.THE_END -> 'E';
            else -> throw Exception("Unknown world environment")
        }
        val chunkX = chunk.x
        val chunkZ = chunk.z

        val chunkFile = File(Main.plugin.dataFolder.absolutePath + File.separator + "ChunkData" + File.separator + "$worldChar$chunkX$chunkZ.json")

        if (chunkFile.exists()) {
            val chunkData = Json.decodeFromString<ChunkData>(chunkFile.readText())
            chunkDataMap[chunkData.key] = chunkData
        }

        println("Unloading chunk ${event.chunk} - $loadedChunksCount")
        --loadedChunksCount
    }

}