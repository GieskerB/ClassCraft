package de.gieskerb.class_craft.classes;

import de.gieskerb.class_craft.data.HorseData;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FarmerClass extends BaseClass {

    public final static String CLASS_NAME = "Farmer";
    private static ItemStack displayItem = null;

    public FarmerClass(Player player) {
        super(FarmerClass.CLASS_NAME, player);
    }

    public FarmerClass(JSONObject json, Player player) {
        super(json, player);
    }

    public static ItemStack getDisplayItem() {
        if (displayItem == null) {
            displayItem = new ItemStack(Material.NETHERITE_HOE);
            var itemMeta = displayItem.getItemMeta();
            assert itemMeta != null;
            itemMeta.setItemName("§6§lFarmer-Class");
            List<String> loreList = new ArrayList<>();
            loreList.add("§r§2super is a lore line!");
            loreList.add("§r§2And super is anoter one.");
            itemMeta.setLore(loreList);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            displayItem.setItemMeta(itemMeta);
        }
        return displayItem;
    }

    @Override
    public void reapplyRewardEffects() {

    }

    @Override
    public void removePermanentEffects() {

    }

    @Override
    public ItemStack getClassItem() {
        return getDisplayItem();
    }

    @Override
    protected HorseData getHorseData() {
        return new HorseData(Horse.Color.values()[(int) (Math.random() * Horse.Color.values().length)],
                             Horse.Style.values()[(int) (Math.random() * Horse.Style.values().length)],
                             0.3,
                             0.6,
                             60,
                             HorseData.getHorseName(super.playerReference, super.CLASS_NAME),
                             Material.DIAMOND_HORSE_ARMOR);
    }

    @Override
    protected void giveFirstToolReward() {
        ItemStack item = new ItemStack(Material.STONE_SWORD);
        item.addUnsafeEnchantment(Enchantment.SHARPNESS, 1);
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);
        super.playerReference.getInventory().addItem(item);
    }

    @Override
    protected void giveSecondToolReward() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        item.addUnsafeEnchantment(Enchantment.SHARPNESS, 3);
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 2);
        item.addUnsafeEnchantment(Enchantment.LOOTING, 1);
        super.playerReference.getInventory().addItem(item);
    }

    @Override
    protected void giveThirdToolReward() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        item.addUnsafeEnchantment(Enchantment.SHARPNESS, 5);
        item.addUnsafeEnchantment(Enchantment.SMITE, 5);
        item.addUnsafeEnchantment(Enchantment.BANE_OF_ARTHROPODS, 5);
        super.playerReference.getInventory().addItem(item);
    }

    @Override
    protected void giveBannerReward() {
        ItemStack item = new ItemStack(Material.GRAY_BANNER);
        BannerMeta m = (BannerMeta) item.getItemMeta();

        List<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern(DyeColor.RED, PatternType.FLOWER));
        patterns.add(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
        patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
        patterns.add(new Pattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE));

        m.setPatterns(patterns);

        item.setItemMeta(m);
        super.playerReference.getInventory().addItem(item);
    }
}
