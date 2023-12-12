package com.natamus.areas.services.helpers;

import net.minecraft.server.level.ServerPlayer;

public interface PacketToClientHelper {
    void createGUIMessageBuffer(ServerPlayer serverPlayer, String message, String rgb);
}