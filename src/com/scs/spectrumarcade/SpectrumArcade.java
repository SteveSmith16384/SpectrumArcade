package com.scs.spectrumarcade;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
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
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
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
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;
import com.scs.spectrumarcade.levels.ILevelGenerator;

public class SpectrumArcade extends SimpleApplication implements ActionListener, PhysicsCollisionListener {

	public static final Random rnd = new Random();

	public List<IEntity> entities = new ArrayList<IEntity>();
	public List<IEntity> entitiesToRemove = new LinkedList<IEntity>();
	public BulletAppState bulletAppState;

	public Avatar player;

	private SpotLight spotlight;
	private HUD hud;
	private boolean game_over = false;
	private boolean player_won = false;
	private VideoRecorderAppState video_recorder;
	public boolean started = false;

	private DirectionalLight sun;
	private GameData gameData = new GameData();
	private ILevelGenerator level;
	private boolean[] abilityActivated = new boolean[3];

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
				settings.setSettingsDialogImage("todo");
			} else {
				settings.setSettingsDialogImage(null);
			}

			SpectrumArcade app = new SpectrumArcade();
			app.setSettings(settings);
			//app.setPauseOnLostFocus(true);

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

		//BitmapFont guiFont_small = assetManager.loadFont("Interface/Fonts/Console.fnt");

		cam.setFrustumPerspective(45f, (float) cam.getWidth() / cam.getHeight(), 0.01f, Settings.CAM_DIST);

		// Set up Physics
		bulletAppState = new BulletAppState();//PhysicsSpace.BroadphaseType.DBVT);
		//bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
		stateManager.attach(bulletAppState);
		//bulletAppState.getPhysicsSpace().enableDebug(assetManager);

		//viewPort.setBackgroundColor(new ColorRGBA(0.1f, 0.1f, 0.1f, 1f));

		setUpKeys();
		setUpLight();

		FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
		/*if (Settings.DEBUG_LIGHT == false) {
			FogFilter fog = new FogFilter(ColorRGBA.Red, 1f, 2f);//Settings.CAM_DIST/2);
			fog.setFogDistance(2f);
			//fpp.addFilter(fog);
		}*/
		viewPort.addProcessor(fpp);

		bulletAppState.getPhysicsSpace().addCollisionListener(this);

		final int SHADOWMAP_SIZE = 1024;
		DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(getAssetManager(), SHADOWMAP_SIZE, 2);
		dlsr.setLight(sun);
		this.viewPort.addProcessor(dlsr);

		hud = new HUD(this, cam);
		this.guiNode.attachChild(hud);

		stateManager.getState(StatsAppState.class).toggleStats(); // Turn off stats

		level = new EricAndTheFloatersLevel(this);//MinedOutLevel(this); // TurboEspritLevel(this);//ArcadeRoom(this);//AntAttackLevel(this); //SplatLevel();//();//
		this.startNewLevel(level);
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
		sun.setColor(ColorRGBA.White);
		sun.setDirection(new Vector3f(-.5f, -1f, -.5f).normalizeLocal());
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

		inputManager.addMapping("Ability1", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(this, "Ability1");

	}


	public void startNewLevel(ILevelGenerator level) {
		try {
			// Clear previous
			this.getBulletAppState().getPhysicsSpace().removeAll(this.getRootNode());
			this.rootNode.detachAllChildren();
			//this.getBulletAppState().getPhysicsSpace().clearForces();

			level.generateLevel(this);
			player = level.createAndPositionAvatar();//.moveAvatarToStartPosition(player);
			this.addEntity((AbstractPhysicalEntity)player);
			this.getViewPort().setBackgroundColor(level.getBackgroundColour());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}


	public void addEntity(AbstractEntity e) {
		this.entities.add(e);
		if (e instanceof AbstractPhysicalEntity) {
			AbstractPhysicalEntity ape = (AbstractPhysicalEntity)e;
			this.getRootNode().attachChild(ape.getMainNode());
			bulletAppState.getPhysicsSpace().add(ape.getMainNode());
		}
	}


	@Override
	public void simpleUpdate(float tpfSecs) {
		if (tpfSecs > 1f) {
			tpfSecs = 1f;
		}

		for (int i=1 ; i<=2 ; i++) {
			if (this.abilityActivated[i]) {
				this.player.activateAbility(i);
			}
		}
		
		while (this.entitiesToRemove.size() > 0) {
			IEntity e = this.entitiesToRemove.remove(0);
			this.actuallyRemoveEntity(e);
		}

		level.process(tpfSecs);

		for(IEntity ip : entities) {
			ip.process(tpfSecs);
		}

		hud.processByClient(tpfSecs);
		/*
		 * By default the location of the box is on the bottom of the terrain
		 * we make a slight offset to adjust for head height.
		 */
		//Vector3f vec = ((AbstractPhysicalEntity)player).getMainNode().getWorldTranslation();
		//cam.setLocation(new Vector3f(vec.x, vec.y + Settings.PLAYER_HEIGHT * .8f, vec.z)); // Drop cam slightly so we're looking out of our eye level
		player.setCameraLocation(cam);

		if (spotlight != null) {
			this.spotlight.setPosition(cam.getLocation());
			this.spotlight.setDirection(cam.getDirection());
		}

		started = true;
	}


	public void onAction(String binding, boolean isPressed, float tpf) {
		// DO NOT DO ANY ACTUAL ACTIONS IN THIS, DO THEM IN THE MAIN THREAD!
		if (this.game_over == false) {
			player.onAction(binding, isPressed, tpf);
			if (binding.equals("Ability1")) {
				//player.activateAbility(1); // todo - in main game thread!
				abilityActivated[1] = isPressed;
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
		//System.out.println(event.getObjectA().getUserObject().toString() + " collided with " + event.getObjectB().getUserObject().toString());

		Spatial ga = (Spatial)event.getObjectA().getUserObject(); 
		AbstractPhysicalEntity a = ga.getUserData(Settings.ENTITY);
		while (a == null && ga.getParent() != null) {
			//Globals.p("Getting parent of " + ga);
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
			//Globals.p("Getting parent of " + gb);
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


	public static void p(String s) {
		System.out.println(System.currentTimeMillis() + ": " + s);
	}


	public static void pe(String s) {
		System.err.println(System.currentTimeMillis() + ": " + s);
	}


	public BulletAppState getBulletAppState() {
		return bulletAppState;
	}


	public void markEntityForRemoval(IEntity e) {
		this.entitiesToRemove.add(e);
	}



	public void actuallyRemoveEntity(IEntity e) {
		e.actuallyRemove();
		this.entities.remove(e);
	}


	public void keyCollected() {
		this.gameData.numKeys++;
	}


	public String getHUDText() {
		return level.getHUDText();
	}
}
