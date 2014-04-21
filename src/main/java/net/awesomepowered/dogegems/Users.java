package net.awesomepowered.dogegems;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Users {

    public static File userFolder;
    public static File userFile;
    public static FileConfiguration userData;
    public static long epoch = System.currentTimeMillis()/1000;

    public static String manageUser(String player, boolean getUserAddress, boolean setUserAddress, boolean addTransactionHistory, boolean setCanCommandTime, boolean getCanCommandTime, String pathData) {
        userFolder = new File(Bukkit.getPluginManager().getPlugin("DogeGems").getDataFolder(), "Users");
        userFile = new File(userFolder, player + ".yml");
        userData = new YamlConfiguration();

        if (!userFolder.exists()) {
            userFolder.mkdirs();
            System.out.println(DogeGems.prefix + "Created User Folders");
        } if (!userFile.exists()) {
             try {
                 userFile.createNewFile();
                 userData.load(userFile);
                 userData.set("User", player);
                 userData.set("DepositAddress", "DQGxZhN7SMaTp9SehNjjbXURLXexMNG6wb");
                 userData.set("CanCommandTime", "1337");
                 userData.save(userFile);
                 System.out.println(DogeGems.prefix + "Created " + userFile.toString() + " file");
             } catch (IOException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (InvalidConfigurationException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             }
        }

        if (getUserAddress) {
            try {
                userData.load(userFile);
            } catch (IOException e) {
                throw new DogeException("Failed to load getUserAddress ",e);
            } catch (InvalidConfigurationException e) {
                throw new DogeException("Failed to load getUserAddress ",e);
            }
            return userData.getString("DepositAddress");
        }

        if (setUserAddress) {
            try {
            userData.load(userFile);
            userData.set("DepositAddress", pathData);
            userData.save(userFile);
            } catch (IOException e) {
                throw new DogeException("Failed to load setUserAddress ",e);
            } catch (InvalidConfigurationException e) {
                throw new DogeException("Failed to load setUserAddress ",e);
            }
        }

        if (addTransactionHistory) {
            try {
                userData.load(userFile);
                userData.getStringList("Transactions").add(pathData+"-"+epoch);
                userData.save(userFile);
            } catch (IOException e) {
                throw new DogeException("Failed to load addTransactionHistory ",e);
            } catch (InvalidConfigurationException e) {
                throw new DogeException("Failed to load addTransactionHistory ",e);
            }
            //add Epoch time as transaction time stamp.
        }

        if (setCanCommandTime) {
            try {
                userData.load(userFile);
                userData.set("canCommandTime", epoch+1200);
            } catch (IOException e) {
                throw new DogeException("Failed to set CanCommandTime ",e);
            } catch (InvalidConfigurationException e) {
                throw new DogeException("Failed to load addTransactionHistory ",e);
            }
        }

        if (getCanCommandTime) {
            try {
                userData.load(userFile);
            } catch (IOException e) {
                throw new DogeException("Failed to get CanCommandTime ",e);
            } catch (InvalidConfigurationException e) {
                throw new DogeException("Failed to get CanCommandTime ",e);
            }
            return userData.getString("CanCommandTime");
        }
        return null;
    }

    public static void exhangeToDoge(String player, Double amount) {
        EconomyResponse response = DogeGems.econ.depositPlayer(player, amount);
        if (response.transactionSuccess()) {
            System.out.println("[DogeGemsEcon] granted " + amount + " to " + player);
        } else {
            System.out.println("[DogeGemsEcon] Failed to grant " + amount + " to " + player);
        }
    }

}
