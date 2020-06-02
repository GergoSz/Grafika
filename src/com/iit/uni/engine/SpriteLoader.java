package com.iit.uni.engine;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import com.iit.uni.game.Player;

public class SpriteLoader {

	public HashMap<String, CSprite> animList = new HashMap<String, CSprite>();
	
	private static SpriteLoader instance = null;

	private SpriteLoader() {

		//animationList.add(new CSprite("textures2/Right_", 5, 200, 200));
		
		animList.put("pRunR", new CSprite("textures2/Right_", 5));
		animList.put("pRunL", new CSprite("textures2/Left_", 5));
		animList.put("pIdleD", new CSprite("textures2/Down_1", 1));
		animList.put("pIdleU", new CSprite("textures2/Up_1", 1));
		animList.put("pIdleR", new CSprite("textures2/Right_1", 1));
		animList.put("pIdleL", new CSprite("textures2/Left_1", 1));
		animList.put("pRunU", new CSprite("textures2/Up_", 5));
		animList.put("pRunD", new CSprite("textures2/Down_", 5));
		animList.put("pAttackD", new CSprite("textures2/ADown_", 5));
		animList.put("bsIdle", new CSprite("textures2/slimes/B_Slime_Idle_", 10));
		//animList.put("floor", new CSprite("textures2/mapelements/floor_0", 1));
		animList.put("floorVariants", new CSprite("textures2/mapelements/floor_", 10));
		animList.put("wallVariants", new CSprite("textures2/mapelements/walls/wall_", 9));
		animList.put("wall", new CSprite("textures2/mapelements/walls/wall_1", 1));
		animList.put("ceilingVariants", new CSprite("textures2/mapelements/ceilings/ceiling_", 56));
		animList.put("ceiling05", new CSprite("textures2/mapelements/ceilings/ceiling_0.5", 1));
		
	}

	public static SpriteLoader getInstance() {
		if (instance == null) {
			synchronized (SpriteLoader.class) {
				if (instance == null) {
					instance = new SpriteLoader();
				}
			}
		}
		return instance;
	}
	
	public CSprite GetAnim(String name) {
		return animList.get(name);
	}

	/*
	 * public void AddAnim(CSprite anim) { animationList.add(anim); }
	 * 
	 * public void RemoveAnimAtIndex(int i) { animationList.remove(i); }
	 */

}
