package com.scs.spectrumarcade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.prefs.BackingStoreException;

import com.aurellem.capture.Capture;
import com.aurellem.capture.IsoTimer;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.VideoRecorderAppState;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.font.BitmapFont;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.LightList;
import com.jme3.light.SpotLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera.FrustumIntersect;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import com.scs.spectrumarcade.entities.AbstractEntity;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.Player;
import com.scs.spectrumarcade.levels.ArcadeRoom;
import com.scs.spectrumarcade.levels.ILevelGenerator;

public class SpectrumArcade extends SimpleApplication implements ActionListener, PhysicsCollisionListener {

	// Our movement speed
	private static final float speed = 3f;
	private static final float strafeSpeed = 3f;

	public static final Random rnd = new Random();

	public List<IEntity> entities = new ArrayList<IEntity>();
	public BulletAppState bulletAppState;

	private Vector3f walkDirection = new Vector3f();
	private boolean left = false, right = false, up = false, down = false;
	public Player player;

	//Temporary vectors used on each frame.
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();

	private SpotLight spotlight;
	//private HUD hud;
	private boolean game_over = false;
	private boolean player_won = false;
	private VideoRecorderAppState video_recorder;
	public boolean started = false;
	
	private DirectionalLight sun;
	/*
	private AudioNode ambient_node;
	private AudioNode game_over_sound_node;
	public AudioNode thunderclap_sound_node;
	private AudioNode scary_sound1, scary_sound2, bens_sfx;
	private float next_scary_sound = 10;
	 */


