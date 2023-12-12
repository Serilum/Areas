package com.natamus.areas.services;

import com.natamus.areas.services.helpers.PacketToClientHelper;
import com.natamus.areas.util.Reference;

import java.util.ServiceLoader;

public class Services {
    public static final PacketToClientHelper PACKETTOCLIENT = load(PacketToClientHelper.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("[" + Reference.NAME + "] Failed to load service for " + clazz.getName() + "."));
    }
}