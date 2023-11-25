package sk.uniza.fri.world;
import java.awt.Color;
/**
 * Trieda reprezentuje text zobrazený na plátne
 *
 * (Prevzaté od učiteľa)
 */
public class Text {
    private String text;
    private int x;
    private int y;

    public Text() {
        this("Ahoj, svet...", 100, 100);
    }

    /**
     * Metóda konštruktor nastavý základné vlastnosti ako sú text, pozíciu y a pozíciu x
     * @param s - String
     * @param x - int
     * @param y - int
     */
    public Text(String s, int x, int y) {
        this.text = s;
        this.x = x;
        this.y = y;
        this.zobraz();
    }

    /**
     *  Metóda setText nastaví nový text, ktorý sa zobrazi na platne
     *  @param novyText - String
     */
    public void setText(String novyText) {
        this.skry();
        this.text = novyText;
        this.zobraz();
    }

    /**
     *  Metóda setPosition nastaví novú pozíciu textu
     *  @param x - int
     *  @param y - int
     */
    public void setPosition(int x, int y) {
        this.skry();
        this.x = x;
        this.y = y;
        this.zobraz();
    }

    /**
     *  Metóda zobraz nakreslí text na plátno
     */
    public void zobraz() {
        Canvas p = Canvas.getCanvas();
        p.drawString(text, x, y, Color.BLACK);
    }

    /**
     *  Metóda skryje text na plátne
     */
    public void skry() {
        Canvas p = Canvas.getCanvas();
        p.drawString(text, x, y, p.getColor());
    }
}