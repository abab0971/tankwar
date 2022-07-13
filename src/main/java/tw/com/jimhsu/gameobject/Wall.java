package tw.com.jimhsu.gameobject;

import java.awt.*;

public class Wall extends GameObject {
    private boolean horizontal;
    private int bricks;

    public Wall(Image image, int x, int y, boolean horizontal, int bricks) {
        super(image, x, y);
        this.horizontal = horizontal;
        this.bricks = bricks;
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < bricks; i++) {
            if (horizontal) {
                g.drawImage(image, x + i * image.getWidth(null), y, null);
            } else {
                g.drawImage(image, x, y + i * image.getHeight(null), null);
            }
        }
    }
}
