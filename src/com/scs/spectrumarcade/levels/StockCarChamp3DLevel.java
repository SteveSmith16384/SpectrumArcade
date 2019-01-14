package com.scs.spectrumarcade.levels;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.CameraSystem;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.MapLoader;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.FloorOrCeiling;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.stockcarchamp.StartFinishLine;
import com.scs.spectrumarcade.entities.stockcarchamp.StockCarAICar;
import com.scs.spectrumarcade.entities.stockcarchamp.StockCarAvatar;

import mygame.util.Vector3Int;

public class StockCarChamp3DLevel extends AbstractLevel implements ILevelGenerator {

	private static final int SQ_SIZE = 1;

	public VoxelTerrainEntity terrainUDG;
	private List<Point> startPos;
	public ArrayList<Vector3f> waypoints;

	@Override
	public void generateLevel(SpectrumArcade game, int levelNum) throws FileNotFoundException, IOException, URISyntaxException {
		/*camSys = new CameraSystem(game, true, 3f);
		camSys.setupFollowCam(3, 0, false);*/

		// Border
		/*terrainUDG.addRectRange_Blocks(BlockCodes.STOCK_CAR_WALL_CYAN, new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.STOCK_CAR_WALL_CYAN, new Vector3Int(0, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		terrainUDG.addRectRange_Blocks(BlockCodes.STOCK_CAR_WALL_CYAN, new Vector3Int(0, 0, MAP_SIZE-1), new Vector3Int(MAP_SIZE, 1, 1));
		terrainUDG.addRectRange_Blocks(BlockCodes.STOCK_CAR_WALL_CYAN, new Vector3Int(MAP_SIZE, 0, 0), new Vector3Int(1, 1, MAP_SIZE));
		 */

		int map[][] = MapLoader.loadMap("maps/stockcarchamp" + levelNum + ".csv");
		int MAP_SIZE = map.length;

		FloorOrCeiling floor = new FloorOrCeiling(game, 0, 0, 0, MAP_SIZE*SQ_SIZE, 1, MAP_SIZE*SQ_SIZE, "Textures/road2.png");
		game.addEntity(floor);

		terrainUDG = new VoxelTerrainEntity(game, 0f, 0f, 0f, new Vector3Int(MAP_SIZE, 1, MAP_SIZE), 16, SQ_SIZE, 1f);
		game.addEntity(terrainUDG);

		waypoints = new ArrayList<>();
		startPos = new ArrayList<>();
		
		ArrayList<Integer> loadedWP = new ArrayList<>();

		for (int z=0 ; z<map[0].length ; z++) {
			for (int x=0 ; x<map.length ; x++) {
				//try {
				if (map[x][z] == 1) {
					terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.STOCK_CAR_WALL_CYAN_TRANSP);
				} else if (map[x][z] == 99) {
					//terrainUDG.addBlock_Block(new Vector3Int(x, 0, z), BlockCodes.START_FINISH);
					StartFinishLine sfl = new StartFinishLine(game, z, x);
					Globals.p("Add sfl to " + x + "," + z);
					game.addEntity(sfl);
				} else if (map[x][z] < 0) {
					this.startPos.add(new Point(x*SQ_SIZE, z*SQ_SIZE));
				} else if (map[x][z] == 0) {
					// Do nothing
				} else {
					//waypoints[map[x][z]-2]= new Vector3f(x*SQ_SIZE, .5f, z*SQ_SIZE);
					int pos = map[x][z]-2;
					if (loadedWP.contains(pos)) {
						throw new RuntimeException("Already loaded WP " + pos);
					}
					loadedWP.add(pos);
					while (waypoints.size() <= pos) {
						waypoints.add(new Vector3f());
					}
					waypoints.remove(pos);
					waypoints.add(pos, new Vector3f(x*SQ_SIZE, .5f, z*SQ_SIZE));
				}
				/*} catch (ArrayIndexOutOfBoundsException ex) {
					ex.printStackTrace();
				}*/
			}
		}

		// Create AI cars
		for (int i=0 ; i<startPos.size()-1 ; i++) {
			Point p = this.startPos.get(i+1);
			StockCarAICar aicar = new StockCarAICar(game, this, p.x, 2f, p.y, this.waypoints.get(0), i+2);
			game.addEntity(aicar);
		}
		
	}


	@Override
	public Vector3f getAvatarStartPos() {
		Point p = this.startPos.get(0);
		return new Vector3f(p.x, 2f, p.y);
	}


	@Override
	public IAvatar createAndPositionAvatar() {
		Point p = this.startPos.get(0);
		return new StockCarAvatar(game, p.x, 2f, p.y, this.waypoints.get(0));
	}


	@Override
	public ColorRGBA getBackgroundColour() {
		return ColorRGBA.Blue;
	}


	@Override
	public void process(float tpfSecs) {
		//camSys.process(game.getCamera(), game.player);

	}


	@Override
	public String getHUDText() {
		return "Laps: [todo]";
	}

/*
	@Override
	public void setInitialCameraDir(Camera cam) {
		// No, camsys does it
	}*/


	public void setupCameraSystem(CameraSystem sys) {
		sys.setupCam(3f, 0, false, 1f);
	}
	


}
