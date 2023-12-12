package com.natamus.areas.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.areas.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean giveUnnamedAreasRandomName = true;
	@Entry(min = 0, max = 1000) public static int radiusAroundPlayerToCheckForSigns = 100;
	@Entry public static boolean sendChatMessages = false;
	@Entry public static boolean showHUDMessages = true;
	@Entry public static String joinPrefix = "Entering ";
	@Entry public static String joinSuffix = ".";
	@Entry public static String leavePrefix = "Leaving ";
	@Entry public static String leaveSuffix = ".";
	@Entry public static boolean HUDOnlyAreaName = false;
	@Entry(min = 100, max = 360000) public static int HUDMessageFadeDelayMs = 4000;
	@Entry(min = 0, max = 3000) public static int HUDMessageHeightOffset = 10;
	@Entry(min = 0, max = 10.0) public static double HUD_FontSizeScaleModifier = 1.0;
	@Entry(min = 0, max = 255) public static int HUD_RGB_R = 100;
	@Entry(min = 0, max = 255) public static int HUD_RGB_G = 200;
	@Entry(min = 0, max = 255) public static int HUD_RGB_B = 50;

	public static void initConfig() {
		configMetaData.put("giveUnnamedAreasRandomName", Arrays.asList(
			"When enabled, gives signs without an area name a randomly chosen one from a preset list."
		));
		configMetaData.put("radiusAroundPlayerToCheckForSigns", Arrays.asList(
			"The radius in blocks around the player in which to check for area signs."
		));
		configMetaData.put("sendChatMessages", Arrays.asList(
			"When enabled, sends the player the area notifications in chat."
		));
		configMetaData.put("showHUDMessages", Arrays.asList(
			"When enabled, sends the player the area notifications in the HUD on screen."
		));
		configMetaData.put("joinPrefix", Arrays.asList(
			"The prefix of the message whenever a player enters an area."
		));
		configMetaData.put("joinSuffix", Arrays.asList(
			"The suffix of the message whenever a player enters an area."
		));
		configMetaData.put("leavePrefix", Arrays.asList(
			"The prefix of the message whenever a player leaves an area."
		));
		configMetaData.put("leaveSuffix", Arrays.asList(
			"The suffix of the message whenever a player leaves an area."
		));
		configMetaData.put("HUDOnlyAreaName", Arrays.asList(
			"When enabled, only shows the areaname in the HUD. When disabled, the prefixes and suffices will also be used."
		));
		configMetaData.put("HUDMessageFadeDelayMs", Arrays.asList(
			"The delay in ms after which the HUD message should fade out."
		));
		configMetaData.put("HUDMessageHeightOffset", Arrays.asList(
			"The vertical offset (y coord) for the HUD message. This determines how far down the message should be on the screen. Can be changed to prevent GUIs from overlapping."
		));
		configMetaData.put("HUD_FontSizeScaleModifier", Arrays.asList(
			"Increases the font size of the text in the HUD message. If you change this value, make sure to test the different GUI scale settings in-game. 6.0 is considered large."
		));
		configMetaData.put("HUD_RGB_R", Arrays.asList(
			"The red RGB value for the HUD message."
		));
		configMetaData.put("HUD_RGB_G", Arrays.asList(
			"The green RGB value for the HUD message."
		));
		configMetaData.put("HUD_RGB_B", Arrays.asList(
			"The blue RGB value for the HUD message."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}