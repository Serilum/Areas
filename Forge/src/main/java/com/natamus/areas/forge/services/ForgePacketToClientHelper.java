package com.natamus.areas.forge.services;

import com.natamus.areas.forge.data.Constants;
import com.natamus.areas.forge.network.PacketToClientShowGUI;
import com.natamus.areas.services.helpers.PacketToClientHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class ForgePacketToClientHelper implements PacketToClientHelper {
    @Override
    public void createGUIMessageBuffer(ServerPlayer serverPlayer, String message, String rgb) {
        Constants.network.sendTo(new PacketToClientShowGUI(message, rgb), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}