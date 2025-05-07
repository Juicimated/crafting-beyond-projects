package dev.juici.cbp.datagen;

import dev.juici.cbp.registry.CBPItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Items;

public class CBPModelProvider extends FabricModelProvider {
    public CBPModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(Items.BREEZE_ROD, Models.HANDHELD);

        itemModelGenerator.register(CBPItems.GOD_RESIDUE, Models.GENERATED);
        itemModelGenerator.register(CBPItems.GODBALL, Models.GENERATED);
        itemModelGenerator.register(CBPItems.BELLE_ROD, Models.HANDHELD);
    }
}
