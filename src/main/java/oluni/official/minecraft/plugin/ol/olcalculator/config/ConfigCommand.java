package oluni.official.minecraft.plugin.ol.olcalculator.config;

import oluni.official.minecraft.plugin.ol.olcalculator.OLCalculator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class ConfigCommand implements CommandExecutor {

    private final OLCalculator plugin;

    public ConfigCommand(OLCalculator plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование|Usage: /olcconfig (en|ru|reload)");
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("olcalculator.reload")) {
                sender.sendMessage(ChatColor.RED + "У вас нет прав для выполнения этой команды | You do not have permission to run this command.");
                return true;
            }
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Плагин перезагружен! | Plugin reloaded!");
            return true;
        }

        String language = args[0].toLowerCase();
        FileConfiguration config = plugin.getConfig();

        switch (language) {
            case "en":
                config.set("language-plugin", "en");
                break;
            case "ru":
                config.set("language-plugin", "ru");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Неверный аргумент. Используйте 'en', 'ru' или 'reload'. | Wrong argument. Use 'en', 'ru' or 'reload'.");
                return true;
        }

        plugin.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "Язык плагина изменен на | Plugin language changed to: " + language);
        return true;
    }
}
