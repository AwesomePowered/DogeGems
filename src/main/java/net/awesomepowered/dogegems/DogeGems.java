package net.awesomepowered.dogegems;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DogeGems extends JavaPlugin implements Listener {

    /**
     This plugin is a simplified version of DogetTips
     Which can be found here: http://dev.bukkit.org/bukkit-plugins/dogetips/
     Author: spacerocket

     DogeGems is a plugin which allows any player to exchange DogeCoins for server economy points.
     Made for use at PoweredByAwesome Network by LaxWasHere.
     Documentation is a bitch so figure it out yourself.
     Need Help? Simply visit #Drtshock on Esper IRC network.

     TODO:
     x: Find a way to fucking multi thread httpsconnections at DogeAPIClient ** Important (Due to main thread freezing)
     x: Make messages pretty.
     x: Make everything configurable * After tests.
     x: Configurable formula for giving doge eg: x2 || /10
     x: Integrate DogeAPIClient to BlissAPI
     x: Release to github.

     **/

    public static String dogeSuperSecretAPIKeyThatMustAlwaysBeKeptSecret = "REPLACE_THIS_WITH_YOUR_API_KEY";
    public static String prefix = "[DogeGems] ";
    public static Economy econ = null;

    public void onEnable() {
        registerCommands();
        registerVaultEcon();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void registerCommands() {
        getCommand("checkdoge").setExecutor(new CommandCheckDoge());
        getCommand("doge2gem").setExecutor(new CommandDogeForGem());
        System.out.println(prefix + "Commands registered!");
    }

    public boolean registerVaultEcon() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println(prefix + "Vault plugin nicht found, Disabling plugin.");
            System.out.println(prefix + "Please install vault and restart the server.");
            getServer().getPluginManager().disablePlugin(this);
            {
                return false;
            }
        } else {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            System.out.println(prefix + "Hooked into vault!");
            if (rsp == null)
            {
                return false;
            }
            econ = rsp.getProvider();
            return econ != null;
        }
    }
}
