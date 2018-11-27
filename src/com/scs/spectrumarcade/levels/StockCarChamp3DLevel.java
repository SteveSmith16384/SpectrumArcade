package com.scs.spectrumarcade.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.MapLoader;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.minedout.Fence;
import com.scs.spectrumarcade.entities.stockcarchamp.StockCarAvatar;

import mygame.util.Vector3Int;
import ssmith.lang.Functions;

public class StockCarChamp3DLevel extends AbstractLevel implements ILevelGenerator {

	//private static final int MAP_SIZE = 160;

	private VoxelTerrainEntity terrainUDG;


	@Override
	public void generateLevel(SpectrumArcade game, int levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		// Border
		/*terrainUDG.addRectRange_Blocks(BlockCodes.STOCK_CAR_WALL_CYAN, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.STOCK_CAR_WALL_CYAN, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		terrainUDG.addRectRange_Blocks(BlockCodes.STOCK_CAR_WALL_CYAN, new Vector3Int(0, 0, MAP_SIZE-1), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.STOCK_CAR_WALL_CYAN, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
*/
		
		int map[][] = MapLoader.loadMap("maps/stockcarchamp1.csv");
		int MAP_SIZE = map.length;

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE, 1, MAP_SIZE, "Textures/road2.png");
		game.addEntity(floor);

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, MAP_SIZE, 1f);
		game.addEntity(terrainUDG);
		

		// Create heightmap
		for (int z=0 ; z<map[0].length ; z++) {
			for (int x=0 ; x<map.length ; x++) {
				try {
					if (map[x][z] == 1) {
						terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.STOCK_CAR_WALL_CYAN);
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}


	@Override
	public Vector3f getAvatarStartPos() {
		return new Vector3f(5, 2f, 5);
	}


	@Override
	public Avatar createAndPositionAvatar() {
		return new StockCarAvatar(game, 5, 2f, 5);
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Blue;
	}


	@Override
	public void process(float tpfSecs) {
		// Do nothing
	}


	@Override
	public String getHUDText() {
		return "";
	}


	@Override
	public void setInitialCameraDir(Camera cam) {
		//cam.lookAt(cam.getLocation().add(new Vector3f(0, 0, 1)), Vector3f.UNIT_Y);
	}

}
