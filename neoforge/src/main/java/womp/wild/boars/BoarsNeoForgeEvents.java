package womp.wild.boars;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import womp.wild.boars.entity.Boar;
import womp.wild.boars.registry.BoarEntities;
import womp.wild.boars.registry.BoarItems;

@EventBusSubscriber(modid = BoarsCommon.MOD_ID)
public class BoarsNeoForgeEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(BoarEntities.WILD_BOAR.get(), Boar.createBoarAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(RegisterSpawnPlacementsEvent event) {
        event.register(BoarEntities.WILD_BOAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.insertAfter(
                Items.COOKED_PORKCHOP.getDefaultInstance(),
                BoarItems.BOAR.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.insertAfter(
                BoarItems.BOAR.get().getDefaultInstance(),
                BoarItems.COOKED_BOAR.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.insertAfter(
                Items.PIG_SPAWN_EGG.getDefaultInstance(),
                BoarItems.BOAR_SPAWN_EGG.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
    }
}
