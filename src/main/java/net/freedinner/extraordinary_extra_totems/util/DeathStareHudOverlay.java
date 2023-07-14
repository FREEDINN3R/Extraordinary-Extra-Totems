package net.freedinner.extraordinary_extra_totems.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.UUID;

public class DeathStareHudOverlay implements HudRenderCallback {
    private static final Identifier OMINOUS_TOTEM = new Identifier(ExtraordinaryExtraTotems.MOD_ID,
            "textures/item/ominous_totem_activated.png");
    private static final ArrayList<DeathStareHudOverlay> list = new ArrayList<>();
    private int counter = -1;

    public DeathStareHudOverlay() {
        list.add(this);
    }

    public static void renderDeathStare(PlayerEntity player) {
        for (DeathStareHudOverlay hudOverlay : list) {
            hudOverlay.tryRender(player.getUuid());
        }
    }

    private void tryRender(UUID uuid) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.player == null) {
            return;
        }

        if (client.player.getUuid().equals(uuid)) {
            counter = 50;
        }
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (counter < 0) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null) {
            return;
        }

        int centerX = client.getWindow().getScaledWidth() / 2;
        int centerY = client.getWindow().getScaledHeight() / 2;
        float alpha = Math.min(0.01f * counter, 0.4f);

        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
        RenderSystem.setShaderTexture(0, OMINOUS_TOTEM);

        drawContext.drawTexture(OMINOUS_TOTEM, centerX - 100, centerY - 100, 0, 0,
                200, 200, 200, 200);

        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        counter--;
    }
}
