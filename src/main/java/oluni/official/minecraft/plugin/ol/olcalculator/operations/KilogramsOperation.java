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

public class KilogramsOperation implements CommandExecutor, TabCompleter {

    private static final String PREFIX = "§aOLCalculator §8-> §r";
    private final List<String> unitsRu = Arrays.asList(
            "пикограмм", "нанограмм", "микрограмм", "миллиграмм",
            "грамм", "килограмм", "мегаграмм", "гигаграмм",
            "тераграмм", "петаграмм", "эксаграмм", "зеттаграмм",
            "йоттаграмм", "тонны"
    );

    private final List<String> unitsEn = Arrays.asList(
            "picograms", "nanograms", "micrograms", "milligrams",
            "grams", "kilograms", "megagrams", "gigagrams",
            "teragrams", "petagrams", "exagrams", "zettagrams",
            "yottagrams", "tons"
    );

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PREFIX + getMessage("onlyPlayers"));
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
            sender.sendMessage(PREFIX + getMessage("invalidNumber"));
            return true;
        }

        double convertedValue = convertMass(value, fromUnit, toUnit);
        if (convertedValue == -1) {
            sender.sendMessage(PREFIX + getMessage("invalidUnits"));
            return true;
        }

        sender.sendMessage(PREFIX + getMessage("result", convertedValue, toUnit));
        return true;
    }

    private double convertMass(double value, String fromUnit, String toUnit) {
        double inGrams;

        switch (fromUnit) {
            case "пикограмм":
                inGrams = value * Math.pow(10, -12);
                break;
            case "нанограмм":
                inGrams = value * Math.pow(10, -9);
                break;
            case "микрограмм":
                inGrams = value * Math.pow(10, -6);
                break;
            case "миллиграмм":
                inGrams = value * Math.pow(10, -3);
                break;
            case "грамм":
                inGrams = value;
                break;
            case "килограмм":
                inGrams = value * Math.pow(10, 3);
                break;
            case "мегаграмм":
            case "тонны":
                inGrams = value * Math.pow(10, 6);
                break;
            case "гигаграмм":
                inGrams = value * Math.pow(10, 9);
                break;
            case "тераграмм":
                inGrams = value * Math.pow(10, 12);
                break;
            case "петаграмм":
                inGrams = value * Math.pow(10, 15);
                break;
            case "эксаграмм":
                inGrams = value * Math.pow(10, 18);
                break;
            case "зеттаграмм":
                inGrams = value * Math.pow(10, 21);
                break;
            case "йоттаграмм":
                inGrams = value * Math.pow(10, 24);
                break;
            default:
                return -1;
        }

        switch (toUnit) {
            case "пикограмм":
                return inGrams * Math.pow(10, 12);
            case "нанограмм":
                return inGrams * Math.pow(10, 9);
            case "микрограмм":
                return inGrams * Math.pow(10, 6);
            case "миллиграмм":
                return inGrams * Math.pow(10, 3);
            case "грамм":
                return inGrams;
            case "килограмм":
                return inGrams / Math.pow(10, 3);
            case "мегаграмм":
            case "тонны":
                return inGrams / Math.pow(10, 6);
            case "гигаграмм":
                return inGrams / Math.pow(10, 9);
            case "тераграмм":
                return inGrams / Math.pow(10, 12);
            case "петаграмм":
                return inGrams / Math.pow(10, 15);
            case "эксаграмм":
                return inGrams / Math.pow(10, 18);
            case "зеттаграмм":
                return inGrams / Math.pow(10, 21);
            case "йоттаграмм":
                return inGrams / Math.pow(10, 24);
            default:
                return -1;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        String language = OLCalculator.getInstance().getConfig().getString("language-plugin", "none");

        List<String> units = language.equals("en") ? unitsEn : unitsRu;

        if (args.length == 1) {
            completions.addAll(units);
        } else if (args.length == 2) {
            completions.addAll(units);
        }

        return completions;
    }

    private String getMessage(String key, Object... args) {
        String language = OLCalculator.getInstance().getConfig().getString("language-plugin", "none");
        String messageKey;

        if (language.equals("ru")) {
            switch (key) {
                case "onlyPlayers":
                    messageKey = "Эту команду можно использовать только игрокам.";
                    break;
                case "usage":
                    messageKey = "Использование: /%s <из_какой_величины> <в_какую_величину> <число>.";
                    break;
                case "invalidUnits":
                    messageKey = "Ошибка: неверные единицы измерения.";
                    break;
                case "invalidNumber":
                    messageKey = "Ошибка: пожалуйста, введите корректное число.";
                    break;
                case "result":
                    messageKey = "Результат: §6%s %s§r.";
                    break;
                default:
                    messageKey = "Ошибка: неизвестное сообщение.";
                    break;
            }
        } else if (language.equals("en")) {
            switch (key) {
                case "onlyPlayers":
                    messageKey = "This command can only be used by players.";
                    break;
                case "usage":
                    messageKey = "Usage: /%s <from_unit> <to_unit> <number>.";
                    break;
                case "invalidUnits":
                    messageKey = "Error: invalid units.";
                    break;
                case "invalidNumber":
                    messageKey = "Error: please enter a valid number.";
                    break;
                case "result":
                    messageKey = "Result: §6%s %s§r.";
                    break;
                default:
                    messageKey = "Error: unknown message.";
                    break;
            }
        } else {
            messageKey = "Error: unknown language.";
        }

        return String.format(messageKey, args);
    }
}
