package de.gieskerb.class_craft.data

import org.bukkit.Material
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import org.json.simple.JSONObject

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

    private constructor(json: JSONObject) {
        this.color = Horse.Color.entries.toTypedArray()[json["color"].toString().toInt()]
        this.style = Horse.Style.entries.toTypedArray()[json["style"].toString().toInt()]
        this.speed = json["speed"].toString().toDouble()
        this.jump = json["jump"].toString().toDouble()
        this.health = json["health"].toString().toDouble()
        this.name = json["name"].toString()
        this.armor = Material.valueOf(json["armor"].toString())
    }

    fun toJSON(): JSONObject {
        val json = JSONObject()
        json["color"] = color.ordinal
        json["style"] = style.ordinal
        json["speed"] = speed
        json["jump"] = jump
        json["health"] = health
        json["name"] = name
        json["armor"] = armor.toString()
        return json
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

        fun fromJSON(json: JSONObject): HorseData? {
            if (json.isEmpty()) return null
            return HorseData(json)
        }
    }
}