package com.gencal.testmod;

import net.fabricmc.api.ClientModInitializer;

public class TestModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		KeyBindingHandler.registerKeyBindings();
	}
}