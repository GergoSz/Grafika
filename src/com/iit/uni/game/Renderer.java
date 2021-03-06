package com.iit.uni.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.iit.uni.engine.Utils;
import com.iit.uni.engine.Window;
import com.iit.uni.engine.graph.ShaderProgram;
import com.iit.uni.engine.graph.Transformation;
import com.iit.uni.engine.math.MATRIX4X4;

/**
 * Simple renderer class
 * 
 * @author Mileff Peter
 *
 */
public class Renderer {

	public final Transformation transformation;

	public ShaderProgram shaderProgram;

	// Our line drawing shader
	public ShaderProgram lineShader;

	// Orthogonal projection Matrix
	public Matrix4f projectionMatrix;

	public static Renderer mRenderer;
	
	//private Player p;

	public Renderer() {
		transformation = new Transformation();
	}

	public void init(Window window) throws Exception {

		mRenderer = this;

		// Create shader
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(Utils.loadFile("shaders/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadFile("shaders/fragment.fs"));
		shaderProgram.link();

		// Create uniforms for world and projection matrices and texture
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("worldMatrix");
		shaderProgram.createUniform("texture_sampler");

		//shaderProgram.createUniform("texture_color");

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
		//p = Player.getInstance();
		
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window) {
		clear();

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}
		
	
		
		//projectionMatrix.translate(new Vector3f(p.GetPosition().x,p.GetPosition().y,0));
		//projectionMatrix = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
		
		// Render the sprite
		DummyGame.sceneManager.Render();
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
