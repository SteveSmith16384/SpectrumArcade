package com.scs.spectrumarcade.misc;

import java.io.IOException;

import mygame.texturesheet.TextureSheetGenerator;

public class GenerateSATextureSheet {

	public static void main(String[] args) {
		try {
			new GenerateSATextureSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public GenerateSATextureSheet() throws IOException {
		String[][] tiles = {
				{"bricks.png", "redfloor_pxl.png", "conveyor_top.png", "exit.png", "greensun.jpg", "fence.png", "conveyor_side.png"}, 
				{"antattack.png", "splat.png", "ericwall.png", "ericwall2.png", "minedout_cyan.png", "redfloor_udg.png"}
		}; // Keep these in the same order!

		TextureSheetGenerator gen = new TextureSheetGenerator();
		gen.generateTextureSheet("assets/Textures", tiles, 16, 32, "mm_tiles", 4);

	}

}
