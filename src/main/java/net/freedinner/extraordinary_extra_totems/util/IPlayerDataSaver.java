package net.freedinner.extraordinary_extra_totems.util;

import net.minecraft.nbt.NbtCompound;

public interface IPlayerDataSaver {
    int getDeathStareCooldown();
    void setDeathStareCooldown(int i);
    NbtCompound getPersistentData();
}
