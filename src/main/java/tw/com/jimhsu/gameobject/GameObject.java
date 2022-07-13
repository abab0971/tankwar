package tw.com.jimhsu.gameobject;

import java.awt.*;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected Image[] image;
    protected int widthImage;
    protected int heightImage;

    public GameObject(Image[] image, int x, int y) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.widthImage = image[0].getWidth(null);
        this.heightImage = image[0].getHeight(null);
    }

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
}