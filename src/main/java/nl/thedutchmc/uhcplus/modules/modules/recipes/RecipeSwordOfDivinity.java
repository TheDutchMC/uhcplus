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

public class RecipeSwordOfDivinity {

	public static NamespacedKey swordKey_1, swordKey_2, swordKey_3;
	public static boolean swordRegistered;

	public ShapedRecipe getSwordOfDivinity() {
		swordRegistered = true;

		ItemStack stack = new ItemStack(Material.IRON_SWORD);
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Sword of Divinity");
		List<String> lores = new ArrayList<>();
		lores.add("Level 1");
		lores.add(UUID.randomUUID().toString());
		meta.setLore(lores);
		stack.setItemMeta(meta);

		stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
		stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		stack.addUnsafeEnchantment(Enchantment.DURABILITY, 2);

		swordKey_1 = new NamespacedKey(UhcPlus.INSTANCE, "sword_of_divinity");
		ShapedRecipe recipe = new ShapedRecipe(swordKey_1, stack);

		recipe.shape("xbx", "xbx", "xix");
		recipe.setIngredient('x', Material.AIR);
		recipe.setIngredient('i', Material.IRON_INGOT);
		recipe.setIngredient('b', Material.IRON_BLOCK);

		return recipe;
	}
	

}
