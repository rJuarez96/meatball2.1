package mx.itesm.demo01;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Demo extends ApplicationAdapter {

	private final float anchoMundo=1280;
	private final float altoMundo=720;
	private OrthographicCamera camara;
	private Viewport vista;
	SpriteBatch batch;
	Texture imgFondo;
	
	@Override
	public void create () {
		camara=new OrthographicCamera();
		camara.position.set(anchoMundo/2,altoMundo/2,0);
		camara.update();
		vista=new StretchViewport(anchoMundo,altoMundo,camara);
		batch = new SpriteBatch();
		imgFondo = new Texture("poke1270.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camara.combined);
		batch.begin();
		batch.draw(imgFondo, 0, 0);
		batch.end();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		vista.update(width,height);
	}
}
