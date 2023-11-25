package sk.uniza.fri.world;

import sk.uniza.fri.world.buildings.Barracks;
import sk.uniza.fri.world.buildings.Barricade;
import sk.uniza.fri.world.buildings.Building;
import sk.uniza.fri.world.buildings.Flag;
import sk.uniza.fri.world.inhabitants.Builder;
import sk.uniza.fri.world.inhabitants.Inhabitant;
import sk.uniza.fri.world.inhabitants.Swordman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 *
 * Trieda ktora riesi vykreslovanie urcitych objektov a aj logiku hry
 *
 */
public class World {
    private Area[][] landscape;
    private final int collumns;
    private final int rows;
    private final int posX;
    private final int posY;

    private int moving;
    private int attacking;
    private int building;
    private int recruitment;

    private IPositionableObject clickedObject;
    private Area clickedArea;

    private Text navigationalText;
    private Text actionsText;
    private Text textTribeOnMove;

    private Tribe economicalTribe;
    private Tribe farmTribe;
    private Tribe tribeOnMove; // which tribe is on move
    private Canvas canvas;

    public World(int posX, int posY) {
        this.canvas = Canvas.getCanvas();
        this.posX = posX;
        this.posY = posY;
        this.collumns = 15;
        this.rows = 10;
        this.landscape = new Area[this.rows][this.collumns];
        this.economicalTribe = new Tribe("Economic",  TypeOfTribe.EconomicalTribe);
        this.farmTribe = new Tribe("Farm", TypeOfTribe.FarmTribe);
        this.navigationalText = new Text("Economical tribe is on move", this.posX, 100);
        this.actionsText = new Text("", this.posX, 120);
        this.tribeOnMove = this.economicalTribe;
        this.clickedArea = null;
        this.clickedObject = null;
        this.recruitment = 0;
        this.textTribeOnMove = new Text("Tribe on move: ", this.posX, 75);

        this.setTribeOnMove();


        JFrame fr = this.canvas.getFrame();

        fr.setResizable(false);
        fr.setTitle("Tribal war");
        fr.setLayout(new BorderLayout());
        fr.setBackground(Color.red);

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();

        final  int partOfWindow = 50;
        fr.setLocation(d.width * (100 - partOfWindow) / 100 / 5, d.height * (100 - partOfWindow) / 100 / 4 );
        fr.setSize(d.width * partOfWindow / 65, d.height * partOfWindow / 68);

        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MyKeyboard myKeyboard = new MyKeyboard();
        fr.addKeyListener(myKeyboard);

        this.initializeLandscape();
        this.drawLandscape();

        fr.validate();
        fr.repaint();
        this.navigationalText.zobraz();
        this.actionsText.zobraz();
        this.textTribeOnMove.zobraz();
    } //end of constructor

    public Area[][] getLandscape() {
        return this.landscape;
    }

    public Tribe getEconomicalTribe() {
        return this.economicalTribe;
    }

    public Tribe getFarmTribe() {
        return this.farmTribe;
    }

    // metoda v ktorej pomocou posX a posY vratime list s stlpcom a riadkom
    public int[] getRowAndCollumnOfSingleArea(int posX, int posY) {
        int[] rowAndCollumn = new int[2];
        for (int i = 0; i < this.landscape.length; i++) {
            for (int j = 0; j < this.landscape[i].length; j++) {
                if (this.landscape[i][j].containPoint(posX, posY)) {
                    rowAndCollumn[0] = i; // row
                    rowAndCollumn[1] = j; //collumn
                    return rowAndCollumn;
                }
            }
        }
        return rowAndCollumn;
    }

