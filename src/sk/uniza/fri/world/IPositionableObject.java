package sk.uniza.fri.world;

/**
 * 15. 4. 2022 - 11:53
 *
 * @author maros
 *
 * Reprezentuje pohiblivy objekt na hracej ploche
 *
 */
public interface IPositionableObject {
    int getCollumn();
    int getRow();
    void setCollumn(int collumn);
    void setRow(int row);
    void move(int row, int collumn);
    void setIsVisible(boolean isVisible);
    boolean getIsVisible();
    String getPathToImage();
    void draw(int posX, int posY);
    void erase();
}
