package com.scs.spectrumarcade.blocks;

import mygame.blocks.IBlock;
import mygame.blocks.IBlockTextureLocator;
import mygame.blocks.MultiBlockTexture;

public class ConveyorBlock implements IBlock {

	private final IBlockTextureLocator blockTextureLocator;

	public ConveyorBlock() {
		blockTextureLocator = new MultiBlockTexture(2, 0, 6, 0, 6, 0);
	}

	public IBlockTextureLocator getTexture() {
		return blockTextureLocator;
	}

}
