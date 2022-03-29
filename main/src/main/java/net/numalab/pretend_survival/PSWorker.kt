package net.numalab.pretend_survival

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class PSWorker(val config: PSConfig, val plugin: JavaPlugin) : Listener {
    companion object {
        var instance: PSWorker? = null
            private set

        fun init(config: PSConfig, plugin: JavaPlugin) {
            if (instance != null) return
            instance = PSWorker(config, plugin)
        }
    }

    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @EventHandler
    fun onSwapItem(e: PlayerSwapHandItemsEvent) {
        if (config.getPlayers().contains(e.player)) {
            if (e.player.isSneaking) {
                PSGUI(config).open(plugin, e.player)
            }
        }
    }

    private val sneak = mutableMapOf<UUID, Boolean>()

    @EventHandler
    fun toggleSneaking(e: PlayerToggleSneakEvent) {
        sneak[e.player.uniqueId] = e.isSneaking
    }
}