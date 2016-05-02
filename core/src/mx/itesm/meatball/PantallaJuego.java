package mx.itesm.meatball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



/**
 * Created by Roberto on 04/02/2016.
 */
public class PantallaJuego implements Screen {
    public static final float ANCHO_MAPA = 8000;


    private final Principal principal;
    private OrthographicCamera camara;
    private Viewport vista;
    //fondo
    private Texture texturaFondo;
    private Sprite spriteFondo;

    private Texto texto;

    //dibujar
    private SpriteBatch batch;

    private Texture texturaAlbondiga;
    private Sprite spriteAlbondiga;
    /*************DESCOMENTAR Y CHECAR MAPAS**********/
    private TiledMap mapa;      // Información del mapa en memoria
    private OrthogonalTiledMapRenderer rendererMapa;    // Objeto para dibujar el mapa

    //sonidos
    //private Sound efectoGolpe;
    private Music musicaFondo;

    // Personaje
    private Texture texturaPersonaje;       // Aquí cargamos la imagen albondigaSprite.png con varios frames
    private Personaje albondiga;
    public static final int TAM_CELDA = 32;
    // HUD. Los componentes en la pantalla que no se mueven
    private OrthographicCamera camaraHUD;
    // Botón saltar
    private Texture texturaSalto;
    private Boton btnSalto;
    private Texture texturaBtnPausa;
    private Boton pausaBtn;
    private Texture texturaReanudar;
    private Boton reanudarBtn;
    private Texture texturaPerdio;
    private Sprite spritePerdio;
    private Texture texturaPausa;
    private Sprite spritePausa;
    private Texture otra;
    private Boton btnOtra;
    private Texture texturaSiguiente;
    private Boton btnSig;
    private Texture textturakitty;
    private Sprite spriteKitty;
    private int puntaje;
    //private  int Nivel;

    // Estados del juego
    private EstadosJuego estadoJuego;

    public PantallaJuego(Principal principal) {

        this.principal=principal;

        //marcador=0;

    }
    @Override
    public void show() {
        //se ejecuta uando se muestra
        puntaje=0;
        camara = new OrthographicCamera(principal.anchoMundo, principal.altoMundo);
        camara.position.set(principal.anchoMundo / 2, principal.altoMundo / 2, 0);
        camara.update();
        vista = new StretchViewport(principal.anchoMundo, principal.altoMundo, camara);

        batch = new SpriteBatch();

        // Cámara para HUD
        camaraHUD = new OrthographicCamera(principal.anchoMundo, principal.altoMundo);
        camaraHUD.position.set(principal.anchoMundo / 2, principal.altoMundo / 2, 0);
        camaraHUD.update();

        cargarRecursos();
        crearObjetos();

        // Indicar el objeto que atiende los eventos de touch (entrada en general)
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

        estadoJuego = EstadosJuego.JUGANDO;
        //efectoGolpe=Gdx.audio.newSound(Gdx.files.internal("golpe.wav"));
        musicaFondo=Gdx.audio.newMusic(Gdx.files.internal("POL-macaron-island-short.ogg"));
        musicaFondo.setLooping(true);
        musicaFondo.play();
        texto=new Texto();
    }
    private void cargarRecursos() {
        // Cargar las texturas/mapas
        AssetManager assetManager = principal.getAssetManager();   // Referencia al assetManager
        //assetManager.load("Mapa.tmx",TiledMap.class);
        assetManager.load("MapaAlbondiRun_Nivel1.tmx", TiledMap.class);  // Cargar info del mapa
           // Cargar de albondiga
        // Texturas de los botones
        assetManager.load("PugSalto.png", Texture.class);
        assetManager.load("PugCorrer.png", Texture.class);
        assetManager.load("salto.png", Texture.class);
        assetManager.load("fin.jpg", Texture.class);
        assetManager.load("botonPausa.png", Texture.class);
        assetManager.load("mira mama sin botones.png",Texture.class);
        assetManager.load("b5.png", Texture.class);
        assetManager.load("reg.png",Texture.class);
        assetManager.load("MrKitty copia.png",Texture.class);
        assetManager.load("botonsiguiente.png",Texture.class);


        // Se bloquea hasta que cargue todos los recursos
        assetManager.finishLoading();

    }


