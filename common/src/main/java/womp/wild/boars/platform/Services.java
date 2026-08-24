package womp.wild.boars.platform;

import womp.wild.boars.BoarsCommon;
import womp.wild.boars.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        BoarsCommon.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}