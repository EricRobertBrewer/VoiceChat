/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 */
package net.gliby.voicechat.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class ModPackSettings {
    public GVCModPackInstructions init() throws UnsupportedEncodingException {
        InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/modpack.json"), "UTF-8");
        Gson gson = new GsonBuilder().create();
        return (GVCModPackInstructions)gson.fromJson((Reader)reader, GVCModPackInstructions.class);
    }

    public class UIContainer {
        public float X;
        public float Y;
        public float SCALE;
        public int TYPE;

        public String toString() {
            return "X: " + this.X + ", Y: " + this.Y + ", SCALE: " + this.SCALE + ", TYPE: " + this.TYPE;
        }
    }

    public class GVCModPackInstructions {
        public UIContainer VOICE_PLATE;
        public UIContainer SPEAK_ICON;
        public boolean VOLUME_CONTROL;
        public boolean SHOW_PLATES;
        public boolean SHOW_PLAYER_ICONS;
        public float WORLD_VOLUME;
        public float UI_OPACITY;
        public int ID;

        public String toString() {
            return "PLATES: " + this.VOICE_PLATE + ", SPEAK ICON: " + this.SPEAK_ICON + ", WORLD VOLUME: " + this.WORLD_VOLUME + ", UI OPACITY: " + this.UI_OPACITY + ", VOLUME CONTROL: " + this.VOLUME_CONTROL + ", PLATES ENABLED: " + this.SHOW_PLATES + ", ICONS ENABLED: " + this.SHOW_PLAYER_ICONS;
        }
    }

}

