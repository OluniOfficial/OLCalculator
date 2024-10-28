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
import java.util.Objects;

public class RulerOperation implements CommandExecutor, TabCompleter {

    private static final String PREFIX = "§aOLCalculator §8-> §r";

    private final List<String> unitsRU = Arrays.asList("пикометр", "нанометр", "микрометр", "миллиметр", "сантиметр",
            "дециметр", "метр", "километр", "миля", "фут",
            "ярд", "морская миля", "мегаметр", "гигабайт",
            "тераметр", "петаметр", "эксаметр", "зеттаметр",
            "йоттаметр");

    private final List<String> unitsEN = Arrays.asList("picometer", "nanometer", "micrometer", "millimeter", "centimeter",
            "decimeter", "meter", "kilometer", "mile", "foot",
            "yard", "nautical mile", "megometer", "gigameter",
            "terameter", "petameter", "exameter", "zettameter",
            "yottameter");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PREFIX + getMessage("command.onlyPlayers"));
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage(PREFIX + getMessage("command.usage", label));
            return false;
        }

        String fromUnit = args[0];
        String toUnit = args[1];
        double value;

        try {
            value = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(PREFIX + getMessage("error.invalidNumber"));
            return true;
        }

        double convertedValue = convertLength(value, fromUnit, toUnit);
        if (convertedValue == -1) {
            sender.sendMessage(PREFIX + getMessage("error.invalidUnits"));
            return true;
        }

        sender.sendMessage(PREFIX + getMessage("result", convertedValue, toUnit));
        return true;
    }

    private double convertLength(double value, String fromUnit, String toUnit) {
        double inMeters;

        switch (fromUnit) {
            case "пикометр":
            case "picometer":
                inMeters = value * Math.pow(10, -12);
                break;
            case "нанометр":
            case "nanometer":
                inMeters = value * Math.pow(10, -9);
                break;
            case "микрометр":
            case "micrometer":
                inMeters = value * Math.pow(10, -6);
                break;
            case "миллиметр":
            case "millimeter":
                inMeters = value * Math.pow(10, -3);
                break;
            case "сантиметр":
            case "centimeter":
                inMeters = value * Math.pow(10, -2);
                break;
            case "дециметр":
            case "decimeter":
                inMeters = value * Math.pow(10, -1);
                break;
            case "метр":
            case "meter":
                inMeters = value;
                break;
            case "километр":
            case "kilometer":
                inMeters = value * Math.pow(10, 3);
                break;
            case "миля":
            case "mile":
                inMeters = value * 1609.34;
                break;
            case "фут":
            case "foot":
                inMeters = value * 0.3048;
                break;
            case "ярд":
            case "yard":
                inMeters = value * 0.9144;
                break;
            case "морская миля":
            case "nautical mile":
                inMeters = value * 1852;
                break;
            case "мегаметр":
            case "megometer":
                inMeters = value * Math.pow(10, 6);
                break;
            case "гигабайт":
            case "gigameter":
                inMeters = value * Math.pow(10, 9);
                break;
            case "тераметр":
            case "terameter":
                inMeters = value * Math.pow(10, 12);
                break;
            case "петаметр":
            case "petameter":
                inMeters = value * Math.pow(10, 15);
                break;
            case "эксаметр":
            case "exameter":
                inMeters = value * Math.pow(10, 18);
                break;
            case "зеттаметр":
            case "zettameter":
                inMeters = value * Math.pow(10, 21);
                break;
            case "йоттаметр":
            case "yottameter":
                inMeters = value * Math.pow(10, 24);
                break;
            default:
                return -1;
        }

        switch (toUnit) {
            case "пикометр":
            case "picometer":
                return inMeters * Math.pow(10, 12);
            case "нанометр":
            case "nanometer":
                return inMeters * Math.pow(10, 9);
            case "микрометр":
            case "micrometer":
                return inMeters * Math.pow(10, 6);
            case "миллиметр":
            case "millimeter":
                return inMeters * Math.pow(10, 3);
            case "сантиметр":
            case "centimeter":
                return inMeters * Math.pow(10, 2);
            case "дециметр":
            case "decimeter":
                return inMeters * 10;
            case "метр":
            case "meter":
                return inMeters;
            case "километр":
            case "kilometer":
                return inMeters / Math.pow(10, 3);
            case "миля":
            case "mile":
                return inMeters / 1609.34;
            case "фут":
            case "foot":
                return inMeters / 0.3048;
            case "ярд":
            case "yard":
                return inMeters / 0.9144;
            case "морская миля":
            case "nautical mile":
                return inMeters / 1852;
            case "мегаметр":
            case "megometer":
                return inMeters / Math.pow(10, 6);
            case "гигабайт":
            case "gigameter":
                return inMeters / Math.pow(10, 9);
            case "тераметр":
            case "terameter":
                return inMeters / Math.pow(10, 12);
            case "петаметр":
            case "petameter":
                return inMeters / Math.pow(10, 15);
            case "эксаметр":
            case "exameter":
                return inMeters / Math.pow(10, 18);
            case "зеттаметр":
            case "zettameter":
                return inMeters / Math.pow(10, 21);
            case "йоттаметр":
            case "yottameter":
                return inMeters / Math.pow(10, 24);
            default:
                return -1;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        String language = Objects.requireNonNull(sender.getServer().getPluginManager().getPlugin("OLCalculator")).getConfig().getString("language-plugin", "none");
        if (args.length == 1 || args.length == 2) {
            if (language.equals("ru")) {
                completions.addAll(unitsRU);
            } else if (language.equals("en")) {
                completions.addAll(unitsEN);
            }
        }

        return completions;
    }

    private String getMessage(String key, Object... args) {
        String language = OLCalculator.getInstance().getConfig().getString("language-plugin", "none");
        if (language.equals("ru")) {
            switch (key) {
                case "command.onlyPlayers":
                    return "Эту команду можно использовать только игрокам.";
                case "command.usage":
                    return "Использование: /%s <из_какой_величины> <в_какую_величину> <число>.";
                case "error.invalidNumber":
                    return "Ошибка: пожалуйста, введите корректное число.";
                case "error.invalidUnits":
                    return "Ошибка: неверные единицы измерения.";
                case "result":
                    return String.format("Результат: §6%.2f %s§r.", args[0], args[1]);
            }
        } else if (language.equals("en")) {
            switch (key) {
                case "command.onlyPlayers":
                    return "This command can only be used by players.";
                case "command.usage":
                    return "Usage: /%s <from_unit> <to_unit> <number>.";
                case "error.invalidNumber":
                    return "Error: please enter a valid number.";
                case "error.invalidUnits":
                    return "Error: invalid units of measurement.";
                case "result":
                    return String.format("Result: §6%.2f %s§r.", args[0], args[1]);
            }
        }
        return "";
    }
}
