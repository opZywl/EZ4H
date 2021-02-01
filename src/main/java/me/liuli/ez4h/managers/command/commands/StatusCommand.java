package me.liuli.ez4h.managers.command.commands;

import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.minecraft.Client;

import java.util.concurrent.TimeUnit;

public class StatusCommand implements CommandBase {
    @Override
    public String getCommandName() {
        return "status";
    }

    @Override
    public String getHelpMessage() {
        return "Show EZ4H status";
    }

    @Override
    public void exec(String[] args, Client client) {
        client.sendMessage("§a---- §fServer status §a----");
        long time = System.currentTimeMillis() - EZ4H.getStartTime();
        client.sendMessage("§bUptime: " + formatUptime(time));
        client.sendMessage("§bThread count: §a" + Thread.getAllStackTraces().size());

        Runtime runtime = Runtime.getRuntime();
        long totalMB = Math.round(((double) runtime.totalMemory()) / 1024 / 1024);
        long usedMB = Math.round((double) (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);
        long maxMB = Math.round(((double) runtime.maxMemory()) / 1024 / 1024);
        int usage = (int) (((double) usedMB) / ((double) totalMB) * 100D);
        String usageColor = "§a";
        if (usage > 85) {
            usageColor = "§c";
        } else if (usage > 60) {
            usageColor = "§e";
        }
        client.sendMessage("§bUsed memory: " + usageColor + usedMB + " MB. (" + usage + "%)");
        client.sendMessage("§bTotal memory: §c" + totalMB + " MB.");
        client.sendMessage("§bMaximum VM memory: §c" + maxMB + " MB.");
        client.sendMessage("§bAvailable processors: §a" + runtime.availableProcessors());
        client.sendMessage("§bPlayers: §a" + EZ4H.getOnlinePlayers() + " online.");
    }

    private String formatUptime(long uptime) {
        long days = TimeUnit.MILLISECONDS.toDays(uptime);
        uptime -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(uptime);
        uptime -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptime);
        uptime -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptime);
        return String.format("§c%d§e days §c%d§e hours §c%d§e minutes §c%d§e seconds", days, hours, minutes, seconds);
    }
}
