package womp.wild.boars.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import womp.wild.boars.BoarsCommon;

public class BoarTags {

    public static class Items {

        public static final TagKey<Item> BOAR_FOOD = createTag("boar_food");
        public static final TagKey<Item> BOAR_BREED = createTag("boar_breed");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, BoarsCommon.id(name));
        }
    }
}
