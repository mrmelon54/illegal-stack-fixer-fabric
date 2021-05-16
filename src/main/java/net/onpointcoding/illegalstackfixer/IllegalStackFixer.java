package net.onpointcoding.illegalstackfixer;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class IllegalStackFixer implements ModInitializer {
    private static IllegalStackFixer instance;

    @Override
    public void onInitialize() {
        instance = this;
    }

    public static IllegalStackFixer getInstance() {
        return instance;
    }

    public boolean shouldReduceStackSize(ItemStack stack) {
        return stack.getCount() > stack.getItem().getMaxCount();
    }

    public boolean shouldIgnorePlayer(PlayerEntity player) {
        return player.isCreative();
    }
}
