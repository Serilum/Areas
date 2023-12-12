package com.natamus.areas;

import com.natamus.areas.neoforge.config.IntegrateNeoForgeConfig;
import com.natamus.areas.neoforge.events.NeoForgeAreaEvent;
import com.natamus.areas.neoforge.events.NeoForgeClientEvent;
import com.natamus.areas.neoforge.events.NeoForgeGUIEvent;
import com.natamus.areas.util.Reference;
import com.natamus.collective.check.RegisterMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Reference.MOD_ID)
public class ModNeoForge {
	
	public ModNeoForge() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::loadComplete);

		setGlobalConstants();
		ModCommon.init();

		IntegrateNeoForgeConfig.registerScreen(ModLoadingContext.get());

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {

	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			NeoForge.EVENT_BUS.register(NeoForgeClientEvent.class);
			NeoForge.EVENT_BUS.register(NeoForgeGUIEvent.class);
		}
		NeoForge.EVENT_BUS.register(NeoForgeAreaEvent.class);
	}

	private static void setGlobalConstants() {

	}
}