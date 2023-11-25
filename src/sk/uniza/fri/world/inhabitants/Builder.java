package sk.uniza.fri.world.inhabitants;

import sk.uniza.fri.world.ActionsOfObjects;
import sk.uniza.fri.world.Canvas;
import sk.uniza.fri.world.Tribe;
import sk.uniza.fri.world.TypeOfTribe;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 * Reprezentuje stavitela na hracej ploche, ktory dokaze stavat budovy
 */
public class Builder extends Inhabitant {
    private String typeOfBuilder;

    public Builder(int collumn, int row, int hp, String pathToImage, Tribe owner) {
        super(collumn, row, hp, pathToImage, owner);

        if (this.getOwner().getTypeOfTribe() == TypeOfTribe.EconomicalTribe) {
            this.typeOfBuilder = "builder_blue.png";
        } else if (this.getOwner().getTypeOfTribe() == TypeOfTribe.FarmTribe) {
            this.typeOfBuilder = "builder_red.png";
        }

        ArrayList<ActionsOfObjects> actions = new ArrayList<>();
        actions.add(ActionsOfObjects.Building);
        actions.add(ActionsOfObjects.Moving);
        this.setPerformedActions(actions);
    }

    //Tuto metodu musim prepisat pretoze musim pouzit jedinecny obrazok, ktory reprezentuje stavitela
    @Override
    public void draw(int posX, int posY) {
        this.setIsVisible(true);
        BufferedImage picture = null;

        try {
            File myObj = new File(this.getPathToImage() + this.typeOfBuilder);
            picture = ImageIO.read(myObj);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException exc) {
            System.out.println("Image was not found");
        }

        if (this.getIsVisible()) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, picture, posX + 10, posY + 10);
            canvas.wait(10);
        }
    }

    // Prepisanie pre vypasnie jedinecnyc a popis stavitela
    @Override
    public String toString() {
        return "Builder | " + "Owner: " + this.getOwner().getNameOfTribe() + " |  HP: " + this.getHp() + " |  situated on [row, collumn]: " + (this.getRow() + 1) + ", " + (this.getCollumn() + 1);
    }

    ////Prepisanie pre pouzitie jedinecnych akcii utocnika
    @Override
    public String informationAboutActions() {
        if (!this.getCanPerformAction()) {
            return ("M - Move | B - Build | D - Destroy");
        } else {
            return ("You can't make any more action with this object");
        }
    }


}
