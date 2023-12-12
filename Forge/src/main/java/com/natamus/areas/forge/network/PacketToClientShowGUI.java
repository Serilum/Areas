package com.natamus.areas.forge.network;

import com.natamus.areas.events.GUIEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketToClientShowGUI {
	private String message;
	private String rgbvalue;

	public PacketToClientShowGUI() {}

	public PacketToClientShowGUI(String m, String rgb) {
		this.message = m;
		this.rgbvalue = rgb;
	}

	public PacketToClientShowGUI(FriendlyByteBuf buf) {
		message = buf.readUtf();
		rgbvalue = buf.readUtf();
	}

	public void toBytes(FriendlyByteBuf buf) {
		buf.writeUtf(message);
		buf.writeUtf(rgbvalue);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			GUIEvent.hudmessage = message;
			GUIEvent.rgb = rgbvalue;
			GUIEvent.gopacity = 255;
		});
		ctx.get().setPacketHandled(true);
	}
}
