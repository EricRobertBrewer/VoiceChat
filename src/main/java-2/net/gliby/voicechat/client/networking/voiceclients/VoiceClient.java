/*
 * Decompiled with CFR 0_118.
 */
package net.gliby.voicechat.client.networking.voiceclients;

import net.gliby.voicechat.common.networking.voiceservers.EnumVoiceNetworkType;

public abstract class VoiceClient
implements Runnable {
    protected EnumVoiceNetworkType type;

    public VoiceClient(EnumVoiceNetworkType enumVoiceServer) {
        this.type = enumVoiceServer;
    }

    public final EnumVoiceNetworkType getType() {
        return this.type;
    }

    public abstract void handleEnd(int var1);

    public abstract void handleEntityPosition(int var1, double var2, double var4, double var6);

    public abstract void handlePacket(int var1, byte[] var2, int var3, boolean var4);

    @Override
    public final void run() {
        this.start();
    }

    public abstract void sendVoiceData(byte var1, byte[] var2, boolean var3);

    public abstract void start();

    public abstract void stop();
}

