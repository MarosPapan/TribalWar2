package sk.uniza.fri.world.inhabitants;

import sk.uniza.fri.world.Area;
import sk.uniza.fri.world.DrawAbleObject;
import sk.uniza.fri.world.Tribe;


/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 *
 * Konkretny obyvatel na hracej ploche, nastavuje zakladne atributy obyvatelovy
 * tuto metodu dedia vsetci obyvatelia
 */
public class Inhabitant extends DrawAbleObject {
    private int hp;
    private int attackPower;
    private Tribe owner;
    private Area area;
    private boolean canPerformAction;

    public Inhabitant(int collumn, int row, int hp, String pathToImage, Tribe owner) {
        super(collumn, row, pathToImage);
        this.hp = hp;
        this.owner = owner;
        this.area = null;
        this.attackPower = 2;
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

    public void setOwner(Tribe referencedOwner) {
        this.owner = referencedOwner;
    }

    public int getAttackPower() {
        return this.attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public boolean getCanPerformAction() {
        return this.canPerformAction;
    }

    public void setCanPerformAction(boolean actionWasPerformed) {
        this.canPerformAction = actionWasPerformed;
    }

    @Override
    public void move(int row, int collumn) {
        if (this.canMove(row, collumn)) {
            super.move(row, collumn);
        }
    };

    public boolean canMove(int row, int collumn) {
        int rowDistance = (Math.max(row, this.getRow())) - (Math.min(row, this.getRow()));
        int collumnDistance = (Math.max(collumn, this.getCollumn())) - (Math.min(collumn, this.getCollumn()));
        if ( rowDistance > 1 || collumnDistance > 1) {
            System.out.println("You can't make such big step");
            return false;
        } else {
            System.out.println("You can move here");
            return true;
        }
    }

    @Override
    public String toString() {
        return "This is an inhabitant, situated on [row, collumn]" + this.getRow() + " " + this.getCollumn();
    }
}
