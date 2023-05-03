package net.freedinner.extraordinary_extra_totems.effect;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.awt.*;

public class ModEffects {
    public static StatusEffect DEATHS_FAVOR;

    private static StatusEffect registerStatusEffect(String name, Color color, StatusEffectCategory category) {
        return Registry.register(
                Registries.STATUS_EFFECT, new Identifier(ExtraordinaryExtraTotems.MOD_ID, name),
                new DeathsFavorEffect(category, color.getRGB())
        );
    }

    public static void registerEffects() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering effects");
        DEATHS_FAVOR = registerStatusEffect("deaths_favor",
                new Color(40, 40, 70), StatusEffectCategory.NEUTRAL);
    }
}
