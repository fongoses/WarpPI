package it.cavallium.warppi.gui.graphicengine;

import it.cavallium.warppi.util.EventSubscriber;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public interface GraphicEngine {

	int[] getSize();

	boolean isSupported();
	
	boolean isInitialized();

	void setTitle(String title);

	void setResizable(boolean r);

	void setDisplayMode(int ww, int wh);

	void create(Runnable object);
	
	default void create() {
		create(null);
	};

	EventSubscriber<Integer[]> onResize();

	int getWidth();

	int getHeight();

	void destroy();

	void start(RenderingLoop d);

	void repaint();

	Renderer getRenderer();

	BinaryFont loadFont(String fontName) throws IOException;

	BinaryFont loadFont(String path, String fontName) throws IOException;

	Skin loadSkin(String file) throws IOException;

	boolean doesRefreshPauses();

	default boolean supportsFontRegistering() {
		return false;
	}

	default List<BinaryFont> getRegisteredFonts() {
		return null;
	}
}