    private void crearObjetos() {
        AssetManager assetManager = principal.getAssetManager();   // Referencia al assetManager
        // Carga el mapa en memoria

        mapa = assetManager.get("MapaAlbondiRun_Nivel1.tmx");

       // mapa.getLayers().get(0).setVisible(false);
       // (;
        // Crear el objeto que dibujará el mapa
        rendererMapa = new OrthogonalTiledMapRenderer(mapa,batch);
        rendererMapa.setView(camara);
        // Cargar frames
        texturaPersonaje = assetManager.get("PugCorrer" +
                ".png");
        // Crear el personaje
        albondiga = new Personaje(texturaPersonaje,3,1);
        // Posición inicial del personaje
        albondiga.getSprite().setPosition(0, 200);

        // Crear los botones
        texturaSalto = assetManager.get("salto.png");
        btnSalto = new Boton(texturaSalto);
        btnSalto.setPosicion(principal.anchoMundo - 5 * TAM_CELDA, 5 * TAM_CELDA);

        btnSalto.setAlfa(0.7f);

        btnSalto.setSize((int) btnSalto.getWidth() / 2, (int) btnSalto.getHeight() / 2);
        texturaBtnPausa=assetManager.get("botonPausa.png");
        pausaBtn=new Boton(texturaBtnPausa);
        pausaBtn.setPosicion(camara.position.x+320, 18 * TAM_CELDA);
        pausaBtn.setAlfa(0.7f);
        texturaPerdio=assetManager.get("fin.jpg");
        spritePerdio= new Sprite(texturaPerdio);
        spritePerdio.setPosition(0, 0);
        texturaPausa=assetManager.get("mira mama sin botones.png");
        spritePausa= new Sprite(texturaPausa);
        spritePausa.setSize((int)(spritePausa.getWidth()*.5),(int)(spritePausa.getHeight()*.5));
        spritePausa.setPosition(camara.position.x - 150, 50);
        texturaReanudar=assetManager.get("b5.png");
        reanudarBtn=new Boton(texturaReanudar);
        reanudarBtn.setPosicion(640,300);
        reanudarBtn.setSize((int) (reanudarBtn.getWidth() * .5), (int) (reanudarBtn.getHeight() * .5));
        otra=assetManager.get("reg.png");
        btnOtra=new Boton(otra);
        btnOtra.setPosicion(0,0);
        textturakitty=assetManager.get("MrKitty copia.png");
        spriteKitty=new Sprite(textturakitty);
        spriteKitty.setPosition(7400, 300);
        texturaSiguiente=assetManager.get("botonsiguiente.png");
        btnSig=new Boton(texturaSiguiente);
        btnSig.setPosicion(1000,100);




    }

    private void cargaTexturasSprites() {
        texturaFondo=new Texture(Gdx.files.internal("2sa2.jpg"));
        spriteFondo=new Sprite(texturaFondo);
        texturaAlbondiga= new Texture(Gdx.files.internal("Albondiga.png"));
        spriteAlbondiga=new Sprite(texturaAlbondiga);
        spriteAlbondiga.setPosition(80, 10);
        spriteAlbondiga.setSize(120, 120);


    }