	public static void main(String[] args) {
		try {
			AppSettings settings = new AppSettings(true);
			try {
				settings.load(Settings.NAME);
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
			settings.setTitle(Settings.NAME + " (v" + Settings.VERSION + ")");
			if (Settings.SHOW_LOGO) {
				settings.setSettingsDialogImage("/ad_logo.png");
			} else {
				settings.setSettingsDialogImage(null);
			}

			SpectrumArcade app = new SpectrumArcade();
			app.setSettings(settings);
			app.setPauseOnLostFocus(true);

			File video, audio;
			if (Settings.RECORD_VID) {
				app.setTimer(new IsoTimer(60));
				video = File.createTempFile("JME-water-video", ".avi");
				audio = File.createTempFile("JME-water-audio", ".wav");
				Capture.captureVideo(app, video);
				Capture.captureAudio(app, audio);
			}

			app.start();

			/*if (Settings.RECORD_VID) {
				System.out.println("Video saved at " + video.getCanonicalPath());
				System.out.println("Audio saved at " + audio.getCanonicalPath());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void simpleInitApp() {
		assetManager.registerLocator("assets/", FileLocator.class); // default
		//assetManager.registerLocator("assets/Textures/", FileLocator.class);

		BitmapFont guiFont_small = assetManager.loadFont("Interface/Fonts/Console.fnt");

		cam.setFrustumPerspective(45f, (float) cam.getWidth() / cam.getHeight(), 0.01f, Settings.CAM_DIST);

		// Set up Physics
		bulletAppState = new BulletAppState(PhysicsSpace.BroadphaseType.DBVT);
		bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
		stateManager.attach(bulletAppState);
		//bulletAppState.getPhysicsSpace().enableDebug(assetManager);

		viewPort.setBackgroundColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1f));

		setUpKeys();
		setUpLight();

		FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
		/*if (Settings.DEBUG_LIGHT == false) {
			FogFilter fog = new FogFilter(ColorRGBA.Red, 1f, 2f);//Settings.CAM_DIST/2);
			fog.setFogDistance(2f);
			//fpp.addFilter(fog);
		}*/
		viewPort.addProcessor(fpp);

		player = new Player(this, 5, 5);
		rootNode.attachChild(player.getMainNode());
		this.entities.add(player);

		bulletAppState.getPhysicsSpace().addCollisionListener(this);

		final int SHADOWMAP_SIZE = 1024;
		DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(getAssetManager(), SHADOWMAP_SIZE, 2);
		dlsr.setLight(sun);
		this.viewPort.addProcessor(dlsr);

		//hud = new HUD(this, this.getAssetManager(), cam.getWidth(), cam.getHeight(), guiFont_small);
		//this.guiNode.attachChild(hud);
		//this.entities.add(hud);

		/*
		// Audio nodes
		ambient_node = new AudioNode(assetManager, "Sound/horror ambient.ogg", true);
		ambient_node.setPositional(false);
		ambient_node.setVolume(0.3f);
		ambient_node.setLooping(true);
		this.rootNode.attachChild(ambient_node);
		try {
			ambient_node.play();
		} catch (java.lang.IllegalStateException ex) {
			// Unable to play sounds - no audiocard/speakers?
		}

		 */
		stateManager.getState(StatsAppState.class).toggleStats(); // Turn off stats

		ILevelGenerator level = new ArcadeRoom();//AntAttackLevel();
		try {
			level.generateLevel(this);
			level.moveAvatarToStartPosition(player);
			this.getViewPort().setBackgroundColor(level.getBackgroundColour());
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	public void addEntity(AbstractEntity e) {
		this.entities.add(e);
		if (e instanceof AbstractPhysicalEntity) {
			AbstractPhysicalEntity ape = (AbstractPhysicalEntity)e;
			this.getRootNode().attachChild(ape.getMainNode());
		}
	}


	@Override
	public void simpleUpdate(float tpf_secs) {
		/*
		 * The direction of character is determined by the camera angle
		 * the Y direction is set to zero to keep our character from
		 * lifting of terrain. For free flying games simply ad speed 
		 * to Y axis
		 */
		if (!game_over) {
			camDir.set(cam.getDirection()).multLocal(speed, 0.0f, speed);
			camLeft.set(cam.getLeft()).multLocal(strafeSpeed);
			walkDirection.set(0, 0, 0);
			player.walking = up || down || left || right;
			if (left) {
				walkDirection.addLocal(camLeft);
			}
			if (right) {
				walkDirection.addLocal(camLeft.negate());
			}
			if (up) {
				walkDirection.addLocal(camDir);
			}
			if (down) {
				walkDirection.addLocal(camDir.negate());
			}
			player.playerControl.setWalkDirection(walkDirection);
			/*
			next_scary_sound -= tpf_secs;
			if (next_scary_sound <= 0) {
				playRandomScarySound();
				next_scary_sound = 5 + rnd.nextInt(10);
			}
			 */
		}

		for(IEntity ip : entities) {
			ip.process(tpf_secs);
		}

		/*
		 * By default the location of the box is on the bottom of the terrain
		 * we make a slight offset to adjust for head height.
		 */
		Vector3f vec = player.getMainNode().getWorldTranslation();
		cam.setLocation(new Vector3f(vec.x, vec.y + Settings.PLAYER_HEIGHT * .8f, vec.z)); // Drop cam slightly so we're looking out of our eye level

		if (spotlight != null) {
			this.spotlight.setPosition(cam.getLocation());
			this.spotlight.setDirection(cam.getDirection());
		}

		started = true;
	}


	private void setUpLight() {
		// Remove existing lights
		this.rootNode.getWorldLightList().clear(); //this.rootNode.getWorldLightList().size();
		LightList list = this.rootNode.getWorldLightList();
		for (Light it : list) {
			this.rootNode.removeLight(it);
		}

		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White);
		rootNode.addLight(al);

		sun = new DirectionalLight();
		sun.setColor(ColorRGBA.Yellow);
		sun.setDirection(new Vector3f(.5f, -1f, .5f).normalizeLocal());
		rootNode.addLight(sun);

		/*this.spotlight = new SpotLight();
			spotlight.setColor(ColorRGBA.White.mult(3f));
			spotlight.setSpotRange(10f);
			spotlight.setSpotInnerAngle(FastMath.QUARTER_PI / 8);
			spotlight.setSpotOuterAngle(FastMath.QUARTER_PI / 2);
			rootNode.addLight(spotlight);
			*/
	}


	/** We over-write some navigational key mappings here, so we can
	 * add physics-controlled walking and jumping: */
	private void setUpKeys() {
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping(Settings.KEY_RECORD, new KeyTrigger(KeyInput.KEY_R));

		inputManager.addListener(this, "Left");
		inputManager.addListener(this, "Right");
		inputManager.addListener(this, "Up");
		inputManager.addListener(this, "Down");
		inputManager.addListener(this, "Jump");
		inputManager.addListener(this, Settings.KEY_RECORD);
	}


	/** These are our custom actions triggered by key presses.
	 * We do not walk yet, we just keep track of the direction the user pressed. */
	public void onAction(String binding, boolean isPressed, float tpf) {
		if (this.game_over == false) {
			if (binding.equals("Left")) {
				left = isPressed;
			} else if (binding.equals("Right")) {
				right = isPressed;
			} else if (binding.equals("Up")) {
				up = isPressed;
				//p("player: " + this.player.getGeometry().getWorldTranslation());
			} else if (binding.equals("Down")) {
				down = isPressed;
			} else if (binding.equals("Jump")) {
				if (isPressed) { 
					player.playerControl.jump(); 
				}
			} else if (binding.equals(Settings.KEY_RECORD)) {
				if (isPressed) {
					if (video_recorder == null) {
						//log("RECORDING VIDEO");
						video_recorder = new VideoRecorderAppState();
						stateManager.attach(video_recorder);
						/*if (Statics.MUTE) {
						log("Warning: sounds are muted");
					}*/
					} else {
						//log("STOPPED RECORDING");
						stateManager.detach(video_recorder);
						video_recorder = null;
					}
				}
			}
		}
	}


	public FrustumIntersect getInsideOutside(AbstractPhysicalEntity entity) {
		FrustumIntersect insideoutside = cam.contains(entity.getMainNode().getWorldBound());
		return insideoutside;
	}


	@Override
	public void collision(PhysicsCollisionEvent event) {
		System.out.println(event.getObjectA().getUserObject().toString() + " collided with " + event.getObjectB().getUserObject().toString());

		Spatial ga = (Spatial)event.getObjectA().getUserObject(); 
		AbstractPhysicalEntity a = ga.getUserData(Settings.ENTITY);
		while (a == null && ga.getParent() != null) {
			ga = ga.getParent();
			a = ga.getUserData(Settings.ENTITY);
		}
		if (a == null) {
			//throw new RuntimeException("Geometry " + ga.getName() + " has no entity");
			return;
		}

		Spatial gb = (Spatial)event.getObjectB().getUserObject(); 
		AbstractPhysicalEntity b = gb.getUserData(Settings.ENTITY);
		while (b == null && gb.getParent() != null) {
			gb = gb.getParent();
			b = gb.getUserData(Settings.ENTITY);
		}
		if (b == null) {
			//throw new RuntimeException("Geometry " + ga.getName() + " has no entity");
			return;
		}

		CollisionLogic.collision(this, a, b);
	}


	public boolean isGameOver() {
		return this.game_over;
	}


	public boolean hasPlayerWon() {
		return this.player_won;
	}

	/*
	private void playRandomScarySound() {
		if (Settings.USE_BENS_SOUND) {
			this.bens_sfx.playInstance();
		} else {
			int i = rnd.nextInt(2);
			switch (i) {
			case 0:
				this.scary_sound1.playInstance();
				break;
			case 1:
				this.scary_sound2.playInstance();
				break;
			}
		}
	}
	 */

	public static void p(String s) {
		System.out.println(System.currentTimeMillis() + ": " + s);
	}


	public static void pe(String s) {
		System.err.println(System.currentTimeMillis() + ": " + s);
	}


	public BulletAppState getBulletAppState() {
		return bulletAppState;
	}


	public void removeEntity(IEntity e) {
		this.entities.remove(e);
	}

}
