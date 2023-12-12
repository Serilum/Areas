package com.natamus.areas.forge.events;

import com.natamus.areas.events.GUIEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeGUIEvent extends Gui {
	public ForgeGUIEvent(Minecraft mcIn, ItemRenderer itemRenderer) {
		super(mcIn);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void renderOverlay(RenderGameOverlayEvent.Post e) {
		GUIEvent.renderOverlay(e.getMatrixStack(), e.getPartialTicks());
	}
}