package nl.thedutchmc.uhcplus.uhc;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import nl.thedutchmc.uhcplus.UhcPlus;

public class Recipes {

	private UhcPlus plugin;
	
	public Recipes(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public static NamespacedKey lightGoldenAppleKey, lightAnvilKey;
	
	public ShapedRecipe getLightGoldenAppleRecipe() {
		
		ItemStack stack = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Light Golden Apple");
		stack.setItemMeta(meta);
		
		lightGoldenAppleKey = new NamespacedKey(plugin, "light_golden_apple");
		ShapedRecipe recipe = new ShapedRecipe(lightGoldenAppleKey, stack);
		recipe.shape("xgx", "gag", "xgx");
		recipe.setIngredient('x', Material.AIR);
		recipe.setIngredient('g', Material.GOLD_INGOT);
		recipe.setIngredient('a', Material.APPLE);
		
		return recipe;
	}
	
	public ShapedRecipe getLightAnvilRecipe() {
		ItemStack stack = new ItemStack(Material.ANVIL);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Light Anvil");
		stack.setItemMeta(meta);
		
		lightAnvilKey = new NamespacedKey(plugin, "light_anvil");
		ShapedRecipe recipe = new ShapedRecipe(lightAnvilKey, stack);
		
		recipe.shape("iii", "xbx", "iii");
		recipe.setIngredient('i', Material.IRON_INGOT);
		recipe.setIngredient('x', Material.AIR);
		recipe.setIngredient('b', Material.IRON_BLOCK);
		
		return recipe;
	}
}
