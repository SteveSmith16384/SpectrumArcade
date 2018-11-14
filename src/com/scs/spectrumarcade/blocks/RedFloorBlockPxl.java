package com.scs.spectrumarcade.blocks;

import mygame.blocks.IBlock;
import mygame.blocks.IBlockTextureLocator;
import mygame.blocks.SimpleBlockTexture;

public class RedFloorBlockPxl implements IBlock {

	private final IBlockTextureLocator blockTextureLocator;

	public RedFloorBlockPxl() {
		blockTextureLocator = new SimpleBlockTexture(1, 0);
	}

	public IBlockTextureLocator getTexture() {
		return blockTextureLocator;
	}

}
