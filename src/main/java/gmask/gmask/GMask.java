package gmask.gmask;

import gmask.gmask.cmd.Help;
import gmask.gmask.events.QQBot;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;



public final class GMask extends JavaPlugin {


    @Override
    public void onEnable() {
        Plugin plugin = gmask.gmask.GMask.getPlugin(gmask.gmask.GMask.class);
        System.out.println(ChatColor.GREEN + "GMask插件加载成功！");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        boolean b = plugin.getConfig().getBoolean("whitelist");

        if (b){
            getServer().getPluginManager().registerEvents(new QQBot(),this);
        }
        this.saveResource("players.yml" ,false);



        getCommand("gmask").setExecutor(new Help());
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(ChatColor.RED + "GMask插件已卸载！");


    }


}
