package de.minetrouble.networkapi.manager.color.pattern;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author KeinByte
 * @since 08.07.2022
 */
public class SolidPattern implements IPattern{

    Pattern pattern = Pattern.compile("#[A-Fa-f0-9]{6}");

    public String process(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String color = text.substring(matcher.start(), matcher.end());
            text = text.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(text);
        }
        return text;
    }

}
