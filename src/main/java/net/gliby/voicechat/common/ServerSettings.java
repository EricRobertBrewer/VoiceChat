package net.gliby.voicechat.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import net.gliby.voicechat.VoiceChat;
import net.gliby.voicechat.common.ModPackSettings;
import net.gliby.voicechat.common.ServerConfiguration;
import net.gliby.voicechat.common.VoiceChatServer;

public class ServerSettings {

   private ServerConfiguration configuration;
   private int soundDist = 64;
   private int udpPort = 0;
   private int bufferSize = 128;
   private int advancedNetworkType = 1;
   public int positionUpdateRate = 40;
   private int defaultChatMode = 0;
   private int minimumQuality = 0;
   private int maximumQuality = 9;
   private boolean canShowVoiceIcons = true;
   private boolean canShowVoicePlates = true;
   private boolean behindProxy;
   private int modPackID = 1;


   public ServerSettings(VoiceChatServer voiceChatServer) {}

   public boolean canShowVoiceIcons() {
      return this.canShowVoiceIcons;
   }

   public final boolean canShowVoicePlates() {
      return this.canShowVoicePlates;
   }

   public final int getAdvancedNetworkType() {
      return this.advancedNetworkType;
   }

   public final int getBufferSize() {
      return this.bufferSize;
   }

   public final int getDefaultChatMode() {
      return this.defaultChatMode;
   }

   public final int getMaximumSoundQuality() {
      return this.maximumQuality;
   }

   public final int getMinimumSoundQuality() {
      return this.minimumQuality;
   }

   protected int getModPackID() {
      return this.modPackID;
   }

   public final int getSoundDistance() {
      return this.soundDist;
   }

   public final int getUDPPort() {
      return this.udpPort;
   }

   public final boolean isUsingProxy() {
      return this.behindProxy;
   }

   public void preInit(File file) {
      this.configuration = new ServerConfiguration(this, file);
      (new Thread(new Runnable() {
         public void run() {
            ServerSettings.this.configuration.init();
         }
      }, "Configuration Process")).start();
      (new Thread(new Runnable() {
         public void run() {
            ModPackSettings settings = new ModPackSettings();

            try {
               ModPackSettings.GVCModPackInstructions e = settings.init();
               if(e.ID != ServerSettings.this.getModPackID()) {
                  VoiceChat.getLogger().info("Modpack defaults applied, original settings overwritten.");
                  ServerSettings.this.setCanShowVoicePlates(e.SHOW_PLATES);
                  ServerSettings.this.setCanShowVoiceIcons(e.SHOW_PLAYER_ICONS);
                  ServerSettings.this.setModPackID(e.ID);
                  ServerSettings.this.configuration.save();
               }
            } catch (UnsupportedEncodingException var3) {
               var3.printStackTrace();
            }

         }
      }, "Mod Pack Overwrite Process")).start();
   }

   public void setAdvancedNetworkType(int type) {
      this.advancedNetworkType = type;
   }

   public void setBufferSize(int bufferSize) {
      this.bufferSize = bufferSize;
   }

   public final void setCanShowVoiceIcons(boolean canShowVoiceIcons) {
      this.canShowVoiceIcons = canShowVoiceIcons;
   }

   public void setCanShowVoicePlates(boolean canShowVoicePlates) {
      this.canShowVoicePlates = canShowVoicePlates;
   }

   public void setDefaultChatMode(int defaultChatMode) {
      this.defaultChatMode = defaultChatMode;
   }

   public void setModPackID(int id) {
      this.modPackID = id;
   }

   public void setQuality(int x0, int x1) {
      this.minimumQuality = x0;
      this.maximumQuality = x1;
   }

   public void setSoundDistance(int dist) {
      this.soundDist = dist;
   }

   public void setUDPPort(int udp) {
      this.udpPort = udp;
   }

   public void setUsingProxy(boolean val) {
      this.behindProxy = val;
   }
}
