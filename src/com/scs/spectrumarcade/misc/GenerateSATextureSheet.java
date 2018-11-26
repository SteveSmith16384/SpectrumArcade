package com.scs.spectrumarcade.misc;

import java.io.IOException;

import com.scs.spectrumarcade.Settings;

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
				{"mm_bricks.png", "redfloor_pxl.png", "conveyor_top.png", "exit.png", "greensun.jpg", "fence.png", "conveyor_side.png", "black.png"}, 
				{"antattack.png", "splat_wall.png", "ericwall.png", "ericwall2.png", "minedout_cyan.png", "redfloor_udg.png", "ericouterwall.png", "white.png"},
				{"motos_cyan.png", "motos_magenta.png", "motos_white.png", "motos_yellow.png"}
		}; // Keep these in the same order!

		TextureSheetGenerator gen = new TextureSheetGenerator();
		gen.generateTextureSheet("assets/Textures", tiles, Settings.TEX_PER_SHEET, 32, "sa_tiles", 4, true);

	}

}
