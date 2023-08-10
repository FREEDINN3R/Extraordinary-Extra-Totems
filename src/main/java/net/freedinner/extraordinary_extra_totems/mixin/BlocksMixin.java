package net.freedinner.extraordinary_extra_totems.mixin;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    @Inject(method = "<clinit>", at = @At("HEAD"))
    private static void onConstructor(CallbackInfo ci) {
        Block block = ModUtil.getDefaultTotemBlock();
        ModUtil.LEGACY_VANILLA_TOTEM_BLOCK = Registry.register(Registries.BLOCK, new Identifier(ExtraordinaryExtraTotems.MOD_ID, "totem_of_undying"), block);
    }
}
