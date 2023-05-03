package net.freedinner.extraordinary_extra_totems.data;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;

public class ModCustomTrades {
    public static void registerCustomTrades() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering custom trades");

        TradeOfferHelper.registerWanderingTraderOffers(2,
                factories -> factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 12),
                    new ItemStack(ModItems.TOTEM_REMNANTS, 1),
                    2, 0, 0)));
    }
}
