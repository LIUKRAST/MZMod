package net.frozenblock.mz_informatica.mixin;

import com.simibubi.create.foundation.item.ItemHandlerWrapper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemHandlerWrapper.class)

public class ItemHandlerWrapperMixin {
    @Shadow
    private IItemHandlerModifiable wrapped;

    @Inject(method = {"getSlots", "getSlotLimit"}, at = @At("HEAD"), cancellable = true)
    private void mz_informatica$fixIntegers(CallbackInfoReturnable<Integer> cir) {
        if (this.wrapped == null) {
            cir.setReturnValue(0);
        }
    }

    @Inject(method = {"getStackInSlot", "insertItem", "extractItem"}, at = @At("HEAD"), cancellable = true)
    private void mz_informatica$fixItemStack(CallbackInfoReturnable<ItemStack> cir) {
        if (this.wrapped == null) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

    @Inject(method = "isItemValid", at = @At("HEAD"), cancellable = true)
    private void mz_informatica$fixBoolean(CallbackInfoReturnable<Boolean> cir) {
        if (this.wrapped == null) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "setStackInSlot", at = @At("HEAD"), cancellable = true)
    private void mz_informatica$fixSetStack(int slot, ItemStack stack, CallbackInfo ci) {
        if (this.wrapped == null) {
            ci.cancel();
        }
    }
}
