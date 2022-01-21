import sas.*;
import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse Stein.
 * 
 *
 * @author (Felix, Marvin) 
 * @version (21.09.21)
 */
public class Stein
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int x;
    private int y;
    private int radius;
    private Color color;
        

    /**
     * Konstruktor für Objekte der Klasse Stein
     * 
     * @param x x-Koordinate des Steins
     * @param y y-Koordinate des Steins
     * @param radius Radius des Steins
     * @param color Farbe des Steins
     */
    public Stein(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    
    /**
     * Konstruktor für Objekte der Klasse Stein
     * 
     * @param radius Radius des Steins
     * @param color Farbe des Steins
     */
    public Stein(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }
    
    /**
     * Gibt die aktuelle x-Koordinate des Steins an.
     * 
     * @return x
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Gibt die aktuelle y-Koordinate des Steins an.
     * 
     * @return y
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * Setzt eine neue x-Koordinate des Steins fest.
     * 
     * @param x - neue x-Koordinate des Steins
     */
    public void setX(int newX)
    {
        x = newX;
    }
    
    /**
     * Setzt eine neue y-Koordinate des Steins fest.
     * 
     * @param y - neue y-Koordinate des Steins
     */
    public void setY(int newY)
    {
        y = newY;
    }
    
    /**
     * Gibt den aktuellen Radius, also die Größe des Steins an.
     * 
     * @return radius
     */
    public int getRadius()
    {
        return radius;
    }
    
    /**
     * Gibt die aktuelle Farbe des Steins an.
     * 
     * @return color - Die Farbe des Steins
     */
    public Color getColor()
    {
        return color;
    }
}