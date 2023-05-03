package net.freedinner.extraordinary_extra_totems.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;

public class PlaceableOnShiftBlockItem extends BlockItem {
    public PlaceableOnShiftBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return context.getPlayer().isSneaking() && super.canPlace(context, state);
    }
}
