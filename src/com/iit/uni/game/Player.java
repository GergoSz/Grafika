package com.iit.uni.game;

import com.iit.uni.engine.BoundingBox2D;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.SpriteLoader;
import com.iit.uni.engine.math.Vector2D;
import com.sun.java.swing.plaf.windows.resources.windows;

public class Player extends GameObject2D{

	private BoundingBox2D hitBox = new BoundingBox2D(new Vector2D(20, 22), new Vector2D(30, 32));
	public BoundingBox2D feetBox = new BoundingBox2D(new Vector2D(20, 22), new Vector2D(30, 32));
	
	private Vector2D centerPoint = new Vector2D();
	
	private BoundingBox2D testbox = new BoundingBox2D(new Vector2D(centerPoint.x - 5, centerPoint.y - 5), new Vector2D(centerPoint.x + 5, centerPoint.y + 5));
	private BoundingBox2D testbox2 = new BoundingBox2D(new Vector2D(centerPoint.x - 5, centerPoint.y - 5), new Vector2D(centerPoint.x + 5, centerPoint.y + 5));
	
	private static Player instance = null;
	
	private Player() {
		SpriteLoader spriteLoader = SpriteLoader.getInstance();
		this.AddFrame(spriteLoader.GetAnim("pIdleD"));
		this.AddFrame(spriteLoader.GetAnim("pRunR"));
		this.AddFrame(spriteLoader.GetAnim("pRunL"));
		this.AddFrame(spriteLoader.GetAnim("pRunU"));
		this.AddFrame(spriteLoader.GetAnim("pRunD"));
		this.AddFrame(spriteLoader.GetAnim("pAttackD"));
		this.AddFrame(spriteLoader.GetAnim("pIdleU"));
		this.AddFrame(spriteLoader.GetAnim("pIdleR"));
		this.AddFrame(spriteLoader.GetAnim("pIdleL"));
		
		this.SetPosition(0,0);
		hitBox = new BoundingBox2D(  new Vector2D(m_vPosition.x + 12, m_vPosition.y + 4), new Vector2D(m_vPosition.x + 20, m_vPosition.y + 28));
		feetBox = new BoundingBox2D(  new Vector2D(m_vPosition.x + 12, m_vPosition.y + 22), new Vector2D(m_vPosition.x + 20, m_vPosition.y + 28));
		
	}
	
	public static  Player getInstance() {
		if(instance == null) {
			synchronized (Player.class) {
				if(instance == null) {
					instance = new Player();
				}
			}
		}
		return instance;
	}
	
	@Override
	public void Draw() {
		
		SetZIndex((int)m_vPosition.y);
		
		if (m_uiNumberOfFrames > 0) {
			if (mVisible == true) {
				// Draw the current frame
				m_Animations.get(m_uiCurrentAnim).Draw(m_vPosition);
				feetBox.Draw();
				testbox.Draw();
				testbox2.Draw();
//				hitBox.Draw();
			}
		}
	}
	
	@Override
	public void SetPosition(Vector2D pos) {
		m_vPosition.set(pos.x, pos.y);
		hitBox.SetPoints( new Vector2D(m_vPosition.x + 12, m_vPosition.y + 4), new Vector2D(m_vPosition.x + 20, m_vPosition.y + 28));
		feetBox.SetPoints(  new Vector2D(m_vPosition.x - 6, m_vPosition.y + 6), new Vector2D(m_vPosition.x + 6, m_vPosition.y + 14));
		testbox = new BoundingBox2D(new Vector2D(m_vPosition.x , m_vPosition.y ), new Vector2D(m_vPosition.x +1 , m_vPosition.y +1));
		testbox2 = new BoundingBox2D(new Vector2D(centerPoint.x -40, centerPoint.y -40), new Vector2D(centerPoint.x +40 , centerPoint.y +40));
		//System.out.println(m_vPosition.x);
		for (int i = 0; i < m_uiNumberOfFrames; ++i) {
			m_Animations.get(i).SetPosition(pos.x,pos.y);
		}
	}


	@Override
	public void SetPosition(float x, float y) {
		m_vPosition.set(x, y);
		hitBox.SetPoints( new Vector2D(m_vPosition.x + 12, m_vPosition.y + 4), new Vector2D(m_vPosition.x + 20, m_vPosition.y + 28));
		feetBox.SetPoints(  new Vector2D(m_vPosition.x + 12, m_vPosition.y + 22), new Vector2D(m_vPosition.x + 20, m_vPosition.y + 28));
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
