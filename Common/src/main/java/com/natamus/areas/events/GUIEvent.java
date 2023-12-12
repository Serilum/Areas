package com.natamus.areas.events;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.areas.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.awt.*;
import java.util.UUID;

public class GUIEvent {
	public static String hudmessage = "";
	public static String rgb = "";
	public static int gopacity = 0;
	
	private static String currentmessage = "";
	private static String currentrandom = "";
	
	private static final Minecraft mc = Minecraft.getInstance();

	public static void renderOverlay(PoseStack posestack, float tickDelta){
		if (!hudmessage.equals("")) {
			Font fontRender = mc.font;
			Window scaled = mc.getWindow();
			int width = scaled.getGuiScaledWidth();

			double stringWidth = fontRender.width(hudmessage);
			
			if (gopacity <= 0) {
				gopacity = 0;
				hudmessage = "";
				return;
			}
			else if (gopacity > 255) {
				gopacity = 255;
			}
			
			Color colour = new Color(ConfigHandler.HUD_RGB_R, ConfigHandler.HUD_RGB_G, ConfigHandler.HUD_RGB_B, gopacity);
			if (!rgb.equals("")) {
				String[] rgbs = rgb.split(",");
				if (rgbs.length == 3) {
					try {
						int r = Integer.parseInt(rgbs[0]);
						if (r < 0) {
							r = 0;
						}
						else if (r > 255) {
							r = 255;
						}
						
						int g = Integer.parseInt(rgbs[1]);
						if (g < 0) {
							g = 0;
						}
						else if (g > 255) {
							g = 255;
						}
						
						int b = Integer.parseInt(rgbs[2]);
						if (b < 0) {
							b = 0;
						}
						else if (b > 255) {
							b = 255;
						}
						
						colour = new Color(r, g, b, gopacity);
					}
					catch(IllegalArgumentException ex) {
						rgb = "";
						hudmessage = "";
					}
				}
			}

			posestack.pushPose();
			
			RenderSystem.enableBlend(); // GL11.glEnable(GL11.GL_BLEND);
			RenderSystem.blendFunc(0x302, 0x303); //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			float modifier = ((float)ConfigHandler.HUD_FontSizeScaleModifier + 0.5F);
			posestack.scale(modifier, modifier, modifier);
			
			fontRender.draw(posestack, hudmessage, (int)(Math.round((width / 2F) / modifier) - stringWidth/2), ConfigHandler.HUDMessageHeightOffset, colour.getRGB());
			
			posestack.popPose();
			
			if (!currentmessage.equals(hudmessage)) {
				currentmessage = hudmessage;
				setHUDFade(UUID.randomUUID().toString());
			}
		}
	}
	
	public static void setHUDFade(String random) {
		currentrandom = random;
		
		new Thread(() -> {
			try  { Thread.sleep( ConfigHandler.HUDMessageFadeDelayMs ); }
			catch (InterruptedException ignored)  {}

			if (currentrandom.equals(random)) {
				startFadeOut(random);
			}
		}).start();
	}
	
	public static void startFadeOut(String random) {
		if (!currentrandom.equals(random)) {
			return;
		}
		
		if (gopacity < 0) {
			hudmessage = "";
			rgb = "";
			return;
		}
		
		gopacity -= 10;
		new Thread(() -> {
			try  { Thread.sleep( 50 ); }
			catch (InterruptedException ignored)  {}
			startFadeOut(random);
		}).start();
	}
}