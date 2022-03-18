package gmask.gmask.events;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class QQBot implements Listener {
    Plugin plugin = gmask.gmask.GMask.getPlugin(gmask.gmask.GMask.class);
    //获取players.yml路径
    File cconfig = new File(gmask.gmask.GMask.getPlugin(gmask.gmask.GMask.class).getDataFolder() ,"players.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(cconfig);
    public void 申请白名单(MiraiGroupMessageEvent event) throws IOException {
        if(event.getMessage().length() > 5){

            String whitecmd = plugin.getConfig().getString("whitecmd");
            if(whitecmd == null){
                System.out.println(ChatColor.RED + "白名单命令设置错误！");
                return;
            }
            if(event.getMessage().substring(0,5).equals(whitecmd)){
                if(config.getString(String.valueOf(event.getSenderID())) == null) {
                    if(config.getString(event.getMessage().substring(6, event.getMessage().length())) == null){
                        //Player player = (Player)Bukkit.getPlayer(event.getMessage().substring(11,event.getMessage().length()));
                        config.set(String.valueOf(event.getSenderID()), event.getMessage().substring(6, event.getMessage().length()));
                        config.set(event.getMessage().substring(6, event.getMessage().length()), event.getSenderID());
                        config.save(new File(gmask.gmask.GMask.getPlugin(gmask.gmask.GMask.class).getDataFolder(), "players.yml"));
                        if (config.getString(event.getMessage().substring(6, event.getMessage().length())) == null) {
                            MiraiBot.getBot(event.getBotID()).getGroup(event.getGroupID()).sendMessage("通行证申请失败，请检查后重试！");
                        } else {
                            MiraiBot.getBot(event.getBotID()).getGroup(event.getGroupID()).sendMessage("申请成功，请重新进入服务器！");
                        }
                    }else{
                        MiraiBot.getBot(event.getBotID()).getGroup(event.getGroupID()).sendMessage(event.getMessage().substring(6, event.getMessage().length()) + "已申请通行证，请勿重复申请！");
                    }

                }else{
                    MiraiBot.getBot(event.getBotID()).getGroup(event.getGroupID()).sendMessage("你已经为" + config.getString(String.valueOf(event.getSenderID())) + "申请通行证，请勿重复申请！");
                }





            }
        }
    }
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        //获取玩家信息
        Player player = event.getPlayer();
        //初始化白名单提示
        String text = "";

        //获取 提示 list

        List<String> list = plugin.getConfig().getStringList("whitelisttext");

        //遍历整合提示list
        for (String string : list ) {
            text =text + "\n" + ChatColor.translateAlternateColorCodes('&' ,string);
        }
        //检测该玩家是否有白名单

        if(config.getString(player.getName()) == null){
            player.kickPlayer(ChatColor.WHITE + "【白名单系统】\n" + text);
        }


    }


    @EventHandler
    public void messageReturn(MiraiGroupMessageEvent event) throws IOException {

        long qqgroup = plugin.getConfig().getLong("QQGroup");
        System.out.println(qqgroup);
        if(event.getGroupID() == qqgroup){
            if(event.getMessage().equals("在线人数")) {
                MiraiBot.getBot(event.getBotID()).getGroup(event.getGroupID()).sendMessageMirai("当前在线人数：" + Bukkit.getServer().getOnlinePlayers().size()+"人");
                return;
            }
            申请白名单(event);

        }



    }



}
