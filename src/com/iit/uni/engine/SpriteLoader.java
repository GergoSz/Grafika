package com.iit.uni.engine;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import com.iit.uni.game.Player;

public class SpriteLoader {

	public ArrayList<AbstractMap.SimpleEntry<String, CSprite>> animationList = new ArrayList<AbstractMap.SimpleEntry<String, CSprite>>();

	private static SpriteLoader instance = null;

	private SpriteLoader() {

		//animationList.add(new CSprite("textures2/Right_", 5, 200, 200));

		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pRunR", new CSprite("textures2/Right_", 5)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pRunL", new CSprite("textures2/Left_", 5)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pIdleD", new CSprite("textures2/Down_1", 1)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pIdleU", new CSprite("textures2/Up_1", 1)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pIdleR", new CSprite("textures2/Right_1", 1)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pIdleL", new CSprite("textures2/Left_1", 1)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pRunU", new CSprite("textures2/Up_", 5)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pRunD", new CSprite("textures2/Down_", 5)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("pAttackD", new CSprite("textures2/ADown_", 5)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("bsIdle", new CSprite("textures2/slimes/B_Slime_Idle_", 10)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("floor", new CSprite("textures2/mapelements/floor_0", 1)));
		animationList.add(new AbstractMap.SimpleEntry<String, CSprite>("floorVariants", new CSprite("textures2/mapelements/floor_", 10)));

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
		for (SimpleEntry<String, CSprite> entry : animationList) {
			if(entry.getKey().equals(name)) {
				return entry.getValue();
			}
		}
		return new CSprite();
	}

	/*
	 * public void AddAnim(CSprite anim) { animationList.add(anim); }
	 * 
	 * public void RemoveAnimAtIndex(int i) { animationList.remove(i); }
	 */

}
