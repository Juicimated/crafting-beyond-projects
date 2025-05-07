package dev.juici.cbp.registry;

import dev.juici.cbp.CraftingBeyondProjects;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CBPItemGroups {
    public static final ItemGroup CBP_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(CraftingBeyondProjects.MOD_ID, "cbp_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(CBPItems.GOD_RESIDUE))
                    .displayName(Text.translatable("itemgroup.cbp.cbp_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(CBPItems.GOD_RESIDUE);
                        entries.add(CBPItems.GODBALL);
                        entries.add(CBPItems.BELLE_ROD);
                        entries.add(CBPItems.STAFF_KINDNESS);
                    }).build());

    public static void register() {
        CraftingBeyondProjects.LOGGER.info("[CBP] Registering item groups");
    }
}
