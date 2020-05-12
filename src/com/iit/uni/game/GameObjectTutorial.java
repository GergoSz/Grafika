package com.iit.uni.game;

import com.iit.uni.engine.GameEngine;
import com.iit.uni.engine.IGameLogic;

/**
 * Simple tutorial for 2D texture loading and rendering
 * 
 * @author Mileff Peter
 *
 */
public class GameObjectTutorial {

	public static void main(String[] args) {
		try {
			boolean vSync = true;
			IGameLogic gameLogic = new DummyGame();
			GameEngine gameEng = new GameEngine("2D Sprite Animation with AABB Demo", 1024, 768, vSync, gameLogic);
			gameEng.start();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}
}