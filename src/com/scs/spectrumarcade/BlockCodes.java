package com.scs.spectrumarcade;

import com.scs.spectrumarcade.blocks.AntAttackBlock;
import com.scs.spectrumarcade.blocks.BrickBlock;
import com.scs.spectrumarcade.blocks.ConveyorBlock;
import com.scs.spectrumarcade.blocks.DebugBlock;
import com.scs.spectrumarcade.blocks.EATFOuterWall;
import com.scs.spectrumarcade.blocks.EATFSolidBlock;
import com.scs.spectrumarcade.blocks.EATFWeakBlock;
import com.scs.spectrumarcade.blocks.ExitBlock;
import com.scs.spectrumarcade.blocks.FenceBlock;
import com.scs.spectrumarcade.blocks.MinedOutPlain;
import com.scs.spectrumarcade.blocks.MinedOutWalkedOn;
import com.scs.spectrumarcade.blocks.RedFloorBlockPxl;
import com.scs.spectrumarcade.blocks.RedFloorBlockUDG;
import com.scs.spectrumarcade.blocks.SplatBlock;

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
	public static final int EATF_WEAK = 12; // todo - not required
	public static final int EATF_OUTER_WALL = 13;
	public static final int MINED_OUT_FRESH = 14;
	public static final int MINED_OUT_WALKED_ON = 15;
	
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
		case EATF_WEAK: return EATFWeakBlock.class;
		case EATF_OUTER_WALL: return EATFOuterWall.class;
		case MINED_OUT_FRESH: return MinedOutPlain.class;
		case MINED_OUT_WALKED_ON: return MinedOutWalkedOn.class;
		default: throw new RuntimeException("code: " + code);
		}
	}
}