    // funkcia ktora sa stara o zaciatocnu inicializaciu hry
    public void initializeLandscape() {
        int areaPosX = this.posX;
        int areaPosY = this.posY;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.collumns; j++) {
                Area singleArea = new Area(areaPosX, areaPosY);
                this.landscape[i][j] = singleArea;

                //initializacia hradov kazdeho kmena
                if (i < 2 && j > (this.collumns - 3)) {
                    singleArea.setBuildingHere(this.economicalTribe.getCastle());
                } else if (i > (this.rows - 3) && j < 2) {
                    singleArea.setBuildingHere(this.farmTribe.getCastle());
                }

                // zaciatocna inicializacia obsadenych poli kazdeho kmena
                if (i < 4 && j > (this.collumns - 5)) {
                    this.economicalTribe.addToOwnedArea(singleArea);
                    singleArea.setOwner(this.economicalTribe);
                } else if (i > (this.rows - 5) && j < 4) {
                    this.farmTribe.addToOwnedArea(singleArea);
                    singleArea.setOwner(this.farmTribe);
                }

                //zaciatocna inicializacia obyvatela Stavitela v kazdom kmeni
                if (i == 2 && j == 12) {
                    Inhabitant economicaInhabitant = new Builder(j, i, 80, "res/pictures/Inhabitants/builders/", this.economicalTribe);
                    this.economicalTribe.addToManageAbleInhabitants(economicaInhabitant);
                    economicaInhabitant.setArea(singleArea);
                    singleArea.setInhabitantOnThisArea(economicaInhabitant);
                }
                if (i == 7 && j == 2) {
                    Inhabitant farmInhabitant = new Builder(j, i, 80, "res/pictures/Inhabitants/builders/", this.farmTribe);
                    this.farmTribe.addToManageAbleInhabitants(farmInhabitant);
                    farmInhabitant.setArea(singleArea);
                    singleArea.setInhabitantOnThisArea(farmInhabitant);
                }
                areaPosX += 41;
            }
            areaPosY += 41;
            areaPosX -= this.collumns * 41;
        }
    } // koniec zaciatocnej initializacii sveta

    //metoda ktora vykresli svet podla zaciatocnej inicializacii
    public void drawLandscape() {
        for (Area[] row: this.landscape) {
            for (Area singleArea: row) {
                singleArea.show(); // drawing landscape

                // nakresli vlajky kazdeho kmena na poli ktory vlastni
                if (singleArea.getOwner() != null) {
                    int x = singleArea.getPosX();
                    int y = singleArea.getPosY();
                    int[] rowAndCollumn = this.getRowAndCollumnOfSingleArea(x, y);
                    this.placeObjectOnLandscape(new Flag(rowAndCollumn[1], rowAndCollumn[0], 0, "res/pictures/flags/", singleArea.getOwner()));
                }

            }
        }

        //vykreslenie hradov
        this.placeObjectOnLandscape(this.economicalTribe.getCastle());
        this.placeObjectOnLandscape(this.farmTribe.getCastle());

        //vykreslenie obyvatelov
        for (Inhabitant human: this.economicalTribe.getManageAbleInhabitants()) {
            this.placeObjectOnLandscape(human);
        }

        for (Inhabitant human: this.farmTribe.getManageAbleInhabitants()) {
            this.placeObjectOnLandscape(human);
        }
    } //koniec vykreslenia hracej plochy

    //vrati jedno policko pomocou riadku a stlpca
    public Area getSingleArea(int row, int collumn) {
        return this.landscape[row][collumn];
    }

    //Tato metoda nastavi a objekt na hraciu plochu
    public void placeObjectOnLandscape(IPositionableObject referencedObject) {
        int positionX = this.getSingleArea(referencedObject.getRow(), referencedObject.getCollumn()).getPosX();
        int positionY = this.getSingleArea(referencedObject.getRow(), referencedObject.getCollumn()).getPosY();
        this.removeObjectFromLandscape(referencedObject);
        int[] rowAndCollumOfArea = this.getRowAndCollumnOfSingleArea(positionX, positionY);
        Area area = this.getSingleArea(rowAndCollumOfArea[0], rowAndCollumOfArea[1]);

        if (referencedObject instanceof Inhabitant) {
            area.setInhabitantOnThisArea((Inhabitant)referencedObject);
        } else if (referencedObject instanceof Building) {
            area.setBuildingHere((Building)referencedObject);
        }

        referencedObject.draw(positionX, positionY);
    }

    //odstrani objekt z hracej plochy
    public void removeObjectFromLandscape(IPositionableObject referencedObject) {
        referencedObject.erase();
    }


    //Metoda spracovava klikanie na hraciu plochu a celkovu logiku hry
    public void clickingOnLandScape(int x, int y) {
        int[] rowAndCollumnOfArea;
        rowAndCollumnOfArea = this.getRowAndCollumnOfSingleArea(x, y);
        this.clickedArea = this.getSingleArea(rowAndCollumnOfArea[0], rowAndCollumnOfArea[1]);
        System.out.println("You clicked -> ");
        System.out.println("Row: " + rowAndCollumnOfArea[0]);
        System.out.println("Collumn: " + rowAndCollumnOfArea[1]);

        if (this.moving == 1) {
            int[] rowCollumnFrom = new int[2];
            //odkial cestuje obyvatel
            rowCollumnFrom[0] = this.clickedObject.getRow();
            rowCollumnFrom[1] = this.clickedObject.getCollumn();
            this.moveInhabitantOnLandscape((DrawAbleObject)this.clickedObject, rowCollumnFrom, rowAndCollumnOfArea);
        }

        if (this.building == 1) {
            this.buildBuildingOnLandscape(this.chooseBuildingToBuild(), rowAndCollumnOfArea[0], rowAndCollumnOfArea[1]);
        }

        if (this.recruitment == 1) {
            this.recruitNewInhabitant(this.chooseToRecruitInhabitant(), rowAndCollumnOfArea[0], rowAndCollumnOfArea[1]);
        }

        if (this.attacking == 1) {
            int[] rowCollumnFrom = new int[2];
            //odkial cestuje obyvatel
            rowCollumnFrom[0] = this.clickedObject.getRow();
            rowCollumnFrom[1] = this.clickedObject.getCollumn();
            this.attack((Inhabitant)this.clickedObject, rowAndCollumnOfArea, rowCollumnFrom);
        }

        if (this.farmTribe.getCastle().getHp() <= 0) {
            JOptionPane.showMessageDialog(null, "Congratulations Economical tribe won this battle");
            System.exit(0);
        }

        if (this.economicalTribe.getCastle().getHp() <= 0) {
            JOptionPane.showMessageDialog(null, "Congratulations Farm tribe won this battle");
            System.exit(0);
        }

        if (this.canSelectObject(this.clickedArea) && this.moving == 0 && this.building == 0 && this.attacking == 0) {
            if (this.clickedArea.getInhabitantOnThisArea() != null) {
                Inhabitant inhabitant = this.clickedArea.getInhabitantOnThisArea();
                this.clickedObject = inhabitant;
                this.clickedOnInhabitant((Inhabitant)this.clickedObject);
            } else if (this.clickedArea.getBouildingOnThisArea() != null) {
                this.clickedObject = (Building)this.clickedArea.getBouildingOnThisArea();
                this.clickedOnBuiding((Building)this.clickedObject);
            } else {
                System.out.println("Nothing is here");
                String ownerOfArea = (this.clickedArea.getOwner() != null) ? this.clickedArea.getOwner().getNameOfTribe() + " " : "nobody -> This area is free";
                this.navigationalText.setText("Area owned by: " + ownerOfArea);
                this.clickedObject = null;
                this.actionsText.setText("");
            }
//        }
        } else {
            if (this.moving == 0 && this.building == 0 && this.attacking == 0) {
                this.navigationalText.setText("This is the enemy area: " + this.clickedArea.getOwner().getNameOfTribe() + " area");
                this.actionsText.setText("");
                this.clickedObject = null;
            }
        }
    }


    //Zobrazenie textu po kliknuti na Obyvatela
    public void clickedOnInhabitant(Inhabitant referencedInhabitant) {
        this.navigationalText.setText(referencedInhabitant.toString());
        this.actionsText.setText(referencedInhabitant.informationAboutActions());
    }

    //Zobrazenie textu po kliknuti na Budovu
    public void clickedOnBuiding(Building referencedBuilding) {
        this.navigationalText.setText(referencedBuilding.toString());
        this.actionsText.setText((referencedBuilding.informationAboutActions()));
    }

    //koniec akcie -- prevzate od ucitela
    private void actionEnd() {
        if (JOptionPane.showConfirmDialog(this.canvas.getFrame(),
                "Do you want to end this program??", "Closing",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION ) {
            System.exit(0);
        }
        this.canvas.getFrame().repaint();
    }

    //Metoda urcuje ci moze kmen ktory je na rade kliknut na objekt
    public boolean canSelectObject(Area area) {
        if (area.getOwner() == this.tribeOnMove || area.getOwner() == null) {
            return true;
        }
        return false;
    }

    //Metoda nastavi pohyb objektu na ploche
    public void initializeMoving(int value) {
        DrawAbleObject object = (DrawAbleObject)this.clickedObject;
        if (this.clickedObject != null && object.getPerformedActions().contains(ActionsOfObjects.Moving)) {
            if (value == 1) {
                this.navigationalText.setText("Click where you want to move (distance you can travel is 1)");
                this.actionsText.setText("");
            }
            this.moving = value;
        } else {
            this.navigationalText.setText("You can't move with this object");
            this.actionsText.setText("");
            this.moving = 0;
        }
    }

    //Metoda nastavi atak urciteho byvatela na hracej ploche
    public void initializeAttacking(int attacking) {
        DrawAbleObject object = (DrawAbleObject)this.clickedObject;
        if (this.clickedObject != null && object.getPerformedActions().contains(ActionsOfObjects.Attacking)) {
            if (attacking == 1) {
                this.navigationalText.setText("Click where you want to attack (distance 1)");
                this.actionsText.setText("");
            }
            this.attacking = attacking;
        } else {
            this.navigationalText.setText("You can't attack with this object");
            this.actionsText.setText("");
            this.attacking = 0;
        }

    }

    // Metoda nastavi budovanie budovy urciteho obyvatela
    public void initializeBuilding(int building) {
        DrawAbleObject object = (DrawAbleObject)this.clickedObject;
        if (this.clickedObject != null && object.getPerformedActions().contains(ActionsOfObjects.Building)) {
            if (building == 1) {
                this.navigationalText.setText("Click where you want to build (distance 1)");
                this.actionsText.setText("");
            }
            this.building = building;
        } else {
            this.navigationalText.setText("You can't build with this object");
            this.actionsText.setText("");
            this.building = 0;
        }
    }

    //Metoda nastavi nabor obyvatela urcitemu objektu
    public void initializeRecruiment(int recruitment) {
        DrawAbleObject object = (DrawAbleObject)this.clickedObject;
        if (this.clickedObject != null && object.getPerformedActions().contains(ActionsOfObjects.Recruitment)) {
            if (recruitment == 1) {
                this.navigationalText.setText("Click where you want to recruit a new inhabitant (distance 1)");
                this.actionsText.setText("");
            }
            this.recruitment = recruitment;
        } else {
            this.navigationalText.setText("You can't recruit new inhabitant with this object");
            this.actionsText.setText("");
            this.recruitment = 0;
        }
    }

    //Metoda pripravi objekt na znicenie
    private void initializeDestroying(IPositionableObject object) {
        DrawAbleObject objectToDestroy = null;

        objectToDestroy = (DrawAbleObject)object;

        if (objectToDestroy != null) {
            int[] rowAndCollumn = new int[2];
            rowAndCollumn[0] = objectToDestroy.getRow();
            rowAndCollumn[1] = objectToDestroy.getCollumn();

            if (JOptionPane.showConfirmDialog(this.canvas.getFrame(),
                    "Do you really want to destroy this object: " + objectToDestroy.toString(), "Destroy object??",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
            ) == JOptionPane.YES_OPTION ) {
                this.destroy(objectToDestroy, rowAndCollumn);
            }
            this.canvas.getFrame().repaint();
        }

    }



    //Metoda nastavi novu poziciu objektu
    public void moveInhabitantOnLandscape(DrawAbleObject referencedObject, int[] rowAndCollumnFrom, int[] rowAndCollumnTo) {
        //pomocne premenne
        Area outArea = this.getSingleArea(rowAndCollumnFrom[0], rowAndCollumnFrom[1]);
        Area newArea = this.getSingleArea(rowAndCollumnTo[0], rowAndCollumnTo[1]);

        if (newArea.getInhabitantOnThisArea() == null && newArea.getBouildingOnThisArea() == null && !outArea.getInhabitantOnThisArea().getCanPerformAction()) {

            //nastavanie obyvatela na novu poziciu
            referencedObject.move(rowAndCollumnTo[0], rowAndCollumnTo[1]);
            referencedObject.erase();
            this.placeObjectOnLandscape(referencedObject);
            referencedObject.setArea(newArea);

            //nastavenie urobenia akcie
            newArea.getInhabitantOnThisArea().setCanPerformAction(true);


            //nastavenie ze sa uz obyvatel nenachadza na starej pozicii
            outArea.setInhabitantOnThisArea(null);

            //obsadenie pozicie a nastavanie vlastnika pozicie
            newArea.setOwner(this.tribeOnMove);
            this.placeObjectOnLandscape(new Flag(referencedObject.getCollumn(), referencedObject.getRow(), 0, "res/pictures/flags/", newArea.getOwner()));
            this.setTribeOnMove();
        } else {
            if (newArea.getBouildingOnThisArea() != null) {
                this.navigationalText.setText("Here is: " + newArea.getBouildingOnThisArea().toString());
            }
            if (newArea.getInhabitantOnThisArea() != null) {
                this.navigationalText.setText("Here is: " + newArea.getInhabitantOnThisArea().toString());
            }
        }
        this.initializeMoving(0);
    }

    //Metoda postavy budovu na urcitej pozicii
    public void buildBuildingOnLandscape(int building, int row, int collumn) {
        Building newBuilding = null;

        if (
                this.clickedObject instanceof Builder &&
                ((Builder)this.clickedObject).canMove(row, collumn) &&
                this.getSingleArea(row, collumn).getOwner() == this.tribeOnMove &&
                        !((Builder)this.clickedObject).getCanPerformAction()
        ) {
            ((Builder)this.clickedObject).setCanPerformAction(true);
            switch (building) {
                case 1:
                    newBuilding = new Barracks(collumn, row, 100, "res/pictures/buildings/", this.tribeOnMove, "Barrack");
                    this.placeObjectOnLandscape(newBuilding);
                    newBuilding.setCanPerformAction(true);
                    this.tribeOnMove.getBuildings().add((Building)newBuilding);
                    this.setTribeOnMove();
                    break;

                case 2:
                    newBuilding = new Barricade(collumn, row, 300, "res/pictures/buildings/", this.tribeOnMove, "Barricade");
                    this.placeObjectOnLandscape(newBuilding);
                    newBuilding.setCanPerformAction(true);
                    this.tribeOnMove.getBuildings().add((Building)newBuilding);
                    this.setTribeOnMove();
                    break;
                default:
                    this.setTribeOnMove();
            }
        } else {
            this.navigationalText.setText("You can't build");
        }
        this.initializeBuilding(0);
    }

    //Metoda vytvori noveho obyvatela na urcitej pozicii
    public void recruitNewInhabitant(int inhabitant, int row, int collumn) {
        Inhabitant newInhabitant = null;

        if (
                this.clickedObject instanceof Barracks &&
                        ((Barracks)this.clickedObject).canRecruit(row, collumn) &&
                        this.getSingleArea(row, collumn).getOwner() == this.tribeOnMove &&
                        !((Barracks)this.clickedObject).getCanPerformAction()
        ) {
            ((Barracks)this.clickedObject).setCanPerformAction(true);
            switch (inhabitant) {
                case 1:
                    newInhabitant = new Swordman(collumn, row, 25, "res/pictures/Inhabitants/", this.tribeOnMove);
                    newInhabitant.setCanPerformAction(true);
                    this.placeObjectOnLandscape(newInhabitant);
                    this.tribeOnMove.getManageAbleInhabitants().add((Inhabitant)newInhabitant);
                    this.setTribeOnMove();
                    break;
                case 2:
                    newInhabitant = new Builder(collumn, row, 80, "res/pictures/Inhabitants/builders/", this.tribeOnMove);
                    this.placeObjectOnLandscape(newInhabitant);
                    newInhabitant.setCanPerformAction(true);
                    this.tribeOnMove.getManageAbleInhabitants().add((Inhabitant)newInhabitant);
                    this.setTribeOnMove();
                    break;
                default:
                    this.setTribeOnMove();
            }
        } else {
            this.navigationalText.setText("You can't recruit");
        }
        this.initializeRecruiment(0);
    }

    //Metoda zautoci urcitym obyvatelom na urcite policko - objekt
    public void attack(Inhabitant attacker, int[] rowAndCollumntTo, int[] rowAndCollumnFrom) {
        Area attackFromArea = this.getSingleArea(rowAndCollumnFrom[0], rowAndCollumnFrom[1]);
        Area attackToArea = this.getSingleArea(rowAndCollumntTo[0], rowAndCollumntTo[1]);

        if (attacker.canMove(rowAndCollumntTo[0], rowAndCollumntTo[1]) && !(attacker.getCanPerformAction())) {
            attacker.setCanPerformAction(true);
            if (attackToArea.getInhabitantOnThisArea() != null && attackToArea.getOwner() != attacker.getOwner()) {
                int hp = attackToArea.getInhabitantOnThisArea().getHp();
                attackToArea.getInhabitantOnThisArea().setHp(hp - attacker.getAttackPower());
                //this.navigationalText.setText("You attacked: " + attackToArea.getInhabitantOnThisArea().toString());
                JOptionPane.showMessageDialog(null, "You attacked: " + attackToArea.getInhabitantOnThisArea().toString());


                if (attackToArea.getInhabitantOnThisArea().getHp() <= 0) {
                    JOptionPane.showMessageDialog(null, attackToArea.getInhabitantOnThisArea().toString() + " was destroyed" );
                    //this.navigationalText.setText(attackToArea.getInhabitantOnThisArea().toString() + " was destroyed" );
                    this.destroy(attackToArea.getInhabitantOnThisArea(), rowAndCollumntTo);
                }
            } else if (attackToArea.getBouildingOnThisArea() != null && attackToArea.getOwner() != attacker.getOwner()) {
                int hp = attackToArea.getBouildingOnThisArea().getHp();
                attackToArea.getBouildingOnThisArea().setHp(hp - attacker.getAttackPower());
                JOptionPane.showMessageDialog(null, "You attacked: " + attackToArea.getBouildingOnThisArea().toString());
                this.navigationalText.setText("You attacked: " + attackToArea.getBouildingOnThisArea().toString());

                if (attackToArea.getBouildingOnThisArea().getHp() <= 0) {
                    JOptionPane.showMessageDialog(null, attackToArea.getBouildingOnThisArea().toString() + " was destroyed");
                    this.navigationalText.setText(attackToArea.getBouildingOnThisArea().toString() + " was destroyed" );
                    this.destroy(attackToArea.getBouildingOnThisArea(), rowAndCollumntTo);
                }
            } else {
                this.navigationalText.setText("Nothing is there");
                this.moveInhabitantOnLandscape(attacker, rowAndCollumnFrom, rowAndCollumntTo);
                this.initializeAttacking(0);
            }
        } else {
            this.navigationalText.setText("You can't attack there, it is too far");
            this.initializeAttacking(0);
        }
        this.initializeAttacking(0);
    }

    //Metoda vymaze urcity objekt z hracej plochy
    public void destroy(DrawAbleObject object, int[] rowAndCollumn) {
        Area areaOfObject = this.getSingleArea(rowAndCollumn[0], rowAndCollumn[1]);

        if (object instanceof Inhabitant) {
            if (!((Inhabitant)object).getCanPerformAction()) {
                areaOfObject.setInhabitantOnThisArea(null);
                ((Inhabitant)object).setHp(0);
            }
        } else if (object instanceof Building) {
            if (!((Building)object).getCanPerformAction()) {
                areaOfObject.setBuildingHere(null);
                ((Building)object).setHp(0);
            }
        }

        object.erase();
        this.setTribeOnMove();
    }

    //Metoda vrati aku budovu chceme postavit
    public int chooseBuildingToBuild() {
        boolean end = false;
        int newBuilding = -1;

        while (!end) {
            newBuilding = Integer.parseInt(JOptionPane.showInputDialog(null, "1 - Barracks | 2 - Barricade"));

            if (newBuilding > 0 && newBuilding < 3) {
                end = true;
            } else {
                JOptionPane.showInputDialog(null, "Choose from choices: 1 - Barracks | 2 - Barricade");
            }
        }

        return newBuilding;
    }

    //Metoda vrati akeho obyvatela chceme vytvorit
    public int chooseToRecruitInhabitant() {
        boolean end = false;
        int inhabitant = -1;

        while (!end) {
            inhabitant = Integer.parseInt(JOptionPane.showInputDialog(null, "1 - Swordman | 2 - Builder"));

            if (inhabitant > 0 && inhabitant < 3) {
                end = true;
            } else {
                JOptionPane.showInputDialog(null, "Choose from choices: 1 - Swordman | 2 - Builder");
            }
        }
        return inhabitant;
    }

    public Tribe getTribeOnMove() {
        return this.tribeOnMove;
    }

    //Nastavi kmen na tahu
    public void setTribeOnMove() {
        if (this.tribeOnMove == this.farmTribe) {
            this.textTribeOnMove.setText(("Tribe on move: " + this.farmTribe.getNameOfTribe()) + "     || End your move:  push 'S' ");
            this.actionsText.setText("");
            this.navigationalText.setText("");
        } else if (this.tribeOnMove == this.economicalTribe) {
            this.textTribeOnMove.setText("Tribe on move: " + this.economicalTribe.getNameOfTribe() + "     || End your move:  push 'S' ");
            this.actionsText.setText("");
            this.navigationalText.setText("");
        }
    }

    //Metoda vymeni aky kmen je na tahu
    public void swapTribeOnMove() {
        if (JOptionPane.showConfirmDialog(this.canvas.getFrame(),
                "Do you really want to end your move", "Ending move",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION ) {
            if (this.tribeOnMove == this.farmTribe) {
                this.tribeOnMove = this.economicalTribe;
                this.setTribeOnMove();

                for (int i = 0; i < this.tribeOnMove.getManageAbleInhabitants().size(); i++) {
                    this.tribeOnMove.getManageAbleInhabitants().get(i).setCanPerformAction(false);
                }

                for (int i = 0; i < this.tribeOnMove.getBuildings().size(); i++) {
                    this.tribeOnMove.getBuildings().get(i).setCanPerformAction(false);
                }

            } else if (this.tribeOnMove == this.economicalTribe) {
                this.tribeOnMove = this.farmTribe;
                this.setTribeOnMove();

                for (int i = 0; i < this.tribeOnMove.getManageAbleInhabitants().size(); i++) {
                    this.tribeOnMove.getManageAbleInhabitants().get(i).setCanPerformAction(false);
                }

                for (int i = 0; i < this.tribeOnMove.getBuildings().size(); i++) {
                    this.tribeOnMove.getBuildings().get(i).setCanPerformAction(false);
                }
            }
        }
        this.canvas.getFrame().repaint();
    }

    private class MyKeyboard extends KeyAdapter {
        MyKeyboard() {
            super();
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            System.out.println("You pushed " + ke.getKeyCode());

            switch (ke.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    actionEnd();
                    break;
                case KeyEvent.VK_M:
                    System.out.println("Moving was initiated");
                    World.this.initializeMoving(1);
                    break;
                case KeyEvent.VK_A:
                    World.this.initializeAttacking(1);
                    break;
                case KeyEvent.VK_B:
                    World.this.initializeBuilding(1);
                    break;
                case KeyEvent.VK_S:
                    World.this.swapTribeOnMove();
                    break;
                case KeyEvent.VK_C:
                    World.this.initializeRecruiment(1);
                    break;
                case KeyEvent.VK_D:
                    World.this.initializeDestroying(World.this.clickedObject);
                    break;
                default:
                    break;
            }
        }
    }
}
