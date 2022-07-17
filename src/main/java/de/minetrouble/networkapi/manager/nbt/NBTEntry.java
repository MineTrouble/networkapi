package de.minetrouble.networkapi.manager.nbt;

import de.minetrouble.networkapi.manager.reflection.VersionEntry;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author KeinByte
 * @since 14.07.2022
 */
public class NBTEntry {

    public static ItemStack setNBTTag(ItemStack itemStack, String name, String value){
        if (itemStack == null) System.err.println("ItemStack == null");
        try {
            Class<?>[] classes = setupNbtClasses();
            Class<?> craftItemClass = classes[0];
            Class<?> nmsItemClass = classes[1];
            Class<?> nbtTagCompoundClass = classes[2];
            Method asNMSCopyMethod = craftItemClass.getDeclaredMethod("asNMSCopy", new Class[] { ItemStack.class });
            Object nmsItemObject = asNMSCopyMethod.invoke(null, new Object[] { itemStack });
            Method hasTagMethod = nmsItemClass.getDeclaredMethod("hasTag", new Class[0]);
            Method getTagMethod = nmsItemClass.getDeclaredMethod("getTag", new Class[0]);
            boolean hasTag = ((Boolean)hasTagMethod.invoke(nmsItemObject, new Object[0])).booleanValue();
            Object nbtTagCompoundObject = hasTag ? getTagMethod.invoke(nmsItemObject, new Object[0]) : nbtTagCompoundClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Method setStringMethod = nbtTagCompoundClass.getDeclaredMethod("setString", new Class[] { String.class, String.class });
            setStringMethod.invoke(nbtTagCompoundObject, new Object[] { name, value });
            nmsItemClass.getDeclaredMethod("setTag", new Class[] { nbtTagCompoundClass }).invoke(nmsItemObject, new Object[] { nbtTagCompoundObject });
            return (ItemStack)craftItemClass.getDeclaredMethod("asBukkitCopy", new Class[] { nmsItemClass }).invoke(null, new Object[] { nmsItemObject });
        }catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|InvocationTargetException|InstantiationException exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static String getNBTTag(ItemStack itemStack, String key) {
        if (itemStack == null) {
            System.out.println("ItemStack = null");
            return "null";
        }
        try {
            Class<?>[] classes = setupNbtClasses();
            Class<?> craftItemClass = classes[0];
            Class<?> nmsItemClass = classes[1];
            Class<?> nbtTagCompoundClass = classes[2];
            Method asNMSCopyMethod = craftItemClass.getDeclaredMethod("asNMSCopy", new Class[] { ItemStack.class });
            Object nmsItemObject = asNMSCopyMethod.invoke(null, new Object[] { itemStack });
            Method getTagMethod = nmsItemClass.getDeclaredMethod("getTag", new Class[0]);
            Object nbtTagCompoundObject = getTagMethod.invoke(nmsItemObject, new Object[0]);
            if (nbtTagCompoundObject != null) {
                Method getString = nbtTagCompoundClass.getDeclaredMethod("getString", new Class[] { String.class });
                return (String)getString.invoke(nbtTagCompoundObject, new Object[] { key });
            }
        } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        }
        return "null";
    }

    private static Class<?>[] setupNbtClasses() throws ClassNotFoundException {
        Class<?> nmsItemClass, nbtTagCompoundClass, craftItemClass = Class.forName("org.bukkit.craftbukkit." + VersionEntry.getBukkitPackage() + ".inventory.CraftItemStack");
        if (VersionEntry.versionNewer(16)) {
            nmsItemClass = Class.forName("net.minecraft.world.item.ItemStack");
            nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
        } else {
            nmsItemClass = Class.forName("net.minecraft.server." + VersionEntry.getBukkitPackage() + ".ItemStack");
            nbtTagCompoundClass = Class.forName("net.minecraft.server." + VersionEntry.getBukkitPackage() + ".NBTTagCompound");
        }
        return new Class[] { craftItemClass, nmsItemClass, nbtTagCompoundClass };
    }

}
