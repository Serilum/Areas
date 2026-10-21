package com.serilum.areas.forge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.bus.EventBus;
import net.minecraftforge.eventbus.api.event.MutableEvent;

public class AreaLeaveEvent extends MutableEvent {
	public static final EventBus<AreaLeaveEvent> BUS = EventBus.create(AreaLeaveEvent.class);

	private final Player player;
	private final String areaName;
	private final String areaType;
	private final BlockPos signPos;
	private final int radius;

	public AreaLeaveEvent(Player player, String areaName, String areaType, BlockPos signPos, int radius) {
		this.player = player;
		this.areaName = areaName;
		this.areaType = areaType;
		this.signPos = signPos.immutable();
		this.radius = radius;
	}

	public Player getPlayer() {
		return player;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getAreaType() {
		return areaType;
	}

	public BlockPos getSignPos() {
		return signPos;
	}

	public int getRadius() {
		return radius;
	}
}
