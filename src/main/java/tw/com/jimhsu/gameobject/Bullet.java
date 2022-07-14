package tw.com.jimhsu.gameobject;

import java.awt.Graphics;
import java.awt.Image;

public class Bullet extends Tank {

    public Bullet(Image[] image, int x, int y, Direction direction, boolean enemy) {
        super(image, x, y, direction, enemy);
    }

    @Override
    public void draw(Graphics g) {
        move();
        collision();

        g.drawImage(image[direction.ordinal()], x, y, null);
    }
}
