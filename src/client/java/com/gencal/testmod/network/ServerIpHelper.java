package com.gencal.testmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class ServerIpHelper {
    public static String getCurrentServerIP() {
        Minecraft client = Minecraft.getInstance();

        if (client.isSingleplayer()) {
            return "localhost";
        }

        if (client.getCurrentServer() != null) {
            ServerData serverData = client.getCurrentServer();
            return serverData.ip; // Returns IP:port or domain
        }

        return null;
    }

}