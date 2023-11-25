package sk.uniza.fri.world.inhabitants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sk.uniza.fri.world.ActionsOfObjects;
import sk.uniza.fri.world.Tribe;
import sk.uniza.fri.world.TypeOfTribe;
import sk.uniza.fri.world.World;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 21. 5. 2022 - 11:40
 *
 * @author maros
 */
class InhabitantTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void inhabitantShouldMoveTest() {
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Inhabitant human = new Builder(2, 12, 80, "res/pictures/Inhabitants/builders/", economicalTribe);
        Assertions.assertEquals(true, human.canMove(13,1));
        Assertions.assertEquals(true, human.canMove(13,2));
        Assertions.assertEquals(true, human.canMove(13,3));
        Assertions.assertEquals(true, human.canMove(12,1));
        Assertions.assertEquals(true, human.canMove(12,2));
        Assertions.assertEquals(true, human.canMove(12,3));
        Assertions.assertEquals(true, human.canMove(11,1));
        Assertions.assertEquals(true, human.canMove(11,2));
        Assertions.assertEquals(true, human.canMove(11,3));
    }

    @Test
    void inhabitantShouldNotMoveTest() {
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Inhabitant human = new Builder(2, 12, 80, "res/pictures/Inhabitants/builders/", economicalTribe);
        Assertions.assertEquals(false, human.canMove(10,2));
        Assertions.assertEquals(false, human.canMove(12,4));

    }

    @Test
    void inhabitantMovedTest() {
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Inhabitant human = new Builder(2, 12, 80, "res/pictures/Inhabitants/builders/", economicalTribe);
        human.move(13,2);
        Assertions.assertEquals(13, human.getRow());
        Assertions.assertEquals(2, human.getCollumn());
    }

    @Test
    void inhabitantIsVisibleTest() {
        Tribe farmTribe = new Tribe("Farm", TypeOfTribe.FarmTribe);
        Inhabitant human = new Builder(2, 7, 80, "res/pictures/Inhabitants/builders/", farmTribe);
        human.draw(120, 120);
        Assertions.assertTrue(human.getIsVisible());
    }

    @Test
    void inhabitantIsNotVisible() {
        Tribe farmTribe = new Tribe("Farm", TypeOfTribe.FarmTribe);
        Inhabitant human = new Builder(2, 7, 80, "res/pictures/Inhabitants/builders/", farmTribe);
        human.draw(120, 120);
        human.erase();
        Assertions.assertFalse(human.getIsVisible());
    }

    @Test
    void builderAttackPowerTest() {
        Tribe farmTribe = new Tribe("Farm", TypeOfTribe.FarmTribe);
        Inhabitant human = new Builder(2, 7, 80, "res/pictures/Inhabitants/builders/", farmTribe);
        Assertions.assertEquals(2, human.getAttackPower());
    }

    @Test
    void swordmanAttackPowerTest() {
        Tribe farmTribe = new Tribe("Farm", TypeOfTribe.FarmTribe);
        Inhabitant human = new Swordman(2, 7, 80, "res/pictures/Inhabitants/builders/", farmTribe);
        Assertions.assertEquals(70, human.getAttackPower());
    }

    @Test
    void builderActionsTest() {
        Tribe farmTribe = new Tribe("Farm", TypeOfTribe.FarmTribe);
        Inhabitant human = new Builder(2, 7, 80, "res/pictures/Inhabitants/builders/", farmTribe);
        ArrayList<ActionsOfObjects> actionsOfBuilder = new ArrayList<>(List.of(ActionsOfObjects.Building, ActionsOfObjects.Moving));
        Assertions.assertEquals(actionsOfBuilder, human.getPerformedActions());
    }

    @Test
    void swordmansActionsTest() {
        Tribe farmTribe = new Tribe("Farm", TypeOfTribe.FarmTribe);
        Inhabitant human = new Swordman(2, 7, 80, "res/pictures/Inhabitants/builders/", farmTribe);
        ArrayList<ActionsOfObjects> actionsOfBuilder = new ArrayList<>(List.of(ActionsOfObjects.Attacking, ActionsOfObjects.Moving));
        Assertions.assertEquals(actionsOfBuilder, human.getPerformedActions());
    }





}