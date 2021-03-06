package tw.com.jimhsu.gameobject;

import java.awt.*;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int preX;
    protected int preY;
    protected Image[] image;
    protected int widthImage;
    protected int heightImage;

    protected boolean alive;
    protected int frame;

    public GameObject(Image[] image, int x, int y) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.widthImage = image[0].getWidth(null);
        this.heightImage = image[0].getHeight(null);
        alive = true;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, widthImage, heightImage);
    }

    public int[] getCenterPos(Rectangle rect) {
        int[] pos = new int[2];

        pos[0] = this.x + (widthImage - rect.width) / 2;
        pos[1] = this.y + (heightImage - rect.height) / 2;

        return pos;
    }

    public abstract void ai();

    public abstract void draw(Graphics g);

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image[] getImage() {
        return this.image;
    }

    public void setImage(Image[] image) {
        this.image = image;
    }

    public boolean getAlive() {
        return alive;
    }
}