    @Override
    public void render(float delta) {


        // Actualizar objetos en la pantalla
        if (albondiga.getVidas()<=0){estadoJuego=EstadosJuego.PERDIO;}
        if (albondiga.getX()>=8000){estadoJuego=EstadosJuego.GANO;}
        //leerEntrada();

        //Gdx.app.log("render","estado= "+Nivel);
        switch (estadoJuego) {
            case JUGANDO:
            moverPersonaje();
            actualizarCamara(); // Mover la cámara para que siga al personaje

            // Dibujar
            borrarPantalla();

            batch.setProjectionMatrix(camara.combined);

            rendererMapa.setView(camara);
            rendererMapa.render();  // Dibuja el mapa
            // Entre begin-end dibujamos nuestros objetos en pantalla
            batch.begin();

            albondiga.render(batch);
               // Dibuja el personaje
                //spritePerdio.draw(batch);
                if (albondiga.getX()>=700){
                    spriteKitty.draw(batch);

                }

            batch.end();

            // Dibuja el HUD
            batch.setProjectionMatrix(camaraHUD.combined);
            batch.begin();

            btnSalto.render(batch);
                pausaBtn.render(batch);
                pausaBtn.setPosicion(camaraHUD.position.x + 400, 600);
                texto.mostrarMensaje("Puntaje"+puntaje, Principal.altoMundo / 2+200, Principal.altoMundo * 0.95f, batch);

            batch.end();
                break;
            case PERDIO:
                musicaFondo.stop();


                borrarPantalla();
                principal.setScreen(new pantallaMenu(principal));

                //camara.position.set(0,0, 0);

                /*batch.setProjectionMatrix(camara.combined);*/

                break;

            case PAUSADO:


               //borrarPantalla();

                //camara.position.set(0,0, 0);

                batch.setProjectionMatrix(camaraHUD.combined);
                batch.begin();

                spritePausa.draw(batch);  // Dibuja el personaje
                reanudarBtn.render(batch);
                reanudarBtn.setPosicion(camaraHUD.position.x,300);
                pausaBtn.setPosicion(camaraHUD.position.x+320, 18 * TAM_CELDA);
                //leerEntradaPausa();
                //Gdx.app.log("render", "Pusado ");
                batch.end();
                break;
            case GANO:
                musicaFondo.stop();
                borrarPantalla();

                //camara.position.set(0,0, 0);

                /*batch.setProjectionMatrix(camara.combined);*/
                batch.begin();

                spritePerdio.draw(batch);
                btnSig.render(batch);
                //Nivel++;
                //principal.setScreen(new PantallaJuego(principal));
                batch.end();
                break;

        }
        if (Gdx.input.justTouched()==true) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(coordenadas);//transforma coordenada
            float touchX = coordenadas.x;
            float touchY = coordenadas.y;
           Gdx.app.log("leer","touch ="+touchX+" x de la pausa "+reanudarBtn.getX() );
            if (touchX>=reanudarBtn.getX() && touchX<=reanudarBtn.getX()+reanudarBtn.getWidth() && touchY>=reanudarBtn.getY() && touchY<=reanudarBtn.getY()+reanudarBtn.getHeight()){
                //Gdx.app.log("leer","touch entro" );
                estadoJuego=EstadosJuego.JUGANDO;
            }

        }
    }

    private void leerEntrada() {

        if (Gdx.input.justTouched()==true) {
            Vector3 coordenadas = new Vector3();
            coordenadas.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camaraHUD.unproject(coordenadas);//transforma coordenada
            float touchX = coordenadas.x;
            float touchY = coordenadas.y;
            //Gdx.app.log("leer","touch ="+touchX+" x de la pausa "+pausaBtn.getX() );
            if (touchX>=pausaBtn.getX() && touchX<=pausaBtn.getX()+pausaBtn.getWidth() && touchY>=pausaBtn.getY() && touchY<=pausaBtn.getY()+pausaBtn.getHeight()){
                Gdx.app.log("leer","touch entro" );
                estadoJuego=EstadosJuego.PAUSADO;
            }

        }
    }

    private void moverPersonaje() {
        // Prueba caída libre inicial o movimiento horizontal
        albondiga.actualizar();

        switch (albondiga.getEstadoMovimiento()) {
            case INICIANDO:     // Mueve el personaje en Y hasta que se encuentre sobre un bloque
                // Los bloques en el mapa son de 16x16
                // Calcula la celda donde estaría después de moverlo
                int celdaX = (int) (albondiga.getX() / TAM_CELDA);
                int celdaY = (int) ((albondiga.getY() + albondiga.VELOCIDAD_Y) / TAM_CELDA);
                TiledMapTileLayer capaMala = (TiledMapTileLayer) mapa.getLayers().get("Items");


                for(int i=0;i<4;i++){
                    for (int j=0;j<4;j++){
                        if (capaMala.getCell(celdaX+i, celdaY+j)!=null){
                            capaMala.setCell(celdaX+i, celdaY+j, null);
                            albondiga.perderVida();
                            puntaje-=100;
                        }
                    }
                }

                // Recuperamos la celda en esta posición
                // La capa 0 es el fondo
                TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Banqueta");
                TiledMapTileLayer.Cell celda = capa.getCell(celdaX, celdaY);
                // probar si la celda está ocupada
                if (celda == null ) {
                    // Celda vacía, entonces el personaje puede avanzar
                    albondiga.caer();
                } else {
                    // Dejarlo sobre la celda que lo detiene
                    albondiga.setPosicion(albondiga.getX(), (celdaY + 1) * TAM_CELDA);
                    albondiga.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                }

                break;
            case MOV_DERECHA:       // Se mueve horizontal
            case MOV_IZQUIERDA:
                albondiga.actualizar();
                break;
        }
        // Prueba si debe caer por llegar a un espacio vacío
        if ( albondiga.getEstadoMovimiento()!= Personaje.EstadoMovimiento.INICIANDO
                && (albondiga.getEstadoSalto() != Personaje.EstadoSalto.SUBIENDO) ) {
            // Calcula la celda donde estaría después de moverlo
            //int celdaX = (int) (albondiga.getX() / TAM_CELDA);
            //int celdaY = (int) ((albondiga.getY() + albondiga.VELOCIDAD_Y) / TAM_CELDA);
            int celdaX = (int) ((albondiga.getX() / TAM_CELDA));
            int celdaY = (int) (((albondiga.getY() + albondiga.VELOCIDAD_Y) / TAM_CELDA));
            TiledMapTileLayer capaMala = (TiledMapTileLayer) mapa.getLayers().get("Items");


            for(int i=0;i<4;i++){
                for (int j=0;j<4;j++){
                    if (capaMala.getCell(celdaX+i, celdaY+j)!=null){
                        capaMala.setCell(celdaX+i, celdaY+j, null);
                        albondiga.perderVida();
                        puntaje-=100;
                    }
                }
            }
            TiledMapTileLayer capaBuena  = (TiledMapTileLayer) mapa.getLayers().get("ItemsBuenos");


            for(int i=0;i<4;i++){
                for (int j=0;j<4;j++){
                    if (capaBuena.getCell(celdaX+i, celdaY+j)!=null){
                        capaBuena.setCell(celdaX+i, celdaY+j, null);
                        albondiga.ganarVida();
                        puntaje+=100;
                    }
                }
            }
            // Recuperamos la celda en esta posición
            // La capa 0 es el fondo
            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Banqueta");
            TiledMapTileLayer.Cell celdaAbajo = capa.getCell(celdaX, celdaY);

            // probar si la celda está ocupada
            if ( celdaAbajo==null  ) {
                // Celda vacía, entonces el personaje puede avanzar
                albondiga.caer();
                //albondiga.setEstadoSalto(Personaje.EstadoSalto.CAIDA_LIBRE);
            } else {
                // Dejarlo sobre la celda que lo detiene
                albondiga.setPosicion(albondiga.getX(), (celdaY + 1) * TAM_CELDA);
                albondiga.setEstadoSalto(Personaje.EstadoSalto.EN_PISO);


            }
        }

        // Saltar
        switch (albondiga.getEstadoSalto()) {
            case SUBIENDO:
            case BAJANDO:
                albondiga.actualizarSalto();    // Actualizar posición en 'y'
                break;
        }
    }

    private void actualizarCamara() {
        float posX = albondiga.getX();

       //Gdx.app.log("actualizarC","x de la camara="+camara.position.x+"    post x ="+posX);
        // Si está en la parte 'media'
        if (posX>=320) {
            // El personaje define el centro de la cámara
            camara.position.set((int)posX+320, camara.position.y, 0);
        }
        if (camara.position.x>=8000-640) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(8000-640, camara.position.y, 0);
        }
        camara.update();
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
        // Los assets se liberan a través del assetsManager
        AssetManager assetManager = principal.getAssetManager();
        assetManager.unload("Albondiga.png");
        assetManager.unload("Mapa.tmx");
        assetManager.unload("MapaAlbondiRun_Nivel1.tmx");  // Cargar info del mapa
        assetManager.unload("PugCorrer.png");    // Cargar de albondiga
        // Texturas de los botones
        assetManager.unload("PugSalto.png");
        assetManager.unload("PugCorrer.png");
        assetManager.unload("salto.png");
        assetManager.unload("fin.jpg");
        assetManager.unload("botonAjustes.png");
        assetManager.unload("mira mama sin botones.png");
        assetManager.unload("b5.png");
        musicaFondo.dispose();

    }
    private void borrarPantalla() {
        Gdx.gl.glClearColor(0.42f, 0.55f, 1, 1);    // r, g, b, alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    public enum EstadosJuego {
        GANO,
        JUGANDO,
        PAUSADO,
        PERDIO
    }
    /*
Clase utilizada para manejar los eventos de touch en la pantalla
 */
    public class ProcesadorEntrada extends InputAdapter
    {
        private Vector3 coordenadas = new Vector3();
        private float x, y;     // Las coordenadas en la pantalla


        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            transformarCoordenadas(screenX, screenY);
            if (estadoJuego==EstadosJuego.JUGANDO) {
                // Preguntar si las coordenadas están sobre el botón derecho
                if (btnSalto.contiene(x, y)) {
                    // Tocó el botón saltar
                    albondiga.saltar();
                }

            }
            if (estadoJuego==EstadosJuego.JUGANDO){
                if (pausaBtn.contiene(x,y)){
                    estadoJuego=EstadosJuego.PAUSADO;
                }
            }
            if (estadoJuego==EstadosJuego.PAUSADO){
                if (reanudarBtn.contiene(x,y)){
                    estadoJuego=EstadosJuego.JUGANDO;
                }

            }
            if (estadoJuego==EstadosJuego.PERDIO){
                if (btnOtra.contiene(x,y)){
                    principal.setScreen(new PantallaJuego(principal));
                }

            }
            if (estadoJuego==EstadosJuego.GANO){
                if (btnSig.contiene(x,y)){
                    principal.setScreen(new PantallaJuego2(principal));
                }

            }
            return true;    // Indica que ya procesó el evento
        }




        private void transformarCoordenadas(int screenX, int screenY) {
            // Transformar las coordenadas de la pantalla física a la cámara HUD
            coordenadas.set(screenX, screenY, 0);
            camaraHUD.unproject(coordenadas);
            // Obtiene las coordenadas relativas a la pantalla virtual
            x = coordenadas.x;
            y = coordenadas.y;
        }

    }

}
