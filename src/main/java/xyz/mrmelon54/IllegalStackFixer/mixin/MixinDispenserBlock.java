package xyz.mrmelon54.IllegalStackFixer.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.mrmelon54.IllegalStackFixer.IllegalStackFixer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
 * The DropperBlock extends DispenserBlock so this works for droppers too
 */

@Mixin(DispenserBlock.class)
public abstract class MixinDispenserBlock {
    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;", ordinal = 0))
    private void wrapOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (IllegalStackFixer.getInstance().shouldIgnorePlayer(player)) return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DispenserBlockEntity dispenserBlockEntity) {
            int size = dispenserBlockEntity.size();
            for (int i = 0; i < size; i++) {
                ItemStack stack = dispenserBlockEntity.getStack(i);
                if (IllegalStackFixer.getInstance().shouldReduceStackSize(stack)) {
                    stack.setCount(stack.getItem().getMaxCount());
                }
            }
        }
    }
}
