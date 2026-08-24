package womp.wild.boars;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import womp.wild.boars.entity.Boar;
import womp.wild.boars.registry.BoarEntities;
import womp.wild.boars.registry.BoarItems;

public class BoarsFabricEvents {
    
    public static void register() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register((tab) -> {
            tab.insertAfter(Items.COOKED_PORKCHOP, BoarItems.BOAR.get());
            tab.insertAfter(BoarItems.BOAR.get(), BoarItems.COOKED_BOAR.get());
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register((tab) -> {
            tab.insertAfter(Items.PIG_SPAWN_EGG, BoarItems.BOAR_SPAWN_EGG.get());
        });

        FabricDefaultAttributeRegistry.register(BoarEntities.WILD_BOAR.get(), Boar.createBoarAttributes());
        SpawnPlacements.register(BoarEntities.WILD_BOAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.FLOWER_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.TAIGA), MobCategory.CREATURE, BoarEntities.WILD_BOAR.get(), 12, 2, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.WINDSWEPT_FOREST), MobCategory.CREATURE, BoarEntities.WILD_BOAR.get(), 8, 2, 5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE), MobCategory.CREATURE, BoarEntities.WILD_BOAR.get(), 2, 2, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.PLAINS), MobCategory.CREATURE, BoarEntities.WILD_BOAR.get(), 2, 1, 2);
    }
}
