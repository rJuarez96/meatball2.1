package mx.itesm.meatball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Roberto on 31/03/2016.
 */
public class Enemy {
    public static final float VELOCIDAD_Y = -5f;   // Velocidad de caída
    private static float VELOCIDAD_X = 5;     // Velocidad horizontal
    private Array<Ataque> ataques;

    private Sprite sprite;  // Sprite cuando no se mueve
    private Texture texturaAlbondiga;
    private Sprite spriteAlbondiga;
    // Animación
    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame

    // Estados del personaje
    private EstadoMovimiento estadoMovimiento;
    private EstadoSalto estadoSalto;

    // SALTO del personaje
    private static final float V0 = 70;     // Velocidad inicial del salto
    private static final float G = 9.81f;
    private static final float G_2 = G/2;   // Gravedad
    private float yInicial;         // 'y' donde inicia el salto
    private float tiempoVuelo;       // Tiempo que estará en el aire
    private float tiempoSalto;      // Tiempo actual de vuelo
    private int tipo;
    private int vidas;
    private Texture ata;

    /*
    Constructor del personaje, recibe una imagen con varios frames, (ver imagen marioSprite.png)
     */
    public Enemy(Texture textura, int vidas) {
        //ata= new Texture(Gdx.files.internal("monedas.png"));
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en frames de 16x32 (ver marioSprite.png)
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(650,300);
        // Crea la animación con tiempo de 0.25 segundos entre frames.
        animacion = new Animation(0.25f,
                texturaPersonaje[0][11],
                texturaPersonaje[0][10],
                texturaPersonaje[0][9],
                texturaPersonaje[0][8],
                texturaPersonaje[0][7],
                texturaPersonaje[0][6],
                texturaPersonaje[0][5],
                texturaPersonaje[0][4],
                texturaPersonaje[0][3],
                texturaPersonaje[0][2],
                texturaPersonaje[0][1]);
        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite cuando para el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // quieto
        estadoMovimiento = EstadoMovimiento.INICIANDO;
        estadoSalto = EstadoSalto.EN_PISO;
        this.tipo=tipo;
        this.vidas=vidas;

    }

    // Dibuja el personaje
    public void render (SpriteBatch batch) {
        texturaAlbondiga= new Texture(Gdx.files.internal("vida Kit.png"));
        spriteAlbondiga=new Sprite(texturaAlbondiga);
        // Dibuja el personaje dependiendo del estadoMovimiento
        timerAnimacion += Gdx.graphics.getDeltaTime();
        // Obtiene el frame que se debe mostrar (de acuerdo al timer)
        TextureRegion region = animacion.getKeyFrame(timerAnimacion);
        batch.draw(region, sprite.getX(), sprite.getY());


        //spriteAlbondiga.setSize(5, 5);

        // Gdx.app.log("render","x="+getX()+" vidas = "+vidas);

        for (int i=0;i<vidas;i++){
            int espacio=i*134;
            //if ((int)getX()==2000 ){vidas-=1; break;}

            //Gdx.app.log("render","x="+vel);

            batch.draw(spriteAlbondiga,500+espacio,600);



        }

    }

    // Actualiza el sprite, de acuerdo al estadoMovimiento
    public void actualizar() {
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        nuevaX += VELOCIDAD_X;
        sprite.setX(nuevaX);
       /* if (nuevaX<=PantallaJuego.ANCHO_MAPA-sprite.getWidth()) {
            sprite.setX(nuevaX);
        }*/
    }

    // Avanza en su caída
    public void caer() {
        sprite.setY(sprite.getY() + VELOCIDAD_Y);
    }

    // Actualiza la posición en 'y', está saltando
    public void actualizarSalto() {
        // Ejecutar movimiento vertical
        float y = V0 * tiempoSalto - G_2 * tiempoSalto * tiempoSalto;  // Desplazamiento desde que inició el salto
        if (tiempoSalto > tiempoVuelo / 2) { // Llegó a la altura máxima?
            // Inicia caída
            estadoSalto = EstadoSalto.BAJANDO;
        }
        tiempoSalto += 10 * Gdx.graphics.getDeltaTime();  // Actualiza tiempo
        sprite.setY(yInicial + y);    // Actualiza posición
        if (y < 0) {
            // Regresó al piso
            sprite.setY(yInicial);  // Lo deja donde inició el salto
            estadoSalto = EstadoSalto.EN_PISO;  // Ya no está saltando
        }
    }

    // Accesor de la variable sprite
    public Sprite getSprite() {
        return sprite;
    }

    // Accesores para la posición
    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public void setPosicion(float x, int y) {
        sprite.setPosition(x,y);
    }
    public int getTipo(){
        return this.tipo;
    }
    public int getVidas(){
        return this.vidas;
    }

    // Accesor del estadoMovimiento
    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    // Modificador del estadoMovimiento
    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }

    public void setEstadoSalto(EstadoSalto estadoSalto) {
        this.estadoSalto = estadoSalto;
    }

    // Inicia el salto
    public void saltar() {
        if (estadoSalto==EstadoSalto.EN_PISO) {
            tiempoSalto = 0;
            yInicial = sprite.getY();
            estadoSalto = EstadoSalto.SUBIENDO;
            tiempoVuelo = 2 * V0 / G;
        }
    }

    public EstadoSalto getEstadoSalto() {
        return estadoSalto;
    }

    public enum EstadoMovimiento {
        INICIANDO,
        QUIETO,
        MOV_IZQUIERDA,
        MOV_DERECHA
    }

    public enum EstadoSalto {
        EN_PISO,
        SUBIENDO,
        BAJANDO,
        CAIDA_LIBRE // Cayó de una orilla
    }
    public void perderVida(){
        vidas-=1;

    }
    public void ganarVida(){
        if (vidas>=3){
            vidas=3;
        }else{
            vidas+=1;
        }
    }
    public void invertirX(){
        VELOCIDAD_X=VELOCIDAD_X*-1;
    }
    public Array<Ataque> atacar(){


        return ataques;

    }
}


