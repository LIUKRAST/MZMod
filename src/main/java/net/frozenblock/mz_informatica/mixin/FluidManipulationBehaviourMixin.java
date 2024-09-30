package net.frozenblock.mz_informatica.mixin;

import com.simibubi.create.content.fluids.transfer.FluidManipulationBehaviour;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(value = FluidManipulationBehaviour.class, remap = false)
public class FluidManipulationBehaviourMixin {

    @Unique
    private BlockPos mZMod$currentPos;

    @ModifyVariable(
            method = "search",
            at = @At("STORE"),
            ordinal = 0
    )
    private BlockPos currentPos(BlockPos value) {
        mZMod$currentPos = value;
        return value;
    }

    @Redirect(
            method = "search",
            at = @At(
                    value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z")
    )
    private <E> boolean redirected(Set<E> instance, E e) {
        final var that = (FluidManipulationBehaviour)(Object)this;
        if(that.getWorld().getFluidState(mZMod$currentPos).isSource()) {
            return instance.add(e);
        }
        return false;
    }
}
