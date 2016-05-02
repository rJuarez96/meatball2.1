package mx.itesm.meatball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Roberto on 25/01/2016.
 */
public class pantallaMenu implements Screen {

    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;
    //fondo
    private Texture texturaFondo;
    private Sprite spriteFondo;
    //boton play
    private Texture texturaPlay;
    private Sprite spriteTexturaPlay;
    //acerca de
    private Texture texturaAcercade;
    private Sprite spriteAcercaDe;
    //salir
    private Texture texturaBtnSalir;
    private Sprite spriteBtnSalir;

    private Texture textureBtnIns;
    private Sprite spriteBtnIns;
    //dibujar
    private SpriteBatch batch;
    private Music musicaFondo;



    public pantallaMenu(Principal principal) {
        this.principal=principal;
    }

    @Override
    public void show() {
        //se ejecuta uando se muestra
        camara=new OrthographicCamera(Principal.anchoMundo,Principal.altoMundo);
        camara.position.set(Principal.anchoMundo/2,Principal.altoMundo/2,0);
        vista= new StretchViewport(Principal.anchoMundo,Principal.altoMundo,camara);
        batch= new SpriteBatch();

        //fondo
        cargaTexturasSprites();
        musicaFondo=Gdx.audio.newMusic(Gdx.files.internal("POL-candy-valley-short.ogg"));
        musicaFondo.setLooping(true);
        musicaFondo.play();
    }

    private void cargaTexturasSprites() {
        texturaFondo=new Texture(Gdx.files.internal("Pantalla_menu.png"));
        spriteFondo=new Sprite(texturaFondo);

        texturaPlay= new Texture(Gdx.files.internal("botonJugar.png"));
        spriteTexturaPlay= new Sprite(texturaPlay);
        spriteTexturaPlay.setPosition(110, 270);
        //spriteTexturaPlay.setSize(450, 190);
        texturaAcercade= new Texture(Gdx.files.internal("botonAcerca.png"));
        spriteAcercaDe= new Sprite(texturaAcercade);
        spriteAcercaDe.setPosition(110,200);
        //spriteAcercaDe.setSize(400, 100);
        texturaBtnSalir=new Texture(Gdx.files.internal("botonSalir.png"));
        spriteBtnSalir=new Sprite(texturaBtnSalir);
        spriteBtnSalir.setPosition(110,130);
        //spriteBtnSalir.setSize(400, 100);
        textureBtnIns=new Texture(Gdx.files.internal("botonInst.png"));
        spriteBtnIns=new Sprite(textureBtnIns);
        spriteBtnIns.setPosition(170, 600);
        //spriteBtnIns.setSize(400,90);
        //spriteTexturaPlay.setPosition(Principal.anchoMundo / 2 - spriteBtnSalir.getWidth() / 2, Principal.altoMundo / 4);

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
        spriteTexturaPlay.draw(batch);
        spriteBtnSalir.draw(batch);
        spriteAcercaDe.draw(batch);
        spriteBtnIns.draw(batch);

        batch.end();

    }
    private void leerEntrada(){
        if (Gdx.input.justTouched()==true){
            Vector3 coordenadas=new Vector3();
            coordenadas.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camara.unproject(coordenadas);//transforma coordenada
            float touchX=coordenadas.x;
            float touchY=coordenadas.y;


            if (touchX>=spriteBtnSalir.getX() && touchX<=spriteBtnSalir.getX()+spriteBtnSalir.getWidth() && touchY>=spriteBtnSalir.getY() && touchY<=spriteBtnSalir.getY()+spriteBtnSalir.getHeight()){
                Gdx.app.exit();
            }
                //toco play?
            if (touchX>=spriteTexturaPlay.getX() && touchX<=spriteTexturaPlay.getX()+spriteTexturaPlay.getWidth() && touchY>=spriteTexturaPlay.getY() && touchY<=spriteTexturaPlay.getY()+spriteTexturaPlay.getHeight()){
                principal.setScreen(new PantallaJuego(principal));
                musicaFondo.stop();
            }
            if (touchX>=spriteAcercaDe.getX() && touchX<=spriteAcercaDe.getX()+spriteAcercaDe.getWidth() && touchY>=spriteAcercaDe.getY() && touchY<=spriteAcercaDe.getY()+spriteAcercaDe.getHeight()){
                principal.setScreen(new PantallaAcercaDe(principal));
                musicaFondo.stop();
            }
            if (touchX>=spriteBtnIns.getX() && touchX<=spriteBtnIns.getX()+spriteBtnIns.getWidth() && touchY>=spriteBtnIns.getY() && touchY<=spriteBtnIns.getY()+spriteBtnIns.getHeight()){
                principal.setScreen(new pantallaIns(principal));
                musicaFondo.stop();
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

    @Override
    public void dispose() {
    //cualdo la pantalla sale de memoria
        //Libera recursos
        texturaFondo.dispose();//regresamos la memoria
        texturaAcercade.dispose();
        texturaBtnSalir.dispose();
        texturaPlay.dispose();
        musicaFondo.dispose();


    }

}
