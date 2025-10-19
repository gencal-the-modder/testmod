package com.gencal.testmod;

import com.gencal.testmod.message.HibernateUtil;
import com.gencal.testmod.network.MessageServiceGrpc;
import com.gencal.testmod.network.ServerMessageService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestMod implements ModInitializer {
	public static final String MOD_ID = "testmod";
	public static int PORT = 50051;
	private Server server;

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		DatabaseConfig config = DatabaseConfig.load();

		HibernateUtil.initialize(config);


		ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> {
			try {
				deployServer();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		ServerLifecycleEvents.SERVER_STOPPING.register((MinecraftServer server) -> {
			shutdownServer();
		});

		LOGGER.info("Hello Fabric world!");
	}


	private void deployServer() throws IOException {
		server = ServerBuilder.forPort(PORT)
				.addService(new ServerMessageService())
				.build();
		server.start();
		System.out.println("✅ gRPC server successfully launched at port 50051");
	}

	private void shutdownServer() {
		if (server != null && !server.isShutdown()) {
			System.out.println("🛑 Stopping gRPC server...");
			server.shutdown();
			try {
				server.awaitTermination(5, TimeUnit.SECONDS); // Graceful shutdown
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}