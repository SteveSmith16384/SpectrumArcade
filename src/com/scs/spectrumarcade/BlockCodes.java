package com.scs.spectrumarcade;

import com.scs.spectrumarcade.blocks.AndroidsWall;
import com.scs.spectrumarcade.blocks.AntAttackBlock;
import com.scs.spectrumarcade.blocks.BrickBlock;
import com.scs.spectrumarcade.blocks.ConveyorBlock;
import com.scs.spectrumarcade.blocks.DebugBlock;
import com.scs.spectrumarcade.blocks.EATFOuterWall;
import com.scs.spectrumarcade.blocks.EATFSolidBlock;
import com.scs.spectrumarcade.blocks.ExitBlock;
import com.scs.spectrumarcade.blocks.FenceBlock;
import com.scs.spectrumarcade.blocks.GauntletDoor;
import com.scs.spectrumarcade.blocks.GauntletWall;
import com.scs.spectrumarcade.blocks.GrassCutBlock;
import com.scs.spectrumarcade.blocks.GrassLongBlock;
import com.scs.spectrumarcade.blocks.LavaBlock;
import com.scs.spectrumarcade.blocks.MinedOutPlain;
import com.scs.spectrumarcade.blocks.MinedOutWalkedOn;
import com.scs.spectrumarcade.blocks.MotosCyanBlock;
import com.scs.spectrumarcade.blocks.MotosMagentaBlock;
import com.scs.spectrumarcade.blocks.MotosWhiteBlock;
import com.scs.spectrumarcade.blocks.MotosYellowBlock;
import com.scs.spectrumarcade.blocks.RedFloorBlockPxl;
import com.scs.spectrumarcade.blocks.RedFloorBlockUDG;
import com.scs.spectrumarcade.blocks.RoadBlock;
import com.scs.spectrumarcade.blocks.SandBlock;
import com.scs.spectrumarcade.blocks.SplatBlock;
import com.scs.spectrumarcade.blocks.StockCarWallCyan;
import com.scs.spectrumarcade.blocks.StockCarWallCyanTransp;
import com.scs.spectrumarcade.blocks.TrailblazerJump;
import com.scs.spectrumarcade.blocks.TrailblazerNormal1;
import com.scs.spectrumarcade.blocks.TrailblazerNormal2;
import com.scs.spectrumarcade.blocks.TrailblazerNormal3;
import com.scs.spectrumarcade.blocks.TrailblazerNudgeLeft;
import com.scs.spectrumarcade.blocks.TrailblazerNudgeRight;
import com.scs.spectrumarcade.blocks.TrailblazerSlowDown;
import com.scs.spectrumarcade.blocks.TrailblazerSpeedUp;

import mygame.blocks.IBlock;

public class BlockCodes {

	public static final int BRICK = 1;
	public static final int RED_FLOOR_UDG = 2;
	public static final int EXIT = 3;	
	public static final int CONVEYOR = 4;
	public static final int DEBUG = 6;
	public static final int FENCE = 7;
	public static final int ANT_ATTACK = 8;
	public static final int SPLAT = 9;
	public static final int RED_FLOOR_PXL = 10;
	public static final int EATF_SOLID = 11;
	//public static final int EATF_WEAK = 12; // todo - not required
	public static final int EATF_OUTER_WALL = 13;
	public static final int MINED_OUT_FRESH = 14;
	public static final int MINED_OUT_WALKED_ON = 15;
	public static final int MOTOS_WHITE = 16;
	public static final int MOTOS_CYAN = 17;
	public static final int MOTOS_MAGENTA = 18;
	public static final int MOTOS_YELLOW = 19;
	public static final int STOCK_CAR_WALL_CYAN = 20;
	public static final int GAUNTLET_WALL = 21;
	public static final int GAUNTLET_DOOR = 22;
	public static final int GRASS_LONG = 23;
	public static final int GRASS_CUT = 24;
	public static final int TRAILBLAZER_NORMAL1 = 25;
	public static final int TRAILBLAZER_NORMAL2 = 26;
	public static final int TRAILBLAZER_NORMAL3 = 27;
	public static final int TRAILBLAZER_SPEED_UP = 28;
	public static final int TRAILBLAZER_SLOW_DOWN = 29;
	public static final int TRAILBLAZER_NUDGE_LEFT = 30;
	public static final int TRAILBLAZER_NUDGE_RIGHT = 31;
	public static final int TRAILBLAZER_JUMP = 32;
	public static final int STOCK_CAR_WALL_CYAN_TRANSP = 33;
	public static final int ANDROIDS_WALL = 34;
	public static final int SAND = 35;
	public static final int ROAD = 36;
	public static final int LAVA = 37;

	
	public static Class<? extends IBlock> getClassFromCode(int code) {
		switch (code) {
		case BRICK: return BrickBlock.class;
		case RED_FLOOR_UDG: return RedFloorBlockUDG.class;
		case CONVEYOR: return ConveyorBlock.class;
		case EXIT: return ExitBlock.class;
		case DEBUG: return DebugBlock.class;
		case FENCE: return FenceBlock.class;
		case ANT_ATTACK: return AntAttackBlock.class;
		case SPLAT: return SplatBlock.class;
		case RED_FLOOR_PXL: return RedFloorBlockPxl.class;
		case EATF_SOLID: return EATFSolidBlock.class;
		//case EATF_WEAK: return EATFWeakBlock.class;
		case EATF_OUTER_WALL: return EATFOuterWall.class;
		case MINED_OUT_FRESH: return MinedOutPlain.class;
		case MINED_OUT_WALKED_ON: return MinedOutWalkedOn.class;
		case MOTOS_WHITE: return MotosWhiteBlock.class;
		case MOTOS_CYAN: return MotosCyanBlock.class;
		case MOTOS_MAGENTA: return MotosMagentaBlock.class;
		case MOTOS_YELLOW: return MotosYellowBlock.class;
		case STOCK_CAR_WALL_CYAN: return StockCarWallCyan.class;
		case GAUNTLET_WALL: return GauntletWall.class;
		case GAUNTLET_DOOR: return GauntletDoor.class;
		case GRASS_LONG: return GrassLongBlock.class;
		case GRASS_CUT: return GrassCutBlock.class;
		case TRAILBLAZER_NORMAL1: return TrailblazerNormal1.class;
		case TRAILBLAZER_NORMAL2: return TrailblazerNormal2.class;
		case TRAILBLAZER_NORMAL3: return TrailblazerNormal3.class;
		case TRAILBLAZER_SPEED_UP: return TrailblazerSpeedUp.class;
		case TRAILBLAZER_SLOW_DOWN: return TrailblazerSlowDown.class;
		case TRAILBLAZER_NUDGE_LEFT: return TrailblazerNudgeLeft.class;
		case TRAILBLAZER_NUDGE_RIGHT: return TrailblazerNudgeRight.class;
		case TRAILBLAZER_JUMP: return TrailblazerJump.class;
		case STOCK_CAR_WALL_CYAN_TRANSP: return StockCarWallCyanTransp.class;
		case ANDROIDS_WALL: return AndroidsWall.class;
		case SAND: return SandBlock.class;
		case ROAD: return RoadBlock.class;
		case LAVA: return LavaBlock.class;

		default: throw new RuntimeException("code: " + code);
		}
	}
}
