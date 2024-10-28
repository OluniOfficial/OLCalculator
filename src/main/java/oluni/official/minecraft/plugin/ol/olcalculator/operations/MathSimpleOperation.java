package oluni.official.minecraft.plugin.ol.olcalculator.operations;

import oluni.official.minecraft.plugin.ol.olcalculator.OLCalculator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MathSimpleOperation implements CommandExecutor {

    private static final String PREFIX = "§aOLCalculator §8-> §r";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PREFIX + getMessage("onlyPlayers"));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(PREFIX + getMessage("usage", label));
            return false;
        }

        // Объединяем аргументы в одно выражение
        String expression = String.join("", args);
        double num1, num2;
        String operator;

        // Определение оператора
        if (expression.contains("+")) {
            operator = "+";
        } else if (expression.contains("-")) {
            operator = "-";
        } else if (expression.contains("*")) {
            operator = "*";
        } else if (expression.contains("/")) {
            operator = "/";
        } else {
            sender.sendMessage(PREFIX + getMessage("invalidOperator"));
            return true;
        }

        // Разделение чисел по оператору с помощью регулярного выражения
        String[] parts = expression.split("[+\\-*/]");
        if (parts.length != 2) {
            sender.sendMessage(PREFIX + getMessage("invalidFormat"));
            return true;
        }

        try {
            num1 = Double.parseDouble(parts[0].trim());
            num2 = Double.parseDouble(parts[1].trim());
        } catch (NumberFormatException e) {
            sender.sendMessage(PREFIX + getMessage("invalidNumber"));
            return true;
        }

        double result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    sender.sendMessage(PREFIX + getMessage("divisionByZero"));
                    return true;
                }
                result = num1 / num2;
                break;
            default:
                sender.sendMessage(PREFIX + getMessage("invalidOperator"));
                return true;
        }

        sender.sendMessage(PREFIX + getMessage("result", result));
        return true;
    }

    private String getMessage(String key, Object... args) {
        String language = OLCalculator.getInstance().getConfig().getString("language-plugin", "none");
        String messageKey;

        // Локализация сообщений
        if (language.equals("ru")) {
            switch (key) {
                case "onlyPlayers":
                    messageKey = "Эту команду можно использовать только игрокам.";
                    break;
                case "usage":
                    messageKey = String.format("Использование: /%s <выражение>, например, 800+300 или 800 + 300.", args);
                    break;
                case "invalidOperator":
                    messageKey = "Ошибка: используйте один из операторов +, -, *, или /.";
                    break;
                case "invalidFormat":
                    messageKey = "Ошибка: неверный формат выражения.";
                    break;
                case "invalidNumber":
                    messageKey = "Ошибка: пожалуйста, введите корректные числа.";
                    break;
                case "divisionByZero":
                    messageKey = "Ошибка: деление на ноль невозможно.";
                    break;
                case "result":
                    messageKey = String.format("Результат: §6%s", args);
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
                    messageKey = String.format("Usage: /%s <expression>, for example, 800+300 or 800 + 300.", args);
                    break;
                case "invalidOperator":
                    messageKey = "Error: use one of the operators +, -, *, or /.";
                    break;
                case "invalidFormat":
                    messageKey = "Error: invalid expression format.";
                    break;
                case "invalidNumber":
                    messageKey = "Error: please enter valid numbers.";
                    break;
                case "divisionByZero":
                    messageKey = "Error: division by zero is not possible.";
                    break;
                case "result":
                    messageKey = String.format("Result: §6%s", args);
                    break;
                default:
                    messageKey = "Error: unknown message.";
                    break;
            }
        } else {
            messageKey = "Error: unknown language.";
        }

        return messageKey;
    }
}
