package com.scs.spectrumarcade.misc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;

public class ExtractAAMap {

	public static void main(String args[]) {
		try {
			new ExtractAAMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public ExtractAAMap() throws IOException {
		File f = new File("docs/antattac.sna");
		byte b[] = Files.readAllBytes(f.toPath());

		int pos = 0xC000 - 0x3FE5;

		byte[] map = Arrays.copyOfRange(b,  pos,  pos + 16384);

		PrintWriter writer = new PrintWriter("aa_map.txt", "UTF-8");
		for (int y = 0; y < 8; y++) {
			byte mask = (byte)(0x01 << y);
			for (int z = 0; z < 128; z++) {
				for (int x = 0; x < 128; x++) {
					int i = (map[z*128+x] & mask);
					if (i != 0) {
						System.out.println(x + "," + y + "," + z);
						writer.println(x + "," + y + "," + z);
					} else {
						System.out.println("No block: " + x + "," + y + "," + z);
					}
				}	
			}
		}
		writer.close();

	}

	/*
	 * #include <stdio.h>
#include <stdlib.h>

unsigned char map[16384];

int main(void)
{
	FILE *fp = fopen("antattac.sna", "rb");
	int x,y,z;

	if (!fp)
	{
		perror("antattac.sna");
		return 1;
	}
	fseek(fp, 0xC000 - 0x3FE5, SEEK_SET);
	fread(map, 1, 16384, fp);
	fclose(fp);

	for (z = 0; z < 8; z++)
	{
		unsigned char mask = (0x01 << z);

		for (y = 0; y < 128; y++)
		{
			for (x = 0; x < 128; x++)
			{
				if (map[y*128+x] & mask) 
					printf("%d,%d,%d\n", x, y, z);
			}	
		}
	}	

}
	 */

}
