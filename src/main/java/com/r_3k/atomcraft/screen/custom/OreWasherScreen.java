package com.r_3k.atomcraft.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.r_3k.atomcraft.AtomCraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class OreWasherScreen extends AbstractContainerScreen<OreWasherMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AtomCraft.MOD_ID,"textures/gui/ore_washer/ore_washer_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AtomCraft.MOD_ID,"textures/gui/arrow_progress.png");

    public OreWasherScreen(OreWasherMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.imageWidth = 176;
        this.imageHeight = 180;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(ARROW_TEXTURE,x + 77, y + 46, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("Ore Washer"), 8, 6, -12829636, false);
    }
}