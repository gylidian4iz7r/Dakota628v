import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FartingSquidCommands implements CommandExecutor {

    private FartingSquid plugin;

    public FartingSquidCommands(FartingSquid plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("fartingsquid")) {
            if(commandSender instanceof Player) {
                Player p = (Player) commandSender;
                FartingSquid.getInstance().spawnSquid(p);
                return true;
            }
        }
        return false;
    }
}