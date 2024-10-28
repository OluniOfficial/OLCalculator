package oluni.official.minecraft.plugin.ol.olcalculator;

import oluni.official.minecraft.plugin.ol.olcalculator.config.ConfigCommand;
import oluni.official.minecraft.plugin.ol.olcalculator.operations.KilogramsOperation;
import oluni.official.minecraft.plugin.ol.olcalculator.operations.MathSimpleOperation;
import oluni.official.minecraft.plugin.ol.olcalculator.operations.RulerOperation;
import oluni.official.minecraft.plugin.ol.olcalculator.operations.TimeOperation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class OLCalculator extends JavaPlugin {
    private FileConfiguration config;
    private static OLCalculator instance;

    @Override
    public void onEnable() {
        createConfig();
        instance = this;

        Objects.requireNonNull(this.getCommand("olcalculator")).setExecutor(new MathSimpleOperation());
        Objects.requireNonNull(this.getCommand("olc")).setExecutor(new MathSimpleOperation());
        Objects.requireNonNull(this.getCommand("olcalc")).setExecutor(new MathSimpleOperation());
        Objects.requireNonNull(this.getCommand("olk")).setExecutor(new KilogramsOperation());
        Objects.requireNonNull(this.getCommand("olm")).setExecutor(new RulerOperation());
        Objects.requireNonNull(this.getCommand("olt")).setExecutor(new TimeOperation(this));

        checkPluginLanguage();
    }

    private void createConfig() {
        File folder = new File(getDataFolder(), "OLCalculator");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File configFile = new File(folder, "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        Objects.requireNonNull(this.getCommand("olcconfig")).setExecutor(new ConfigCommand(this));

        config = getConfig();

        checkPluginLanguage();
    }

    public static OLCalculator getInstance() {
        return instance;
    }


    private void checkPluginLanguage() {
        String language = config.getString("language-plugin", "none");

        if (language.equalsIgnoreCase("none")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.isOp()) {
                    player.sendMessage(ChatColor.GRAY + "Only operators can see this message! / Это сообщение видят только операторы! "
                            + ChatColor.GREEN + "Поменяйте выбранный язык плагина в его конфиге. Сейчас стоит None. "
                            + "Вы можете поставить либо en либо ru. "
                            + "Либо же вы можете установить его командой /olcconfig (ru|en)! "
                            + "/ Change the selected plugin language in its config. Now it's None. "
                            + "You can put either en or ru. Or you can install it with the /olcconfig command (ru|en)!");
                }
            });
        }
    }
}
