package de.gieskerb.classCraft.data

import org.bukkit.Material
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
class HorseData (
    var color: Horse.Color,
    var style: Horse.Style,
    val speed: Double,
    val jump: Double,
    val health: Double,
    val name: String,
    val armor: Material
) {

    companion object {
        fun horseNameByPlayer(player: Player, className: String): String {
            var playerName = player.name
            playerName += if (playerName[playerName.length - 1] == 's' || playerName[playerName.length - 1] == 'x' || playerName[playerName.length - 1] == 'ß') {
                '\''
            } else {
                's'
            }
            return "$playerName $className-Horse"
        }

        val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

        fun serialize(horseData: HorseData): String {
            return json.encodeToString(horseData)
        }

        fun deserialize(jsonString: String): HorseData? {
            return try {
                if (jsonString.isBlank()) {
                    null
                } else {
                    json.decodeFromString(jsonString)
                }
            } catch (e: Exception) {
                // Log the exception for debugging, e.g., using a Bukkit logger
                println("Error deserializing HorseData from JSON: $jsonString - ${e.message}")
                null
            }
        }

    }
}