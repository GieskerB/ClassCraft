package de.gieskerb.classCraft.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import org.bukkit.Material
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import java.io.File
import kotlin.text.get

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

    private constructor(json: JsonObject) {
        this.color = Horse.Color.entries.toTypedArray()[json["color"].toString().toInt()]
        this.style = Horse.Style.entries.toTypedArray()[json["style"].toString().toInt()]
        this.speed = json["speed"].toString().toDouble()
        this.jump = json["jump"].toString().toDouble()
        this.health = json["health"].toString().toDouble()
        this.name = json["name"].toString()
        this.armor = Material.valueOf(json["armor"].toString())
    }

    fun toJSON(): JsonObject {
        val json = Json { prettyPrint = true }
        val jsonObject: JsonObject = json.encodeToJsonElement(this).jsonObject
        return jsonObject
//        val jsonString = Json.encodeToJsonElement(this)
//        val jsonFile = File("PlayerData" + File.separator + playerReference.name + ".json")
//        jsonFile.writeText(jsonString)
////        val json = JsonObject()
//        json["color"] = color.ordinal
//        json["style"] = style.ordinal
//        json["speed"] = speed
//        json["jump"] = jump
//        json["health"] = health
//        json["name"] = name
//        json["armor"] = armor.toString()
//        return json
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

        fun fromJSON(json: JsonObject): HorseData? {
            if (json.isEmpty()) return null
            return HorseData(json)
        }
    }
}