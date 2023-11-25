package sk.uniza.fri.world;

import sk.uniza.fri.world.buildings.Building;
import sk.uniza.fri.world.inhabitants.Inhabitant;

import java.util.ArrayList;

/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 *
 * Tried vytvori a nastavi zakladne hodnoty kmena
 */
public class Tribe {
    private String nameOfTribe;

    private TypeOfTribe typeOfTribe; // enum -> Economical || Farm -> Tribe
    private Building castle;
    private ArrayList<Area> ownedArea; // which area the tribe is owning
    private ArrayList<Inhabitant> manageAbleInhabitants; // Inhabitants that you can move and play with
    private ArrayList<Building> buildings; // buildings on landscape

    public Tribe(String nameOfTribe, TypeOfTribe typeOfTribe) {
        this.nameOfTribe = nameOfTribe;

        this.typeOfTribe = typeOfTribe;
        this.buildings = new ArrayList<>();

        if (this.typeOfTribe == TypeOfTribe.EconomicalTribe) {
            this.castle = new Building(13, 0, 200, "res/pictures/buildings/castle_blue.png", this, "Castle");
            //this.castle = new Building(13, 0, 200, Tribe.class.getResource("buildings").getPath() + "/castle_blue.png", this, "Castle");
        } else if (this.typeOfTribe == TypeOfTribe.FarmTribe) {
            this.castle = new Building(0, 8, 200, "res/pictures/buildings/castle_red.png", this, "Castle");
        }

        this.ownedArea = new ArrayList<Area>();
        this.manageAbleInhabitants = new ArrayList<Inhabitant>();
        this.buildings.add(castle);
    }

    public String getNameOfTribe() {
        return this.nameOfTribe;
    }

    public void setNameOfTribe(String nameOfTribe) {
        this.nameOfTribe = nameOfTribe;
    }

    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    public TypeOfTribe getTypeOfTribe() {
        return this.typeOfTribe;
    }

    public Building getCastle() {
        return this.castle;
    }

    public void setCastle(Building castle) {
        this.castle = castle;
    }

    public ArrayList<Area> getOwnedArea() {
        return this.ownedArea;
    }

    public void addToOwnedArea(Area referencedArea) {
        this.ownedArea.add(referencedArea);
    }

    public void removeFromOwnedArea(Area referencedArea) {
        this.ownedArea.remove(referencedArea);
    }

    public ArrayList<Inhabitant> getManageAbleInhabitants() {
        return this.manageAbleInhabitants;
    }

    public void addToManageAbleInhabitants(Inhabitant referencedInhabitants) {
        this.manageAbleInhabitants.add(referencedInhabitants);
    }

    public void removeFromManageAbleInhabitants(Inhabitant referencedInhabitants) {
        this.manageAbleInhabitants.remove(referencedInhabitants);
    }
}
