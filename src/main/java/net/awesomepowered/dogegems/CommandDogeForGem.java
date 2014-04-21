package net.awesomepowered.dogegems;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDogeForGem implements CommandExecutor {

    public static long epoch = System.currentTimeMillis()/1000;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String CommandLabel, String[] args) {
        if (epoch < Long.valueOf(Users.manageUser(sender.getName(), false, false, false, false, true, null))) {
          sender.sendMessage("You must wait 20 minutes before generating a new address.");
        } else {
        sender.sendMessage(ChatColor.GREEN + "Generating address . . .");
        DogeAPIClient api = new DogeAPIClient(DogeGems.dogeSuperSecretAPIKeyThatMustAlwaysBeKeptSecret);
        String depAddress = api.getLabledAddress(sender.getName()+epoch);
        Users.manageUser(sender.getName(), false, true, false, false, false, depAddress);
        Users.manageUser(sender.getName(), false, false, false, true, false, null);
        System.out.print(DogeGems.prefix + "Generated a new deposit address for " + sender.getName());
        System.out.println(DogeGems.prefix + "Address: " + depAddress);
        sender.sendMessage(">");
        sender.sendMessage("Please send DogeCoins to: http://remove.com/" + depAddress);
        sender.sendMessage("Wait a little bit and type /checkdoge");
        sender.sendMessage("The doge will automatically coverted to your econ balance");
        sender.sendMessage(">");
        } return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
