package net.gliby.voicechat.common;

import net.gliby.gman.JINIFile;
import net.gliby.voicechat.VoiceChat;

import java.io.File;
import java.io.IOException;

public class ServerConfiguration {

    private static final String MODPACK_ID = "ModPackID";
    private static final String BEHIND_PROXY = "ServerBehindProxy";
    private static final String SHOW_VOICEPLATES = "ShowVoicePlates";
    private static final String SHOW_PLAYERICONS = "ShowPlayerIcons";
    private static final String MINIMUM_QUALITY = "MinimumQuality";
    private static final String MAXIMUM_QUALITY = "MaximumQuality";
    private static final String SOUND_DISTANCE = "SoundDistance";
    private static final String FADE_RADIUS = "FadeRadius";
    private static final String DEFAULT_CHAT_MODE = "DefaultChatMode";
    private static final String UDP_PORT = "UDPPort";
    private static final String NETWORK_TYPE = "NetworkType";
    private static final String BUFFER_SIZE = "BufferSize";
    private final File location;
    private final ServerSettings settings;
    private JINIFile init;


    public ServerConfiguration(ServerSettings settings, File file) {
        this.settings = settings;
        this.location = file;
    }

    public void init() {
        if (!this.load()) {
            VoiceChat.getLogger().info("No Configuration file found on server, will create one with default settings.");
            if (this.save()) {
                VoiceChat.getLogger().info("Created Configuration file with default settings on server.");
            }
        }

    }

    private boolean load() {
        if (this.location.exists()) {
            try {
                this.init = new JINIFile(this.location);
                this.settings.setSoundDistance(this.init.ReadFloat("Game", "SoundDistance", 16.0F).intValue());
                this.settings.setFadeRadius(this.init.ReadFloat("Game", "FadeRadius", 16.0F).intValue());
                this.settings.setDefaultChatMode(this.init.ReadInteger("Game", "DefaultChatMode", 0));
                this.settings.setCanShowVoiceIcons(this.init.ReadBool("Game", "ShowPlayerIcons", true));
                this.settings.setCanShowVoicePlates(this.init.ReadBool("Game", "ShowVoicePlates", true));
                this.settings.setAdvancedNetworkType(this.init.ReadInteger("Network", "NetworkType", 1));
                this.settings.setUDPPort(this.init.ReadInteger("Network", "UDPPort", this.settings.getUDPPort()));
                this.settings.setQuality(this.init.ReadInteger("Network", "MinimumQuality", this.settings.getMinimumSoundQuality()), this.init.ReadInteger("Network", "MaximumQuality", this.settings.getMaximumSoundQuality()));
                this.settings.setBufferSize(this.init.ReadInteger("Network", "BufferSize", this.settings.getBufferSize()));
                this.settings.setUsingProxy(this.init.ReadBool("Network", "ServerBehindProxy", false));
                this.settings.setModPackID(this.init.ReadInteger("Miscellaneous", "ModPackID", 1));
                return true;
            } catch (Exception var2) {
                VoiceChat.getLogger().fatal("Couldn\'t read configuration file, fix it or delete it. Default settings being used.");
                var2.printStackTrace();
            }
        }

        return false;
    }

    public boolean save() {
        if (this.init == null || !this.location.exists()) {
            try {
                this.init = new JINIFile(this.location);
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

        this.init.WriteFloat("Game", "SoundDistance", (float) this.settings.getSoundDistance());
        this.init.WriteComment("Game", "Sound Distance is proximity in which players can hear you fully! @Whiskey.");
        this.init.WriteFloat("Game", "FadeRadius", (float) this.settings.getFadeRadius());
        this.init.WriteComment("Game", "Sound Distance is an additional proximity to @SoundDistance in which players can hear you fading!");
        this.init.WriteInteger("Game", "DefaultChatMode", this.settings.getDefaultChatMode());
        this.init.WriteComment("Game", "DefaultChatMode: 0 - distance based, 1 - world based, 2 - global.");
        this.init.WriteBool("Game", "ShowPlayerIcons", this.settings.canShowVoiceIcons());
        this.init.WriteBool("Game", "ShowVoicePlates", this.settings.canShowVoicePlates());
        this.init.WriteComment("Game", "ShowPlayerIcons, if false - players won\'t see icons when someone talks; ShowVoicePlates, if false - players won\'t see player names(voice plates) on their screens.");
        this.init.WriteInteger("Network", "NetworkType", this.settings.getAdvancedNetworkType());
        this.init.WriteComment("Network", "NetworkType, 0 - Minecraft Network, 1 - UDP Network. UDP networking improves performance and network speeds extensively, it is highly recommended.");
        this.init.WriteInteger("Network", "UDPPort", this.settings.getUDPPort());
        this.init.WriteComment("Network", "If UDPPort is set to 0, minecraft\'s own port will be used, this cannot be the same as query port! Change the network type to 0, if you can\'t port forward to a UDP custom port.");
        this.init.WriteInteger("Network", "MinimumQuality", this.settings.getMinimumSoundQuality());
        this.init.WriteInteger("Network", "MaximumQuality", this.settings.getMaximumSoundQuality());
        this.init.WriteComment("Network", "Sound Quality level, starting from 0 to 9. If you want to reduce bandwidth, make the maximum quality smaller. If you\'d like to make sound quality great, set the minimum quality to a high value.");
        this.init.WriteInteger("Network", "BufferSize", this.settings.getBufferSize());
        this.init.WriteComment("Network", "BufferSize - recommended buffer size is 128, max 500, going any higher will cause issues. Buffer Size determines voice data amount in a single packet, big buffers equal in bigger latency. If you are experiencing stuttering with players, or having network lag - set this to a higher value. ");
        this.init.WriteBool("Network", "ServerBehindProxy", this.settings.isUsingProxy());
        this.init.WriteComment("Network", "ServerBehindProxy: if server is behind a proxy, like bungeecord, enable this.");
        this.init.WriteInteger("Miscellaneous", "ModPackID", this.settings.getModPackID());
        return this.init.UpdateFile();
    }
}
