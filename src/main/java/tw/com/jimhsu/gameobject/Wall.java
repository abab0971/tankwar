package tw.com.jimhsu.gameobject;

import java.awt.*;

public class Wall {
    private int x;
    private int y;

    private boolean horizontal;
    private int bricks;

    private Image image;

    public Wall(Image image, int x, int y, boolean horizontal, int bricks) {
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.bricks = bricks;
        this.image = image;
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
