package me.scill.mobdrops.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.scill.mobdrops.MobDrops;

public class ReloadCommand implements CommandExecutor {
	
	private final MobDrops plugin;
	
	public ReloadCommand(final MobDrops plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mobdrops")) {
			if (sender.hasPermission("mobdrops.reload")) {
				plugin.reloadConfig();
				plugin.reloadMobDrops();
				sender.sendMessage(ChatColor.GREEN + "MobDrops has been reloaded!");
			}
			return true;
		}
		return false;
	}
}
