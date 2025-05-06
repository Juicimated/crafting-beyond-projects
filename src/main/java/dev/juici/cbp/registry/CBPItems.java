package dev.juici.cbp.registry;

import dev.juici.cbp.CraftingBeyondProjects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CBPItems {
    public static final Item BELLE_ROD = registerItem("belle_rod",
            new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(CraftingBeyondProjects.MOD_ID, name), item);
    }

    public static void register() {
        CraftingBeyondProjects.LOGGER.info("[CBP] Registering items");
    }
}
