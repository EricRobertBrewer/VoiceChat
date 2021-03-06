package net.gliby.voicechat.common.networking.voiceservers.udp;

import com.google.common.io.ByteArrayDataOutput;

public class UDPServerEntityPositionPacket extends UDPPacket {

    public int entityId;
    public double x;
    public double y;
    public double z;


    public UDPServerEntityPositionPacket(int entityId, double x, double y, double z) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public byte id() {
        return (byte) 4;
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeInt(this.entityId);
        out.writeDouble(this.x);
        out.writeDouble(this.y);
        out.writeDouble(this.z);
    }
}
