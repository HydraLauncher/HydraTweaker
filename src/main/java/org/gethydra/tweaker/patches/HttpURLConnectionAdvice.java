package org.gethydra.tweaker.patches;

import net.bytebuddy.asm.Advice;

import javax.net.ssl.HttpsURLConnection;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionAdvice
{
    @Advice.OnMethodExit
    static void intercept(@Advice.This HttpURLConnection con, @Advice.Return(readOnly = false) int code)
    {
        if (con.getURL().toString().contains("/MinecraftSkins/"))
        {
            try
            {
                String url = con.getURL().toString();
                String username = (url.contains("/MinecraftSkins/")
                        ? url.substring(url.indexOf("/MinecraftSkins/"))
                        : url.substring(url.indexOf("/skin/")))
                        .replace("/MinecraftSkins/", "")
                        .replace("/skin/", "")
                        .replace(".png", "");

                HttpsURLConnection httpClient = (HttpsURLConnection) new URL("https://api.gethydra.org/cosmetics/skin?username=" + username).openConnection();
                httpClient.setRequestMethod("GET");
                httpClient.setRequestProperty("User-Agent", "Hydra/0");

                Field isField = con.getClass().getDeclaredField("inputStream");
                isField.setAccessible(true);
                isField.set(con, httpClient.getInputStream());

                code = 200;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (con.getURL().toString().contains("/MinecraftCloaks/")) {
            try
            {
                String url = con.getURL().toString();
                String username = (url.contains("/MinecraftCloaks/")
                        ? url.substring(url.indexOf("/MinecraftCloaks/"))
                        : url.substring(url.indexOf("/cloak/")))
                        .replace("/MinecraftCloaks/", "")
                        .replace("/cloak/", "")
                        .replace(".png", "");

                HttpsURLConnection httpClient = (HttpsURLConnection) new URL("https://api.gethydra.org/cosmetics/cloak?username=" + username).openConnection();
                httpClient.setRequestMethod("GET");
                httpClient.setRequestProperty("User-Agent", "Hydra/0");

                Field isField = con.getClass().getDeclaredField("inputStream");
                isField.setAccessible(true);
                isField.set(con, httpClient.getInputStream());

                code = 200;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (con.getURL().toString().contains("/MinecraftResources/")) {
            try
            {
                String url = con.getURL().toString().replace("http://s3.amazonaws.com/MinecraftResources/", "https://www.oldschoolminecraft.com/grabsound?k=");

                HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();
                httpClient.setRequestMethod("GET");
                httpClient.setRequestProperty("User-Agent", "Hydra/0");

                Field isField = con.getClass().getDeclaredField("inputStream");
                isField.setAccessible(true);
                isField.set(con, httpClient.getInputStream());

                code = 200;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
