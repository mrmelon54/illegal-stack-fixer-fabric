package net.onpointcoding.illegalstackfixer.mixin;

import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.onpointcoding.illegalstackfixer.IMinecartEntitySize;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HopperMinecartEntity.class)
public abstract class MixinHopperMinecartEntity implements IMinecartEntitySize {
    @Shadow
    public abstract int size();

    @Override
    public int getSize() {
        return size();
    }
}
