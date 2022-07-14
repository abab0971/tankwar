package tw.com.jimhsu.gameobject;

import java.awt.*;

import tw.com.jimhsu.App;

public class Tank extends GameObject {
    private int speed;
    private Direction direction;
    // 上: bit3, 下: bit2, 左: bit1, 右: bit0
    private int dirs;

    private boolean enemy;

    public Tank(Image[] image, int x, int y, Direction direction, boolean enemy) {
        super(image, x, y);
        this.direction = direction;
        this.speed = 5;
        dirs = 0b0000;
        this.enemy = enemy;
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
        preX = x;
        preY = y;
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

    /**
     * 碰撞偵測(邊界、敵方、牆面、彈道)
     */
    public void collision() {

        // 邊界限制
        if (x < 0) {
            x = 0;
        } else if (x > App.gameClient.getScreenWidth() - this.widthImage) {
            x = App.gameClient.getScreenWidth() - this.widthImage;
            ;
        }
        if (y < 0) {
            y = 0;
        } else if (y > App.gameClient.getScreenHeight() - this.heightImage) {
            y = App.gameClient.getScreenHeight() - this.heightImage;
        }

        // 多型物件偵測
        for (GameObject object : App.gameClient.getGameObjects()) {
            if (object == this) {
                continue;
            }
            if (getRectangle().intersects(object.getRectangle())) {
                x = preX;
                y = preY;
                return;
            }
        }
    }

    @Override
    public Rectangle getRectangle() {
        int padding = 8;
        return new Rectangle(x + padding, y + padding, widthImage - padding, heightImage - padding);
    }

    @Override
    public void draw(Graphics g) {
        if (!isStop()) {
            determineDirection();
            move();
            collision();
        }
        g.drawImage(image[direction.ordinal()], x, y, null);
    }
}
