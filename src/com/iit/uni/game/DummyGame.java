package com.iit.uni.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_SUBTRACT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ADD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
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
import com.iit.uni.engine.math.Vector3D;

public class DummyGame implements IGameLogic {

	private final Renderer renderer;
	private String direction = "none";

	// 2D GameObject items
	private Player player;
	private GameObject2D dummy;
	private GameObject2D slime;
	private WallTile wall;

	// Global Scene manager
	public static C2DSceneManager sceneManager;
	
	// Sprite Loader
	public static SpriteLoader spriteLoader;
	
	private final float camSpeed = 2;

	private C2DScene scene;
	
	private BoundingBox2D center;
	boolean a = true;

	
	MapGen map = new MapGen(100, 80);
	C2DGraphicsLayer mapLayer = new C2DGraphicsLayer();
	C2DGraphicsLayer entityLayer  = new C2DGraphicsLayer();
	C2DGraphicsLayer ceilingLayer  = new C2DGraphicsLayer();
	
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
		player.SetSpeed(camSpeed);
		//player.SetPosition((window.getWidth()/4) - 16, (window.getHeight()/4)-16);
		//player.setCenterPoint(new Vector2D((window.getWidth()/4) - 16, (window.getHeight()/4)-16));
		//center = new BoundingBox2D(player.getCenterPoint(), new Vector2D(1,1));
		
		
		dummy = new GameObject2D();
		
		slime = new GameObject2D();
		
	/*	wall = new WallTile();
		wall.AddFrame(spriteLoader.GetAnim("wall"));
		wall.SetPosition(20, 40);*/
		
		

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
		
		
		
		entityLayer.AddGameObject(player);
		entityLayer.AddGameObject(dummy);
		entityLayer.AddGameObject(slime);
		
		//entityLayer.AddGameObject(wall);
		
		
		/*floors.get(0).AddFrame(spriteLoader.GetAnim("floorVariants"));
		floors.get(0).SetCurrentFrame(1);*/
		

		//mapLayer.AddTexture(background);
		
		// register layer at the scene
		//scene.RegisterLayer(layer0);
		//scene.RegisterLayer(layer1);
		/*scene.RegisterLayer(layer2);
		scene.RegisterLayer(layer3);
		scene.RegisterLayer(layer4);*/
		
		map.DrawToLayer(mapLayer, entityLayer, ceilingLayer);
		
		scene.RegisterLayer(mapLayer);
		scene.RegisterLayer(entityLayer);
		scene.RegisterLayer(ceilingLayer);
		
		Vector2D tilePos =  new Vector2D( map.floors.get(0).GetPosition());
		player.SetPosition(tilePos.add(new Vector2D(8,8)));
		player.setCenterPoint(tilePos.add(new Vector2D(8,8)));
		
		//renderer.projectionMatrix.translate(tilePos.x , tilePos.y, 0);
		//renderer.projectionMatrix.setTranslation(tilePos.x, tilePos.y, 1);
		/*Matrix4f m = new Matrix4f();
		Matrix4f m2 = new Matrix4f();
		
		m = renderer.projectionMatrix;
		
		m.add(m2);*/
		

		renderer.projectionMatrix.translate(-tilePos.x + (window.getWidth()/4), -tilePos.y + (window.getHeight()/4), 0);
		
		//renderer.projectionMatrix.translate(tilePos.x, tilePos.y, 0);

		
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
		
		ArrayList<FloorTile> adjacentFloors = new ArrayList<FloorTile>();
		
