/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package net.gliby.voicechat.client;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import net.gliby.voicechat.VoiceChat;
import net.gliby.voicechat.client.Configuration;
import net.gliby.voicechat.client.device.Device;
import net.gliby.voicechat.client.device.DeviceHandler;
import net.gliby.voicechat.client.gui.EnumUIPlacement;
import net.gliby.voicechat.client.gui.UIPosition;
import net.gliby.voicechat.common.MathUtility;
import net.gliby.voicechat.common.ModPackSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class Settings {
    private final DeviceHandler deviceHandler = new DeviceHandler();
    private boolean debugMode;
    private Device inputDevice;
    private float worldVolume = 1.0f;
    private float inputBoost = 0.0f;
    private float uiOpacity = 1.0f;
    private int speakMode = 0;
    private int encodingMode = 0;
    private int minimumQuality = 0;
    private int maximumQuality = 10;
    private float encodingQuality = 0.6f;
    private UIPosition uiPositionSpeak;
    private UIPosition uiPositionPlate;
    private boolean perceptualEnchantment = true;
    private boolean setupNeeded;
    private boolean snooperEnabled = false;
    private boolean volumeControl = true;
    private int maxSoundDistance = 63;
    private boolean voicePlatesAllowed = true;
    private boolean voiceIconsAllowed = true;
    private int bufferSize = 144;
    private int modPackId = 1;
    Configuration configuration;

    public Settings(File file) {
        this.configuration = new Configuration(this, file);
        this.uiPositionSpeak = new UIPosition(EnumUIPlacement.SPEAK, EnumUIPlacement.SPEAK.x, EnumUIPlacement.SPEAK.y, EnumUIPlacement.SPEAK.positionType, 1.0f);
        this.uiPositionPlate = new UIPosition(EnumUIPlacement.VOICE_PLATES, EnumUIPlacement.VOICE_PLATES.x, EnumUIPlacement.VOICE_PLATES.y, EnumUIPlacement.VOICE_PLATES.positionType, 1.0f);
    }

    public final int getBufferSize() {
        return this.bufferSize;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public DeviceHandler getDeviceHandler() {
        return this.deviceHandler;
    }

    public int getEncodingMode() {
        return (int)MathUtility.clamp(this.encodingMode, 0.0f, 2.0f);
    }

    public String getEncodingModeString() {
        String s = "Narrowband";
        switch (this.encodingMode) {
            case 0: {
                s = "Narrowband";
                break;
            }
            case 1: {
                s = "Wideband";
                break;
            }
            case 2: {
                s = "Ultrawideband";
            }
        }
        return s;
    }

    public final float getEncodingQuality() {
        return MathUtility.clamp(this.encodingQuality, 0.0f, 1.0f);
    }

    public final float getInputBoost() {
        return this.inputBoost;
    }

    public Device getInputDevice() {
        if (this.inputDevice == null) {
            this.inputDevice = this.deviceHandler.getDefaultDevice();
        }
        return this.inputDevice;
    }

    public final int getMaximumQuality() {
        return this.maximumQuality;
    }

    public final int getMaximumRenderableVoiceIcons() {
        return 20;
    }

    public final int getMinimumQuality() {
        return this.minimumQuality;
    }

    public final int getModPackID() {
        return this.modPackId;
    }

    public final int getSoundDistance() {
        return this.maxSoundDistance;
    }

    public final int getSpeakMode() {
        return this.speakMode;
    }

    public float getUIOpacity() {
        return this.uiOpacity;
    }

    public final UIPosition getUIPositionPlate() {
        return this.uiPositionPlate;
    }

    public final UIPosition getUIPositionSpeak() {
        return this.uiPositionSpeak;
    }

    public float getWorldVolume() {
        return this.worldVolume;
    }

    public void init() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                Settings.this.deviceHandler.loadDevices();
                Settings.this.configuration.init(Settings.this.deviceHandler);
                ModPackSettings settings = new ModPackSettings();
                try {
                    ModPackSettings.GVCModPackInstructions newDefault = settings.init();
                    if (newDefault.ID != Settings.this.getModPackID()) {
                        VoiceChat.getLogger().info("Modpack defaults applied, original settings overwritten.");
                        Settings.this.uiPositionSpeak = new UIPosition(EnumUIPlacement.SPEAK, newDefault.SPEAK_ICON.X, newDefault.SPEAK_ICON.Y, newDefault.SPEAK_ICON.TYPE, newDefault.SPEAK_ICON.SCALE);
                        Settings.this.uiPositionPlate = new UIPosition(EnumUIPlacement.VOICE_PLATES, newDefault.VOICE_PLATE.X, newDefault.VOICE_PLATE.Y, newDefault.VOICE_PLATE.TYPE, newDefault.VOICE_PLATE.SCALE);
                        Settings.this.setWorldVolume(newDefault.WORLD_VOLUME);
                        Settings.this.setUIOpacity(newDefault.UI_OPACITY);
                        Settings.this.setVolumeControl(newDefault.VOLUME_CONTROL);
                        Settings.this.setVoicePlatesAllowed(newDefault.SHOW_PLATES);
                        Settings.this.setVoiceIconsAllowed(newDefault.SHOW_PLAYER_ICONS);
                        Settings.this.setModPackID(newDefault.ID);
                        Settings.this.configuration.save();
                    }
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, "Settings Process").start();
    }

    public final boolean isDebug() {
        return this.debugMode;
    }

    public final boolean isPerceptualEnchantmentAllowed() {
        return this.perceptualEnchantment;
    }

    public final boolean isSetupNeeded() {
        return this.setupNeeded;
    }

    public final boolean isSnooperAllowed() {
        return this.snooperEnabled;
    }

    public final boolean isVoiceIconAllowed() {
        return this.voiceIconsAllowed;
    }

    public final boolean isVoicePlateAllowed() {
        return this.voicePlatesAllowed;
    }

    public final boolean isVolumeControlled() {
        return this.volumeControl;
    }

    public void resetQuality() {
        this.minimumQuality = 0;
        this.maximumQuality = 10;
    }

    public void resetUI(int width, int height) {
        this.uiPositionSpeak.type = this.uiPositionSpeak.info.positionType;
        this.uiPositionSpeak.x = this.uiPositionSpeak.info.x;
        this.uiPositionSpeak.y = this.uiPositionSpeak.info.y;
        this.uiPositionSpeak.scale = 1.0f;
        this.uiPositionPlate.type = this.uiPositionPlate.info.positionType;
        this.uiPositionPlate.x = this.uiPositionPlate.info.x;
        this.uiPositionPlate.y = this.uiPositionPlate.info.y;
        this.uiPositionPlate.scale = 1.0f;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setDebug(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void setEncodingMode(int encodingMode) {
        this.encodingMode = encodingMode;
    }

    public void setEncodingQuality(float encodingQuality) {
        this.encodingQuality = encodingQuality;
    }

    public void setInputBoost(float inputBoost) {
        this.inputBoost = inputBoost;
    }

    public void setInputDevice(Device loadedDevice) {
        this.inputDevice = loadedDevice;
    }

    public void setModPackID(int modPackId) {
        this.modPackId = modPackId;
    }

    public void setNetworkQuality(int soundQualityMin, int soundQualityMax) {
        this.minimumQuality = soundQualityMin;
        this.maximumQuality = soundQualityMax;
    }

    public void setPerceptualEnchantment(boolean perceptualEnchantment) {
        this.perceptualEnchantment = perceptualEnchantment;
    }

    public void setSetupNeeded(boolean setupNeeded) {
        this.setupNeeded = setupNeeded;
    }

    public void setSnooperAllowed(boolean b) {
        this.snooperEnabled = b;
    }

    public void setSoundDistance(int soundDist) {
        this.maxSoundDistance = soundDist;
    }

    public void setSpeakMode(int speakMode) {
        this.speakMode = speakMode;
    }

    public void setUIOpacity(float chatIconOpacity) {
        this.uiOpacity = chatIconOpacity;
    }

    public void setUIPosition(EnumUIPlacement placement, float x, float y, float scale, int type) {
        if (placement == EnumUIPlacement.SPEAK) {
            this.uiPositionSpeak = new UIPosition(placement, x, y, type, scale);
        }
        if (placement == EnumUIPlacement.VOICE_PLATES) {
            this.uiPositionPlate = new UIPosition(placement, x, y, type, scale);
        }
    }

    public final void setVoiceIconsAllowed(boolean voiceIconsAllowed) {
        this.voiceIconsAllowed = voiceIconsAllowed;
    }

    public final void setVoicePlatesAllowed(boolean voicePlatesAllowed) {
        this.voicePlatesAllowed = voicePlatesAllowed;
    }

    public void setVolumeControl(boolean volumeControl) {
        this.volumeControl = volumeControl;
    }

    public void setWorldVolume(float worldVolume) {
        this.worldVolume = worldVolume;
    }

}

