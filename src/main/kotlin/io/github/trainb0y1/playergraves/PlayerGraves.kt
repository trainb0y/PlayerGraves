package io.github.trainb0y1.playergraves

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class PlayerGraves: JavaPlugin() {
	companion object {
		lateinit var plugin: PlayerGraves
			private set
	}

	override fun onEnable() {
		plugin = this
		Bukkit.getPluginManager().registerEvents(PlayerDeathListener(), this)
	}
}