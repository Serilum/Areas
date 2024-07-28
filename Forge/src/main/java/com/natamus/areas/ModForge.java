package com.natamus.areas;

import com.natamus.areas.forge.config.IntegrateForgeConfig;
import com.natamus.areas.forge.events.ForgeAreaEvent;
import com.natamus.areas.forge.events.ForgeClientEvent;
import com.natamus.areas.forge.events.ForgeGUIEvent;
import com.natamus.areas.util.Reference;
import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Reference.MOD_ID)
public class ModForge {
	
	public ModForge() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::loadComplete);

		setGlobalConstants();
		ModCommon.init();

		IntegrateForgeConfig.registerScreen(ModLoadingContext.get());

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

	private void loadComplete(final FMLLoadCompleteEvent event) {
		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			MinecraftForge.EVENT_BUS.register(new ForgeClientEvent());
			MinecraftForge.EVENT_BUS.register(new ForgeGUIEvent(Minecraft.getInstance(), Minecraft.getInstance().getItemRenderer()));
		}
    	MinecraftForge.EVENT_BUS.register(new ForgeAreaEvent());
	}

	private static void setGlobalConstants() {

	}
}