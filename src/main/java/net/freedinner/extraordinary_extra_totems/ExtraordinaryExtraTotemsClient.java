package net.freedinner.extraordinary_extra_totems;

import net.freedinner.extraordinary_extra_totems.networking.NetworkingConstants;
import net.freedinner.extraordinary_extra_totems.util.DeathStareHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ExtraordinaryExtraTotemsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new DeathStareHudOverlay());

        ClientPlayNetworking.registerGlobalReceiver(NetworkingConstants.DEATH_STARE_RENDER_ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> DeathStareHudOverlay.renderDeathStare(client.player));
        });
    }
}
