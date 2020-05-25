package com.iit.uni.game;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.iit.uni.engine.C2DGraphicsLayer;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.SpriteLoader;

public class Map {

	public int randomFillPercent = 45;
	
	public String seed;
	public boolean useRandomSeed = true;
	
	public int width;
	public int height;
	
	ArrayList<GameObject2D> floors = new ArrayList<GameObject2D>();
	
	int[][] map;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		GenerateMap();
	}
	
	public Map(int width, int height, String seed) {
		this.width = width;
		this.height = height;
		this.useRandomSeed = false;
		this.seed = seed;
		GenerateMap();
	}
	
	void GenerateMap() {
		map = new int[width][height];
		RandomFillMap();
		
		for (int i = 0; i < 5; i++) {
			SmoothMap();
		}
	}
	
	
	void RandomFillMap() {
		long s = java.lang.System.currentTimeMillis();
		if(useRandomSeed)
			seed = "" + s;
		
		Random r = new Random(seed.hashCode());
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if(x == 0 || x == width - 1 || y == 0 || y == height - 1) {
					map[x][y] = 1;
				}else {
					map[x][y] = (r.nextInt(100) < randomFillPercent)? 1 : 0;
				}
			}
		}
	}
	
	void SmoothMap() {
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int neighbourWallTiles = GetSurroundingWallCount(x, y);
				if(neighbourWallTiles > 4) 
					map[x][y] = 1;
				else if(neighbourWallTiles < 4)
					map[x][y] = 0;
				
			}
		}
		
	}
	
	int GetSurroundingWallCount(int gridX, int gridY) {
		int wallCount = 0;
		for(int neighbourX = gridX - 1; neighbourX <= gridX + 1; neighbourX++) {
			for(int neighbourY = gridY - 1; neighbourY <= gridY + 1; neighbourY++) {
				if(neighbourX >= 0 && neighbourX < width && neighbourY >= 0 && neighbourY < height) {
					if(neighbourX != gridX || neighbourY != gridY) {
						wallCount += map[neighbourX][neighbourY];
					}
				}else {
					wallCount++;
				}
				
			}
		}
		return wallCount;
	}
	
	C2DGraphicsLayer GetMapLayer() {
		SpriteLoader spriteLoader = SpriteLoader.getInstance();
		C2DGraphicsLayer mapLayer= new C2DGraphicsLayer();
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				if(map[x][y] == 0) {
					FloorTile floorTile = new FloorTile();
					floorTile.AddFrame(spriteLoader.GetAnim("floor"));
					floorTile.AddFrame(spriteLoader.GetAnim("floorVariants"));
					floorTile.SetPosition(64+(x * 16),64+(y * 16));
					floors.add(floorTile);
					mapLayer.AddGameObject(floorTile);
				}
				
				
			}
		}
		
		return mapLayer;
		
		
	}
		
	
}
