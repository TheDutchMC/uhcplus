package nl.thedutchmc.uhcplus.modules.modules.recipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import nl.thedutchmc.uhcplus.UhcPlus;

public class RecipeLightAnvil {

	public static NamespacedKey anvil;
	public static boolean anvilRegistered;
	
	public ShapedRecipe getLightAnvilRecipe() {
		anvilRegistered = true;

		ItemStack stack = new ItemStack(Material.ANVIL);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Light Anvil");
		stack.setItemMeta(meta);

		anvil = new NamespacedKey(UhcPlus.INSTANCE, "light_anvil");
		ShapedRecipe recipe = new ShapedRecipe(anvil, stack);

		recipe.shape("iii", "xbx", "iii");
		recipe.setIngredient('i', Material.IRON_INGOT);
		recipe.setIngredient('x', Material.AIR);
		recipe.setIngredient('b', Material.IRON_BLOCK);

		return recipe;
	}
}
