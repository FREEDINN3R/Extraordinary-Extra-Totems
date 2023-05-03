package net.freedinner.extraordinary_extra_totems.mixin;

import net.freedinner.extraordinary_extra_totems.item.custom.PlaceableOnShiftBlockItem;
import net.freedinner.extraordinary_extra_totems.util.ModUtil;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class ItemsMixin {
    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "Lnet/minecraft/item/Item;*",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args= {
                                    "stringValue=totem_of_undying"
                            },
                            ordinal = 0
                    )
            )
    )
    private static Item vanillaTotem(Item.Settings settings) {
        return new PlaceableOnShiftBlockItem(ModUtil.VANILLA_TOTEM_BLOCK, settings);
    }
}
