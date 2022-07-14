package tw.com.jimhsu.gameobject;

import java.awt.Graphics;
import java.awt.Image;

import tw.com.jimhsu.App;

public class Bullet extends Tank {

    private int damage;

    public Bullet(Image[] image, int x, int y, Direction direction, boolean enemy) {
        super(image, x, y, direction, enemy);
        setSpeed(6);
        run();
    }

    void run() {
        new Thread(() -> {
            while (alive) {
                try {
                    Thread.sleep(25);
                    if (++speed >= 50) {
                        speed = 50;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void collision() {
        if (isCollisionBound()) {
            alive = false;
            return;
        }

        // 多型物件偵測
        for (GameObject object : App.gameClient.getGameObjects()) {
            if (object == this) {
                continue;
            }
            if (object instanceof Tank) {
                // 向下轉型
                if (((Tank) object).isEnemy() == isEnemy()) {
                    continue;
                }

                // 子彈攻擊敵方
                if (getRectangle().intersects(object.getRectangle())) {
                    // App.gameClient.getGameObjects().add(new Explosion(
                    // App.gameClient.getExplosionImg(), object.getX(), object.getY()));

                    ((Tank) object).getHurt(damage);
                    alive = false;
                    continue;
                }
            }

            // 實際偵測碰撞
            if (getRectangle().intersects(object.getRectangle())) {
                // 返回沒碰撞前的位置
                alive = false;
                return;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!alive) {
            return;
        }

        move();
        collision();

        g.drawImage(image[direction.ordinal()], x, y, null);
    }
}
