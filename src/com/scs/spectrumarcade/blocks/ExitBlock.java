package com.scs.spectrumarcade.blocks;

import mygame.blocks.IBlock;
import mygame.blocks.IBlockTextureLocator;
import mygame.blocks.SimpleBlockTexture;

public class ExitBlock implements IBlock {

	private final IBlockTextureLocator blockTextureLocator;

	public ExitBlock() {
		blockTextureLocator = new SimpleBlockTexture(3, 0);
	}

	public IBlockTextureLocator getTexture() {
		return blockTextureLocator;
	}

}
