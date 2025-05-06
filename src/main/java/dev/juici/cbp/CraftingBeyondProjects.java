package dev.juici.cbp;

import dev.juici.cbp.registry.CBPItemGroups;
import dev.juici.cbp.registry.CBPItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CraftingBeyondProjects implements ModInitializer {
	public static final String MOD_ID = "cbp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("[CBP] Registering " + MOD_ID);

		CBPItemGroups.register();
		CBPItems.register();
	}
}
