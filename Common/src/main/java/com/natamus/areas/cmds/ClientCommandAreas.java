package com.natamus.areas.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.areas.data.AreaVariables;
import com.natamus.areas.functions.ZoneFunctions;
import com.natamus.areas.objects.AreaObject;
import com.natamus.areas.util.Util;
import com.natamus.collective.data.BlockEntityData;
import com.natamus.collective.functions.HashMapFunctions;
import com.natamus.collective.functions.MessageFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
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

		BlockPos ppos = player.blockPosition();

		List<BlockEntity> blockEntitiesAround = new ArrayList<BlockEntity>();
		blockEntitiesAround.addAll(BlockEntityData.getCachedBlockEntities(BlockEntityType.SIGN, level));
		blockEntitiesAround.addAll(BlockEntityData.getCachedBlockEntities(BlockEntityType.HANGING_SIGN, level));
		for (BlockEntity nearbyBlockEntity : blockEntitiesAround) {
			BlockPos signPos = nearbyBlockEntity.getBlockPos();
			if (signPos.closerThan(ppos, 200)) {
				BlockEntity liveSign = level.getBlockEntity(signPos);
				if (liveSign instanceof SignBlockEntity && ZoneFunctions.hasZonePrefix((SignBlockEntity)liveSign)) {
					if (!sentfirst) {
						MessageFunctions.sendClientTranslatableMessage(player, "collective.areas.message.areasignpositions", ChatFormatting.DARK_GREEN);
						sentfirst = true;
					}

					String areaName = "";
					if (HashMapFunctions.computeIfAbsent(AreaVariables.areaObjects, level, k -> new HashMap<BlockPos, AreaObject>()).containsKey(signPos)) {
						AreaObject ao = AreaVariables.areaObjects.get(level).get(signPos);
						areaName = ao.areaName + " ";
					}
					else {
						Util.getAreaSign(level, signPos);
					}

					double distance = Math.round(Math.sqrt(signPos.distSqr(new Vec3i(Mth.floor(pvec.x), Mth.floor(pvec.y), Mth.floor(pvec.z)))) * 100.0) / 100.0;

					MessageFunctions.sendClientTranslatableMessage(player, " ", "collective.areas.message.signlocation", ChatFormatting.YELLOW, areaName, signPos.getX(), signPos.getY(), signPos.getZ(), distance);
				}
			}
		}

		if (!sentfirst) {
			MessageFunctions.sendClientTranslatableMessage(player, "collective.areas.message.areasignsaround", ChatFormatting.DARK_GREEN);
		}

		return 1;
	}
}
