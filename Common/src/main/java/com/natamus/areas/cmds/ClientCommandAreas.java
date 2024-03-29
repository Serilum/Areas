package com.natamus.areas.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.areas.data.AreaVariables;
import com.natamus.areas.functions.ZoneFunctions;
import com.natamus.areas.objects.AreaObject;
import com.natamus.areas.util.Util;
import com.natamus.collective.functions.FABFunctions;
import com.natamus.collective.functions.HashMapFunctions;
import com.natamus.collective.functions.MessageFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;

public class ClientCommandAreas {
	private static final Minecraft mc = Minecraft.getInstance();

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("areas")
			.requires((iCommandSender) -> iCommandSender.getEntity() instanceof Player)
			.executes((command) -> {
				return areas(mc.player);
			})
		);
	}

	public static int areas(Player player) {
		if (player == null) {
			return 0;
		}

		Level level = player.level();

		Vec3 pvec = player.position();
		boolean sentfirst = false;

		List<BlockEntity> blockEntitiesAround = FABFunctions.getBlockEntitiesAroundPosition(level, player.blockPosition(), 200);
		for (BlockEntity nearbyBlockEntity : blockEntitiesAround) {
			BlockState blockEntityState = nearbyBlockEntity.getBlockState();
			if (blockEntityState.is(BlockTags.ALL_SIGNS) || blockEntityState.is(BlockTags.ALL_HANGING_SIGNS)) {
				BlockPos signPos = nearbyBlockEntity.getBlockPos();
				if (ZoneFunctions.hasZonePrefix((SignBlockEntity)nearbyBlockEntity)) {
					if (!sentfirst) {
						MessageFunctions.sendMessage(player, "Area sign positions around you:", ChatFormatting.DARK_GREEN);
						sentfirst = true;
					}

					String prefix = "a";
					if (HashMapFunctions.computeIfAbsent(AreaVariables.areaObjects, level, k -> new HashMap<BlockPos, AreaObject>()).containsKey(signPos)) {
						AreaObject ao = AreaVariables.areaObjects.get(level).get(signPos);
						prefix = ao.areaName + " a";
					}
					else {
						Util.getAreaSign(level, signPos);
					}

					double distance = Math.round(Math.sqrt(signPos.distSqr(new Vec3i(Mth.floor(pvec.x), Mth.floor(pvec.y), Mth.floor(pvec.z)))) * 100.0) / 100.0;
					String blocksaway = " (" + distance + " blocks)";

					MessageFunctions.sendMessage(player, " " + prefix + "t x=" + signPos.getX() + ", y=" + signPos.getY() + ", z=" + signPos.getZ() + "." + blocksaway, ChatFormatting.YELLOW);
				}
			}
		}

		if (!sentfirst) {
			MessageFunctions.sendMessage(player, "There are no area signs around you.", ChatFormatting.DARK_GREEN);
		}

		return 1;
	}
}
