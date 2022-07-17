package de.minetrouble.networkapi.manager.discord;

import de.minetrouble.networkapi.NetworkApi;

import java.lang.reflect.Field;

/**
 * @author KeinByte
 * @since 14.07.2022
 */
public enum WebHookLogType {

    ADMIN_WEB_HOOK_LOG("https://discord.com/api/webhooks/997199754331176980/eXzDgMaeEIasvALYkTOrvJQuDdX22hxe14CR_pc0MWnSFynGaKI_JL5dap52Gft5YTP0");

    private final String fieldName;

    WebHookLogType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getUrl() {
        WebHookConfig webHookConfig = NetworkApi.getNetworkApi().getWebHookConfig();
        if (webHookConfig != null)
            try {
                Field declaredField = webHookConfig.getClass().getDeclaredField(this.fieldName);
                declaredField.setAccessible(true);
                Object value = declaredField.get(webHookConfig);
                if (value instanceof String)
                    return (String)value;
            } catch (NoSuchFieldException|IllegalAccessException noSuchFieldException) {}
        return null;
    }

}
