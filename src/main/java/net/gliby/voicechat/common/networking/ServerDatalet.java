package net.gliby.voicechat.common.networking;

import net.minecraft.entity.player.EntityPlayerMP;

public class ServerDatalet {

    public final EntityPlayerMP player;
    public final int id;
    public byte[] data;
    public boolean end;
    public byte divider;


    public ServerDatalet(EntityPlayerMP player, int id, byte[] data, byte divider, boolean end) {
        this.player = player;
        this.id = id;
        this.data = data;
        this.end = end;
        this.divider = divider;
    }
}
