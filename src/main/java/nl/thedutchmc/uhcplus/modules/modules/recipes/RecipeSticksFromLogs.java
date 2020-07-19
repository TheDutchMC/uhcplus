package nl.thedutchmc.uhcplus.modules.modules.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import nl.thedutchmc.uhcplus.UhcPlus;

public class RecipeSticksFromLogs {

	public static NamespacedKey stickKey;
	public static boolean stickRegistered;
	
	public void setupRecipe() {
		if(!stickRegistered) {
			Bukkit.getServer().addRecipe(getSticksRecipe());
		}
	}
	
	public void removeRecipe() {
		//TODO Remove recipes
	}
	
	public ShapedRecipe getSticksRecipe() {
		stickRegistered = true;
		
		stickKey = new NamespacedKey(UhcPlus.INSTANCE, "stick_oak");
		
		ShapedRecipe recipe = new ShapedRecipe(stickKey, new ItemStack(Material.STICK, 16));
		
        recipe.setIngredient('l', new RecipeChoice.MaterialChoice(Material.ACACIA_LOG, 
                Material.BIRCH_LOG, 
                Material.DARK_OAK_LOG, 
                Material.JUNGLE_LOG, 
                Material.OAK_LOG, 
                Material.SPRUCE_LOG));

        return recipe;
	}
}
