package nl.thedutchmc.uhcplus.modules.modules.kits;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class ItemStackSerializer {
	
	public static String getStringFromInventory(Inventory inventory) {
		return getStringFromStackList(Arrays.asList(inventory.getContents()));
	}

	public static String getStringFromStackList(List<ItemStack> contents) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			dataOutput.writeInt(contents.size());

			for (ItemStack stack : contents) {
				if(stack == null) continue;
				dataOutput.writeObject(stack);
			}
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	public static List<ItemStack> getStackListFromString(String data) {
		if (data == null || Base64Coder.decodeLines(data) == null)
			return new ArrayList<ItemStack>();

		ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
		BukkitObjectInputStream dataInput = null;
		ItemStack[] stacks = null;

		try {
			dataInput = new BukkitObjectInputStream(inputStream);
			stacks = new ItemStack[dataInput.readInt()];
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < stacks.length; i++) {
			try {
				stacks[i] = (ItemStack) dataInput.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			dataInput.close();
		} catch (IOException e1) {
		}

		return Arrays.asList(stacks);
	}

	public static String getStringFromStack(ItemStack stack) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			dataOutput.writeObject(stack);
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stack.", e);
		}
	}

	public static ItemStack getStackFromString(String data) {
		if (data == null || Base64Coder.decodeLines(data) == null)
			return new ItemStack(Material.AIR);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
		BukkitObjectInputStream dataInput = null;
		ItemStack stack = null;

		try {
			dataInput = new BukkitObjectInputStream(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			stack = (ItemStack) dataInput.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			dataInput.close();
		} catch (IOException e1) {
		}

		return stack;
	}
}
