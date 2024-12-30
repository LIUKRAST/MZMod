package net.frozenblock.mz_informatica.mixin;

import com.jesz.createdieselgenerators.blocks.entity.DistillationTankBlockEntity;
import com.jesz.createdieselgenerators.recipes.RecipeRegistry;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = DistillationTankBlockEntity.class, remap = false)
public class DistillationTankBlockEntityMixin {
    @Unique
    private static final Object mz_mod$RECIPE_CACHE_KEY = new Object();

    @Inject(method = "getMatchingRecipes", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/recipe/RecipeFinder;get(Ljava/lang/Object;Lnet/minecraft/world/level/Level;Ljava/util/function/Predicate;)Ljava/util/List;"), cancellable = true)
    private void mz_mod$getMatchingRecipes(CallbackInfoReturnable<List<Recipe<?>>> cir) {
        cir.setReturnValue(RecipeFinder.get(mz_mod$RECIPE_CACHE_KEY, ((DistillationTankBlockEntity)(Object)this).getLevel(), (recipe) -> recipe.getType() == RecipeRegistry.DISTILLATION.getType()));
    }
}
