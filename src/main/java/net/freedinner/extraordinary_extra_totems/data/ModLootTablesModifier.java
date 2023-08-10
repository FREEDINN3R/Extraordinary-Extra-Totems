package net.freedinner.extraordinary_extra_totems.data;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTablesModifier {
    private static final Identifier PILLAGER_OUTPOST_CHEST_ID =
            new Identifier("minecraft", "chests/pillager_outpost");
    private static final Identifier BURIED_TREASURE_CHEST_ID =
            new Identifier("minecraft", "chests/buried_treasure");
    private static final Identifier RUINED_PORTAL_CHEST_ID =
            new Identifier("minecraft", "chests/ruined_portal");
    private static final Identifier NETHER_FORTRESS_CHEST_ID =
            new Identifier("minecraft", "chests/nether_bridge");
    private static final Identifier BASTION_STABLE_CHEST_ID =
            new Identifier("minecraft", "chests/bastion_hoglin_stable");
    private static final Identifier BASTION_OTHER_CHEST_ID =
            new Identifier("minecraft", "chests/bastion_other");
    private static final Identifier BASTION_TREASURE_CHEST_ID =
            new Identifier("minecraft", "chests/bastion_treasure");
    private static final Identifier EVOKER_LOOT_ID =
            new Identifier("minecraft", "entities/evoker");

    public static void modifyLootTables() {
        ExtraordinaryExtraTotems.LOGGER.info("Modifying loot tables");

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            //Totem remnants
            if(id.equals(PILLAGER_OUTPOST_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.TOTEM_REMNANTS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(BURIED_TREASURE_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.66f))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.TOTEM_REMNANTS)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))));
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(EVOKER_LOOT_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(KilledByPlayerLootCondition.builder())
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.TOTEM_REMNANTS)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                                .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 0.7f))));
                tableBuilder.pool(poolBuilder.build());
            }

            //Ominous totem remnants + totem
            if(id.equals(RUINED_PORTAL_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.1f))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.OMINOUS_TOTEM_REMNANTS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(NETHER_FORTRESS_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.15f))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.OMINOUS_TOTEM_REMNANTS)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)))
                                .weight(9))
                        .with(ItemEntry.builder(ModItems.OMINOUS_TOTEM)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .weight(1));
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(BASTION_STABLE_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.1f))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.OMINOUS_TOTEM_REMNANTS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(BASTION_OTHER_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.1f))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.OMINOUS_TOTEM_REMNANTS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(BASTION_TREASURE_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.33f))
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.OMINOUS_TOTEM_REMNANTS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
