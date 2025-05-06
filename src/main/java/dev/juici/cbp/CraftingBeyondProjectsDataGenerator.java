package dev.juici.cbp;

import dev.juici.cbp.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CraftingBeyondProjectsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(CBPBlockTagProvider::new);
		pack.addProvider(CBPItemTagProvider::new);
		pack.addProvider(CBPLootTableProvider::new);
		pack.addProvider(CBPModelProvider::new);
		pack.addProvider(CBPRecipeProvider::new);
	}
}
