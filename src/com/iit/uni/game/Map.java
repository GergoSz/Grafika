package com.iit.uni.game;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.iit.uni.engine.C2DGraphicsLayer;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.SpriteLoader;
import com.iit.uni.engine.math.Vector2D;
import com.iit.uni.game.Map.Coord;

public class Map {

	public int randomFillPercent = 40;
	
	public String seed;
	public boolean useRandomSeed = true;
	
	public int width;
	public int height;
	
	ArrayList<GameObject2D> floors = new ArrayList<GameObject2D>();
	static ArrayList<Line> lines = new ArrayList<Line>();
	
	int[][] map;
	int[][] borderedMap;
	int[][] mapFlags;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		mapFlags = new int[width][height];
		GenerateMap();
	}
	
	public Map(int width, int height, String seed) {
		this.width = width;
		this.height = height;
		this.useRandomSeed = false;
		this.seed = seed;
		mapFlags = new int[width][height];
		GenerateMap();
	}
	
	void GenerateMap() {
		lines.clear();
		map = new int[width][height];
		RandomFillMap();
		
		for (int i = 0; i < 5; i++) {
			SmoothMap();
		}
		
		ProcessMap();
						
		/*int borderSize = 1;
		borderedMap = new int[width + borderSize * 2][ height + borderSize * 2];
		
		for (int x = 0; x < borderedMap.length; x++) {
			for (int y = 0; y < borderedMap[1].length; y++) {
				if(x >= borderSize && x < width + borderSize && y >= borderSize && y < height + borderSize) {
					borderedMap[x][y] = map[x - borderSize][ y - borderSize];
				}else {
					borderedMap[x][y] = 0;
				}
			}
		}*/
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
					map[x][y] = (r.nextInt(100/randomFillPercent)+1 == 1)? 1 : 0;
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
				if(IsInMapRange(neighbourX, neighbourY)) {
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
	

	
	ArrayList<Coord> GetRegionTiles(int startX, int startY){
		ArrayList<Coord> tiles = new ArrayList<Coord>();
		int[][] mapFlags = new int[width][height];
		int tileType = map[startX][startY];
		
		LinkedList<Coord> queue = new LinkedList<Coord>();
		queue.add(new Coord(startX, startY));
		mapFlags[startX][startY] = 1;
		
		while(queue.size() > 0) {
			Coord tile = queue.remove();
			tiles.add(tile);
			for (int x = tile.tileX - 1; x <= tile.tileX + 1; x++) {
				for (int y = tile.tileY - 1; y <= tile.tileY + 1; y++) {
					if(IsInMapRange(x, y) && (y == tile.tileY || x == tile.tileX)) {
						if(mapFlags[x][y] == 0 && map[x][y] == tileType) {
							mapFlags[x][y] = 1;
							queue.add(new Coord(x, y));
						}
					}
				}
			}
		}
		
		return tiles;
	}
	
	void ProcessMap() {
		ArrayList<ArrayList<Coord>> wallRegions = GetRegions(1);
		int wallThresholdSize = 5;
		
		for (ArrayList<Coord> region : wallRegions) {
			if(region.size() < wallThresholdSize) {
				for (Coord tile : region) {
					map[tile.tileX][tile.tileY] = 0;
				}
			}
		}
		
		ArrayList<ArrayList<Coord>> roomRegions = GetRegions(0);
		int roomThresholdSize = 50;
		ArrayList<Room> roomsLeft = new ArrayList<Room>();
		
		for (ArrayList<Coord> region : roomRegions) {
			if(region.size() < roomThresholdSize) {
				for (Coord tile : region) {
					map[tile.tileX][tile.tileY] = 1;
				}
			}else {
				roomsLeft.add(new Room(region, map));
			}
		}
		
		roomsLeft.sort(null);
	  /*for (Room room : roomsLeft) {
			System.out.println(room.roomSize);
		}*/
		
		roomsLeft.get(0).isMainRoom = true;
		roomsLeft.get(0).isAccessibleFromMainRoom = true;
		
		
		
		ConnectClosestRooms(roomsLeft, false);
	}
	
	void ConnectClosestRooms(ArrayList<Room> allRooms, boolean forceAccessFromMainRoom) {
		
		ArrayList<Room> roomListA = new ArrayList<Room>();
		ArrayList<Room> roomListB = new ArrayList<Room>();
		
		if(forceAccessFromMainRoom) {
			for (Room room : allRooms) {
				if(room.isAccessibleFromMainRoom) {
					roomListB.add(room);
				}else {
					roomListA.add(room);
				}
			}
		}else {
			roomListA = allRooms;
			roomListB = allRooms;
		}
		
		int bestDistance = 0;
		Coord bestTileA = new Coord();
		Coord bestTileB = new Coord();
		Room bestRoomA = new Room();
		Room bestRoomB = new Room();
		boolean possibleConnectionFound = false;
		
		for (Room roomA : roomListA) {
			if(!forceAccessFromMainRoom) {
				possibleConnectionFound = false;
				if(roomA.connectedRooms.size() > 0) {
					continue;
				}
			}
			
			
						
			for (Room roomB : roomListB) {
				
				if(roomA == roomB || roomA.IsConnected(roomB)) {
					continue;
				}
				
				for(int tileIndexA = 0; tileIndexA < roomA.edgeTiles.size(); tileIndexA++) {
					for(int tileIndexB = 0; tileIndexB < roomB.edgeTiles.size(); tileIndexB++) {
						Coord tileA = roomA.edgeTiles.get(tileIndexA);
						Coord tileB = roomB.edgeTiles.get(tileIndexB);
						int distanceBetweenRooms = (int) (Math.pow(tileA.tileX-tileB.tileX, 2) + Math.pow(tileA.tileY-tileB.tileY, 2));
						
						if(distanceBetweenRooms < bestDistance || !possibleConnectionFound) {
							bestDistance = distanceBetweenRooms;
							possibleConnectionFound = true;
							bestTileA = tileA;
							bestTileB = tileB;
							bestRoomA = roomA;
							bestRoomB = roomB;
						}
					}
				}
			}
			
			if(possibleConnectionFound && !forceAccessFromMainRoom) {
				CreatePassage(bestRoomA, bestRoomB, bestTileA, bestTileB);
			}
			
		}
		
		if(possibleConnectionFound && forceAccessFromMainRoom) {
			CreatePassage(bestRoomA, bestRoomB, bestTileA, bestTileB);
			ConnectClosestRooms(allRooms, true);
		}
				
		if(!forceAccessFromMainRoom) {
			ConnectClosestRooms(allRooms, true);
		}
		
	}
	
	void CreatePassage(Room roomA, Room roomB, Coord tileA, Coord tileB) {
		Room.ConnectRooms (roomA, roomB);
		/*glColor3f(1, 1, 1);
        glBegin(GL_LINES);
            glVertex2f(10, 10);
            glVertex2f(20, 20);
        glEnd();*/
		
		ArrayList<Coord> line = GetLineCoords(tileA, tileB);
		
		for (Coord coord : line) {
			DrawCircle(coord, 1);
		}
		
		/*lines.add(new Line(tileA.toVec2D(), tileB.toVec2D()));
		Coord a = new Coord(0,0);
		Coord b = new Coord(0,1);
		lines.add(new Line(a.toVec2D(),b.toVec2D()));*/
	}
	
	void DrawCircle(Coord c, int r) {
		for(int x = -r; x <= r; x++) {
			for(int y = -r; y <= r; y++) {
				if(x*x + y*y <= r*r) {
					int realX = c.tileX + x;
					int realY = c.tileY + y;
					if(IsInMapRange(realX, realY)) {
						map[realX][realY] = 0;
					}
				}
			}
		}
	}
	
	public ArrayList<Coord> GetLineCoords(Coord start, Coord end){
		ArrayList<Coord> line = new ArrayList<Coord>();
		
		boolean inverted = false;
		
		int x = start.tileX;
		int y = start.tileY;
		
		int dx = end.tileX - start.tileX;
		int dy = end.tileY - start.tileY;
		
		int step = (int) Math.signum(dx);
		int gradientStep = (int) Math.signum(dy);
		
		int longest = Math.abs(dx);
		int shortest = Math.abs(dy);
		
		if(longest < shortest) {
			inverted = true;
			longest = Math.abs(dy);
			shortest = Math.abs(dx);
			
			step = (int) Math.signum(dy);
			gradientStep = (int) Math.signum(dx);
		}
		
		int gradientAccumulation = longest / 2;
		for (int i = 0; i < longest; i++) {
			line.add(new Coord(x,y));
			
			if(inverted) {
				y += step;
			}else {
				x += step;
			}
			
			gradientAccumulation += shortest;
			if(gradientAccumulation >= longest) {
				if(inverted) {
					x += gradientStep;
				}else {
					y += gradientStep;
				}
				gradientAccumulation -= longest;
			}
		}
		
		return line;
	}

	ArrayList<ArrayList<Coord>> GetRegions (int tileType){
		ArrayList<ArrayList<Coord>> regions = new ArrayList<ArrayList<Coord>>();
		int[][] mapFlags = new int[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if(mapFlags[x][y] == 0 && map[x][y] == tileType) {
					ArrayList<Coord> newRegion = GetRegionTiles(x,y);
					regions.add(newRegion);
					
					for (Coord coord : newRegion) {
						mapFlags[coord.tileX][coord.tileY] = 1;
					}
				}
			}		
		}
		
		return regions;
	}
	
	void DrawToLayer(C2DGraphicsLayer mapLayer) {
		SpriteLoader spriteLoader = SpriteLoader.getInstance();
		mapLayer.Clear();		
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
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
		
	}
	
	public class Coord {
		public int tileX;
		public int tileY;
		
		public Coord(int x, int y) {
			this.tileX = x;
			this.tileY = y;
		}

		public Coord() {
		}
		public Vector2D toVec2D() {
			return new Vector2D(64 + (tileX * 16), 64 + (tileY * 16));
		}
	}
	
	public class Line{
		Vector2D pointA;
		Vector2D pointB;
		
		public Line(Vector2D pointA, Vector2D pointB) {
			this.pointA = pointA;
			this.pointB = pointB;
		}
		public Line() {}
	}
	
	public static class Room implements Comparable<Room>{
		public ArrayList<Coord> tiles;
		public ArrayList<Coord> edgeTiles;
		public ArrayList<Room> connectedRooms;
		public int roomSize;
		public boolean isAccessibleFromMainRoom;
		public boolean isMainRoom;
		
		public Room() {
			
		}
		
		public Room(ArrayList<Coord> roomTiles, int[][] map) {
			tiles = roomTiles;
			roomSize = tiles.size();
			connectedRooms = new ArrayList<Room>();
			
			edgeTiles = new ArrayList<Map.Coord>();
			for (Coord tile : tiles) {
				for(int x = tile.tileX - 1; x <= tile.tileX + 1; x++) {
					for(int y = tile.tileY - 1; y <= tile.tileY + 1; y++) {
						if(x == tile.tileX || y == tile.tileY) {
							if(map[x][y] == 1) {
								edgeTiles.add(tile);
							}
						}
					}					
				}
			}
			
		}
		
		public void setAccessibleFromMainRoom() {
			if(!isAccessibleFromMainRoom) {
				isAccessibleFromMainRoom = true;
				for (Room connectedRoom : connectedRooms) {
					connectedRoom.setAccessibleFromMainRoom();
				}
			}
		}
		
		public static void ConnectRooms(Room roomA, Room roomB) {
			if(roomA.isAccessibleFromMainRoom) {
				roomB.setAccessibleFromMainRoom();
			}else if(roomB.isAccessibleFromMainRoom) {
				roomA.setAccessibleFromMainRoom();
			}
			roomA.connectedRooms.add(roomB);
			roomB.connectedRooms.add(roomA);
			
		}
		
		public boolean IsConnected(Room otherRoom) {
			return connectedRooms.contains(otherRoom);
		}

		@Override
		public int compareTo(Room otherRoom) {
			return Integer.compare(otherRoom.roomSize, roomSize);
		}
	}
	
	boolean IsInMapRange(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	//TODO: Move to drawDebugStuff
	/*public static void DrawLines() {

//		Vector2D minpoint = new Vector2D(20, 20);
//		Vector2D maxpoint = new Vector2D(70, 50);
		
		for (Line line : lines) {
			// Data for quad
			float vertices[] = { line.pointA.x, line.pointA.y, 0.0f, line.pointB.x, line.pointB.y, 0.0f, line.pointB.x, line.pointB.y, 0.0f,
					line.pointA.x, line.pointA.y, 0.0f, line.pointA.x, line.pointA.y, 0.0f };

			// Create new VAO Object
			int vaoId = glGenVertexArrays();
			glBindVertexArray(vaoId);

			// Position VBO
			int vboId = glGenBuffers();

			FloatBuffer posBuffer = BufferUtils.createFloatBuffer(vertices.length);
			posBuffer.put(vertices).flip();
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

			// Activate shader
			Renderer.mRenderer.lineShader.bind();
			Renderer.mRenderer.lineShader.setUniform("projectionMatrix", Renderer.mRenderer.projectionMatrix);

			// Set world/model matrix for this item
			Matrix4f modelMatrix = new Matrix4f();
			Renderer.mRenderer.lineShader.setUniform("modelMatrix", modelMatrix);

			// Set color
			Renderer.mRenderer.lineShader.setUniform4f("linecolor", 0f, 1f, 0f, 1.0f);

			// Render the VAO
			glBindVertexArray(vaoId);
			glEnableVertexAttribArray(0);

			//glDisable(GL_DEPTH_TEST);
			glDrawArrays(GL_LINE_STRIP, 0, 5);
			//glEnable(GL_DEPTH_TEST);
			// Restore state
			glDisableVertexAttribArray(0);

			Renderer.mRenderer.lineShader.unbind();
		}
		
		
	}
	*/
	
}
