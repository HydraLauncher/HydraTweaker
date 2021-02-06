package org.gethydra.tweaker;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.gethydra.tweaker.patches.HttpURLConnectionPatch;

import java.io.File;
import java.util.List;

public class HydraTweaker implements ITweaker
{
    public static File gameDir;
    public static File assetsDir;
    private List<String> args;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile)
    {
        this.args = args;
        HydraTweaker.gameDir = gameDir;
        HydraTweaker.assetsDir = assetsDir;
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader)
    {
        ByteBuddyAgent.install();
        new HttpURLConnectionPatch().apply();
    }

    @Override
    public String getLaunchTarget()
    {
        return "net.minecraft.client.Minecraft";
    }

    @Override
    public String[] getLaunchArguments()
    {
        return this.args.toArray(new String[this.args.size()]);
    }
}
