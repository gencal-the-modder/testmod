package com.gencal.testmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class MessageScreenWrapper {

    public static void openGUI(Player player) {
        Minecraft.getInstance().setScreen(new MessageScreen(player));
    }

}