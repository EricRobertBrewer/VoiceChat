/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 */
package net.gliby.voicechat.client.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class IndependentGUITexture {
    public static final IndependentGUITexture TEXTURES = new IndependentGUITexture("gvctextures");
    public static final IndependentGUITexture GUI_WIZARD = new IndependentGUITexture("wizard_gui");
    private static final ResourceLocation steve = new ResourceLocation("textures/entity/steve.png");
    private final Object resource;

    public static void bindClientPlayer(Minecraft mc) {
        mc.getTextureManager().bindTexture(mc.thePlayer.getLocationSkin());
    }

    public static void bindDefaultPlayer(Minecraft mc) {
        mc.getTextureManager().bindTexture(steve);
    }

    public static void bindPlayer(Minecraft mc, Entity entity) {
        mc.getTextureManager().bindTexture(((AbstractClientPlayer)entity).getLocationSkin());
    }

    public IndependentGUITexture(String texture) {
        this.resource = new ResourceLocation("gvc:textures/gui/" + texture + ".png");
    }

    public void bindTexture(Minecraft mc) {
        mc.getTextureManager().bindTexture((ResourceLocation)this.getTexture());
    }

    public Object getTexture() {
        return this.resource;
    }
}

