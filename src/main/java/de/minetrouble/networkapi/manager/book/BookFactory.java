package de.minetrouble.networkapi.manager.book;

import de.minetrouble.networkapi.manager.reflection.ReflectionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Collection;

/**
 * @author KeinByte
 * @since 03.07.2022
 */
public class BookFactory {

    @Deprecated
    protected static void openBook(Player player, ItemStack bookItem){
        ItemStack itemInHand = player.getItemInHand();
        try {
            Class<?> packetDataSerializerClazz = ReflectionUtil.getNMSClass("PacketDataSerializer");
            Class<?> packetClazz = ReflectionUtil.getNMSClass("Packet");
            Object entityObject = player.getClass().getMethod("getHandle").invoke(player);
            player.setItemInHand(bookItem);
            Object packet = ReflectionUtil.getNMSClass("PacketPlayOutCustomPayload")
                    .getConstructor(new Class[] {String.class, packetDataSerializerClazz})
                    .newInstance(new Object[] {"MC|BOPen",
                    packetDataSerializerClazz.getConstructor(ByteBuf.class).newInstance(Unpooled.buffer())});
            Object playerConnection = entityObject.getClass().getField("playerConnection").get(entityObject);
            if (playerConnection != null) playerConnection.getClass().getMethod("sendPacket", packetClazz).invoke(playerConnection, packet);
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            player.setItemInHand(itemInHand);
        }
    }

    protected static ItemStack toItemStack(BookBuilder bookBuilder){
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setTitle(bookBuilder.getTitle());
        bookMeta.setAuthor(bookBuilder.getAuthor());
        try {
            Collection<Object> objects = (Collection<Object>) ReflectionUtil.getField(ReflectionUtil.getCraftClass("inventory.CraftMetaBook"), "pages").get(bookMeta);
            TextComponent textComponent = new TextComponent("");
            for (TextComponent component : bookBuilder.getLines()){
                textComponent.addExtra(component);
            }
            objects.add(toIChatBaseComponent(textComponent));
        }catch (Exception exception){
            exception.printStackTrace();
        }
        itemStack.setItemMeta(bookMeta);
        return itemStack;
    }

    private static Object toIChatBaseComponent(TextComponent tc) {
        try {
            Class<?> chatSerializer = ReflectionUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];

            return chatSerializer.getMethod("a", String.class).invoke(chatSerializer,
                    new Object[] { ComponentSerializer.toString(tc) });
        } catch (Exception exception) {

            return null;
        }
    }

}
