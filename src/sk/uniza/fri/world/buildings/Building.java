package sk.uniza.fri.world.buildings;

import sk.uniza.fri.world.DrawAbleObject;
import sk.uniza.fri.world.Tribe;


/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 *
 * Trieda reprezentuje konkretnu budovu na hracej ploche
 */
public class Building extends DrawAbleObject {
    private int hp;
    private int level;
    private String nameOfBuilding;
    private Tribe owner;
    private boolean canPerformAction;

    public Building(int collumn, int row, int hp, String pathToImage, Tribe owner, String nameOfBuilding) {
        super(collumn, row, pathToImage);
        this.hp = hp;
        this.level = 0;
        this.owner = owner;
        this.nameOfBuilding = nameOfBuilding;
        this.canPerformAction = false;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Tribe getOwner() {
        return this.owner;
    }

    public void setOwner(Tribe owner) {
        this.owner = owner;
    }

    public String getNameOfBuilding() {
        return this.nameOfBuilding;
    }

    public void setNameOfBuilding(String nameOfBuilding) {
        this.nameOfBuilding = nameOfBuilding;
    }

    public boolean getCanPerformAction() {
        return this.canPerformAction;
    }

    public void setCanPerformAction(boolean actionWasPerformed) {
        this.canPerformAction = actionWasPerformed;
    }

    public void updateBuilding() {
        if (this.level <= 3) {
            this.level += 1;
        } else {
            System.out.println("This building have the biggest possible level");
        }

    }

    //Prepisanie pre uvedenie jedinecnych vlastnosti budovy
    @Override
    public String toString() {
        return (this.nameOfBuilding + " | " + "Owner: " + this.owner.getNameOfTribe() + "| HP: " + this.getHp() + " | situated on [row, collumn]: " + (this.getRow() + 1) + ", " + (this.getCollumn() + 1));
    }

    //Prepisanie pre uvedenie jedinecnych akcii budovy
    @Override
    public String informationAboutActions() {
        if (!this.canPerformAction) {
            return ("D - Destroy");
        } else {
            return ("You can't make any more action with this object");
        }
    }


}
