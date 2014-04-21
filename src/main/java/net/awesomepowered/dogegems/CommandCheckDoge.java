package net.awesomepowered.dogegems;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCheckDoge implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String CommandLabel, String[] args) {
        sender.sendMessage(ChatColor.GREEN + "Checking your doge. . . ");
        DogeAPIClient api = new DogeAPIClient(DogeGems.dogeSuperSecretAPIKeyThatMustAlwaysBeKeptSecret);
        String depositAddress = Users.manageUser(sender.getName(), true, false, false, false, false, null);
        if (!depositAddress.equalsIgnoreCase("DQGxZhN7SMaTp9SehNjjbXURLXexMNG6wb")) {
        System.out.println(DogeGems.prefix + "Checking doge sent by " + sender.getName());
        System.out.println(DogeGems.prefix + "Using Address "  + depositAddress);
        String amountSent = api.checkDogeFromAddress(depositAddress);
        if (Double.valueOf(amountSent) <= 0) {
            sender.sendMessage(ChatColor.AQUA + "Please wait a little bit more until the system has done processing your coins.");
            sender.sendMessage(ChatColor.RED + "Please do not use the /doge4gem command until you have recieved your gems");
            System.out.println(DogeGems.prefix + "No deposits found.");
        } if (Double.valueOf(amountSent) > 0) {
            System.out.println(DogeGems.prefix + "Recieved " + amountSent + " from " + sender.getName());
            System.out.println(DogeGems.prefix + "Exchanging " + amountSent + "doge for gems");
            Users.exhangeToDoge(sender.getName(), Double.valueOf(amountSent));
            Users.manageUser(sender.getName(), false, false, true, false, false, depositAddress+"-"+amountSent);
            Users.manageUser(sender.getName(), false, true, false, false, false, "DQGxZhN7SMaTp9SehNjjbXURLXexMNG6wb");
        }
        } else {
           sender.sendMessage(ChatColor.RED + "Please generate an address by doing /doge4gem");
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
