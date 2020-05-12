package com.iit.uni.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Matrix4f;

import com.iit.uni.engine.CSprite;
import com.iit.uni.engine.GameObject2D;
import com.iit.uni.engine.Utils;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.graph.ShaderProgram;
import com.iit.uni.engine.graph.Transformation;

/**
 * Simple renderer class
 * 
 * @author Mileff Peter
 *
 */
public class Renderer {

	private final Transformation transformation;

	private ShaderProgram shaderProgram;
	
	// Our line drawing shader
	public static ShaderProgram lineShader;
	
	// Orthogonal projection Matrix
	public static Matrix4f projectionMatrix;
	

	public Renderer() {
		transformation = new Transformation();
	}

	public void init(Window window) throws Exception {

		// Create shader
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(Utils.loadFile("shaders/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadFile("shaders/fragment.fs"));
		shaderProgram.link();

		// Create uniforms for world and projection matrices and texture
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("worldMatrix");
		shaderProgram.createUniform("texture_sampler");
		
		lineShader = new ShaderProgram();
		lineShader.createVertexShader(Utils.loadFile("shaders/line.vs"));
		lineShader.createFragmentShader(Utils.loadFile("shaders/line.fs"));
		lineShader.link();

		// Create uniforms for world and projection matrices and texture
		lineShader.createUniform("projectionMatrix");
		lineShader.createUniform("modelMatrix");
		lineShader.createUniform("linecolor");
		
		// Update orthogonal projection Matrix
		projectionMatrix = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window, GameObject2D gameObject) {
		clear();

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();

		shaderProgram.setUniform("projectionMatrix", projectionMatrix);
		shaderProgram.setUniform("texture_sampler", 0);

		// Render each gameItem

		Matrix4f worldMatrix = gameObject.GetCurrentFrame().GetCurrentFrameTextureWorldMatrix();
		shaderProgram.setUniform("worldMatrix", worldMatrix);

		// Render the sprite
		gameObject.Draw();

		shaderProgram.unbind();
		
		// Render bounding box
		gameObject.GetCurrentFrame().getCurrentFrameTransformedBoundingBox().Draw();
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
