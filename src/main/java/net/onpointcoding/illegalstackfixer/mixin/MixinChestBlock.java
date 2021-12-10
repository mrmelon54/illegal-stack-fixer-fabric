package net.onpointcoding.illegalstackfixer.mixin;

import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.onpointcoding.illegalstackfixer.IllegalStackFixer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ChestBlock.class)
public abstract class MixinChestBlock extends AbstractChestBlock<ChestBlockEntity> {
    protected MixinChestBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityTypeSupplier) {
        super(settings, blockEntityTypeSupplier);
    }

    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;", ordinal = 0))
    private void wrapOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (IllegalStackFixer.getInstance().shouldIgnorePlayer(player)) return;

        Inventory inventory = ChestBlock.getInventory((ChestBlock) (Object) this, state, world, pos, false);
        if (inventory == null) return;
        int size = inventory.size();
        for (int i = 0; i < size; i++) {
            ItemStack stack = inventory.getStack(i);
            System.out.println(stack.getCount());
            if (IllegalStackFixer.getInstance().shouldReduceStackSize(stack)) {
                stack.setCount(stack.getItem().getMaxCount());
            }
        }
    }
}
