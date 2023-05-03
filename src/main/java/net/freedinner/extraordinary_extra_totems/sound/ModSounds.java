package net.freedinner.extraordinary_extra_totems.sound;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static SoundEvent ITEM_OMINOUS_TOTEM_USE = register("use_ominous_totem");
    public static SoundEvent DEATH_STARE = register("death_stare");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(ExtraordinaryExtraTotems.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering sounds");
    }
}
