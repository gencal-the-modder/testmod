package com.gencal.testmod.network;

import com.gencal.testmod.message.MessageService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import static com.gencal.testmod.TestMod.LOGGER;

public class ServerMessageService extends MessageServiceGrpc.MessageServiceImplBase {

    private final MessageService messageService = new MessageService();  // Delegate

    @Override
    public void sendMessage(MessagePacketProtos.Message request,
                            StreamObserver<MessagePacketProtos.EmptyResponse> responseObserver) {
        messageService.saveMessage(request.getUuid(), request.getMessage());  // Delegate to DB service
        try {
            LOGGER.info("[TestMod] THE UUID IS: " + request.getUuid());
            LOGGER.info("[TestMod] THE MESSAGE IS: " + request.getMessage());

            MessagePacketProtos.EmptyResponse response = MessagePacketProtos.EmptyResponse.newBuilder().build();
            responseObserver.onNext(response);        // Send response
            responseObserver.onCompleted();            // Complete the call

        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Failed to process message: " + e.getMessage())
                            .asException()
            );
        }
    }
}
