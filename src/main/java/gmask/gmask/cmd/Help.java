package gmask.gmask.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Player;


public class Help implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            player.sendMessage(ChatColor.GREEN + "--=====[GMask帮助]=====--");
            player.sendMessage(ChatColor.WHITE + "/gmask    主命令，查看相关帮助");
        }else{
            System.out.println(ChatColor.WHITE + "GMask帮助");
        }


        return false;
    }

}
