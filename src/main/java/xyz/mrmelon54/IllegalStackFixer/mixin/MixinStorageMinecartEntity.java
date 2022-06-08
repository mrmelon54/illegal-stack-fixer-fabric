package xyz.mrmelon54.IllegalStackFixer.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import xyz.mrmelon54.IllegalStackFixer.IMinecartEntitySize;
import xyz.mrmelon54.IllegalStackFixer.IllegalStackFixer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StorageMinecartEntity.class)
public abstract class MixinStorageMinecartEntity implements IMinecartEntitySize {
    @Shadow
    public abstract ItemStack getStack(int slot);

    @Inject(method = "interact", at = @At("HEAD"))
    public void wrapInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (IllegalStackFixer.getInstance().shouldIgnorePlayer(player)) return;

        for (int i = 0; i < getSize(); i++) {
            ItemStack stack = getStack(i);
            if (IllegalStackFixer.getInstance().shouldReduceStackSize(stack)) {
                stack.setCount(stack.getItem().getMaxCount());
            }
        }
    }
}
