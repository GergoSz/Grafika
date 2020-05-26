package com.iit.uni.game;

import java.util.Random;

import com.iit.uni.engine.BoundingBox2D;
import com.iit.uni.engine.CSprite;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.Texture2D;
import com.iit.uni.engine.math.Vector2D;

public class FloorTile extends GameObject2D{

	private BoundingBox2D box;
	private int variantID;
	private final int variantChance = 20;
	
	public FloorTile() {
		super();
		box = new BoundingBox2D();
		//box = new BoundingBox2D(new Vector2D(20, 22), new Vector2D(200, 220));
		
		Random rand = new Random();
		if(rand.nextInt(100/variantChance)+1 == 1)
		variantID = rand.nextInt(7);
		
	}

	@Override
	public void Draw() {
		
		SetZIndex((int)m_vPosition.y);
	
		
		if (m_uiNumberOfFrames > 0) {
			if (mVisible == true) {
				// Draw the current frame
				CSprite sp = m_Animations.get(1);
				Texture2D tex = sp.GetTexture(variantID);
				tex.Draw(m_vPosition);
				//Update();
				//box.Draw();
			}
		}
	}
	
	@Override
	public void SetPosition(Vector2D pos) {
		m_vPosition.set(pos.x, pos.y);
		box.SetPoints( new Vector2D(pos.x, pos.y), new Vector2D(pos.x + 16, pos.y + 16));
		for (int i = 0; i < m_uiNumberOfFrames; ++i) {
			m_Animations.get(i).SetPosition(pos.x,pos.y);
		}
	}


	@Override
	public void SetPosition(float x, float y) {
		m_vPosition.set(x, y);
		box.SetPoints( new Vector2D(x, y), new Vector2D(x + 16, y + 16));
		for (int i = 0; i < m_uiNumberOfFrames; ++i) {
			m_Animations.get(i).SetPosition(x,y);
		}
	}
	
	public void setVariantID(int vID) {
		variantID = vID;
	}
	
}
