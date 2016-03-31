package mx.itesm.meatball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Roberto on 04/02/2016.
 */
public class Objeto {
    //dimensiones
    private float ancho;
    private float alto;
    private float alturaActual;
    private float velocidad=2;

    private Sprite sprite;

    // Estado
    private Estado estado;

    // Oculto
    private float tiempoOculto; // total oculto (azar)
    private float tiempoMuriendo;   // EL tiempo que tarda en morir (1)


    public Objeto(Texture textura){
        sprite=new Sprite(textura);
        ancho=sprite.getWidth();
        alto=sprite.getHeight();
        alturaActual=alto;
        estado = Estado.BAJANDO;

    }
    public void render(SpriteBatch batch){

        sprite.draw(batch);
    }
    public void setPosicion(float x, float y){
        sprite.setPosition(x, y);

    }
    private float tiempoAzar() {
        return (float)(Math.random()*2+0.5);
    }
    public void actualizar() {
        switch (estado) {
            case BAJANDO:
                alturaActual -= velocidad;
                if (alturaActual<=0) {
                    alturaActual = 0;
                    estado = Estado.OCULTO;
                    tiempoOculto = tiempoAzar();
                }
                break;
            case OCULTO:
                if (tiempoOculto<=0) {
                    estado = Estado.SUBIENDO;
                }
                tiempoOculto -= Gdx.graphics.getDeltaTime();
                break;
            case MURIENDO:
                tiempoMuriendo -= Gdx.graphics.getDeltaTime();
                sprite.setColor(Color.PURPLE);
                sprite.setRotation(sprite.getRotation()+30);
                if (tiempoMuriendo<=0) {
                    estado = Estado.OCULTO;
                    tiempoOculto = tiempoAzar();
                    alturaActual = 0;
                    sprite.setColor(Color.WHITE);
                    sprite.setRotation(0);
                }
                break;
            case SUBIENDO:
                alturaActual += velocidad;
                if (alturaActual>=alto) {
                    alturaActual = alto;
                    estado = Estado.BAJANDO;
                }
        }

        sprite.setRegion(0,0,(int)ancho,(int)alturaActual);
        sprite.setSize(ancho, alturaActual);
    }
    public enum Estado {
        BAJANDO,
        SUBIENDO,
        OCULTO,
        MURIENDO
    }
    public boolean detectarTouch(float x, float y) {
        if (x>=sprite.getX() && x<=sprite.getX()+ancho
                && y>=sprite.getY() && y<=sprite.getY()+alturaActual) {
            estado = Estado.MURIENDO;
            tiempoMuriendo = 1;
            return true;
        }
        return false;
    }

}
