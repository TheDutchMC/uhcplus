package nl.thedutchmc.uhcplus.modules.modules.recipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import nl.thedutchmc.uhcplus.UhcPlus;

public class RecipeLightGoldenApple {

	public static NamespacedKey appleKey;
	public static boolean appleRegistered;
	
	public ShapedRecipe getLightGoldenAppleRecipe() {
		appleRegistered = true;

		ItemStack stack = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Light Golden Apple");
		stack.setItemMeta(meta);

		appleKey = new NamespacedKey(UhcPlus.INSTANCE, "light_golden_apple");
		ShapedRecipe recipe = new ShapedRecipe(appleKey, stack);
		recipe.shape("xgx", "gag", "xgx");
		recipe.setIngredient('x', Material.AIR);
		recipe.setIngredient('g', Material.GOLD_INGOT);
		recipe.setIngredient('a', Material.APPLE);

		return recipe;
	}
}
