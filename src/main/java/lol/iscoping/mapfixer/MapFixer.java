package lol.iscoping.mapfixer;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class MapFixer extends JavaPlugin {

    private int count = 0;
    ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
    @Override
    public void onEnable() {
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        service.scheduleAtFixedRate(() -> {
            if (count > 0) count--;
        }, 0, 1, TimeUnit.SECONDS);
        saveDefaultConfig();
        int max = getConfig().getInt("Max");
        pm.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.MAP) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
            }

            @Override
            public void onPacketSending(PacketEvent event) {
                count++;
                if (count >= max) event.setCancelled(true);
            }
        });
    }
}
