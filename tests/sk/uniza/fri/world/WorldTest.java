package sk.uniza.fri.world;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniza.fri.world.buildings.Barracks;
import sk.uniza.fri.world.buildings.Building;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 21. 5. 2022 - 11:40
 *
 * @author maros
 */
class WorldTest {
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void chooseRightCollumnRow() {
        World world1 = new World(1200 - ( ( 15 * 41) + 300), 180);
        int[] rowAndCollumn = {0,0};
        Assertions.assertArrayEquals(world1.getRowAndCollumnOfSingleArea(1200 - ( ( 15 * 41) + 300), 180), rowAndCollumn);
    }

    @Test
    void placeObjectOnLandscapeTest() {
        World world1 = new World(1200 - ( ( 15 * 41) + 300), 180);
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Building barracks1 = new Barracks(13, 4, 100, "res/pictures/buildings/", economicalTribe, "Barrack");
        world1.placeObjectOnLandscape(barracks1);
        Assertions.assertEquals(barracks1, world1.getSingleArea(4, 13).getBouildingOnThisArea());
    }

    @Test
    void removeObjectOnLandscapeTest() {
        int[] rowAndCollumn = {4,13};
        World world1 = new World(1200 - ( ( 15 * 41) + 300), 180);
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Building barracks1 = new Barracks(13, 4, 100, "res/pictures/buildings/", economicalTribe, "Barrack");
        world1.placeObjectOnLandscape(barracks1);
        world1.destroy(barracks1, rowAndCollumn);
        Assertions.assertEquals(null, world1.getSingleArea(4, 13).getBouildingOnThisArea());
    }

    @Test
    void swapTribeOnMove() {
        World world1 = new World(1200 - ( ( 15 * 41) + 300), 180);
        Tribe economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        Tribe farmTribe = new Tribe("Farm",  TypeOfTribe.FarmTribe);
        Assertions.assertEquals(world1.getTribeOnMove().getNameOfTribe(), economicalTribe.getNameOfTribe());
        world1.swapTribeOnMove();
        Assertions.assertEquals(world1.getTribeOnMove().getNameOfTribe(), farmTribe.getNameOfTribe());

    }

}