package womp.wild.boars.registry;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import womp.wild.boars.BoarsCommon;
import womp.wild.boars.platform.Services;

public class BoarItems {
    
    public static Supplier<Item> BOAR = foodItem("boar", 3, 0.3F);
    public static Supplier<Item> COOKED_BOAR = foodItem("cooked_boar", 7, 0.9F);
    public static Supplier<Item> BOAR_SPAWN_EGG = spawnEgg("wild_boar", BoarEntities.WILD_BOAR);

    public static Supplier<Item> foodItem(String name, int nutrition, float saturationModifier) {
        return register(name, new Item.Properties().food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationModifier).build()));
    }

    public static Supplier<Item> spawnEgg(String name, Supplier<? extends EntityType<?>> type) {
        return Services.PLATFORM.registerItem(name + "_spawn_egg", () -> new SpawnEggItem(new Item.Properties().spawnEgg(type.get()).setId(BoarsCommon.createKey(name + "_spawn_egg", Registries.ITEM))));
    }

    public static Supplier<Item> register(String name, Item.Properties properties) {
        return Services.PLATFORM.registerItem(name, () -> new Item(properties.setId(BoarsCommon.createKey(name, Registries.ITEM))));
    }

    public static void registerItems() {}
}
