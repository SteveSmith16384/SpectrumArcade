package com.scs.spectrumarcade.entities;

import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.scs.spectrumarcade.BlockCodes;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.blocks.AntAttackBlock;
import com.scs.spectrumarcade.blocks.BrickBlock;
import com.scs.spectrumarcade.blocks.ConveyorBlock;
import com.scs.spectrumarcade.blocks.DebugBlock;
import com.scs.spectrumarcade.blocks.EATFSolidBlock;
import com.scs.spectrumarcade.blocks.ExitBlock;
import com.scs.spectrumarcade.blocks.FenceBlock;
import com.scs.spectrumarcade.blocks.RedFloorBlockPxl;
import com.scs.spectrumarcade.blocks.RedFloorBlockUDG;
import com.scs.spectrumarcade.blocks.SplatBlock;
import com.scs.spectrumarcade.misc.FibonacciSequence;

import mygame.BlockSettings;
import mygame.blocks.BlockTerrainControl;
import mygame.blocks.ChunkControl;
import mygame.blocks.IBlockTerrainListener;
import mygame.util.Vector3Int;

public class VoxelTerrainEntity extends AbstractPhysicalEntity {

	private static final int CHUNK_SIZE = 16;

	public BlockTerrainControl blocks;
	private float blockSize;
	private int worldSizeBlocks;

	public VoxelTerrainEntity(SpectrumArcade _game, float x, float y, float z, int _worldSizeBlocks, float _blockSize) {
		super(_game, "VoxelTerrainEntity");

		worldSizeBlocks = _worldSizeBlocks;
		blockSize = _blockSize;

		final BlockSettings blockSettings = new BlockSettings();
		blockSettings.setChunkSize(new Vector3Int(CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE));
		blockSettings.setBlockSize(blockSize);
		blockSettings.setMaterial(game.getAssetManager().loadMaterial("Materials/BlockTexture.j3m"));
		int s = worldSizeBlocks / CHUNK_SIZE;
		blockSettings.setWorldSizeInChunks(new Vector3Int(s+1, s+1, s+1));
		blockSettings.setViewDistance(200f);
		blockSettings.texturesPerSheet = Settings.TEX_PER_SHEET;

		blocks = new BlockTerrainControl(blockSettings);
		/*blocks.registerBlock(new BrickBlock());
		blocks.registerBlock(new RedFloorBlockUDG());
		blocks.registerBlock(new RedFloorBlockPxl());
		blocks.registerBlock(new ExitBlock());
		blocks.registerBlock(new ConveyorBlock());
		blocks.registerBlock(new DebugBlock());
		blocks.registerBlock(new FenceBlock());
		blocks.registerBlock(new AntAttackBlock());*/
		//blocks.registerBlock(new SplatBlock());
		//blocks.registerBlock(new EATFSolidBlock());

		this.getMainNode().addControl(blocks);
		this.getMainNode().setLocalTranslation(x, y, z);

		blocks.addListener(new IBlockTerrainListener() {

			@Override
			public void onChunkUpdated(ChunkControl c) {
				Geometry geom = c.getGeometry();
				RigidBodyControl control = geom.getControl(RigidBodyControl.class);
				if(control == null){
					control = new RigidBodyControl(0);
					geom.addControl(control);
					//control.setKinematic(true);
					game.bulletAppState.getPhysicsSpace().add(control);
				}
				control.setCollisionShape(new MeshCollisionShape(geom.getMesh()));
			}
		});
	}


	public void removeBlock(Vector3Int pos) {
		Vector3Int blockPosition = blocks.getPointedBlockLocation(pos);//, false);
		//Globals.p("Removing block at " + blockPosition);
		blocks.removeBlock(blockPosition);

	}


	public void addBlock_Actual(Vector3Int pos, int blockType) {
		Vector3Int blockPosition = blocks.getPointedBlockLocation(pos);//, false);
		//Globals.p("Adding block at " + blockPosition);
		blocks.setBlock(blockPosition, BlockCodes.getClassFromCode(blockType));

	}


	public void addBlock_Block(Vector3Int pos, int blockType) {
		//Vector3Int blockPosition = blocks.getPointedBlockLocation(pos);//, false);
		//Globals.p("Adding block at " + blockPosition);
		blocks.setBlock(pos, BlockCodes.getClassFromCode(blockType));

	}


	public void addRectRange_Actual(int blockType, Vector3Int pos, Vector3Int size) {
		int scale = (int)(1/blockSize);
		pos.multLocal(scale);
		size.multLocal(scale);
		this.addRectRange_Blocks(blockType, pos, size);
	}


	public void addRectRange_Blocks(int blockType, Vector3Int blockPos, Vector3Int size) {
		blocks.setBlockArea(blockPos, size, BlockCodes.getClassFromCode(blockType));

	}


	public void addArrayRange_Blocks(int blockType, Vector3Int blockPos, int[][] heights) {
		blocks.setBlockHeightsFromArray(blockPos, heights, BlockCodes.getClassFromCode(blockType));
	}


	public void addSphereRange_Blocks(int blockType, Vector3f worldPos, int size) {
		Vector3Int blockPos = new Vector3Int(worldPos.subtract(this.mainNode.getWorldTranslation()).multLocal(1/blockSize));
		blocks.setBlockAreaBySphere(blockPos, size, BlockCodes.getClassFromCode(blockType));
	}


	public void removeSphereRange_Blocks(Vector3f worldPos, int size) {
		Vector3Int blockPos = new Vector3Int(worldPos.subtract(this.mainNode.getWorldTranslation()).multLocal(1/blockSize));
		blocks.setBlockAreaBySphere(blockPos, size, null);
	}


	private Vector3Int getBlockPosFromWorldPos(Vector3f worldPos) {
		Vector3Int blockPos = new Vector3Int(worldPos.subtract(this.mainNode.getWorldTranslation()).multLocal(1/blockSize));
		return blockPos;
	}


	public void addRectRange_Fibonacci_Actual(int blockType, Vector3f worldPos, Vector3f worldSize) {
		Vector3Int blockPos = new Vector3Int(worldPos.subtract(this.mainNode.getWorldTranslation()).multLocal(1/blockSize));
		Vector3Int size = new Vector3Int(worldSize.mult( 1 / blockSize));
		FibonacciSequence fs = new FibonacciSequence(1);
		for(int z=0 ; z<size.getZ() ; z++) {
			for(int x=0 ; x<size.getX() ; x++) {
				int depth = 3 + (fs.getNext() % 3);
				blocks.setBlockArea(new Vector3Int(x+blockPos.getX(), blockPos.getY()+8-depth, z+blockPos.getZ()), new Vector3Int(1, depth, 1), BlockCodes.getClassFromCode(blockType));
			}
		}

	}


	@Override
	public void process(float tpfSecs) {
		
	}

}
