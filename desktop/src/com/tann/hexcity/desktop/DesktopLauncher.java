package com.tann.hexcity.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.tann.hexcity.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable=false;
		config.vSyncEnabled=false;
		config.foregroundFPS=0;
		config.width=Main.width;
		config.height=Main.height;
		Settings settings = new Settings();
		settings.combineSubdirectories = true;
		TexturePacker.process(settings, "../images", "../android/assets", "atlas_image");
		config.title="Hexcity";
		config.addIcon("icon.png", FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}
