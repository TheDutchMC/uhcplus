package nl.thedutchmc.uhcplus.modules.modules.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import nl.thedutchmc.uhcplus.UhcPlus;

public class RecipeSticksFromLogs {

	public static NamespacedKey stickOakKey, stickSpruceKey, stickBirchKey, stickJungleKey, stickAcaciaKey, stickDarkOakKey;
	public static boolean stickOakRegistered, stickSpruceRegistered, stickBirchRegistered, stickJungleRegistered, stickAcaciaRegistered, stickDarkOakRegistered;
	
	public void setupRecipe() {
		if(!stickOakRegistered)
			Bukkit.getServer().addRecipe(getSticksFromLogsOak());
		
		if(!stickSpruceRegistered)
			Bukkit.getServer().addRecipe(getSticksFromLogsSpruce());
		
		if(!stickBirchRegistered)
			Bukkit.getServer().addRecipe(getSticksFromLogsBirch());
		
		if(!stickJungleRegistered)
			Bukkit.getServer().addRecipe(getSticksFromLogsJungle());
		
		if(!stickAcaciaRegistered)
			Bukkit.getServer().addRecipe(getSticksFromLogsAcacia());
		
		if(!stickDarkOakRegistered)
			Bukkit.getServer().addRecipe(getSticksFromLogsDarkOak());
	}
	
	public void removeRecipe() {
		//TODO Remove recipes
	}
	
	public ShapedRecipe getSticksFromLogsOak() {
		stickOakRegistered = true;
		
		stickOakKey = new NamespacedKey(UhcPlus.INSTANCE, "stick_oak");
		ShapedRecipe recipe = getStickBaseRecipe(stickOakKey);
		
		recipe.setIngredient('l', Material.OAK_LOG);
		return recipe;
		
	}
	
	public ShapedRecipe getSticksFromLogsSpruce() {
		stickSpruceRegistered = true;
		
		stickSpruceKey = new NamespacedKey(UhcPlus.INSTANCE, "stick_spruce");
		ShapedRecipe recipe = getStickBaseRecipe(stickSpruceKey);
		
		recipe.setIngredient('l', Material.SPRUCE_LOG);
		return recipe;
		
	}
	
	public ShapedRecipe getSticksFromLogsBirch() {
		stickBirchRegistered = true;

		stickBirchKey = new NamespacedKey(UhcPlus.INSTANCE, "stick_birch");
		ShapedRecipe recipe = getStickBaseRecipe(stickBirchKey);
		
		recipe.setIngredient('l', Material.BIRCH_LOG);
		return recipe;
		
	}
	
	public ShapedRecipe getSticksFromLogsJungle() {
		stickJungleRegistered = true;
		
		stickJungleKey = new NamespacedKey(UhcPlus.INSTANCE, "stick_jungle");
		ShapedRecipe recipe = getStickBaseRecipe(stickJungleKey);
		recipe.setIngredient('l', Material.JUNGLE_LOG);
		return recipe;
		
	}
	
	public ShapedRecipe getSticksFromLogsAcacia() {
		stickAcaciaRegistered = true;
		
		stickAcaciaKey = new NamespacedKey(UhcPlus.INSTANCE, "stick_acacia");
		ShapedRecipe recipe = getStickBaseRecipe(stickAcaciaKey);
		recipe.setIngredient('l', Material.ACACIA_LOG);
		return recipe;
		
	}
	
	public ShapedRecipe getSticksFromLogsDarkOak() {
		stickDarkOakRegistered = true;
		
		stickDarkOakKey = new NamespacedKey(UhcPlus.INSTANCE, "stick_dark_oak");
		ShapedRecipe recipe = getStickBaseRecipe(stickDarkOakKey);
		recipe.setIngredient('l', Material.DARK_OAK_LOG);
		return recipe;
		
	}
	
	private ShapedRecipe getStickBaseRecipe(NamespacedKey key) {
		ItemStack stack = new ItemStack(Material.STICK, 16);
		ShapedRecipe recipe = new ShapedRecipe(key, stack);
		recipe.shape("l", "l");
		
		return recipe;
	}
}
