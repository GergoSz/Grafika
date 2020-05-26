package com.iit.uni.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GLUtil;

import com.iit.uni.engine.BoundingBox2D;
import com.iit.uni.engine.C2DGraphicsLayer;
import com.iit.uni.engine.C2DScene;
import com.iit.uni.engine.C2DSceneManager;
import com.iit.uni.engine.CSprite;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.IGameLogic;
import com.iit.uni.engine.SpriteLoader;
import com.iit.uni.engine.Texture2D;
import com.iit.uni.engine.Timer;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.math.Vector2D;

public class DummyGame implements IGameLogic {

	private final Renderer renderer;
	private String direction = "none";

	// 2D GameObject items
	private Player player;
	private GameObject2D dummy;
	private GameObject2D slime;

	// Global Scene manager
	public static C2DSceneManager sceneManager;
	
	// Sprite Loader
	public static SpriteLoader spriteLoader;

	private C2DScene scene;
	
	private BoundingBox2D center;
	boolean a = true;

	Map map = new Map(100, 80, "seed");
	C2DGraphicsLayer mapLayer = new C2DGraphicsLayer();
	
	public DummyGame() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);
		spriteLoader  = SpriteLoader.getInstance();
		
		/**
		 * Creating an animated game object
		 */
		player = Player.getInstance();
		player.SetPosition((window.getWidth()/4) - 16, (window.getHeight()/4)-16);
		player.setCenterPoint(new Vector2D((window.getWidth()/4) - 16, (window.getHeight()/4)-16));
		//center = new BoundingBox2D(player.getCenterPoint(), new Vector2D(1,1));
		
		
		dummy = new GameObject2D();
		
		slime = new GameObject2D();
		
		
		
		

		slime.AddFrame(spriteLoader.GetAnim("bsIdle"));
		slime.SetPosition(20,20);
		
		dummy.AddFrame(spriteLoader.GetAnim("pIdleD"));
		
		dummy.SetPosition(70, 50);

		/*player.AddFrame(idleD);
		player.AddFrame(frameRunRight);
		player.AddFrame(frameRunLeft);
		player.AddFrame(frameRunUp);
		player.AddFrame(frameRunDown);
		player.AddFrame(frameAttackDown);
		player.AddFrame(idleU);
		player.AddFrame(idleR);
		player.AddFrame(idleL);*/
		
		/*player.SetScale(2.6f);
		player.SetSpeed(2.5f);

		player.SetPosition(200, 504);*/

		sceneManager = new C2DSceneManager();
		scene = new C2DScene();

		// Create a background texture
		Texture2D background = new Texture2D();
		background.CreateTexture("textures2/ceilings.png");
/*
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
		
		C2DGraphicsLayer enityLayer = new C2DGraphicsLayer();
		
		enityLayer.AddGameObject(player);
		enityLayer.AddGameObject(dummy);
		enityLayer.AddGameObject(slime);
		
		
		
		
		/*floors.get(0).AddFrame(spriteLoader.GetAnim("floorVariants"));
		floors.get(0).SetCurrentFrame(1);*/
		

		//mapLayer.AddTexture(background);
		
		// register layer at the scene
		//scene.RegisterLayer(layer0);
		//scene.RegisterLayer(layer1);
		/*scene.RegisterLayer(layer2);
		scene.RegisterLayer(layer3);
		scene.RegisterLayer(layer4);*/
		
		map.DrawToLayer(mapLayer);
		
		scene.RegisterLayer(mapLayer);
		scene.RegisterLayer(enityLayer);
		
		// Register scene at the manager
		sceneManager.RegisterScene(scene);
		
	}

	@Override
	public void input(Window window) {
		/*if(window.isKeyPressed('W') || window.isKeyPressed('A') || window.isKeyPressed('S') || window.isKeyPressed('D')) {
			
		}else {
			
		}*/
		
		//int distance = (int)Vector2D.distance(player.getCenterPoint(),player.GetPosition());
		//System.out.println(distance);
		player.SetCurrentFrame(0);
		if (window.isKeyPressed('S')) {
			direction = "S";
			Vector2D pos = player.GetPosition();
			player.SetCurrentFrame(4);
			pos.y += player.GetSpeed();
			player.SetPosition(pos);
			if(player.GetPosition().y - player.getCenterPoint().y > 40) {
			
			Vector2D centerPos = player.getCenterPoint();
			centerPos.y += player.GetSpeed();
			player.setCenterPoint(centerPos);
			renderer.projectionMatrix.translate(0, -1, 0);
			}
			
		}else
		if (window.isKeyPressed('A')) {
			direction = "A";
			player.SetCurrentFrame(2);
			Vector2D pos = player.GetPosition();
			pos.x -= player.GetSpeed();
			player.SetPosition(pos);
			if(player.GetPosition().x - player.getCenterPoint().x < -40) {
			
			Vector2D centerPos = player.getCenterPoint();
			centerPos.x -= player.GetSpeed() * 0.9f;
			player.setCenterPoint(centerPos);
			renderer.projectionMatrix.translate(1, 0, 0);
			}
		}else
		if (window.isKeyPressed('D')) {
			direction = "D";
			player.SetCurrentFrame(1);
			Vector2D pos = player.GetPosition();
			//if(pos.x < 300)
				pos.x += player.GetSpeed();
			//System.out.println(pos.x);
			player.SetPosition(pos);
			if(player.GetPosition().x - player.getCenterPoint().x > 40) {
			
			Vector2D centerPos = player.getCenterPoint();
			centerPos.x += player.GetSpeed();
			player.setCenterPoint(centerPos);
			renderer.projectionMatrix.translate(-1, 0, 0);
			}
			
		}else
		if (window.isKeyPressed('W')) {
			direction = "W";
			Vector2D pos = player.GetPosition();
			player.SetCurrentFrame(3);
			pos.y -= player.GetSpeed();
			player.SetPosition(pos);
			if(player.GetPosition().y - player.getCenterPoint().y < -40) {
			
			Vector2D centerPos = player.getCenterPoint();
			centerPos.y -= player.GetSpeed();
			player.setCenterPoint(centerPos);
			renderer.projectionMatrix.translate(0, 1, 0);
			}
		}else {
			switch (direction) {
			case "W":
				player.SetCurrentFrame(6);
				break;
			case "S":
				player.SetCurrentFrame(0);
				break;
			case "A":
				player.SetCurrentFrame(8);
				break;
			case "D":
				player.SetCurrentFrame(7);
				break;
			}
		}
		
		//System.out.println(Vector2D.distance(player.GetPosition( ), dummy.GetPosition()));
		
		
		
		if(window.isKeyPressed(' ') && a) {
			//player.SetCurrentFrame(5);
			//System.out.println(renderer.projectionMatrix);
			a = false;
			map.GenerateMap();
			map.DrawToLayer(mapLayer);
		}

		if(window.isKeyReleased(' ')) {
			a = true;
		}
		
	}

	@Override
	public void update(float interval) {

	}

	@Override
	public void render(Window window) {
		renderer.render(window);

		Map.DrawLines();
		//center.Draw();
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		player.cleanUp();
		dummy.cleanUp();
	}
}
