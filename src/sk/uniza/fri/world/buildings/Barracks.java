package sk.uniza.fri.world.buildings;

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
 *
 * Trieda reprezentuje kasarne na hracej ploche
 */
public class Barracks extends Building {
    private String typeOfBarracks;

    public Barracks(int collumn, int row, int hp, String pathToImage, Tribe owner, String nameOfBuilding) {
        super(collumn, row, hp, pathToImage, owner, nameOfBuilding);

        if (this.getOwner().getTypeOfTribe() == TypeOfTribe.EconomicalTribe) {
            this.typeOfBarracks = "barracks_economical.png";
        } else if (this.getOwner().getTypeOfTribe() == TypeOfTribe.FarmTribe) {
            this.typeOfBarracks = "baracks_farm.png";
        }

        ArrayList<ActionsOfObjects> actions = new ArrayList<>();
        actions.add(ActionsOfObjects.Destroy);
        actions.add(ActionsOfObjects.Recruitment);
        this.setPerformedActions(actions);

    }

    public boolean canRecruit(int row, int collumn) {
        int rowDistance = (Math.max(row, this.getRow())) - (Math.min(row, this.getRow()));
        int collumnDistance = (Math.max(collumn, this.getCollumn())) - (Math.min(collumn, this.getCollumn()));
        if (rowDistance > 1 || collumnDistance > 1) {
            System.out.println("You can't recruit there");
            return false;
        } else {
            System.out.println("You can recruit here");
            return true;
        }
    }

    //Prepisanie pre pouzitie jedinecneho obrazka kasarne
    @Override
    public void draw(int posX, int posY) {
        this.setIsVisible(true);
        BufferedImage picture = null;

        try {
            File myObj = new File(this.getPathToImage() + this.typeOfBarracks);
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
            return ("D - Destroy | C - Recruit");
        } else {
            return ("You can't make any more action with this object");
        }
    }
}
