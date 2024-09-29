package net.frozenblock.mz_informatica.mixin;

import com.simibubi.create.content.fluids.transfer.FluidDrainingBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(FluidDrainingBehaviour.class)
public class FluidDrainingBehaviourMixin {

    @Inject(at = @At(""))
}
