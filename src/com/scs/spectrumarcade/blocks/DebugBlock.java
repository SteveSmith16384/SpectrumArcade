package com.scs.spectrumarcade.blocks;

import mygame.blocks.IBlock;
import mygame.blocks.IBlockTextureLocator;
import mygame.blocks.SimpleBlockTexture;

public class DebugBlock implements IBlock {

	private final IBlockTextureLocator blockTextureLocator;

	public DebugBlock() {
		blockTextureLocator = new SimpleBlockTexture(4, 0);
	}

	public IBlockTextureLocator getTexture() {
		return blockTextureLocator;
	}

}
