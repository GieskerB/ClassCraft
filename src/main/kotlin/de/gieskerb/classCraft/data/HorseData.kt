package de.gieskerb.classCraft.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import org.bukkit.Material
import org.bukkit.entity.Horse
import org.bukkit.entity.Player

@Serializable
class HorseData {
    var color: Horse.Color
    var style: Horse.Style
    val speed: Double
    val jump: Double
    val health: Double
    val name: String
    val armor: Material

    constructor(
        color: Horse.Color,
        style: Horse.Style,
        speed: Double,
        jump: Double,
        health: Double,
        name: String,
        armor: Material
    ) {
        this.color = color
        this.style = style
        this.speed = speed
        this.jump = jump
        this.health = health
        this.name = name
        this.armor = armor
    }

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
    }
}