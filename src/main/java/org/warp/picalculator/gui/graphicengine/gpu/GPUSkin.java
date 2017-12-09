package org.warp.picalculator.gui.graphicengine.gpu;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.warp.picalculator.gui.graphicengine.GraphicEngine;
import org.warp.picalculator.gui.graphicengine.Skin;
import org.warp.picalculator.gui.graphicengine.gpu.GPURenderer.OpenedTextureData;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;

public class GPUSkin implements Skin {

	public Texture t;
	public int w;
	public int h;

	private String texturePath;
	private boolean initialized = false;
	private boolean isResource;
	
	GPUSkin(GraphicEngine d, String file) throws IOException {
		load(file);
	}

	@Override
	public void load(String file) throws IOException {
		boolean isResource = !Files.exists(Paths.get(file));
		if (isResource && (this.getClass().getClassLoader().getResource(file)) == null) {
			throw new IOException("File '" + file + "' not found!");
		}
		texturePath = file;
		this.isResource = isResource;
	}

	@Override
	public void initialize(GraphicEngine d) {
		try {
			final OpenedTextureData i = GPURenderer.openTexture(texturePath, isResource);
			final GL2ES1 gl = GPURenderer.gl;
			t = GPURenderer.importTexture(i.f, i.deleteOnExit);
			w = i.w;
			h = i.h;
			t.setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
			initialized = true;
		} catch (GLException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void use(GraphicEngine d) {
		if (!initialized) {
			initialize(d);
		}
		final GPURenderer r = (GPURenderer) d.getRenderer();
		r.useTexture(t, w, h);
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

}
