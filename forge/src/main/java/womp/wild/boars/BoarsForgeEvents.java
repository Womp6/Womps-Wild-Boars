package womp.wild.boars;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import womp.wild.boars.entity.Boar;
import womp.wild.boars.registry.BoarEntities;
import womp.wild.boars.registry.BoarItems;

@Mod.EventBusSubscriber(modid = BoarsCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.BOTH)
public class BoarsForgeEvents {
    
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(BoarEntities.WILD_BOAR.get(), Boar.createBoarAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(BoarEntities.WILD_BOAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.getEntries().putAfter(
                Items.COOKED_PORKCHOP.getDefaultInstance(),
                BoarItems.BOAR.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.getEntries().putAfter(
                BoarItems.BOAR.get().getDefaultInstance(),
                BoarItems.COOKED_BOAR.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.getEntries().putAfter(
                Items.PIG_SPAWN_EGG.getDefaultInstance(),
                BoarItems.BOAR_SPAWN_EGG.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
    }
}
