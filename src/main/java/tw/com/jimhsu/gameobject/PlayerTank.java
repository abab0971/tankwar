package tw.com.jimhsu.gameobject;

import java.awt.*;

import tw.com.jimhsu.App;

public class PlayerTank extends Tank implements SuperFire {

    public PlayerTank(Image[] image, int x, int y, Direction direction) {
        super(image, x, y, direction, false);
    }

    @Override
    public void superFire() {
        Bullet bullet = new Bullet(App.gameClient.getBulletImage(), 0, 0, direction, isEnemy());
        int[] pos = getCenterPos(bullet.getRectangle());

        for (Direction direction : Direction.values()) {
            bullet = new Bullet(App.gameClient.getBulletImage(), pos[0], pos[1], direction, isEnemy());
            App.gameClient.getGameObjects().add(bullet);
        }
    }
}
