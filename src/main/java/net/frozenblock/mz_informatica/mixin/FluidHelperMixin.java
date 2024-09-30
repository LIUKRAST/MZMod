package net.frozenblock.mz_informatica.mixin;

import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.frozenblock.mz_informatica.FluidMap;
import net.frozenblock.mz_informatica.MZMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

import static net.frozenblock.mz_informatica.FluidMap.*;

//@Mixin(value = FluidHelper.class, remap = false)
public class FluidHelperMixin {

    //@Inject(at = @At("RETURN"), method = "convertToStill", cancellable = true)
    private static void iConvertToStill(Fluid fluid, CallbackInfoReturnable<Fluid> cir) {
        cir.setReturnValue(get(convert(get(fluid))));
    }

    //@Inject(at = @At("RETURN"), method = "isSame(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/world/level/material/Fluid;)Z", cancellable = true)
    private static void isSame1(FluidStack fluidStack, Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) {
            var f1 = get(fluid);
            var f2 = get(fluidStack.getFluid());
            var c1 = convert(f1);
            var c2 = convert(f2);
            if (Objects.equals(c1, c2)) cir.setReturnValue(true);
        }
    }

    //@Inject(at = @At("RETURN"), method = "isSame(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/FluidStack;)Z", cancellable = true)
    private static void isSame2(FluidStack fluidStack, FluidStack fluidStack2, CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) {
            var f1 = get(fluidStack2.getFluid());
            var f2 = get(fluidStack.getFluid());
            var c1 = convert(f1);
            var c2 = convert(f2);
            if (Objects.equals(c1, c2)) cir.setReturnValue(true);
        }
    }

    //@ModifyVariable(method = "deserializeFluidStack", at = @At("STORE"), ordinal = 0)
    private static ResourceLocation weDeserialize(ResourceLocation resourceLocation) {
        return new ResourceLocation(convert(resourceLocation.toString()));
    }

    //@Redirect(method = "serializeFluidStack", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/RegisteredObjects;getKeyOrThrow(Lnet/minecraft/world/level/material/Fluid;)Lnet/minecraft/resources/ResourceLocation;"))
    private static ResourceLocation weSerialize(Fluid value) {
        final var original = RegisteredObjects.getKeyOrThrow(value).toString();
        return new ResourceLocation(convert(original));
    }
}
