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
		
		animList.put("pRunR", new CSprite("textures2/Right_", 5, false));
		animList.put("pRunL", new CSprite("textures2/Left_", 5, false));
		animList.put("pIdleD", new CSprite("textures2/Down_1", 1, false));
		animList.put("pIdleU", new CSprite("textures2/Up_1", 1, false));
		animList.put("pIdleR", new CSprite("textures2/Right_1", 1, false));
		animList.put("pIdleL", new CSprite("textures2/Left_1", 1, false));
		animList.put("pRunU", new CSprite("textures2/Up_", 5, false));
		animList.put("pRunD", new CSprite("textures2/Down_", 5, false));
		animList.put("pAttackD", new CSprite("textures2/ADown_", 5, false));
		animList.put("pAttackU", new CSprite("textures2/AUp_", 5, false));
		animList.put("pAttackR", new CSprite("textures2/ARight_", 5, false));
		animList.put("pAttackL", new CSprite("textures2/ALeft_", 5, false));
		
		animList.put("bsIdle", new CSprite("textures2/slimes/B_Slime_Idle_", 10, false));
		
		animList.put("gsIdle", new CSprite("textures2/slimes/G_Slime_Idle_", 10, false));
		animList.put("gsRoll", new CSprite("textures2/slimes/G_Slime_Roll_", 10, false));
		animList.put("gsRollF", new CSprite("textures2/slimes/G_Slime_Roll_", 10, true));
		animList.put("gsJump", new CSprite("textures2/slimes/G_Slime_Jump_", 10, false));
		animList.put("gsJumpF", new CSprite("textures2/slimes/G_Slime_Jump_", 10, true));
		animList.put("gsHit", new CSprite("textures2/slimes/G_Slime_Hit_", 10, false));
		animList.put("gsDie", new CSprite("textures2/slimes/G_Slime_Die_", 10, false));


		//animList.put("floor", new CSprite("textures2/mapelements/floor_0", 1));
		animList.put("floorVariants", new CSprite("textures2/mapelements/floor_", 10, false));
		animList.put("wallVariants", new CSprite("textures2/mapelements/walls/wall_", 9, false));
		animList.put("wall", new CSprite("textures2/mapelements/walls/wall_1", 1, false));
		animList.put("ceilingVariants", new CSprite("textures2/mapelements/ceilings/ceiling_", 56, false));
		animList.put("ceiling05", new CSprite("textures2/mapelements/ceilings/ceiling_0.5", 1, false));
		
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
