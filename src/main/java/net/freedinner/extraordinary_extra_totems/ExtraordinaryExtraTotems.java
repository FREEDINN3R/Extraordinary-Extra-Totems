package net.freedinner.extraordinary_extra_totems;

import net.freedinner.extraordinary_extra_totems.legacy.block.ModBlocks;
import net.freedinner.extraordinary_extra_totems.data.ModCustomTrades;
import net.freedinner.extraordinary_extra_totems.data.ModLootTablesModifier;
import net.freedinner.extraordinary_extra_totems.effect.ModEffects;
import net.freedinner.extraordinary_extra_totems.item.ModItemGroups;
import net.freedinner.extraordinary_extra_totems.item.ModItems;
import net.freedinner.extraordinary_extra_totems.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtraordinaryExtraTotems implements ModInitializer {
	public static final String MOD_ID = "extraordinary_extra_totems";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerItems();
		ModBlocks.registerBlocks();
		ModCustomTrades.registerCustomTrades();
		ModLootTablesModifier.modifyLootTables();
		ModSounds.registerSounds();
		ModEffects.registerEffects();
	}
}