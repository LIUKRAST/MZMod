package net.frozenblock.mz_informatica.registry;

import net.frozenblock.mz_informatica.MZMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PaintingRegistry {
    public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, MZMod.MOD_ID);

    //public static final RegistryObject<PaintingVariant> PAINTING_VARIANT = REGISTRY.register("example", () -> new PaintingVariant(2, 2));

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
