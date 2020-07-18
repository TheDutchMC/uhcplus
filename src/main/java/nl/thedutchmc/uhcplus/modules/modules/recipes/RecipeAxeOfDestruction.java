package nl.thedutchmc.uhcplus.modules.modules.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import nl.thedutchmc.uhcplus.UhcPlus;

public class RecipeAxeOfDestruction {

	public static NamespacedKey axeKey_1, axeKey_2;
	public static boolean axeRegistered;
	
	public ShapedRecipe getAxeOfDestructionRecipe() {
		axeRegistered = true;

		ItemStack stack = new ItemStack(Material.IRON_AXE);
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(ChatColor.RED + "Axe of Destruction");
		List<String> lores = new ArrayList<>();
		lores.add("Level 1");
		lores.add(UUID.randomUUID().toString());
		meta.setLore(lores);
		stack.setItemMeta(meta);

		stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
		stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		stack.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
		stack.addUnsafeEnchantment(Enchantment.DURABILITY, 2);

		axeKey_1 = new NamespacedKey(UhcPlus.INSTANCE, "axe_of_destruction_1");
		ShapedRecipe recipe = new ShapedRecipe(axeKey_1, stack);

		recipe.shape("xii", "xbi", "xbx");
		recipe.setIngredient('x', Material.AIR);
		recipe.setIngredient('i', Material.IRON_INGOT);
		recipe.setIngredient('b', Material.IRON_BLOCK);

		return recipe;
	}
}
