package com.iit.uni.game;

import com.iit.uni.engine.BoundingBox2D;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.SpriteLoader;
import com.iit.uni.engine.math.Vector2D;

public class Slime extends GameObject2D {

public BoundingBox2D hitBox = new BoundingBox2D(new Vector2D(20, 22), new Vector2D(30, 32));
public BoundingBox2D centerBox = new BoundingBox2D(new Vector2D(20, 22), new Vector2D(30, 32));
	
	private Vector2D centerPoint = new Vector2D();
	public boolean dead = false;
	
	public Slime() {
		SpriteLoader spriteLoader = SpriteLoader.getInstance(); //Note to self: Why is this not static shit? Singleton goes brrrrrr.
		this.AddFrame(spriteLoader.GetAnim("gsIdle"));
		this.AddFrame(spriteLoader.GetAnim("gsJump"));
		this.AddFrame(spriteLoader.GetAnim("gsRoll"));
		this.AddFrame(spriteLoader.GetAnim("gsRollF"));
		this.AddFrame(spriteLoader.GetAnim("gsJumpF"));
		this.AddFrame(spriteLoader.GetAnim("gsHit"));
		this.AddFrame(spriteLoader.GetAnim("gsDie"));
				
		this.SetSpeed(0.5f);
		this.SetPosition(0,0);
		//hitBox = new BoundingBox2D(  new Vector2D(m_vPosition.x, m_vPosition.y), new Vector2D(m_vPosition.x + 10, m_vPosition.y + 8));
				
	}
	
	public void MoveTowards(Vector2D pos) {
		
			if(	Math.abs( pos.x - this.m_vPosition.x ) > 3 )
				if(pos.x > this.m_vPosition.x) {
					this.SetCurrentFrame(3);
					this.m_vPosition.x += this.GetSpeed();
					hitBox.SetPoints( new Vector2D(m_vPosition.x - 6, m_vPosition.y + 8), new Vector2D(m_vPosition.x + 6, m_vPosition.y + 16));
					centerBox.SetPoints( new Vector2D(m_vPosition.x, m_vPosition.y), new Vector2D(m_vPosition.x +1, m_vPosition.y + 1));
				}else {
					this.SetCurrentFrame(2);
					this.m_vPosition.x -= this.GetSpeed();
					hitBox.SetPoints( new Vector2D(m_vPosition.x - 6, m_vPosition.y + 8), new Vector2D(m_vPosition.x + 6, m_vPosition.y + 16));
					centerBox.SetPoints( new Vector2D(m_vPosition.x, m_vPosition.y), new Vector2D(m_vPosition.x +1, m_vPosition.y + 1));
				}
			
			if(	Math.abs( pos.y - this.m_vPosition.y ) > 3 )
				if(pos.y > this.m_vPosition.y) {
					if(pos.x > this.m_vPosition.x) {
						this.SetCurrentFrame(3);
					}else {
						this.SetCurrentFrame(2);
					}
					this.m_vPosition.y += this.GetSpeed();
					hitBox.SetPoints( new Vector2D(m_vPosition.x - 6, m_vPosition.y + 8), new Vector2D(m_vPosition.x + 6, m_vPosition.y + 16));
					centerBox.SetPoints( new Vector2D(m_vPosition.x, m_vPosition.y), new Vector2D(m_vPosition.x +1, m_vPosition.y + 1));
					return;
				}else {
					if(pos.x > this.m_vPosition.x) {
						this.SetCurrentFrame(4);
					}else {
						this.SetCurrentFrame(1);
					}
					this.m_vPosition.y -= this.GetSpeed();
					hitBox.SetPoints( new Vector2D(m_vPosition.x - 6, m_vPosition.y + 8), new Vector2D(m_vPosition.x + 6, m_vPosition.y + 16));
					centerBox.SetPoints( new Vector2D(m_vPosition.x, m_vPosition.y), new Vector2D(m_vPosition.x +1, m_vPosition.y + 1));
				}
		
		
	}
		
	@Override
	public void Draw() {
		
		SetZIndex((int)m_vPosition.y);
		
		if (m_uiNumberOfFrames > 0 && !dead) {
			if (mVisible == true) {
				// Draw the current frame
				m_Animations.get(m_uiCurrentAnim).Draw(m_vPosition);
				//hitBox.Draw();
				centerBox.Draw();
			}
		}
		
		if(dead) {
			if(m_Animations.get(6).DrawOnce(m_vPosition)) {
				mVisible = false;
			}
			
		}
	}
	
	@Override
	public void SetPosition(Vector2D pos) {
		m_vPosition.set(pos.x, pos.y);
		hitBox.SetPoints( new Vector2D(m_vPosition.x + 12, m_vPosition.y + 4), new Vector2D(m_vPosition.x + 20, m_vPosition.y + 28));
		//System.out.println(m_vPosition.x);
		for (int i = 0; i < m_uiNumberOfFrames; ++i) {
			m_Animations.get(i).SetPosition(pos.x,pos.y);
		}
	}


	@Override
	public void SetPosition(float x, float y) {
		m_vPosition.set(x, y);
		hitBox.SetPoints( new Vector2D(m_vPosition.x, m_vPosition.y), new Vector2D(m_vPosition.x + 10, m_vPosition.y + 8));
		for (int i = 0; i < m_uiNumberOfFrames; ++i) {
			m_Animations.get(i).SetPosition(x,y);
		}
	}
	
	public Vector2D getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Vector2D centerPoint) {
		this.centerPoint = centerPoint;
	}
	
}
