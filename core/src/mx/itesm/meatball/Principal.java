package mx.itesm.meatball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Principal extends Game {

	public static final float anchoMundo = 1280;
	public static final float altoMundo = 720;
	private final AssetManager assetManager = new AssetManager();
	@Override
	public void create() {

		// Agregamos un loader para los mapas
		assetManager.setLoader(TiledMap.class,
				new TmxMapLoader(new InternalFileHandleResolver()));
		// Pantalla inicial
		setScreen(new pantallaMenu(this));
	}

	// Método accesor de assetManager
	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void dispose() {
		super.dispose();
		assetManager.clear();
	}
}