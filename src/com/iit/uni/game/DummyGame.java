package com.iit.uni.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import com.iit.uni.engine.C2DGraphicsLayer;
import com.iit.uni.engine.C2DScene;
import com.iit.uni.engine.C2DSceneManager;
import com.iit.uni.engine.CSprite;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.IGameLogic;
import com.iit.uni.engine.Texture2D;
import com.iit.uni.engine.Timer;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.math.Vector2D;

public class DummyGame implements IGameLogic {

	private final Renderer renderer;
	private String direction = "none";

	// 2D GameObject items
	private GameObject2D gameItem;
	private GameObject2D dummy;

	// Global Scene manager
	public static C2DSceneManager sceneManager;

	private C2DScene scene;

	public DummyGame() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);

		/**
		 * Creating an animated game object
		 */
		gameItem = new GameObject2D();
		
		dummy = new GameObject2D();
		
		

		CSprite frameRunRight = new CSprite("textures2/Right_", 5, 200, 200);
		CSprite frameRunLeft = new CSprite("textures2/Left_", 5, 200, 200);
		CSprite idleD = new CSprite("textures2/Down_1", 1, 200, 200);
		CSprite idleU = new CSprite("textures2/Up_1", 1, 200, 200);
		CSprite idleR = new CSprite("textures2/Right_1", 1, 200, 200);
		CSprite idleL = new CSprite("textures2/Left_1", 1, 200, 200);
		CSprite frameRunUp = new CSprite("textures2/Up_", 5, 200, 200);
		CSprite frameRunDown = new CSprite("textures2/Down_", 5, 200, 200);
		CSprite frameAttackDown = new CSprite("textures2/ADown_", 5, 200, 200);

		dummy.AddFrame(idleD);
		dummy.SetScale(2.6f);
		dummy.SetPosition(700, 500);

		gameItem.AddFrame(idleD);
		gameItem.AddFrame(frameRunRight);
		gameItem.AddFrame(frameRunLeft);
		gameItem.AddFrame(frameRunUp);
		gameItem.AddFrame(frameRunDown);
		gameItem.AddFrame(frameAttackDown);
		gameItem.AddFrame(idleU);
		gameItem.AddFrame(idleR);
		gameItem.AddFrame(idleL);
		
		gameItem.SetScale(2.6f);
		gameItem.SetSpeed(2.5f);

		gameItem.SetPosition(200, 504);

		sceneManager = new C2DSceneManager();
		scene = new C2DScene();

	/*	// Create a background texture
		Texture2D background = new Texture2D();
		background.CreateTexture("textures2/ceilings.png");

		// Create a cloud layer
		Texture2D clouds = new Texture2D();
		//clouds.CreateTexture("textures2/eggs1.png");

		// Create a mountain layer
		Texture2D mountains = new Texture2D();
		mountains.CreateTexture("textures/background/layer_sd_03.png");

		// Create a tree layer
		Texture2D trees = new Texture2D();
		trees.CreateTexture("textures/background/layer_sd_04.png");

		// Create a ground layer
		Texture2D ground = new Texture2D();
		ground.CreateTexture("textures/background/layer_sd_05.png");

		// Create graphics layer
		C2DGraphicsLayer layer0 = new C2DGraphicsLayer();
		layer0.AddTexture(background);

		// Create graphics layer
		C2DGraphicsLayer layer1 = new C2DGraphicsLayer();
		layer1.AddTexture(clouds);

		// Create graphics layer
		C2DGraphicsLayer layer2 = new C2DGraphicsLayer();
		layer2.AddTexture(mountains);

		C2DGraphicsLayer layer3 = new C2DGraphicsLayer();
		layer3.AddTexture(trees);

		C2DGraphicsLayer layer4 = new C2DGraphicsLayer();
		layer4.AddTexture(ground);
*/
		
		C2DGraphicsLayer playerLayer = new C2DGraphicsLayer();
		C2DGraphicsLayer dummyLayer= new C2DGraphicsLayer();
		playerLayer.AddGameObject(gameItem);
		playerLayer.AddGameObject(dummy);
		
		// register layer at the scene
		//scene.RegisterLayer(layer0);
		//scene.RegisterLayer(layer1);
		/*scene.RegisterLayer(layer2);
		scene.RegisterLayer(layer3);
		scene.RegisterLayer(layer4);*/

		scene.RegisterLayer(dummyLayer);
		scene.RegisterLayer(playerLayer);
		
		// Register scene at the manager
		sceneManager.RegisterScene(scene);
		
	}

	@Override
	public void input(Window window) {
		/*if(window.isKeyPressed('W') || window.isKeyPressed('A') || window.isKeyPressed('S') || window.isKeyPressed('D')) {
			
		}else {
			
		}*/
		gameItem.SetCurrentFrame(0);
		if (window.isKeyPressed('S')) {
			direction = "S";
			Vector2D pos = gameItem.GetPosition();
			gameItem.SetCurrentFrame(4);
			pos.y += gameItem.GetSpeed();
			gameItem.SetPosition(pos);
		}else
		if (window.isKeyPressed('A')) {
			direction = "A";
			gameItem.SetCurrentFrame(2);
			Vector2D pos = gameItem.GetPosition();
			pos.x -= gameItem.GetSpeed();
			gameItem.SetPosition(pos);
		}else
		if (window.isKeyPressed('D')) {
			direction = "D";
			gameItem.SetCurrentFrame(1);
			Vector2D pos = gameItem.GetPosition();
			//if(pos.x < 300)
				pos.x += gameItem.GetSpeed();
			System.out.println(pos.x);
			gameItem.SetPosition(pos);
		}else
		if (window.isKeyPressed('W')) {
			direction = "W";
			Vector2D pos = gameItem.GetPosition();
			gameItem.SetCurrentFrame(3);
			pos.y -= gameItem.GetSpeed();
			gameItem.SetPosition(pos);
		}else {
			switch (direction) {
			case "W":
				gameItem.SetCurrentFrame(6);
				break;
			case "S":
				gameItem.SetCurrentFrame(0);
				break;
			case "A":
				gameItem.SetCurrentFrame(8);
				break;
			case "D":
				gameItem.SetCurrentFrame(7);
				break;
			}
		}
		
				
		
		if(window.isKeyPressed(' ')) {
			gameItem.SetCurrentFrame(5);
		}

		
	}

	@Override
	public void update(float interval) {

	}

	@Override
	public void render(Window window) {
		renderer.render(window);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		gameItem.cleanUp();
		dummy.cleanUp();
	}
}
