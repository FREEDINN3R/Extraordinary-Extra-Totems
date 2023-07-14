package net.freedinner.extraordinary_extra_totems.item.custom;

import net.freedinner.extraordinary_extra_totems.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UnstableTotemBlockItem extends PlaceableOnShiftBlockItem {
    public UnstableTotemBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient && !user.isSneaking()) {
            user.getItemCooldownManager().set(this, 20);
            explode(user.getStackInHand(hand), user);
        }

        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient && !context.getPlayer().isSneaking()) {
            context.getPlayer().getItemCooldownManager().set(this, 20);
            explode(context.getPlayer().getStackInHand(context.getHand()), context.getPlayer());
        }

        return super.useOnBlock(context);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(!attacker.getWorld().isClient) {
            explode(stack, attacker);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if(!miner.getWorld().isClient) {
            explode(stack, miner);
        }

        return super.postMine(stack, world, state, pos, miner);
    }

    private void explode(ItemStack stack, LivingEntity entity) {
        World world = entity.getWorld();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        stack.decrement(1);
        world.createExplosion(null, x, y, z, 6.0f, World.ExplosionSourceType.BLOCK);

        ModUtil.scatterRemnants(entity, 5, 7, false);
    }
}
