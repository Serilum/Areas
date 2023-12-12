package com.natamus.areas;

import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.areas.events.GUIEvent;
import com.natamus.areas.fabric.network.PacketToClientShowGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		registerEvents();
	}
	
	private void registerEvents() {
		PacketToClientShowGUI.registerHandle();

		HudRenderCallback.EVENT.register((PoseStack poseStack, float tickDelta) -> {
			GUIEvent.renderOverlay(poseStack, tickDelta);
		});
	}
}
