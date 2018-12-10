package com.scs.spectrumarcade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import ssmith.lang.Functions;

public class MapLoader {

	public static int[][] loadMap(String s) throws FileNotFoundException, IOException, URISyntaxException {
		String text = Functions.readAllFileFromJar(s);
		String[] lines = text.split("\n");

		int mapsize = Integer.parseInt(lines[0].split(",")[0]);
		int[][] mapCode = new int[mapsize][mapsize];

		for (int lineNum=1 ; lineNum<lines.length ; lineNum++) { // Skip line 1
			String line = lines[lineNum];
			String[] tokens = line.split(",");
			for (int x=0 ; x<tokens.length ; x++) {
				String cell = tokens[x];
				int val = Integer.parseInt(cell);
				//try {
					mapCode[x][lineNum-1] = val;
				/*} catch (ArrayIndexOutOfBoundsException ex) { 

				}*/
			}
		}

		return mapCode;
	}
}
