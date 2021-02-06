package org.gethydra.tweaker.patches;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;

import java.net.HttpURLConnection;

public class HttpURLConnectionPatch extends RuntimePatch
{
    public void apply()
    {
        new ByteBuddy().redefine(HttpURLConnection.class).visit(Advice.to(HttpURLConnectionAdvice.class).on(ElementMatchers.named("getResponseCode"))).make().load(HttpURLConnection.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }
}
