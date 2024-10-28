package oluni.official.minecraft.plugin.ol.olcalculator.operations;

import oluni.official.minecraft.plugin.ol.olcalculator.OLCalculator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeOperation implements CommandExecutor, TabCompleter {

    private static final String PREFIX = "§aOLCalculator §8-> §r";
    private final List<String> timeUnitsRU = Arrays.asList("миллисекунда", "секунда", "минута", "час", "сутки", "неделя", "месяц", "год");
    private final List<String> timeUnitsEN = Arrays.asList("millisecond", "second", "minute", "hour", "day", "week", "month", "year");

    private final OLCalculator plugin;

    public TimeOperation(OLCalculator plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PREFIX + getMessage("only_players"));
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage(PREFIX + getMessage("usage", label));
            return false;
        }

        String fromUnit = args[0];
        String toUnit = args[1];
        double value;

        try {
            value = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(PREFIX + getMessage("invalid_number"));
            return true;
        }

        double convertedValue = convertTime(value, fromUnit, toUnit);
        if (convertedValue == -1) {
            sender.sendMessage(PREFIX + getMessage("invalid_units"));
            return true;
        }

        String language = plugin.getConfig().getString("language-plugin", "none");
        String unitMessage = language.equals("ru") ? toUnit : translateUnitToEnglish(toUnit);

        sender.sendMessage(PREFIX + getMessage("result", convertedValue, unitMessage));
        return true;
    }

    private double convertTime(double value, String fromUnit, String toUnit) {
        double inSeconds;

        switch (fromUnit) {
            case "миллисекунда":
            case "millisecond":
                inSeconds = value * Math.pow(10, -3);
                break;
            case "секунда":
            case "second":
                inSeconds = value;
                break;
            case "минута":
            case "minute":
                inSeconds = value * 60;
                break;
            case "час":
            case "hour":
                inSeconds = value * 3600;
                break;
            case "сутки":
            case "day":
                inSeconds = value * 86400;
                break;
            case "неделя":
            case "week":
                inSeconds = value * 604800;
                break;
            case "месяц":
            case "month":
                inSeconds = value * 2628000;
                break;
            case "год":
            case "year":
                inSeconds = value * 31536000;
                break;
            default:
                return -1;
        }

        switch (toUnit) {
            case "миллисекунда":
            case "millisecond":
                return inSeconds * 1000;
            case "секунда":
            case "second":
                return inSeconds;
            case "минута":
            case "minute":
                return inSeconds / 60;
            case "час":
            case "hour":
                return inSeconds / 3600;
            case "сутки":
            case "day":
                return inSeconds / 86400;
            case "неделя":
            case "week":
                return inSeconds / 604800;
            case "месяц":
            case "month":
                return inSeconds / 2628000;
            case "год":
            case "year":
                return inSeconds / 31536000;
            default:
                return -1;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1 || args.length == 2) {
            String language = plugin.getConfig().getString("language-plugin", "none");
            if (language.equals("ru")) {
                completions.addAll(timeUnitsRU);
            } else {
                completions.addAll(timeUnitsEN);
            }
        }

        return completions;
    }

    private String translateUnitToEnglish(String unit) {
        switch (unit) {
            case "миллисекунда":
                return "millisecond";
            case "секунда":
                return "second";
            case "минута":
                return "minute";
            case "час":
                return "hour";
            case "сутки":
                return "day";
            case "неделя":
                return "week";
            case "месяц":
                return "month";
            case "год":
                return "year";
            default:
                return unit;
        }
    }

    private String getMessage(String key, Object... args) {
        String language = plugin.getConfig().getString("language-plugin", "none");
        String message;

        switch (key) {
            case "only_players":
                message = language.equals("ru") ? "Эту команду можно использовать только игрокам." : "This command can only be used by players.";
                break;
            case "usage":
                message = language.equals("ru") ? "Использование: /%s <из_какой_величины> <в_какую_величину> <число>." : "Usage: /%s <from_unit> <to_unit> <number>.";
                return String.format(message, args[0]);
            case "invalid_number":
                message = language.equals("ru") ? "Ошибка: пожалуйста, введите корректное число." : "Error: please enter a valid number.";
                break;
            case "invalid_units":
                message = language.equals("ru") ? "Ошибка: неверные единицы измерения." : "Error: invalid units of measurement.";
                break;
            case "result":
                message = language.equals("ru") ? "Результат: §6%.2f %s§r." : "Result: §6%.2f %s§r.";
                return String.format(message, args[0], args[1]);
            default:
                return "Unknown message key.";
        }

        return message;
    }
}
