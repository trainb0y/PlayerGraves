package io.github.trainb0y1.playergraves

import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener: Listener {

	@EventHandler
	fun onPlayerDeath(event: PlayerDeathEvent){
		if (event.keepInventory){
			PlayerGraves.plugin.server.logger.warning(
				"Player Graves is installed but keepInventory is on! Ignoring player death."
			)
			return
		}
		// Check if the player has any items
		val player = event.entity
		if (player.inventory.isEmpty) return

		// Find out where we need to place the chest
		// Needs to be a 2 block tall space of air
		var loc = player.location
		while (loc.block.type != Material.AIR || loc.clone().add(0.0,1.0,0.0).block.type != Material.AIR){
			loc.y++
		}

		// Create the chest
		loc.block.type = Material.CHEST
		var chest = loc.block.state as Chest

		// Fill it
		player.inventory.forEach {
			it?.let {
				if (chest.inventory.addItem(it).isNotEmpty()) {
					// Chest couldn't fit, make another chest above it
					loc.add(0.0,1.0,0.0)
					loc.block.type = Material.CHEST
					chest = loc.block.state as Chest

					// This item didn't fit, add it into the new one
					chest.inventory.addItem(it)
				}
			}
		}

		// Don't want to drop the items
		event.drops.clear()
	}
}