package de.gieskerb.class_craft.classes;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassSpecificLUT {

    private static List<EntityPair> warriorEntities;

    private static List<MaterialPair> minerBlocks;

    private static List<MaterialPair> lumberjackBlocks;
    private static List<MaterialPair> farmerBlocks;
    private static List<EntityPair> farmerEntities;
    private static HashMap<Material, Integer> ageTable;

    public static List<EntityPair> getWarriorEntities() {
        if (warriorEntities == null) {
            warriorEntities = new ArrayList<>();
            warriorEntities.add(new EntityPair(EntityType.BLAZE, 20));
            warriorEntities.add(new EntityPair(EntityType.BOGGED, 20));
            warriorEntities.add(new EntityPair(EntityType.BREEZE, 20));
            warriorEntities.add(new EntityPair(EntityType.CAVE_SPIDER, 20));
            warriorEntities.add(new EntityPair(EntityType.CREEPER, 20));
            warriorEntities.add(new EntityPair(EntityType.DROWNED, 20));
            warriorEntities.add(new EntityPair(EntityType.ELDER_GUARDIAN, 20));
            warriorEntities.add(new EntityPair(EntityType.ENDER_DRAGON, 20));
            warriorEntities.add(new EntityPair(EntityType.ENDERMAN, 20));
            warriorEntities.add(new EntityPair(EntityType.ENDERMITE, 20));
            warriorEntities.add(new EntityPair(EntityType.EVOKER, 20));
            warriorEntities.add(new EntityPair(EntityType.GHAST, 20));
            warriorEntities.add(new EntityPair(EntityType.GUARDIAN, 20));
            warriorEntities.add(new EntityPair(EntityType.HOGLIN, 20));
            warriorEntities.add(new EntityPair(EntityType.HUSK, 20));
            warriorEntities.add(new EntityPair(EntityType.MAGMA_CUBE, 20));
            warriorEntities.add(new EntityPair(EntityType.PHANTOM, 20));
            warriorEntities.add(new EntityPair(EntityType.PIGLIN, 20));
            warriorEntities.add(new EntityPair(EntityType.PIGLIN_BRUTE, 20));
            warriorEntities.add(new EntityPair(EntityType.PILLAGER, 20));
            warriorEntities.add(new EntityPair(EntityType.RAVAGER, 20));
            warriorEntities.add(new EntityPair(EntityType.SHULKER, 20));
            warriorEntities.add(new EntityPair(EntityType.SILVERFISH, 20));
            warriorEntities.add(new EntityPair(EntityType.SKELETON, 20));
            warriorEntities.add(new EntityPair(EntityType.SLIME, 20));
            warriorEntities.add(new EntityPair(EntityType.SPIDER, 20));
            warriorEntities.add(new EntityPair(EntityType.STRAY, 20));
            warriorEntities.add(new EntityPair(EntityType.VEX, 20));
            warriorEntities.add(new EntityPair(EntityType.VINDICATOR, 20));
            warriorEntities.add(new EntityPair(EntityType.WARDEN, 20));
            warriorEntities.add(new EntityPair(EntityType.WITCH, 20));
            warriorEntities.add(new EntityPair(EntityType.WITHER, 20));
            warriorEntities.add(new EntityPair(EntityType.WITHER_SKELETON, 20));
            warriorEntities.add(new EntityPair(EntityType.ZOGLIN, 20));
            warriorEntities.add(new EntityPair(EntityType.ZOMBIE, 20));
            warriorEntities.add(new EntityPair(EntityType.ZOMBIE_VILLAGER, 20));
            warriorEntities.add(new EntityPair(EntityType.ZOMBIFIED_PIGLIN, 20));
        }
        return warriorEntities;
    }

    public static List<MaterialPair> getMinerBlocks() {
        if (minerBlocks == null) {
            minerBlocks = new ArrayList<>();
            minerBlocks.add(new MaterialPair(Material.SANDSTONE, 20));
            minerBlocks.add(new MaterialPair(Material.RED_SANDSTONE, 20));
            minerBlocks.add(new MaterialPair(Material.STONE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE, 20));
            minerBlocks.add(new MaterialPair(Material.GRANITE, 20));
            minerBlocks.add(new MaterialPair(Material.DIORITE, 20));
            minerBlocks.add(new MaterialPair(Material.ANDESITE, 20));
            minerBlocks.add(new MaterialPair(Material.CALCITE, 20));
            minerBlocks.add(new MaterialPair(Material.TUFF, 20));
            minerBlocks.add(new MaterialPair(Material.DRIPSTONE_BLOCK, 20));
            minerBlocks.add(new MaterialPair(Material.MAGMA_BLOCK, 20));
            minerBlocks.add(new MaterialPair(Material.OBSIDIAN, 20));
            minerBlocks.add(new MaterialPair(Material.NETHERRACK, 20));
            minerBlocks.add(new MaterialPair(Material.CRIMSON_NYLIUM, 20));
            minerBlocks.add(new MaterialPair(Material.WARPED_NYLIUM, 20));
            minerBlocks.add(new MaterialPair(Material.BLACKSTONE, 20));
            minerBlocks.add(new MaterialPair(Material.BASALT, 20));
            minerBlocks.add(new MaterialPair(Material.SMOOTH_BASALT, 20));
            minerBlocks.add(new MaterialPair(Material.END_STONE, 20));
            minerBlocks.add(new MaterialPair(Material.COAL_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE_COAL_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.IRON_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE_IRON_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.COPPER_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE_COPPER_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.GOLD_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE_GOLD_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.REDSTONE_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE_REDSTONE_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.EMERALD_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE_EMERALD_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.LAPIS_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE_LAPIS_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DIAMOND_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.DEEPSLATE_DIAMOND_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.NETHER_GOLD_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.NETHER_QUARTZ_ORE, 20));
            minerBlocks.add(new MaterialPair(Material.ANCIENT_DEBRIS, 20));
            minerBlocks.add(new MaterialPair(Material.AMETHYST_CLUSTER, 20));
            minerBlocks.add(new MaterialPair(Material.AMETHYST_BLOCK, 20));

        }
        return minerBlocks;
    }

    public static List<MaterialPair> getLumberjackBlocks() {
        if (lumberjackBlocks == null) {
            lumberjackBlocks = new ArrayList<>();
            lumberjackBlocks.add(new MaterialPair(Material.OAK_LOG, 20));
            lumberjackBlocks.add(new MaterialPair(Material.SPRUCE_LOG, 20));
            lumberjackBlocks.add(new MaterialPair(Material.BIRCH_LOG, 20));
            lumberjackBlocks.add(new MaterialPair(Material.JUNGLE_LOG, 20));
            lumberjackBlocks.add(new MaterialPair(Material.ACACIA_LOG, 20));
            lumberjackBlocks.add(new MaterialPair(Material.DARK_OAK_LOG, 20));
            lumberjackBlocks.add(new MaterialPair(Material.MANGROVE_LOG, 20));
            lumberjackBlocks.add(new MaterialPair(Material.CHERRY_LOG, 20));
            lumberjackBlocks.add(new MaterialPair(Material.MUSHROOM_STEM, 20));
            lumberjackBlocks.add(new MaterialPair(Material.CRIMSON_STEM, 20));
            lumberjackBlocks.add(new MaterialPair(Material.WARPED_STEM, 20));
            lumberjackBlocks.add(new MaterialPair(Material.OAK_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.SPRUCE_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.BIRCH_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.JUNGLE_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.ACACIA_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.DARK_OAK_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.MANGROVE_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.CHERRY_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.AZALEA_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.FLOWERING_AZALEA_LEAVES, 20));
            lumberjackBlocks.add(new MaterialPair(Material.BROWN_MUSHROOM_BLOCK, 20));
            lumberjackBlocks.add(new MaterialPair(Material.RED_MUSHROOM_BLOCK, 20));
            lumberjackBlocks.add(new MaterialPair(Material.NETHER_WART_BLOCK, 20));
            lumberjackBlocks.add(new MaterialPair(Material.WARPED_WART_BLOCK, 20));
        }
        return lumberjackBlocks;
    }

    public static List<MaterialPair> getFarmerBlocks() {
        if (farmerBlocks == null) {
            farmerBlocks = new ArrayList<>();
            farmerBlocks.add(new MaterialPair(Material.BROWN_MUSHROOM, 20));
            farmerBlocks.add(new MaterialPair(Material.RED_MUSHROOM, 20));
            farmerBlocks.add(new MaterialPair(Material.CRIMSON_FUNGUS, 20));
            farmerBlocks.add(new MaterialPair(Material.WARPED_FUNGUS, 20));
            farmerBlocks.add(new MaterialPair(Material.SHORT_GRASS, 20));
            farmerBlocks.add(new MaterialPair(Material.FERN, 20));
            farmerBlocks.add(new MaterialPair(Material.DANDELION, 20));
            farmerBlocks.add(new MaterialPair(Material.POPPY, 20));
            farmerBlocks.add(new MaterialPair(Material.BLUE_ORCHID, 20));
            farmerBlocks.add(new MaterialPair(Material.ALLIUM, 20));
            farmerBlocks.add(new MaterialPair(Material.AZURE_BLUET, 20));
            farmerBlocks.add(new MaterialPair(Material.RED_TULIP, 20));
            farmerBlocks.add(new MaterialPair(Material.ORANGE_TULIP, 20));
            farmerBlocks.add(new MaterialPair(Material.WHITE_TULIP, 20));
            farmerBlocks.add(new MaterialPair(Material.PINK_TULIP, 20));
            farmerBlocks.add(new MaterialPair(Material.OXEYE_DAISY, 20));
            farmerBlocks.add(new MaterialPair(Material.CORNFLOWER, 20));
            farmerBlocks.add(new MaterialPair(Material.LILY_OF_THE_VALLEY, 20));
            farmerBlocks.add(new MaterialPair(Material.TORCHFLOWER, 20));
            farmerBlocks.add(new MaterialPair(Material.WITHER_ROSE, 20));
            farmerBlocks.add(new MaterialPair(Material.PINK_PETALS, 20));
            farmerBlocks.add(new MaterialPair(Material.SPORE_BLOSSOM, 20));
            farmerBlocks.add(new MaterialPair(Material.BAMBOO, 20));
            farmerBlocks.add(new MaterialPair(Material.SUGAR_CANE, 20));
            farmerBlocks.add(new MaterialPair(Material.CACTUS, 20));
            farmerBlocks.add(new MaterialPair(Material.TALL_GRASS, 20));
            farmerBlocks.add(new MaterialPair(Material.LARGE_FERN, 20));
            farmerBlocks.add(new MaterialPair(Material.SUNFLOWER, 20));
            farmerBlocks.add(new MaterialPair(Material.LILAC, 20));
            farmerBlocks.add(new MaterialPair(Material.ROSE_BUSH, 20));
            farmerBlocks.add(new MaterialPair(Material.PEONY, 20));
            farmerBlocks.add(new MaterialPair(Material.CHORUS_PLANT, 20));
            farmerBlocks.add(new MaterialPair(Material.CHORUS_FLOWER, 20));
            farmerBlocks.add(new MaterialPair(Material.WHEAT_SEEDS, 20));
            farmerBlocks.add(new MaterialPair(Material.CARROT, 20));
            farmerBlocks.add(new MaterialPair(Material.POTATO, 20));
            farmerBlocks.add(new MaterialPair(Material.COCOA_BEANS, 20));
            farmerBlocks.add(new MaterialPair(Material.PUMPKIN_SEEDS, 20));
            farmerBlocks.add(new MaterialPair(Material.MELON_SEEDS, 20));
            farmerBlocks.add(new MaterialPair(Material.BEETROOT_SEEDS, 20));
            farmerBlocks.add(new MaterialPair(Material.CAVE_VINES, 20)); // Glow-Berries
            farmerBlocks.add(new MaterialPair(Material.SWEET_BERRY_BUSH, 20));
            farmerBlocks.add(new MaterialPair(Material.NETHER_WART, 20));
            farmerBlocks.add(new MaterialPair(Material.MELON, 20));
            farmerBlocks.add(new MaterialPair(Material.PUMPKIN, 20));
        }
        return farmerBlocks;
    }

    public static List<EntityPair> getFarmerEntities() {
        if (farmerEntities == null) {
            farmerEntities = new ArrayList<>();
            farmerEntities.add(new EntityPair(EntityType.CHICKEN, 20));
            farmerEntities.add(new EntityPair(EntityType.COD, 20));
            farmerEntities.add(new EntityPair(EntityType.COW, 20));
            farmerEntities.add(new EntityPair(EntityType.DONKEY, 20));
            farmerEntities.add(new EntityPair(EntityType.GLOW_SQUID, 20));
            farmerEntities.add(new EntityPair(EntityType.HORSE, 20));
            farmerEntities.add(new EntityPair(EntityType.MOOSHROOM, 20));
            farmerEntities.add(new EntityPair(EntityType.PIG, 20));
            farmerEntities.add(new EntityPair(EntityType.RABBIT, 20));
            farmerEntities.add(new EntityPair(EntityType.SALMON, 20));
            farmerEntities.add(new EntityPair(EntityType.SHEEP, 20));
            farmerEntities.add(new EntityPair(EntityType.SQUID, 20));
            farmerEntities.add(new EntityPair(EntityType.TROPICAL_FISH, 20));
            farmerEntities.add(new EntityPair(EntityType.TURTLE, 20));
        }
        return farmerEntities;
    }

    public static int getMaxAge(Material material) {
        if (ageTable == null) {
            ageTable = HashMap.newHashMap(10);
            ageTable.put(Material.WHEAT_SEEDS, 7);
            ageTable.put(Material.CARROT, 7);
            ageTable.put(Material.POTATO, 7);
            ageTable.put(Material.PUMPKIN_SEEDS, 7);
            ageTable.put(Material.MELON_SEEDS, 7);
            ageTable.put(Material.BEETROOT_SEEDS, 3);
            ageTable.put(Material.NETHER_WART, 3);
            ageTable.put(Material.COCOA, 2);
        }
        return ageTable.getOrDefault(material, 0);
    }

    public record EntityPair(EntityType type, int xpValue) {

        public EntityPair(EntityType type) {
            this(type, 0);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EntityPair other)) return false;
            return type == other.type;
        }
    }

    public record MaterialPair(Material type, int xpValue) {
        public MaterialPair(Material type) {
            this(type, 0);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MaterialPair other)) return false;
            return type == other.type;
        }
    }

}
