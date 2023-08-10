package net.freedinner.extraordinary_extra_totems.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.item.custom.UnstableTotemItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    //Remnants
    public static final Item TOTEM_REMNANTS = registerItem("totem_remnants",
            new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON)));
    public static final Item OMINOUS_TOTEM_REMNANTS = registerItem("ominous_totem_remnants",
            new Item(new FabricItemSettings().rarity(Rarity.RARE).fireproof()));

    //Totems
    public static final Item UNSTABLE_TOTEM = registerItem("unstable_totem",
            new UnstableTotemItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item UNRELIABLE_TOTEM = registerItem("unreliable_totem",
            new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item CONFUSED_TOTEM = registerItem("confused_totem",
            new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item STRANGE_TOTEM = registerItem("strange_totem",
            new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item FRAGILE_TOTEM = registerItem("fragile_totem",
            new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item OMINOUS_TOTEM = registerItem("ominous_totem",
            new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC).fireproof()));

    //Debug
    public static final Item OMINOUS_TOTEM_ACTIVATED = registerItem("ominous_totem_activated",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ExtraordinaryExtraTotems.MOD_ID, name), item);
    }

    public static void registerItems() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering items");
    }
}
