package nl.thedutchmc.uhcplus.uhc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import nl.thedutchmc.uhcplus.UhcPlus;

public class Recipes {

	private UhcPlus plugin;
	
	public Recipes(UhcPlus plugin) {
		this.plugin = plugin;
	}
	
	public static NamespacedKey lightGoldenAppleKey, lightAnvilKey, axeOfDestructionKey;
	public static boolean lightGoldenAppleRegistered, lightAnvilRegistered, axeOfDestructionRegistered;
	
	public ShapedRecipe getLightGoldenAppleRecipe() {
		lightGoldenAppleRegistered = true;
		
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
		lightAnvilRegistered = true;
		
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
	
	public ShapedRecipe getAxeOfDestructionRecipe() {
		axeOfDestructionRegistered = true;
		
		ItemStack stack = new ItemStack(Material.IRON_AXE);
		ItemMeta meta = stack.getItemMeta();
		
		meta.setDisplayName(ChatColor.RED + "Axe of Destruction");
		stack.setItemMeta(meta);
		
		stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
		stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		stack.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
		stack.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
		
		axeOfDestructionKey = new NamespacedKey(plugin, "axe_of_destruction");
		ShapedRecipe recipe = new ShapedRecipe(axeOfDestructionKey, stack);
		
		recipe.shape("xii", "xbi", "xbx");
		recipe.setIngredient('x', Material.AIR);
		recipe.setIngredient('i', Material.IRON_INGOT);
		recipe.setIngredient('b', Material.IRON_BLOCK);
		
		return recipe;
	}
}
