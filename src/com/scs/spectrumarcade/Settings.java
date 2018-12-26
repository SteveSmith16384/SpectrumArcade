package com.scs.spectrumarcade;

public class Settings {

	public static final boolean RECORD_VID = false;
	public static final boolean RELEASE_MODE = true;
	public static final boolean FREE_CAM = false; // For viewing maps
	
	public static final boolean TEST_BOMBS = false;
	public static final boolean TEST_BILLBOARD = false;	
	public static final boolean TEST_BALL_ROLLING = false;
	public static final boolean TEST_ANT_AI = false;
	public static final boolean DEBUG_CLEAR_FORCES = false;
		
	public static final boolean SHOW_LOGO = false;

	public static final String VERSION = "0.01";

	public static final float CAM_DIST = 201f;
	public static final int TEX_PER_SHEET = 16;
	public static final String NAME = "Advanced Spectrum Arcade Simulator";
	
	// Player dimensions
	public static final float PLAYER_HEIGHT = 1.7f;
	public static final float PLAYER_RAD = .35f;
	
	// User Data
	public static final String ENTITY = "Entity";
	

	
	public static void p(String s) {
		System.out.println(s);
	}


	public static void pe(String s) {
		System.err.println(s);
	}

}
