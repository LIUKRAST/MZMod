package net.frozenblock.mz_informatica.mixin;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.frozenblock.mz_informatica.FluidMap.*;

//@Mixin(value = FluidTank.class, remap = false)
public class FluidTankMixin {
    /*@Redirect(method = {
            "fill",
            "drain(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/minecraftforge/fluids/FluidStack;"
    }, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidStack;isFluidEqual(Lnet/minecraftforge/fluids/FluidStack;)Z"))
     */
    private boolean isActuallyEqual(FluidStack instance, FluidStack other) {
        if(!instance.isFluidEqual(other)) {
            var f1 = get(instance.getFluid());
            var f2 = get(other.getFluid());
            final var c1 = convert(f1);
            final var c2 = convert(f2);
            return c1.equals(c2);
        }
        return true;
    }

    /*
    @ModifyVariable(method = {
            "fill",
            "drain(ILnet/minecraftforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/minecraftforge/fluids/FluidStack;"
    }, at = @At(value = "FIELD", target = "Lnet/minecraftforge/fluids/capability/templates/FluidTank;fluid:Lnet/minecraftforge/fluids/FluidStack;", opcode = Opcodes.PUTFIELD), argsOnly = true)
    */
    private FluidStack createNew(FluidStack value) {
        final var f1 = get(value.getFluid());
        final var c1 = convert(f1);
        return new FluidStack(get(c1), value.getAmount());
    }
}
