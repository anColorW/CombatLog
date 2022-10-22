package me.color;

import me.color.listeners.AttackLog;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    static Main instance;

    @Override
    public void onEnable() {
        System.out.println("dziala");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new AttackLog(), this);
        instance = this;
    }
    public static Main getInstance() {
        return instance;
    }

}
