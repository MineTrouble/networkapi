package de.minetrouble.networkapi.manager.color.pattern;

import de.minetrouble.networkapi.manager.color.ColorBuilder;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author KeinByte
 * @since 08.07.2022
 */
public class GradientPattern implements IPattern{

    Pattern pattern = Pattern.compile("<G:#([0-9A-Fa-f]{6})>(.*?)</G:#([0-9A-Fa-f]{6})>");

    @Override
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()){
            String start = matcher.group(1);
            String end = matcher.group(3);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), ColorBuilder.color(
                    content,
                    new Color(Integer.parseInt(start, 16)),
                    new Color(Integer.parseInt(end, 16))));
        }
        return string;
    }
}
