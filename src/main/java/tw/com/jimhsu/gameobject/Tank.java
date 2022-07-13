package tw.com.jimhsu.gameobject;

import java.awt.*;

import javax.swing.ImageIcon;;

public class Tank {
    private int x;
    private int y;
    private int speed;
    private Direction direction;
    // private boolean[] dirs;
    // 上: bit3, 下: bit2, 左: bit1, 右: bit0
    private int dirs;

    public Tank(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = 5;
        dirs = 0b0000;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getDirs() {
        return dirs;
    }

    public void setDirs(int dirs) {
        this.dirs = dirs;
    }

    /**
     * 坦克方向圖片
     * 
     * @return
     */
    public Image getImage() {
        switch (this.direction) {
            case UP:
                return new ImageIcon("assets/images/itankU.png").getImage();
            case DOWN:
                return new ImageIcon("assets/images/itankD.png").getImage();
            case LEFT:
                return new ImageIcon("assets/images/itankL.png").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/itankR.png").getImage();
            case UP_LEFT:
                return new ImageIcon("assets/images/itankLU.png").getImage();
            case UP_RIGHT:
                return new ImageIcon("assets/images/itankRU.png").getImage();
            case DOWN_LEFT:
                return new ImageIcon("assets/images/itankLD.png").getImage();
            case DOWN_RIGHT:
                return new ImageIcon("assets/images/itankRD.png").getImage();
            default:
                return null;
        }
    }

    // 避免上下或者左右一起按
    private void determineDirection() {
        switch (dirs) {
            case 0b1000:
                direction = Direction.UP;
                break;
            case 0b0100:
                direction = Direction.DOWN;
                break;
            case 0b0010:
                direction = Direction.LEFT;
                break;
            case 0b0001:
                direction = Direction.RIGHT;
                break;
            case 0b1010:
                direction = Direction.UP_LEFT;
                break;
            case 0b1001:
                direction = Direction.UP_RIGHT;
                break;
            case 0b0110:
                direction = Direction.DOWN_LEFT;
                break;
            case 0b0101:
                direction = Direction.DOWN_RIGHT;
                break;
        }
    }

    /**
     * 移動
     */
    public void move() {
        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP_LEFT:
                y -= speed;
                x -= speed;
                break;
            case UP_RIGHT:
                y -= speed;
                x += speed;
                break;
            case DOWN_LEFT:
                y += speed;
                x -= speed;
                break;
            case DOWN_RIGHT:
                y += speed;
                x += speed;
                break;
        }
    }

    /**
     * 停止移動檢查
     * 
     * @return
     */
    public boolean isStop() {
        return this.dirs == 0 ? true : false;
    }

    // 更新畫面
    public void draw(Graphics g) {
        if (!isStop()) {
            determineDirection();
            move();
        }
        g.drawImage(getImage(), x, y, null);
    }
}
