/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataOutput
 */
package net.gliby.voicechat.common.networking.voiceservers.udp;

import com.google.common.io.ByteArrayDataOutput;
import net.gliby.voicechat.common.networking.voiceservers.udp.UDPPacket;

public class UDPClientVoiceEnd
extends UDPPacket {
    @Override
    public byte id() {
        return 2;
    }

    @Override
    public void write(ByteArrayDataOutput out) {
    }
}

