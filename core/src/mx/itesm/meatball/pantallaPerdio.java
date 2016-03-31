package mx.itesm.meatball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Roberto on 30/03/2016.
 */
public class pantallaPerdio implements Screen {
    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;
    //fondo
    private Texture texturaFondo;
    private Sprite spriteFondo;
    private SpriteBatch batch;
    private Texture texturaBtnSalir;
    private Sprite spriteBtnSalir;
    public pantallaPerdio(Principal principal) {
        this.principal=principal;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
