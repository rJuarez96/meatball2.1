package mx.itesm.meatball;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Roberto on 17/02/2016.
 */

public class PantallaAcercaDe implements Screen {
    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;
    //fondo
    private Texture texturaFondo;
    private Sprite spriteFondo;
    private SpriteBatch batch;
    private Texture texturaBtnSalir;
    private Sprite spriteBtnSalir;
    public PantallaAcercaDe(Principal principal) {
        this.principal=principal;
}
    private void cargaTexturasSprites() {

        texturaFondo=new Texture(Gdx.files.internal("acerca de pantalla.png"));
        spriteFondo=new Sprite(texturaFondo);
        texturaBtnSalir=new Texture(Gdx.files.internal("reg.png"));
        spriteBtnSalir=new Sprite(texturaBtnSalir);
        spriteBtnSalir.setPosition(0,0);
    }

    @Override
    public void show() {
        camara=new OrthographicCamera(Principal.anchoMundo,Principal.altoMundo);
        camara.position.set(Principal.anchoMundo/2,Principal.altoMundo/2,0);
        vista= new StretchViewport(Principal.anchoMundo,Principal.altoMundo,camara);
        batch= new SpriteBatch();
        cargaTexturasSprites();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Proyectar camars
        leerEntrada();
        batch.setProjectionMatrix(camara.combined);
        //dibujamos
        batch.begin();
        spriteFondo.draw(batch);

        spriteBtnSalir.draw(batch);


        batch.end();

    }

    private void leerEntrada() {
        if (Gdx.input.justTouched()==true){
            Vector3 coordenadas=new Vector3();
            coordenadas.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camara.unproject(coordenadas);//transforma coordenada
            float touchX=coordenadas.x;
            float touchY=coordenadas.y;
            if (touchX>=spriteBtnSalir.getX() && touchX<=spriteBtnSalir.getX()+spriteBtnSalir.getWidth() && touchY>=spriteBtnSalir.getY() && touchY<=spriteBtnSalir.getY()+spriteBtnSalir.getHeight()){
                borrarPantalla();
                principal.setScreen(new pantallaMenu(principal));

            }

        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
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
    private void borrarPantalla() {
        Gdx.gl.glClearColor(0.42f, 0.55f, 1, 1);    // r, g, b, alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
    texturaFondo.dispose();
        texturaBtnSalir.dispose();
    }
}
