package net.freedinner.extraordinary_extra_totems.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlaceableOnShiftBlockItem extends BlockItem {
    public PlaceableOnShiftBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return context.getPlayer().isSneaking() && super.canPlace(context, state);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.extraordinary_extra_totems.shift_item.tooltip").formatted(Formatting.GRAY));
    }
}
