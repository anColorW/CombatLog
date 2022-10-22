package me.color.listeners;

import me.color.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class AttackLog implements Listener {

        public static int sec = 0;
        public static boolean midcd = false;

        public static String p1name;
        public static String p2name;
        public static Inventory p1inv;
        public static Inventory p2inv;

        public static ArrayList<Player> hasCooldown = new ArrayList<>();

        @EventHandler
        public static void onleft(PlayerQuitEvent e){

            if(p1name == null || p2name == null)
                return;


           if(hasCooldown.contains(e.getPlayer()) && midcd){
                if(e.getPlayer().getName() == p1name){
                    Bukkit.broadcastMessage(e.getPlayer().getName() + " left midfight");

                    for(int i = 0; i < p1inv.getSize(); i++){
                        if(p1inv.getItem(i) == null){
                            continue;
                        }
                        Bukkit.getWorld(e.getPlayer().getWorld().getName()).dropItem(e.getPlayer().getLocation(), p1inv.getItem(i));
                    }
                    p1inv.clear();
                }

                if(e.getPlayer().getName() == p2name){
                    Bukkit.broadcastMessage(e.getPlayer().getName() + " left midfight");

                    for(int i = 0; i < p2inv.getSize(); i++){
                        if(p2inv.getItem(i) == null){
                            continue;
                        }
                        Bukkit.getWorld(e.getPlayer().getWorld().getName()).dropItem(e.getPlayer().getLocation(), p2inv.getItem(i));
                    }

                    p2inv.clear();
                }

           }


        }

    @EventHandler
    public static void onhit(EntityDamageByEntityEvent e){

        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p1 = (Player) e.getEntity();
            Player p2 = (Player) e.getDamager();

            p1name = p1.getName();
            p2name = p2.getName();
            p1inv = p1.getInventory();
            p2inv = p2.getInventory();

            System.out.println(p1);
            System.out.println(p2);
            sec = 5;

            if(midcd) {
                sec = 5;
                return;
            }
            new BukkitRunnable() {

                @Override
                public void run() {
                    if(sec  == 0){
                        hasCooldown.remove(p1);
                        hasCooldown.remove(p2);
                        midcd = false;
                        cancel();
                    }
                        midcd = true;
                        if(!(hasCooldown.contains(p1) && hasCooldown.contains(p2))){
                            hasCooldown.add(p1);
                            hasCooldown.add(p2);
                        }
                        System.out.println(sec);
                        sec = sec -1;

                }
            }.runTaskTimer(Main.getInstance(), 0, 20);
        }
    }
}
