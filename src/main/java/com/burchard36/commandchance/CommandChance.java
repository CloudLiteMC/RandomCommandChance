package com.burchard36.commandchance;

import com.burchard36.ApiLib;
import com.burchard36.command.ApiCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

import static com.burchard36.ApiLib.convert;

public final class CommandChance extends JavaPlugin {

    private ApiLib lib;

    @Override
    public void onEnable() {
        this.lib = new ApiLib().initializeApi(this);

        final ApiCommand command = new ApiCommand("commandchance",
                "Runs a plugin with a chance percentage",
                "/commandchance <chance> <your command>",
                new ArrayList<>())
                .onConsoleSender((consoleSent) -> {
                    if (consoleSent.args().size() >= 2) {
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
                    
                });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
