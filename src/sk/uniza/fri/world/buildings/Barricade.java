package sk.uniza.fri.world.buildings;

import sk.uniza.fri.world.Canvas;
import sk.uniza.fri.world.Tribe;
import sk.uniza.fri.world.TypeOfTribe;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 */
public class Barricade extends Building {
    private String typeOfBarricade;

    public Barricade(int collumn, int row, int hp, String pathToImage, Tribe owner, String nameOfBuilding) {
        super(collumn, row, hp, pathToImage, owner, nameOfBuilding);

        if (this.getOwner().getTypeOfTribe() == TypeOfTribe.EconomicalTribe) {
            this.typeOfBarricade = "barricade_economical.png";
        } else if (this.getOwner().getTypeOfTribe() == TypeOfTribe.FarmTribe) {
            this.typeOfBarricade = "barricade_farm.png";
        }

    }

    //Prepisanie pre pouzitie jedinecneho obrazka kasarne
    @Override
    public void draw(int posX, int posY) {
        this.setIsVisible(true);
        BufferedImage picture = null;

        try {
            File myObj = new File(this.getPathToImage() + this.typeOfBarricade);
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

    //Prepisanie pre uvedenie jedinecnych akcii budovy
    @Override
    public String informationAboutActions() {
        if (!this.getCanPerformAction()) {
            return ("D - Destroy");
        } else {
            return ("You can't make any more action with this object");
        }
    }
}
