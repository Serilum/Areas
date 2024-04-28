package com.natamus.areas.forge.events;

import com.natamus.areas.events.GUIEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ForgeGUIEvent {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void renderOverlay(RenderGuiOverlayEvent.Post e) {
		GUIEvent.renderOverlay(e.getGuiGraphics(), e.getPartialTick());
	}
}