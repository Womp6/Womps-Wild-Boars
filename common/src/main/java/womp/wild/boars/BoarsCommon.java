package womp.wild.boars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import womp.wild.boars.registry.BoarEntities;
import womp.wild.boars.registry.BoarItems;
import womp.wild.boars.registry.BoarSounds;

public class BoarsCommon {

    public static final String MOD_ID = "boars";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
		BoarItems.registerItems();
		BoarEntities.registerEntities();
		BoarSounds.registerSounds();
    }

    public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static <T> ResourceKey<T> createKey(String path, ResourceKey<? extends Registry<T>> type) {
		return ResourceKey.create(type, id(path));
	}
}