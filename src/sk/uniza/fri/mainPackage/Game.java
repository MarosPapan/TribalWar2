package sk.uniza.fri.mainPackage;

import sk.uniza.fri.world.Manazer;
import sk.uniza.fri.world.World;

import javax.swing.*;

//import javax.swing.*;
/**
 * 15. 4. 2022 - 11:53
 *Trieda na vytvorenie hry
 * @author maros
 */
public class Game {
    private World world1;
    private Manazer manager;

    public Game() {
        this.world1 = new World(1200 - ( ( 15 * 41) + 300), 180);
        this.manager = new Manazer();

        this.manager.spravujObjekt(world1);

    }


}
