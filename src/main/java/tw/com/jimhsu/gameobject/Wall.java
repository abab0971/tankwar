package tw.com.jimhsu.gameobject;

import java.awt.*;

public class Wall extends GameObject {
    private boolean horizontal;
    private int bricks;

    public Wall(Image[] image, int x, int y, boolean horizontal, int bricks) {
        super(image, x, y);
        this.horizontal = horizontal;
        this.bricks = bricks;
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < bricks; i++) {
            if (horizontal) {
                g.drawImage(image[0], x + i * widthImage, y, null);
            } else {
                g.drawImage(image[0], x, y + i * heightImage, null);
            }
        }
    }
}
