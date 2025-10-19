package com.gencal.testmod.gui;

import com.gencal.testmod.network.ClientPacketHandler;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import java.util.List;

public class MessageScreen extends Screen {

    private EditBox lineEditBox;
    private Button sendMessageButton;
    private Player player;

    private static Component screenComponent = new Component() {
        @Override
        public Style getStyle() {
            return Style.EMPTY;
        }

        @Override
        public ComponentContents getContents() {
            return null;
        }

        @Override
        public String getString() {
            return "Message Screen";
        }

        @Override
        public List<Component> getSiblings() {
            return List.of();
        }

        @Override
        public FormattedCharSequence getVisualOrderText() {
            return null;
        }
    };

    public MessageScreen(Player player) {
        super(screenComponent);
        this.player = player;
    }

    @Override
    protected void init() {
        clearWidgets();
        int centerX = width / 2;
        int centerY = height / 2;
        int textFieldWidth = 200;
        int textFieldHeight = 20;
        int textFieldX = centerX - (textFieldWidth / 2);
        int textFieldY = centerY - 10;

        // Button dimensions
        int buttonWidth = 60;
        int buttonHeight = 20;
        int buttonX = textFieldX + textFieldWidth + 10;
        int buttonY = textFieldY;

        lineEditBox = addRenderableWidget(new EditBox(getFont(), 0, 0, Component.translatable("string.testmod.messagefield")));
        lineEditBox.setPosition(textFieldX, textFieldY);
        lineEditBox.setSize(textFieldWidth, textFieldHeight);
        lineEditBox.setMaxLength(Integer.MAX_VALUE);

        sendMessageButton = addRenderableWidget(new Button.Builder(Component.translatable("string.testmod.sendmessage"), (onPress) -> {
            ClientPacketHandler.sendMessage(this.player, lineEditBox.getValue());
            onClose();
        }).build());
        sendMessageButton.active = true;
        sendMessageButton.setPosition(buttonX, buttonY);
        sendMessageButton.setSize(buttonWidth, buttonHeight);

    }


}
