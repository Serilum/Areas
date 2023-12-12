package com.natamus.areas.fabric.network;

import com.natamus.areas.events.GUIEvent;
import com.natamus.areas.objects.Variables;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;

public class PacketToClientShowGUI {
	public static FriendlyByteBuf createBuffer(String message, String rgb) {
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeUtf(message);
		buf.writeUtf(rgb);
		return buf;
	}

	public static void registerHandle() {
		ClientPlayNetworking.registerGlobalReceiver(Variables.networkchannel, (client, handler, buf, responseSender) -> {
			String hudmessagevalue = buf.readUtf();
			String rgbvalue = buf.readUtf();

			client.execute(() -> {
				GUIEvent.hudmessage = hudmessagevalue;
				GUIEvent.rgb = rgbvalue;
				GUIEvent.gopacity = 255;
			});
		});
	}
}
