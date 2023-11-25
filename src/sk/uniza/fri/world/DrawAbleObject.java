package sk.uniza.fri.world;

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
 * Implementuje IPositionalObject a je to konkretny objekt na hracej ploche
 */
public class DrawAbleObject implements IPositionableObject {
    private int collumn;
    private int row;
    private String pathToImage;
    private boolean isVisible;
    private ArrayList<ActionsOfObjects> performedActions;

    private Area area;

    public DrawAbleObject(int collumn, int row, String pathToImage) {
        this.collumn = collumn;
        this.row = row;
        this.pathToImage = pathToImage;
        this.isVisible = false;
        this.area = null;
        this.performedActions = new ArrayList<>();
    }

    @Override
    public int getCollumn() {
        return this.collumn;
    }

    @Override
    public void setCollumn(int collumn) {
        this.collumn = collumn;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public boolean getIsVisible() {
        return this.isVisible;
    }

    @Override
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public String getPathToImage() {
        return this.pathToImage;
    }

    public ArrayList<ActionsOfObjects> getPerformedActions() {
        return this.performedActions;
    }

    public void setPerformedActions(ArrayList<ActionsOfObjects> actions) {
        this.performedActions.addAll(actions);
    }

    @Override
    public void move(int row, int collumn) {
        this.setRow(row);
        this.setCollumn(collumn);
    }

    @Override
    public void draw(int posX, int posY) {
        this.isVisible = true;
        BufferedImage picture = null;

        try {
            File myObj = new File(this.pathToImage);
            picture = ImageIO.read(myObj);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException exc) {
            System.out.println("Image was not found");
        }

        if (this.isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, picture, posX, posY);
            canvas.wait(10);
        }
    }

    @Override
    public void erase() {
        this.isVisible = false;
        Canvas canvas = Canvas.getCanvas();
        canvas.erase(this);
    }

    public String toString() {
        return ("This object is situated on [row, collumn]: " + this.row + " " + this.collumn);
    }

    public String informationAboutActions() {
        return ("Actions that object can perform");
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area referencedaArea) {
        this.area = referencedaArea;
    }


}
