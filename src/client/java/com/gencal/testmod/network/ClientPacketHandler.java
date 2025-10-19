package com.gencal.testmod.network;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import static com.gencal.testmod.TestMod.PORT;


public class ClientPacketHandler {



    public static void sendMessage(@Nullable Player player, String message) {
        if (player==null) return;

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(ServerIpHelper.getCurrentServerIP(), PORT)
                .usePlaintext()
                .build();

        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);

        stub.sendMessage(
                MessagePacketProtos.Message.newBuilder()
                        .setUuid(player.getStringUUID())
                        .setMessage(message)
                .build()
        );
    }

}
