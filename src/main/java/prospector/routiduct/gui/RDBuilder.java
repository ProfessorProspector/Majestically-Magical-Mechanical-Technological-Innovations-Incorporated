/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2017 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package prospector.routiduct.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import prospector.routiduct.Routiduct;
import reborncore.client.guibuilder.GuiBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prospector
 */
public class RDBuilder extends GuiBuilder {
	public static final ResourceLocation GUI_SHEET = new ResourceLocation(Routiduct.PREFIX + "textures/gui/gui_sheet.png");

	public RDBuilder() {
		super(GUI_SHEET);
	}

	public void drawSlot(GuiScreen gui, int posX, int posY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUI_SHEET);
		gui.drawTexturedModalRect(posX, posY, 150, 0, 18, 18);
	}

	public TextFormatting getPercentageColour(int percentage) {
		if (percentage <= 10) {
			return TextFormatting.RED;
		} else if (percentage >= 75) {
			return TextFormatting.GREEN;
		} else {
			return TextFormatting.YELLOW;
		}
	}

	public int percentage(int MaxValue, int CurrentValue) {
		if (CurrentValue == 0)
			return 0;
		return (int) ((CurrentValue * 100.0f) / MaxValue);
	}
}
