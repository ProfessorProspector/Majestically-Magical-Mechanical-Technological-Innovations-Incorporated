package prospector.routiduct.api;

import prospector.routiduct.block.tiles.TileRoutiduct;

import javax.annotation.Nonnull;

/**
 * Created by Prospector
 */
public interface IProtocolProvider {
	/**
	 * @return the block's EnumProtocol
	 */
	@Nonnull
	public abstract EnumProtocol getProtocol();

	/**
	 * Runs every tick on the tile, put the stuff the block does here. ONLY WORKS IF IT HAS A TILE!!
	 */
	default void update(TileRoutiduct tile) {

	}
}
