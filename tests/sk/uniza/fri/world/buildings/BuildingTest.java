package sk.uniza.fri.world.buildings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniza.fri.world.ActionsOfObjects;
import sk.uniza.fri.world.Tribe;
import sk.uniza.fri.world.TypeOfTribe;
import sk.uniza.fri.world.inhabitants.Builder;
import sk.uniza.fri.world.inhabitants.Inhabitant;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 21. 5. 2022 - 11:40
 *
 * @author maros
 */
class BuildingTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void allowedActionsOfBarracks() {
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Building barracks1 = new Barracks(13, 4, 100, "res/pictures/buildings/", economicalTribe, "Barrack");
        ArrayList<ActionsOfObjects> actionsOfBarracks = new ArrayList<>(List.of(ActionsOfObjects.Destroy, ActionsOfObjects.Recruitment));
        Assertions.assertEquals(actionsOfBarracks, barracks1.getPerformedActions());
    }

    @Test
    void barracksCanRecruit(){
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Barracks barracks1 = new Barracks(12, 3, 100, "res/pictures/buildings/", economicalTribe, "Barrack");
        Assertions.assertEquals(true, barracks1.canRecruit(3,11));
        Assertions.assertEquals(true, barracks1.canRecruit(3,13));
        Assertions.assertEquals(true, barracks1.canRecruit(2,11));
        Assertions.assertEquals(true, barracks1.canRecruit(2,12));
        Assertions.assertEquals(true, barracks1.canRecruit(2,13));
        Assertions.assertEquals(true, barracks1.canRecruit(4,11));
        Assertions.assertEquals(true, barracks1.canRecruit(4,12));
        Assertions.assertEquals(true, barracks1.canRecruit(4,13));
    }

    @Test
    void barracksCanNotRecruit(){
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Barracks barracks1 = new Barracks(12, 3, 100, "res/pictures/buildings/", economicalTribe, "Barrack");
        Assertions.assertEquals(false, barracks1.canRecruit(3,14));
        Assertions.assertEquals(false, barracks1.canRecruit(5,13));
    }

    @Test
    void hpIsCorrectlySetToBarricade() {
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Barricade baricade1 = new Barricade(12, 3, 300, "res/pictures/buildings/", economicalTribe, "Barrack");
        Assertions.assertEquals(300, baricade1.getHp());
    }

}