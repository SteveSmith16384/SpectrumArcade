package com.scs.spectrumarcade.blocks;

import mygame.blocks.IBlock;
import mygame.blocks.IBlockTextureLocator;
import mygame.blocks.SimpleBlockTexture;

public class RedFloorBlockUDG implements IBlock {

	private final IBlockTextureLocator blockTextureLocator;

	public RedFloorBlockUDG() {
		blockTextureLocator = new SimpleBlockTexture(1, 0);
	}

	public IBlockTextureLocator getTexture() {
		return blockTextureLocator;
	}

}
