package com.gencal.testmod;

import com.gencal.testmod.gui.MessageScreenWrapper;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyBindingHandler {

    public static KeyMapping OPEN_SCREEN_KEY;

    public static void registerKeyBindings() {
        OPEN_SCREEN_KEY = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.testmod.open_screen",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.testmod.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_SCREEN_KEY.isDown()) {
                openScreen(client);
            }
        });
    }

    private static void openScreen(Minecraft client) {
        if (client.player != null && client.level != null) {
            client.execute(() -> {
                MessageScreenWrapper.openGUI(client.player);
            });
        }
    }
}