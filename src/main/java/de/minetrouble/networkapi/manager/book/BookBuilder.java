package de.minetrouble.networkapi.manager.book;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.List;

/**
 * @author KeinByte
 * @since 03.07.2022
 */
@Getter
public class BookBuilder {

    private String author;
    private String title;
    private List<TextComponent> lines = Lists.newArrayList();

    protected BookBuilder(String author){
        this.author = author;
    }

    public BookBuilder setTitle(String title){
        this.title = title;
        return this;
    }

    public BookBuilder addLine(String content){
        this.lines.add(new TextComponent(content));
        return this;
    }

    @Deprecated
    public BookBuilder addClickableLink(String textPrefix, String text, String hover, String link){
        TextComponent textComponent = new TextComponent(textPrefix);
        TextComponent clickablePart = new TextComponent(text);
        clickablePart.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hover)));
        clickablePart.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        textComponent.addExtra(clickablePart);
        this.lines.add(textComponent);
        return this;
    }

    @Deprecated
    public BookBuilder addClickableLine(String textPrefix, String text, String textSuffix, String hover, String command) {
        TextComponent textComponent = new TextComponent(textPrefix);
        TextComponent clickablePart = new TextComponent(text);
        clickablePart.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hover)));
        clickablePart.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command));
        textComponent.addExtra(clickablePart);
        textComponent.addExtra(new TextComponent(textSuffix));

        this.lines.add(textComponent);

        return this;
    }

    public void open(Player player){
        //BookFactory.openBook(player, BookFactory);
    }

}
