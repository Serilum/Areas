package com.natamus.areas.forge.events;

import com.natamus.areas.events.GUIEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeGUIEvent {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void renderOverlay(RenderGuiOverlayEvent.Post e) {
		GUIEvent.renderOverlay(e.getGuiGraphics(), e.getPartialTick());
	}
}