package de.minetrouble.networkapi.manager.color.pattern;

import de.minetrouble.networkapi.manager.color.ColorBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author KeinByte
 * @since 08.07.2022
 */
public class RainbowPattern implements IPattern{

    Pattern pattern = Pattern.compile("<R([0-9]{1,3})>(.*?)</R>");

    public String process(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            text = text.replace(matcher.group(), ColorBuilder.rainbow(matcher.group(2), Float.parseFloat(matcher.group(1))));
        }
        return text;
    }

}
