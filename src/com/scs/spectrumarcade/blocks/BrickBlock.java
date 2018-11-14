package com.scs.spectrumarcade.blocks;

import mygame.blocks.IBlock;
import mygame.blocks.IBlockTextureLocator;
import mygame.blocks.SimpleBlockTexture;

public class BrickBlock implements IBlock {

	private final IBlockTextureLocator blockTextureLocator;

	public BrickBlock() {
		blockTextureLocator = new SimpleBlockTexture(0, 0);
	}

	public IBlockTextureLocator getTexture() {
		return blockTextureLocator;
	}

}
