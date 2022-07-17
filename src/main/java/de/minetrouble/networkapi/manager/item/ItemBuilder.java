package de.minetrouble.networkapi.manager.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * @author KeinByte
 * @since 03.07.2022
 */
public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material){
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setName(String name){
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setLore(String... lore){
        for (String strings : lore){
            strings.replaceAll("&", "§");
        }
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setUnbreakable(){
        itemMeta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder setHideEnchantment(){
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setDamage(int damage) {
        ((Damageable) itemMeta).setDamage(damage);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
