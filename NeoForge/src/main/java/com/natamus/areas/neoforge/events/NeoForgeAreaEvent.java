package com.natamus.areas.neoforge.events;

import com.natamus.areas.events.AreaEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.TickEvent.LevelTickEvent;
import net.neoforged.neoforge.event.TickEvent.Phase;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeAreaEvent {
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
