package net.freedinner.extraordinary_extra_totems.item;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static ItemGroup EXTRAORDINARY_EXTRA_TOTEMS;

    static {
        EXTRAORDINARY_EXTRA_TOTEMS = FabricItemGroup.builder(new Identifier(ExtraordinaryExtraTotems.MOD_ID, "items"))
                .displayName(Text.translatable("extraordinary_extra_totems.item_group_name"))
                .icon(() -> new ItemStack(ModBlocks.OMINOUS_TOTEM)).build();
    }

    public static void registerItemGroups() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering item groups");
    }
}
