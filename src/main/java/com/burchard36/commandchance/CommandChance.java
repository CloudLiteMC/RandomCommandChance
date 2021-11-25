package com.burchard36.commandchance;

import com.burchard36.Api;
import com.burchard36.ApiLib;
import com.burchard36.Logger;
import com.burchard36.command.ApiCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Random;

import static com.burchard36.ApiLib.convert;

public final class CommandChance extends JavaPlugin implements Api {

    private Random random;

    @Override
    public void onEnable() {
        ApiLib lib = new ApiLib().initializeApi(this);
        this.random = new Random();

        final ApiCommand command = new ApiCommand("commandchance",
                "Runs a plugin with a chance percentage",
                "/commandchance <chance> <your command>",
                new ArrayList<>())
                .onConsoleSender((consoleSent) -> {
                    if (consoleSent.args().size() < 2) {
                        consoleSent.getConsoleSender().sendMessage(Component.text(convert("&c/commandchance <chance> <your command>")));
                        return;
                    }


                    double chance;
                    try {
                        chance = Double.parseDouble(consoleSent.args().get(0));
                    } catch (final NumberFormatException ignored) {
                        consoleSent.getConsoleSender().sendMessage(Component.text(convert("&cFirst argument wasnt a number!")));
                        return;
                    }

                    if (chance > 1D) {
                        consoleSent.getConsoleSender().sendMessage(Component.text("&cNumber cannot be higher than 1!"));
                        return;
                    }

                    if (!(this.random.nextLong() <= chance)) {
                        Logger.debug("Chance failed for command!", this);
                        return;
                    }

                    Logger.debug("Chance passed for command! Executing now. . .", this);

                    consoleSent.args().remove(0);

                    StringBuilder commandString = new StringBuilder();
                    for (final String cmd : consoleSent.args()) {
                        commandString.append(cmd).append(" ");
                    }

                    this.getServer().dispatchCommand(consoleSent.getConsoleSender(), commandString.toString());
                });

        ApiLib.registerCommand(command);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean isDebug() {
        return true;
    }
}
