package sk.uniza.fri.world;

import sk.uniza.fri.world.buildings.Building;
import sk.uniza.fri.world.inhabitants.Inhabitant;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 *
 * Reprezentuje jedno policko hracej plochy
 *
 */
public class Area {
    private int posX; //pixels
    private int posY; //pixels
    private int width;
    private int height;
    private String color;
    private boolean isVisible;
    private Random rand;
    private Building buildingOnthisArea;
    private Inhabitant inhabitantOnThisArea;
    private Tribe owner;

    public Area(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.width = 40;
        this.height = 40;
        this.isVisible = false;
        this.color = "green";
        this.rand = new Random();
        this.buildingOnthisArea = null;
        this.owner = null;
        this.inhabitantOnThisArea = null;
    }

    public int getPosX() {
        return this.posX;
    }


    public int getPosY() {
        return this.posY;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Building getBouildingOnThisArea() {
        return this.buildingOnthisArea;
    }

    public void setBuildingHere(Building building) {
        this.buildingOnthisArea = building;
    }

    public Inhabitant getInhabitantOnThisArea() {
        return this.inhabitantOnThisArea;
    }

    public void setInhabitantOnThisArea(Inhabitant referencedInhabitant) {
        this.inhabitantOnThisArea = referencedInhabitant;
    }

    public Tribe getOwner() {
        return this.owner;
    }

    public void setOwner(Tribe referencedOwner) {
        this.owner = referencedOwner;
    }

    /**
     * Metóda show nastaví blok na viditeľný, takže sa zobrazí na plátne
     */
    public void show() {
        this.isVisible = true;
        this.draw();
    }

    /**
     * Metóda hide nastaví blok na nie viditeľný, takže sa na plátne blok skryje
     */
    public void hide() {
        this.erase();
        this.isVisible = false;
    }

    /**
     * Metóda changeColor zmení farbu bloku
     * @param color (farba)
     */
    public void changeColor(String color) {
        this.color = color;
        this.draw();
    }

    /**
     * Metóda changePosition nastaví nové hodnoty pre premenné leftUpperX(ľavé horné X bloku)
     * a leftUppreY(ľavé horné Y bloku), takže zmení pozíciu bloku
     * @param leftUpperX (ľavé horné X bloku)
     * @param lefUpperY (ľavé horné Y bloku)
     */
    public void changePosition(int leftUpperX, int lefUpperY) {
        boolean isDrawed = this.isVisible;
        this.erase();
        this.posX = leftUpperX;
        this.posY = lefUpperY;
        if (isDrawed) {
            this.draw();
        }
    }



    /**
     * Metóda draw vykreslí na plátno jeden blok
     */
    public void draw() {
        int numberOfImage = rand.nextInt(4) + 1;
        BufferedImage picture = null;

        try {
            File myObj = new File("res/pictures/ground", "ground_" + numberOfImage + ".png");
            picture = ImageIO.read(myObj);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException exc) {
            System.out.println("Image was not found");
        }

        if (this.isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, picture, this.posX, this.posY);
            canvas.wait(10);
        }
    }

    /**
     * Metóda erase vymaže blok z plátna
     */
    public void erase() {
        if (this.isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    // Metoda pozera ci sme klikli na policko v hracej ploche
    public boolean containPoint(int x, int y) {
        if (x < this.posX || x > this.posX + this.width) {
            return false;
        }
        if (y < this.posY || y > this.posY + this.height) {
            return false;
        }

        return true;
    }
}
