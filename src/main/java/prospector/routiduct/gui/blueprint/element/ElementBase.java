package prospector.routiduct.gui.blueprint.element;

import jline.internal.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import prospector.routiduct.Routiduct;
import prospector.routiduct.gui.GuiRoutiduct;
import prospector.routiduct.gui.Sprite;

public class ElementBase {

	protected final int x;
	protected final int y;
	protected final Sprite sprite;

	public ElementBase(Sprite sprite, int x, int y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

	public ElementBase(int x, int y) {
		this(null, x, y);
	}

	/**
	 * @return the element's sprite **MAY BE NULL**
	 */
	@Nullable
	public Sprite getSprite() {
		return sprite;
	}

	@SideOnly(Side.CLIENT)
	public void draw(GuiRoutiduct gui) {
		if (sprite != null) {
			GlStateManager.color(1F, 1F, 1F);
			Routiduct.proxy.getGuiAssembler().setTextureSheet(sprite.textureLocation);
			gui.drawTexturedModalRect(x + gui.getOffsetFactorX(), y + gui.getOffsetFactorY(), sprite.x, sprite.y, sprite.width, sprite.height);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