		player.SetCurrentFrame(0);
		if (window.isKeyPressed('S')) {
			direction = "S";
			
			Vector2D minFeetPos = new Vector2D(player.feetBox.GetMinPoint().x,player.feetBox.GetMaxPoint().y);
			Vector2D maxFeetPos = new Vector2D(player.feetBox.GetMaxPoint().x,player.feetBox.GetMaxPoint().y);
			minFeetPos.y += 2;
			maxFeetPos.y += 2;
			if( map.isFloor(minFeetPos) && map.isFloor(maxFeetPos)) {
				Vector2D pos = player.GetPosition();
				player.SetCurrentFrame(4);
				pos.y += player.GetSpeed();
				player.SetPosition(pos);
				
				//adjacentFloors = map.GetAdjacentFloors(pos);
				
				if(player.GetPosition().y - player.getCenterPoint().y > 40) {
				
				Vector2D centerPos = player.getCenterPoint();
				centerPos.y += player.GetSpeed();
				player.setCenterPoint(centerPos);
				renderer.projectionMatrix.translate(0, -camSpeed, 0);
				}
			}
			
			
			
		}else
		if (window.isKeyPressed('A')) {
			direction = "A";
			
			Vector2D minFeetPos = new Vector2D(player.feetBox.GetMinPoint().x,player.feetBox.GetMinPoint().y);
			Vector2D maxFeetPos = new Vector2D(player.feetBox.GetMinPoint().x,player.feetBox.GetMaxPoint().y);
			minFeetPos.x -= 2;
			maxFeetPos.x -= 2;
			if( map.isFloor(minFeetPos) && map.isFloor(maxFeetPos)) {
				player.SetCurrentFrame(2);
				Vector2D pos = player.GetPosition();
				pos.x -= player.GetSpeed();
				
				//adjacentFloors = map.GetAdjacentFloors(pos);
				
				player.SetPosition(pos);
				if(player.GetPosition().x - player.getCenterPoint().x < -40) {
				
				Vector2D centerPos = player.getCenterPoint();
				centerPos.x -= player.GetSpeed();
				player.setCenterPoint(centerPos);
				renderer.projectionMatrix.translate(camSpeed, 0, 0);
				}
			}
			
			
		}else
		if (window.isKeyPressed('D')) {
			direction = "D";
			
			Vector2D minFeetPos = new Vector2D(player.feetBox.GetMaxPoint().x,player.feetBox.GetMinPoint().y);
			Vector2D maxFeetPos = new Vector2D(player.feetBox.GetMaxPoint().x,player.feetBox.GetMaxPoint().y);
			minFeetPos.x += 2;
			maxFeetPos.x += 2;
			if( map.isFloor(minFeetPos) && map.isFloor(maxFeetPos)) {
				player.SetCurrentFrame(1);
				Vector2D pos = player.GetPosition();
				//if(pos.x < 300)
					pos.x += player.GetSpeed();
					
					//adjacentFloors = map.GetAdjacentFloors(pos);
					
				//System.out.println(pos.x);
				player.SetPosition(pos);
				if(player.GetPosition().x - player.getCenterPoint().x > 40) {
				
				Vector2D centerPos = player.getCenterPoint();
				centerPos.x += player.GetSpeed();
				player.setCenterPoint(centerPos);
				renderer.projectionMatrix.translate(-camSpeed, 0, 0);
				}
			}
			
		}else
		if (window.isKeyPressed('W')) {
			direction = "W";
			
			Vector2D minFeetPos = new Vector2D(player.feetBox.GetMinPoint().x,player.feetBox.GetMinPoint().y);
			Vector2D maxFeetPos = new Vector2D(player.feetBox.GetMaxPoint().x,player.feetBox.GetMinPoint().y);
			minFeetPos.y -= 1;
			maxFeetPos.y -= 1;
			if( map.isFloor(minFeetPos) && map.isFloor(maxFeetPos)) {
				Vector2D pos = player.GetPosition();
				player.SetCurrentFrame(3);
				pos.y -= player.GetSpeed();
				
				//adjacentFloors = map.GetAdjacentFloors(pos);
				
				player.SetPosition(pos);
				if(player.GetPosition().y - player.getCenterPoint().y < -40) {
				
				Vector2D centerPos = player.getCenterPoint();
				centerPos.y -= player.GetSpeed();
				player.setCenterPoint(centerPos);
				renderer.projectionMatrix.translate(0, camSpeed, 0);
				}
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
			map.DrawToLayer(mapLayer, entityLayer, ceilingLayer);
		}

		if(window.isKeyReleased(' ')) {
			a = true;
		}
		
		if(window.isKeyPressed(GLFW_KEY_KP_ADD)) {
			renderer.projectionMatrix.scale(1.1f, 1.1f, 1);
		}
		
		if(window.isKeyPressed(GLFW_KEY_KP_SUBTRACT)) {
			renderer.projectionMatrix.scale(0.9f, 0.9f, 1);
		}
		
		if(window.isKeyPressed(GLFW_KEY_KP_ENTER)) {
		//reset scale
			System.out.println( player.GetPosition().toString() );
			
		}
	
		map.updateFloors(mapLayer);
		
	}

	@Override
	public void update(float interval) {
	
	}

	@Override
	public void render(Window window) {
		renderer.render(window);

		//Map.DrawLines();
		//center.Draw();
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		player.cleanUp();
		dummy.cleanUp();
	}
}
