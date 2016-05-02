package mx.itesm.meatball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;import java.lang.String;


public class Texto
{
    private BitmapFont font;

    public Texto() {
        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().scale(2);
    }

    public void mostrarMensaje(String mensaje, float x, float y, SpriteBatch batch) {
        GlyphLayout glyp = new GlyphLayout(font,mensaje);
        glyp.setText(font,mensaje);
        float ancho = glyp.width;
        font.draw(batch,glyp,x-ancho/2,y);
    }
}
