package com.iit.uni.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import com.iit.uni.engine.CSprite;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.IGameLogic;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.math.Vector2D;

public class DummyGame implements IGameLogic {

	private final Renderer renderer;
	private int direction = 0;

	// 2D GameObject items
	private GameObject2D gameItem;

	public DummyGame() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		
		renderer.init(window);

		gameItem = new GameObject2D();

		CSprite frameRunRight = new CSprite("textures2/Right_", 5, 200, 200);
		CSprite frameRunLeft = new CSprite("textures2/Left_", 5, 200, 200);
		CSprite idle = new CSprite("textures2/Down_1", 1, 200, 200);
		CSprite frameRunUp = new CSprite("textures2/Up_", 5, 200, 200);
		CSprite frameRunDown = new CSprite("textures2/Down_", 5, 200, 200);

		/*CSprite jumpRight = new CSprite("textures/Jump_right_", 10, 200, 200);
		CSprite jumpLeft = new CSprite("textures/Jump_left_", 10, 200, 200);*/

		gameItem.AddFrame(idle);
		gameItem.AddFrame(frameRunRight);
		gameItem.AddFrame(frameRunLeft);
		gameItem.AddFrame(frameRunDown);
		gameItem.AddFrame(frameRunUp);
		gameItem.SetScale(3.0f);
		
	}

	@Override
	public void input(Window window) {

		
		if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			Vector2D pos = gameItem.GetPosition();
			gameItem.SetCurrentFrame(3);
			pos.y += 1000;
			gameItem.SetPosition(pos);
		} else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			direction = -1;
			gameItem.SetCurrentFrame(2);
			Vector2D pos = gameItem.GetPosition();
			pos.x -= 1000;
			gameItem.SetPosition(pos);
		} else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
			direction = 1;
			gameItem.SetCurrentFrame(1);
			Vector2D pos = gameItem.GetPosition();
			pos.x += 1000;
			gameItem.SetPosition(pos);
		} else if (window.isKeyPressed(GLFW_KEY_UP)) {
			Vector2D pos = gameItem.GetPosition();
			gameItem.SetCurrentFrame(4);
			pos.y -= 1000;
			gameItem.SetPosition(pos);
		}else{
			gameItem.SetCurrentFrame(0);
		}
		
		/*if (window.isKeyPressed(GLFW_KEY_UP)) {

			if (direction == 1) {
				gameItem.SetCurrentFrame(3);
			} else {
				gameItem.SetCurrentFrame(4);
			}
			Vector2D pos = gameItem.GetPosition();
			pos.y -= 10;
			gameItem.SetPosition(pos);

		} else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			Vector2D pos = gameItem.GetPosition();
			pos.y += 10;
			gameItem.SetPosition(pos);

		} else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			direction = -1;
			gameItem.SetCurrentFrame(2);
			Vector2D pos = gameItem.GetPosition();
			pos.x -= 10;
			gameItem.SetPosition(pos);
		} else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
			direction = 1;
			gameItem.SetCurrentFrame(1);
			Vector2D pos = gameItem.GetPosition();
			pos.x += 10;
			gameItem.SetPosition(pos);
		}*/
		
	}

	@Override
	public void update(float interval) {
	}

	@Override
	public void render(Window window) {
		renderer.render(window, gameItem);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		gameItem.cleanUp();
	}
}
