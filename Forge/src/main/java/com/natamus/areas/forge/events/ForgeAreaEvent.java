package com.natamus.areas.forge.events;

import com.natamus.areas.events.AreaEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeAreaEvent {
	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent e) {
		Level level = e.level;
		if (level.isClientSide || !e.phase.equals(Phase.START)) {
			return;
		}

		AreaEvent.onWorldTick((ServerLevel)level);
	}

	@SubscribeEvent
	public static void onNeighbourNotice(BlockEvent.NeighborNotifyEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		AreaEvent.onNeighbourNotice(level, e.getPos(), e.getState(), e.getNotifiedSides(), e.getForceRedstoneUpdate());
	}
}
