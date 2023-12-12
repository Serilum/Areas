package com.natamus.areas;

import com.natamus.areas.cmds.CommandAreas;
import com.natamus.areas.events.AreaEvent;
import com.natamus.areas.util.Reference;
import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		ServerTickEvents.START_SERVER_TICK.register((MinecraftServer server) -> {
			AreaEvent.onServerTick(server);
		});

		CollectivePlayerEvents.PLAYER_TICK.register((ServerLevel world, ServerPlayer player) -> {
			AreaEvent.onPlayerTick(world, player);
		});

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			AreaEvent.onSignBreak(world, player, pos, state, entity);
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            CommandAreas.register(dispatcher);
        });
	}

	private static void setGlobalConstants() {

	}
}
