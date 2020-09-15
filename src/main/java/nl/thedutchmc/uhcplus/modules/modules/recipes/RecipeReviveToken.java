package nl.thedutchmc.uhcplus.modules.modules.recipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import nl.thedutchmc.uhcplus.UhcPlus;

public class RecipeReviveToken {

	public static NamespacedKey reviveTokenKey;
	public static boolean reviveTokenRegistered;
	
	public ShapedRecipe getReviveTokenRecipe() {
		reviveTokenRegistered = true;

		ItemStack stack = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Revive Token");
		stack.setItemMeta(meta);
		
		stack.addUnsafeEnchantment(Enchantment.MENDING, 1);

		reviveTokenKey = new NamespacedKey(UhcPlus.INSTANCE, "revive_token");
		ShapedRecipe recipe = new ShapedRecipe(reviveTokenKey, stack);
		recipe.shape("ogo", "gdg", "ogo");
		recipe.setIngredient('o', Material.OBSIDIAN);
		recipe.setIngredient('g', Material.GOLD_BLOCK);
		recipe.setIngredient('d', Material.DIAMOND_BLOCK);

		return recipe;
	}
}
