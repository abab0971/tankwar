package tw.com.jimhsu.gameobject;

import java.awt.*;
import java.util.Random;

import tw.com.jimhsu.App;

public class Tank extends GameObject {
    private int speed;
    protected Direction direction;
    // 上: bit3, 下: bit2, 左: bit1, 右: bit0
    private int dirs;

    private boolean enemy;

    protected boolean isCollision;

    public Tank(Image[] image, int x, int y, Direction direction, boolean enemy) {
        super(image, x, y);
        this.direction = direction;
        this.speed = 5;
        dirs = 0b0000;
        this.enemy = enemy;
        this.isCollision = false;
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

    public boolean isEnemy() {
        return enemy;
    }

    public int getSpeed() {
        return this.speed;
    }

    /**
     * 避免上下或者左右一起按
     * 1000: 上
     * 1100: 右上
     * 0100: 右
     * 0110: 右下
     * 0010: 下
     * 0011: 左下
     * 0001: 左
     * 1001: 左上
     */
    private void determineDirection() {
        switch (dirs) {
            case 0b1000:
                direction = Direction.UP;
                break;
            case 0b1100:
                direction = Direction.UP_RIGHT;
                break;
            case 0b0100:
                direction = Direction.RIGHT;
                break;
            case 0b0110:
                direction = Direction.DOWN_RIGHT;
                break;
            case 0b0010:
                direction = Direction.DOWN;
                break;
            case 0b0011:
                direction = Direction.DOWN_LEFT;
                break;
            case 0b0001:
                direction = Direction.LEFT;
                break;
            case 0b1001:
                direction = Direction.UP_LEFT;
                break;
        }
    }

    /**
     * 反解析方向數值
     * 1000: 上
     * 1100: 右上
     * 0100: 右
     * 0110: 右下
     * 0010: 下
     * 0011: 左下
     * 0001: 左
     * 1001: 左上
     * 
     * @param direction
     * @return
     */
    private int reverseDirection(Direction direction) {
        int dirs = 0;
        switch (direction) {
            case UP:
                dirs = 0b1000;
                break;
            case RIGHT:
                dirs = 0b0100;
                break;
            case DOWN:
                dirs = 0b0010;
                break;
            case LEFT:
                dirs = 0b0001;
                break;
            case UP_RIGHT:
                dirs = 0b1100;
                break;
            case DOWN_RIGHT:
                dirs = 0b0110;
                break;
            case DOWN_LEFT:
                dirs = 0b0011;
                break;
            case UP_LEFT:
                dirs = 0b1001;
                break;
        }
        return dirs;
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
     * 邊界限制
     * 
     * @return
     */
    public boolean isCollisionBound() {
        if (x < 0) {
            x = 0;
            return true;
        } else if (x > App.gameClient.getScreenWidth() - this.widthImage) {
            x = App.gameClient.getScreenWidth() - this.widthImage;
            return true;
        }
        if (y < 0) {
            y = 0;
            return true;
        } else if (y > App.gameClient.getScreenHeight() - this.heightImage) {
            y = App.gameClient.getScreenHeight() - this.heightImage;
            return true;
        }
        return false;
    }

    /**
     * 碰撞偵測(邊界、敵方、牆面、彈道)
     */
    public void collision() {

        if (isCollisionBound()) {
            isCollision = true;
            return;
        }

        // 多型物件偵測
        for (GameObject object : App.gameClient.getGameObjects()) {
            if (object == this) {
                continue;
            }
            if (object instanceof Tank) {
                // 向下轉型
                if (((Tank) object).enemy == enemy) {
                    continue;
                }
            }

            // 實際偵測碰撞
            if (getRectangle().intersects(object.getRectangle())) {
                // 返回沒碰撞前的位置
                x = preX;
                y = preY;
                return;
            }
        }
    }

    /**
     * 發射子彈
     */
    public void fire() {
        Bullet bullet = new Bullet(App.gameClient.getBulletImage(), 0, 0, direction, enemy);
        int[] pos = getCenterPos(bullet.getRectangle());
        bullet.setX(pos[0]);
        bullet.setY(pos[1]);

        App.gameClient.getGameObjects().add(bullet);
    }

    /**
     * 取得新方向
     * 以正向為主5個方向
     */
    public void getNewDirection() {
        int dr = direction.ordinal() + new Random().nextInt(5) - 2;
        dr = ((dr < 0) ? dr += 8 : dr) % Direction.values().length;

        // System.out.printf("[%d]org: %s, new: %s\n", dr,
        // Integer.toBinaryString(this.dirs),
        // Integer.toBinaryString(reverseDirection(Direction.values()[dr])));

        this.dirs = reverseDirection(Direction.values()[dr]);
    }

    // /**
    // * 碰撞時取得新方向
    // * 以正向為主取左右方向
    // */
    // public void getNewDirectionInCollision() {
    // int dr = (direction.ordinal()) + ((new Random().nextInt(2) == 0) ? 2 : -2);
    // dr = ((dr < 0) ? dr += 8 : dr) % Direction.values().length;
    // System.out.printf("[%d]org: %s, new: %s\n", dr,
    // Integer.toBinaryString(this.dirs),
    // Integer.toBinaryString(reverseDirection(Direction.values()[dr])));
    // this.dirs = reverseDirection(Direction.values()[dr]);
    // }

    @Override
    public Rectangle getRectangle() {
        int padding = 8;
        return new Rectangle(x + padding, y + padding, widthImage - padding, heightImage - padding);
    }

    @Override
    public void ai() {
        if (!enemy) {
            return;
        }

        if (isCollision) {
            getNewDirection();
            isCollision = false;
            return;
        }

        Random random = new Random();

        // 移動
        if (random.nextInt(50) == 1) {
            getNewDirection();
        }

        // 開火
        if (random.nextInt(100) == 1) {
            fire();
        }
    }

    @Override
    public void draw(Graphics g) {

        if (!alive) {
            return;
        }

        ai();

        if (!isStop()) {
            determineDirection();
            move();
            collision();
        }
        g.drawImage(image[direction.ordinal()], x, y, null);
    }
}
