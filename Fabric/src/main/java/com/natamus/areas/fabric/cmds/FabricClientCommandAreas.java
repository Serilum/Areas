package com.natamus.areas.fabric.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.areas.cmds.ClientCommandAreas;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.world.entity.player.Player;

public class FabricClientCommandAreas {
	public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
		dispatcher.register(ClientCommandManager.literal("areas")
			.requires((iCommandSender) -> iCommandSender.getEntity() instanceof Player)
			.executes((command) -> {
				FabricClientCommandSource source = command.getSource();
				Player player = source.getPlayer();
                return ClientCommandAreas.areas(player);
			})
		);
	}
}
