package dev.juici.cbp.registry;

import dev.juici.cbp.CraftingBeyondProjects;
import dev.juici.cbp.item.staff.KindnessStaff;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class CBPItems {
    public static final Item GOD_RESIDUE = registerItem("god_residue",
            new Item(new Item.Settings()
                    .rarity(Rarity.UNCOMMON)));
    public static final Item GODBALL = registerItem("godball",
            new Item(new Item.Settings()
                    .rarity(Rarity.UNCOMMON)));
    public static final Item BELLE_ROD = registerItem("belle_rod",
            new Item(new Item.Settings()));
    public static final Item STAFF_KINDNESS = registerItem("staff_kindness",
            new KindnessStaff(new Item.Settings()
                    .rarity(Rarity.RARE)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(CraftingBeyondProjects.MOD_ID, name), item);
    }

    public static void register() {
        CraftingBeyondProjects.LOGGER.info("[CBP] Registering items");
    }
}
