package sk.uniza.fri.world.buildings;

import sk.uniza.fri.world.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 *
 * Tato trieda reprezentuje vlajku, ktora sluzi na oznacenie vlastnika policka
 */
public class Flag extends DrawAbleObject {
    private int hp;
    private String typeOfFlag;
    private Tribe owner;

    public Flag(int collumn, int row, int hp, String pathToImage, Tribe owner) {
        super(collumn, row, pathToImage);
        this.hp = hp;
        this.owner = owner;

        if (this.owner.getTypeOfTribe() == TypeOfTribe.EconomicalTribe) {
            this.typeOfFlag = "blue1.png";
        } else if (this.owner.getTypeOfTribe() == TypeOfTribe.FarmTribe) {
            this.typeOfFlag = "red1.png";
        }
    }

    public int getHp() {
        return this.hp;
    }

    public void setOwner(Tribe newOwner) {
        this.owner = newOwner;
    }

    //Prepisanie pre pouzitie jedinecneho obrazka vlajky
    @Override
    public void draw(int posX, int posY) {
        this.setIsVisible(true);
        BufferedImage picture = null;

        try {
            File myObj = new File(this.getPathToImage() + this.typeOfFlag);
            picture = ImageIO.read(myObj);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException exc) {
            System.out.println("Image was not found");
        }

        if (this.getIsVisible()) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, picture, posX, posY);
            canvas.wait(10);
        }
    }

    //Prepisanie pre vypis vlastnosti vlajky
    @Override
    public String toString() {
        return ("This is your Area situated at [raow, collumn]" + (this.getRow() + 1) + ", " + (this.getCollumn() + 1));
    }
}
