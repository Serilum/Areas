package com.natamus.areas.util;

import com.natamus.areas.config.ConfigHandler;
import com.natamus.areas.data.AreaVariables;
import com.natamus.areas.data.ClientConstants;
import com.natamus.areas.data.GUIVariables;
import com.natamus.areas.functions.ZoneFunctions;
import com.natamus.areas.objects.AreaObject;
import com.natamus.collective.data.GlobalVariables;
import com.natamus.collective.functions.HashMapFunctions;
import com.natamus.collective.functions.SignFunctions;
import com.natamus.collective.functions.MessageFunctions;
import com.natamus.collective.functions.TileEntityFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Util {
	public static AreaObject getAreaSign(Level level, BlockPos signPos) {
		BlockEntity blockEntity = level.getBlockEntity(signPos);
		if (!(blockEntity instanceof SignBlockEntity)) {
			return null;
		}

		SignBlockEntity signBlockEntity = (SignBlockEntity)blockEntity;
		
		StringBuilder areaNameBuilder = new StringBuilder();
		String rgb = "";
		String zonePrefix = "Area";
		int radius = ConfigHandler.defaultAreaRadius;
		boolean customrgb = false;

		List<String> signLines = SignFunctions.getSignText(blockEntity);

		HashMap<BlockPos, AreaObject> areaObjectHashMap = HashMapFunctions.computeIfAbsent(AreaVariables.areaObjects, level, k -> new HashMap<BlockPos, AreaObject>());
		if (areaObjectHashMap != null) {
			if (areaObjectHashMap.containsKey(signPos)) {
				AreaObject cachedAreaObject = areaObjectHashMap.get(signPos);
				if (cachedAreaObject.signLines.equals(signLines)) {
					return cachedAreaObject;
				}
				else {
					AreaVariables.areaObjects.get(level).remove(signPos);
				}
			}
		}

		int i = -1;
		for (String line : signLines) {
			i += 1;
			if (i == 0 && !ZoneFunctions.hasZonePrefix(line)) {
				return null;
			}
			else if (i > 3) {
				break;
			}
			
			if (line.length() < 1) {
				continue;
			}
			
			Integer possibleradius = ZoneFunctions.getRadius(line.toLowerCase());
			if (possibleradius >= 0) {
				radius = possibleradius;
				continue;
			}

			String possiblergb = ZoneFunctions.getZoneRGB(line.toLowerCase());
			if (!possiblergb.equals("")) {
				rgb = possiblergb;
				continue;
			}
			
			if (ZoneFunctions.hasZonePrefix(line)) {
				continue;
			}
			
			if (!areaNameBuilder.toString().equals("")) {
				areaNameBuilder.append(" ");
			}
			areaNameBuilder.append(line);
		}

		String areaName = areaNameBuilder.toString();

		return new AreaObject(level, signPos, areaName, radius, rgb, signLines);
	}

	public static void updateAreaSign(Level level, BlockPos signPos, SignBlockEntity signBlockEntity, List<String> signLines, String areaName, String zonePrefix, String rgb, int radius, boolean customrgb) {
		boolean setradius = false;
		int maxradius = ConfigHandler.radiusAroundPlayerToCheckForSigns;
		if (radius > maxradius) {
			radius = maxradius;
			setradius = true;
		}

		int i;
		StringBuilder areaNameBuilder = new StringBuilder();

		boolean shouldUpdateSign = false;
		if (areaName.trim().equals("")) {
			List<String> newSignContentList = new ArrayList<String>();

			newSignContentList.add("[" + zonePrefix + "] " + radius);
			if (customrgb) {
				newSignContentList.add("[RGB] " + rgb);
			}
			else {
				newSignContentList.add("");
			}

			if (ConfigHandler.giveUnnamedAreasRandomName) {
				String randomname = getRandomAreaName();
				for (String word : randomname.split(" ")) {
					if (newSignContentList.size() == 8) {
						break;
					}
					newSignContentList.add(word);
				}
			}
			else {
				newSignContentList.add("Unnamed area");
			}

			i = 0;
			SignText signText = signBlockEntity.getFrontText();
			for (String line : newSignContentList) {
				if (i > 3) {
					break;
				}

				signText = signText.setMessage(i, Component.literal(line));
				i+=1;
			}
			signBlockEntity.setText(signText, true);

			shouldUpdateSign = true;
		}

		if (!shouldUpdateSign) {
			if (radius == 0 || setradius) {
				i = 0;
				for (String line : signLines) {
					if (i == 0) {
						line = "[" + zonePrefix + "] " + radius;
					}
					else if (i > 3) {
						break;
					}

					signBlockEntity.getFrontText().setMessage(i, Component.literal(line));
					i+=1;
				}

				shouldUpdateSign = true;
			}
		}

		if (shouldUpdateSign) {
			TileEntityFunctions.updateTileEntity(level, signPos, signBlockEntity);
		}
	}

	public static void enterArea(AreaObject areaObject, Player player) {
		if (playerIsEditingASign()) {
			return;
		}

		AreaObject currentAreaObject = getAreaSign(areaObject.level, areaObject.location);
		if (currentAreaObject.signLines != areaObject.signLines) {
			AreaVariables.enteredAreas.remove(areaObject);
			AreaVariables.areaObjects.get(areaObject.level).put(areaObject.location, currentAreaObject);
			areaObject = currentAreaObject;
		}

		boolean shouldMessage = shouldMessagePlayer(areaObject, true);

		if (!AreaVariables.enteredAreas.contains(areaObject)) {
			AreaVariables.enteredAreas.add(areaObject);
		}
		
		if (shouldMessage) {
			String message = ConfigHandler.enterPrefix + areaObject.areaName + ConfigHandler.enterSuffix;
			areaChangeMessage(player, message, areaObject.customRGB);
		}
	}
	public static void exitArea(AreaObject areaObject, Player player) {
		AreaVariables.enteredAreas.remove(areaObject);

		boolean shouldMessage = shouldMessagePlayer(areaObject, false);

		if (shouldMessage) {
			String message = ConfigHandler.leavePrefix + areaObject.areaName + ConfigHandler.leaveSuffix;
			areaChangeMessage(player, message, areaObject.customRGB);
		}
	}

	public static void removedArea(AreaObject areaObject, Player player) {
		AreaVariables.areaObjects.get(areaObject.level).remove(areaObject.location);
		AreaVariables.enteredAreas.remove(areaObject);

		boolean shouldMessage = shouldMessagePlayer(areaObject, false);

		if (shouldMessage) {
			String message = "The area " + areaObject.areaName + " no longer exists.";

			BlockPos pPos = player.blockPosition();
			if (areaObject.location.distSqr(new Vec3i(pPos.getX(), pPos.getY(), pPos.getZ())) > (areaObject.radius*2)) {
				message = ConfigHandler.leavePrefix + areaObject.areaName + ConfigHandler.leaveSuffix;
			}

			areaChangeMessage(player, message, areaObject.customRGB);
		}
	}

	public static boolean playerIsEditingASign() {
		Screen screen = ClientConstants.mc.screen;
		return screen instanceof SignEditScreen || screen instanceof HangingSignEditScreen;
	}

	public static boolean shouldMessagePlayer(AreaObject areaObject, boolean isEntering) {
		if (areaObject.areaName.equals("")) {
			return false;
		}

		for (AreaObject enteredAreaObject : AreaVariables.enteredAreas) {
			if (enteredAreaObject.equals(areaObject)) {
				continue;
			}

			if (areaObject.signLines.equals(enteredAreaObject.signLines)) {
				return false;
			}
		}

		if (isEntering) {
			return ConfigHandler.showEnterMessage;
		}

		return ConfigHandler.showLeaveMessage;
	}

	public static void areaChangeMessage(Player player, String message, String rgb) {
		if (ConfigHandler.sendChatMessages) {
			MessageFunctions.sendMessage(player, message, ChatFormatting.DARK_GREEN);
		}
		if (ConfigHandler.showHUDMessages) {
			GUIVariables.ticksLeftBeforeFade = ConfigHandler.HUDMessageFadeDelayMs/50;
			GUIVariables.hudMessage = message;
			GUIVariables.rgb = rgb;
			GUIVariables.guiOpacity = 255;
		}		
	}
	
	public static Boolean isSignBlock(Block block) {
		return block instanceof SignBlock;
	}
	public static Boolean isSignItem(Item item) {
		return isSignBlock(Block.byItem(item));
	}
	
	private static String getRandomAreaName() {
		return GlobalVariables.areaNames.get(GlobalVariables.random.nextInt(GlobalVariables.areaNames.size()));
	}
}
