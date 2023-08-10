package net.freedinner.extraordinary_extra_totems.legacy.block;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    //Totems
    public static final Block LEGACY_UNSTABLE_TOTEM = registerBlock("unstable_totem");
    public static final Block LEGACY_UNRELIABLE_TOTEM = registerBlock("unreliable_totem");
    public static final Block LEGACY_CONFUSED_TOTEM = registerBlock("confused_totem");
    public static final Block LEGACY_STRANGE_TOTEM = registerBlock("strange_totem");
    public static final Block LEGACY_FRAGILE_TOTEM = registerBlock("fragile_totem");
    public static final Block LEGACY_OMINOUS_TOTEM = registerBlock("ominous_totem");

    private static Block registerBlock(String name) {
        Block block = ModUtil.getDefaultTotemBlock();

        return Registry.register(Registries.BLOCK, new Identifier(ExtraordinaryExtraTotems.MOD_ID, name), block);
    }

    public static void registerBlocks() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering blocks");
    }
}
