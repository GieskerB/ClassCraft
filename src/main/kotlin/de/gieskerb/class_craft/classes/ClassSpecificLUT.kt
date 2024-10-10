package de.gieskerb.class_craft.classes

import org.bukkit.Material
import org.bukkit.entity.EntityType

object ClassSpecificLUT {
    var warriorEntities: ArrayList<EntityPair>? = null
        get() {
            if (field == null) {
                field = ArrayList()
                field!!.add(EntityPair(EntityType.BLAZE, 20))
                field!!.add(EntityPair(EntityType.BOGGED, 20))
                field!!.add(EntityPair(EntityType.BREEZE, 20))
                field!!.add(EntityPair(EntityType.CAVE_SPIDER, 20))
                field!!.add(EntityPair(EntityType.CREEPER, 20))
                field!!.add(EntityPair(EntityType.DROWNED, 20))
                field!!.add(EntityPair(EntityType.ELDER_GUARDIAN, 20))
                field!!.add(EntityPair(EntityType.ENDER_DRAGON, 20))
                field!!.add(EntityPair(EntityType.ENDERMAN, 20))
                field!!.add(EntityPair(EntityType.ENDERMITE, 20))
                field!!.add(EntityPair(EntityType.EVOKER, 20))
                field!!.add(EntityPair(EntityType.GHAST, 20))
                field!!.add(EntityPair(EntityType.GUARDIAN, 20))
                field!!.add(EntityPair(EntityType.HOGLIN, 20))
                field!!.add(EntityPair(EntityType.HUSK, 20))
                field!!.add(EntityPair(EntityType.MAGMA_CUBE, 20))
                field!!.add(EntityPair(EntityType.PHANTOM, 20))
                field!!.add(EntityPair(EntityType.PIGLIN, 20))
                field!!.add(EntityPair(EntityType.PIGLIN_BRUTE, 20))
                field!!.add(EntityPair(EntityType.PILLAGER, 20))
                field!!.add(EntityPair(EntityType.RAVAGER, 20))
                field!!.add(EntityPair(EntityType.SHULKER, 20))
                field!!.add(EntityPair(EntityType.SILVERFISH, 20))
                field!!.add(EntityPair(EntityType.SKELETON, 20))
                field!!.add(EntityPair(EntityType.SLIME, 20))
                field!!.add(EntityPair(EntityType.SPIDER, 20))
                field!!.add(EntityPair(EntityType.STRAY, 20))
                field!!.add(EntityPair(EntityType.VEX, 20))
                field!!.add(EntityPair(EntityType.VINDICATOR, 20))
                field!!.add(EntityPair(EntityType.WARDEN, 20))
                field!!.add(EntityPair(EntityType.WITCH, 20))
                field!!.add(EntityPair(EntityType.WITHER, 20))
                field!!.add(EntityPair(EntityType.WITHER_SKELETON, 20))
                field!!.add(EntityPair(EntityType.ZOGLIN, 20))
                field!!.add(EntityPair(EntityType.ZOMBIE, 20))
                field!!.add(EntityPair(EntityType.ZOMBIE_VILLAGER, 20))
                field!!.add(EntityPair(EntityType.ZOMBIFIED_PIGLIN, 20))
            }
            return field
        }
        private set

    var minerBlocks: MutableList<MaterialPair>? = null
        get() {
            if (field == null) {
                field = ArrayList()
                field!!.add(MaterialPair(Material.SANDSTONE, 20))
                field!!.add(MaterialPair(Material.RED_SANDSTONE, 20))
                field!!.add(MaterialPair(Material.STONE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE, 20))
                field!!.add(MaterialPair(Material.GRANITE, 20))
                field!!.add(MaterialPair(Material.DIORITE, 20))
                field!!.add(MaterialPair(Material.ANDESITE, 20))
                field!!.add(MaterialPair(Material.CALCITE, 20))
                field!!.add(MaterialPair(Material.TUFF, 20))
                field!!.add(MaterialPair(Material.DRIPSTONE_BLOCK, 20))
                field!!.add(MaterialPair(Material.MAGMA_BLOCK, 20))
                field!!.add(MaterialPair(Material.OBSIDIAN, 20))
                field!!.add(MaterialPair(Material.NETHERRACK, 20))
                field!!.add(MaterialPair(Material.CRIMSON_NYLIUM, 20))
                field!!.add(MaterialPair(Material.WARPED_NYLIUM, 20))
                field!!.add(MaterialPair(Material.BLACKSTONE, 20))
                field!!.add(MaterialPair(Material.BASALT, 20))
                field!!.add(MaterialPair(Material.SMOOTH_BASALT, 20))
                field!!.add(MaterialPair(Material.END_STONE, 20))
                field!!.add(MaterialPair(Material.COAL_ORE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE_COAL_ORE, 20))
                field!!.add(MaterialPair(Material.IRON_ORE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE_IRON_ORE, 20))
                field!!.add(MaterialPair(Material.COPPER_ORE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE_COPPER_ORE, 20))
                field!!.add(MaterialPair(Material.GOLD_ORE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE_GOLD_ORE, 20))
                field!!.add(MaterialPair(Material.REDSTONE_ORE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE_REDSTONE_ORE, 20))
                field!!.add(MaterialPair(Material.EMERALD_ORE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE_EMERALD_ORE, 20))
                field!!.add(MaterialPair(Material.LAPIS_ORE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE_LAPIS_ORE, 20))
                field!!.add(MaterialPair(Material.DIAMOND_ORE, 20))
                field!!.add(MaterialPair(Material.DEEPSLATE_DIAMOND_ORE, 20))
                field!!.add(MaterialPair(Material.NETHER_GOLD_ORE, 20))
                field!!.add(MaterialPair(Material.NETHER_QUARTZ_ORE, 20))
                field!!.add(MaterialPair(Material.ANCIENT_DEBRIS, 20))
                field!!.add(MaterialPair(Material.AMETHYST_CLUSTER, 20))
                field!!.add(MaterialPair(Material.AMETHYST_BLOCK, 20))
            }
            return field
        }
        private set

