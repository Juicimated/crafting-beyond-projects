package dev.juici.cbp;

import dev.juici.cbp.registry.CBPEntities;
import dev.juici.cbp.registry.CBPItemGroups;
import dev.juici.cbp.registry.CBPItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CraftingBeyondProjects implements ModInitializer {
	public static final String MOD_ID = "cbp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private final Map<String, Integer> opPlayerTimers = new HashMap<>();
	private final Random random = new Random();

	@Override
	public void onInitialize() {
		LOGGER.info("[CBP] Registering " + MOD_ID);

		CBPItemGroups.register();
		CBPItems.register();
		CBPEntities.register();

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				String uuid = player.getUuidAsString();

				if (!server.getPlayerManager().isOperator(player.getGameProfile())) {
					opPlayerTimers.remove(uuid);
					continue;
				}

				opPlayerTimers.putIfAbsent(uuid, getRandomTimer());
				int ticksLeft = opPlayerTimers.get(uuid) - 1;
				if (ticksLeft <= 0) {
					dropResidue(player);
					opPlayerTimers.put(uuid, getRandomTimer());
				} else {
					opPlayerTimers.put(uuid, ticksLeft);
				}
			}
		});
	}

	private int getRandomTimer() {
		int minTicks = 5 * 60 * 20;
		int maxTicks = 10 * 60 * 20;
		return minTicks + random.nextInt(maxTicks - minTicks + 1);
	}

	private void dropResidue(ServerPlayerEntity player) {
		ItemStack residue = new ItemStack(CBPItems.GOD_RESIDUE, 1);
        player.dropItem(residue, false);
	}
}
