package ravioli.gravioli.rpg.util;

import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftChatMessage;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class Title {
    private String title;
    private String sub;

    private int fadeIn = 20;
    private int fadeOut = 20;
    private int stay = 60;

    public void send(RPGPlayer... players) {
        if (this.fadeIn == 0) {
            this.fadeIn = 20;
        }
        if (this.fadeOut == 0) {
            this.fadeOut = 20;
        }
        if (this.stay == 0) {
            this.stay = 60;
        }
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, CraftChatMessage.fromString(this.title)[0]);
        PacketPlayOutTitle packet2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, CraftChatMessage.fromString(this.sub)[0]);
        PacketPlayOutTitle packet3 = new PacketPlayOutTitle(this.fadeIn, this.stay, this.fadeOut);

        for (RPGPlayer player : players) {
            ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(packet3); // send time protocol first
            ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(packet);
            ((CraftPlayer) player.getPlayer()).getHandle().playerConnection.sendPacket(packet2);
        }
    }

    public Title setTitle(String title) {
        this.title = title;
        return this;
    }

    public Title setSubTitle(String subtitle) {
        this.sub = subtitle;
        return this;
    }

    public Title setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public Title setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public Title setStay(int stay) {
        this.stay = stay;
        return this;
    }
}
