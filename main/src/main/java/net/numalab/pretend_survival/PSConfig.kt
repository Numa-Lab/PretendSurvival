package net.numalab.pretend_survival

import net.kunmc.lab.configlib.BaseConfig
import net.kunmc.lab.configlib.value.collection.StringListValue
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class PSConfig(plugin: Plugin) : BaseConfig(plugin) {
    // プレイヤーの名前を保存
    val players = StringListValue()

    fun getPlayers() = players.value().mapNotNull { Bukkit.getPlayer(it) }
    fun addPlayer(player: Player) {
        val copy = players.value().toMutableList()
        copy.add(player.name)
        players.value(copy.distinct())
    }

    fun removePlayer(player: Player) {
        val copy = players.value().toMutableList()
        copy.remove(player.name)
        players.value(copy.distinct())
    }

    /**
     * 出させないアイテム一覧
     */
    val bannedItems = StringListValue()

    fun addItem(material: Material) {
        val copy = bannedItems.value().toMutableList()
        copy.add(material.name)
        bannedItems.value(copy.distinct())
    }

    fun removeItem(material: Material) {
        val copy = bannedItems.value().toMutableList()
        copy.remove(material.name)
        bannedItems.value(copy.distinct())
    }

    fun getItem() = bannedItems.value().mapNotNull { Material.getMaterial(it) }
}