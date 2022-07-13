package tw.com.jimhsu.gameobject;

import java.awt.*;

import javax.swing.ImageIcon;;

public class Tank {
    private int x;
    private int y;
    private Direction direction;

    public Tank() {
    }

    public Tank(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * 坦克方向圖片
     * @return
     */
    public Image getImage(){
        switch (this.direction) {
            case UP:
                return new ImageIcon("assets/images/itankU.png").getImage();
            case DOWN:
                return new ImageIcon("assets/images/itankD.png").getImage();
            case LEFT:
                return new ImageIcon("assets/images/itankL.png").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/itankR.png").getImage();
            default:
                return null;
        }
    }
}