    var lumberjackBlocks: MutableList<MaterialPair>? = null
        get() {
            if (field == null) {
                field = ArrayList()
                field!!.add(MaterialPair(Material.OAK_LOG, 20))
                field!!.add(MaterialPair(Material.SPRUCE_LOG, 20))
                field!!.add(MaterialPair(Material.BIRCH_LOG, 20))
                field!!.add(MaterialPair(Material.JUNGLE_LOG, 20))
                field!!.add(MaterialPair(Material.ACACIA_LOG, 20))
                field!!.add(MaterialPair(Material.DARK_OAK_LOG, 20))
                field!!.add(MaterialPair(Material.MANGROVE_LOG, 20))
                field!!.add(MaterialPair(Material.CHERRY_LOG, 20))
                field!!.add(MaterialPair(Material.MUSHROOM_STEM, 20))
                field!!.add(MaterialPair(Material.CRIMSON_STEM, 20))
                field!!.add(MaterialPair(Material.WARPED_STEM, 20))
                field!!.add(MaterialPair(Material.OAK_LEAVES, 20))
                field!!.add(MaterialPair(Material.SPRUCE_LEAVES, 20))
                field!!.add(MaterialPair(Material.BIRCH_LEAVES, 20))
                field!!.add(MaterialPair(Material.JUNGLE_LEAVES, 20))
                field!!.add(MaterialPair(Material.ACACIA_LEAVES, 20))
                field!!.add(MaterialPair(Material.DARK_OAK_LEAVES, 20))
                field!!.add(MaterialPair(Material.MANGROVE_LEAVES, 20))
                field!!.add(MaterialPair(Material.CHERRY_LEAVES, 20))
                field!!.add(MaterialPair(Material.AZALEA_LEAVES, 20))
                field!!.add(MaterialPair(Material.FLOWERING_AZALEA_LEAVES, 20))
                field!!.add(MaterialPair(Material.BROWN_MUSHROOM_BLOCK, 20))
                field!!.add(MaterialPair(Material.RED_MUSHROOM_BLOCK, 20))
                field!!.add(MaterialPair(Material.NETHER_WART_BLOCK, 20))
                field!!.add(MaterialPair(Material.WARPED_WART_BLOCK, 20))
            }
            return field
        }
        private set
    var farmerBlocks: MutableList<MaterialPair>? = null
        get() {
            if (field == null) {
                field = ArrayList()
                field!!.add(MaterialPair(Material.BROWN_MUSHROOM, 20))
                field!!.add(MaterialPair(Material.RED_MUSHROOM, 20))
                field!!.add(MaterialPair(Material.CRIMSON_FUNGUS, 20))
                field!!.add(MaterialPair(Material.WARPED_FUNGUS, 20))
                field!!.add(MaterialPair(Material.SHORT_GRASS, 20))
                field!!.add(MaterialPair(Material.FERN, 20))
                field!!.add(MaterialPair(Material.DANDELION, 20))
                field!!.add(MaterialPair(Material.POPPY, 20))
                field!!.add(MaterialPair(Material.BLUE_ORCHID, 20))
                field!!.add(MaterialPair(Material.ALLIUM, 20))
                field!!.add(MaterialPair(Material.AZURE_BLUET, 20))
                field!!.add(MaterialPair(Material.RED_TULIP, 20))
                field!!.add(MaterialPair(Material.ORANGE_TULIP, 20))
                field!!.add(MaterialPair(Material.WHITE_TULIP, 20))
                field!!.add(MaterialPair(Material.PINK_TULIP, 20))
                field!!.add(MaterialPair(Material.OXEYE_DAISY, 20))
                field!!.add(MaterialPair(Material.CORNFLOWER, 20))
                field!!.add(MaterialPair(Material.LILY_OF_THE_VALLEY, 20))
                field!!.add(MaterialPair(Material.TORCHFLOWER, 20))
                field!!.add(MaterialPair(Material.WITHER_ROSE, 20))
                field!!.add(MaterialPair(Material.PINK_PETALS, 20))
                field!!.add(MaterialPair(Material.SPORE_BLOSSOM, 20))
                field!!.add(MaterialPair(Material.BAMBOO, 20))
                field!!.add(MaterialPair(Material.SUGAR_CANE, 20))
                field!!.add(MaterialPair(Material.CACTUS, 20))
                field!!.add(MaterialPair(Material.TALL_GRASS, 20))
                field!!.add(MaterialPair(Material.LARGE_FERN, 20))
                field!!.add(MaterialPair(Material.SUNFLOWER, 20))
                field!!.add(MaterialPair(Material.LILAC, 20))
                field!!.add(MaterialPair(Material.ROSE_BUSH, 20))
                field!!.add(MaterialPair(Material.PEONY, 20))
                field!!.add(MaterialPair(Material.CHORUS_PLANT, 20))
                field!!.add(MaterialPair(Material.CHORUS_FLOWER, 20))
                field!!.add(MaterialPair(Material.WHEAT_SEEDS, 20))
                field!!.add(MaterialPair(Material.CARROT, 20))
                field!!.add(MaterialPair(Material.POTATO, 20))
                field!!.add(MaterialPair(Material.COCOA_BEANS, 20))
                field!!.add(MaterialPair(Material.PUMPKIN_SEEDS, 20))
                field!!.add(MaterialPair(Material.MELON_SEEDS, 20))
                field!!.add(MaterialPair(Material.BEETROOT_SEEDS, 20))
                field!!.add(MaterialPair(Material.CAVE_VINES, 20)) // Glow-Berries
                field!!.add(MaterialPair(Material.SWEET_BERRY_BUSH, 20))
                field!!.add(MaterialPair(Material.NETHER_WART, 20))
                field!!.add(MaterialPair(Material.MELON, 20))
                field!!.add(MaterialPair(Material.PUMPKIN, 20))
            }
            return field
        }
        private set
    var farmerEntities: MutableList<EntityPair>? = null
        get() {
            if (field == null) {
                field = ArrayList()
                field!!.add(EntityPair(EntityType.CHICKEN, 20))
                field!!.add(EntityPair(EntityType.COD, 20))
                field!!.add(EntityPair(EntityType.COW, 20))
                field!!.add(EntityPair(EntityType.DONKEY, 20))
                field!!.add(EntityPair(EntityType.GLOW_SQUID, 20))
                field!!.add(EntityPair(EntityType.HORSE, 20))
                field!!.add(EntityPair(EntityType.MOOSHROOM, 20))
                field!!.add(EntityPair(EntityType.PIG, 20))
                field!!.add(EntityPair(EntityType.RABBIT, 20))
                field!!.add(EntityPair(EntityType.SALMON, 20))
                field!!.add(EntityPair(EntityType.SHEEP, 20))
                field!!.add(EntityPair(EntityType.SQUID, 20))
                field!!.add(EntityPair(EntityType.TROPICAL_FISH, 20))
                field!!.add(EntityPair(EntityType.TURTLE, 20))
            }
            return field
        }
        private set
    private var ageTable: HashMap<Material, Int>? = null

    fun getMaxAge(material: Material): Int {
        if (ageTable == null) {
            ageTable = HashMap.newHashMap(10)
            ageTable!![Material.WHEAT_SEEDS] = 7
            ageTable!![Material.CARROT] = 7
            ageTable!![Material.POTATO] = 7
            ageTable!![Material.PUMPKIN_SEEDS] = 7
            ageTable!![Material.MELON_SEEDS] = 7
            ageTable!![Material.BEETROOT_SEEDS] = 3
            ageTable!![Material.NETHER_WART] = 3
            ageTable!![Material.COCOA] = 2
        }
        return ageTable!!.getOrDefault(material, 0)
    }

    @JvmRecord
    data class EntityPair(val type: EntityType, val xpValue: Int) {
        constructor(type: EntityType) : this(type, 0)

        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o !is EntityPair) return false
            return type == o.type
        }

        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + xpValue
            return result
        }
    }

    @JvmRecord
    data class MaterialPair(val type: Material, val xpValue: Int) {
        constructor(type: Material) : this(type, 0)

        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o !is MaterialPair) return false
            return type == o.type
        }

        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + xpValue
            return result
        }
    }
}