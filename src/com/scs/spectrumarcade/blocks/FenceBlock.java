package com.scs.spectrumarcade.blocks;

import mygame.blocks.IBlock;
import mygame.blocks.IBlockTextureLocator;
import mygame.blocks.SimpleBlockTexture;

public class FenceBlock implements IBlock {

	private final IBlockTextureLocator blockTextureLocator;

	public FenceBlock() {
		blockTextureLocator = new SimpleBlockTexture(5, 0);
	}

	public IBlockTextureLocator getTexture() {
		return blockTextureLocator;
	}

}
