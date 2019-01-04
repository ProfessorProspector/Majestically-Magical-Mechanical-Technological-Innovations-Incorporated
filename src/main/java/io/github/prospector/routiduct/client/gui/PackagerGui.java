package io.github.prospector.routiduct.client.gui;

import io.github.prospector.routiduct.blockentity.PackagerBlockEntity;
import io.github.prospector.routiduct.container.builder.BuiltContainer;

public class PackagerGui extends GuiBase {

	PackagerBlockEntity blockEntity;

	public PackagerGui(BuiltContainer container) {
		super(container);
		this.blockEntity = (PackagerBlockEntity) container.getBlockEntity();
	}

	@Override
	public void drawSlots() {
		drawSlot(55, 45);
		drawOutputSlot(101, 45);
	}

	@Override
	protected void drawForeground(int i, int i1) {
		super.drawForeground(i, i1);
	}
}
