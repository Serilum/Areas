package com.natamus.areas.fabric.services;

import com.natamus.areas.fabric.network.PacketToClientShowGUI;
import com.natamus.areas.objects.Variables;
import com.natamus.areas.services.helpers.PacketToClientHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class FabricPacketToClientHelper implements PacketToClientHelper {
    @Override
    public void createGUIMessageBuffer(ServerPlayer serverPlayer, String message, String rgb) {
        ServerPlayNetworking.send(serverPlayer, Variables.networkchannel, PacketToClientShowGUI.createBuffer(message, rgb));
    }
}