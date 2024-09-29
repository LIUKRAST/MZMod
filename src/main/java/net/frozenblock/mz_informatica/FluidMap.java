package net.frozenblock.mz_informatica;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;

public class FluidMap {
    public static final HashMap<String, String> MAP = new HashMap<>();

    public static Fluid get(String id) {
        return BuiltInRegistries.FLUID.get(new ResourceLocation(id));
    }

    public static String get(Fluid fluid) {
        return BuiltInRegistries.FLUID.getKey(fluid).toString();
    }

    public static String convert(String id) {
        if(FluidMap.MAP.containsKey(id)) {
            final var replace = FluidMap.MAP.get(id);
            MZMod.LOGGER.info("Converting fluid {} into {}", id, replace);
            return replace;
        }
        return id;
    }

    static {
        MAP.put("gtceu:flowing_oil_medium", "gtceu:oil_medium");
    }
}
