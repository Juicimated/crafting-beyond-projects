package dev.juici.cbp.datagen;

import dev.juici.cbp.registry.CBPItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class CBPRecipeProvider extends FabricRecipeProvider {
    public CBPRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, CBPItems.GODBALL, 1)
                .input(CBPItems.GOD_RESIDUE)
                .criterion(hasItem(CBPItems.GOD_RESIDUE), conditionsFromItem(CBPItems.GOD_RESIDUE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, CBPItems.STAFF_KINDNESS)
                .pattern(" FG")
                .pattern(" BF")
                .pattern("B  ")
                .input('F', Items.FEATHER)
                .input('B', CBPItems.BELLE_ROD)
                .input('G', CBPItems.GODBALL)
                .criterion(hasItem(CBPItems.BELLE_ROD), conditionsFromItem(CBPItems.BELLE_ROD))
                .offerTo(recipeExporter);
    }
}
