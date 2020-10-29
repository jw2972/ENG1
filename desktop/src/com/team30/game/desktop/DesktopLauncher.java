package com.team30.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.team30.game.GameContainer;

public class DesktopLauncher {
	public static void main(String[] arg) {
		System.out.println("Test");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameContainer(), config);
	}
}
