package tw.com.jimhsu;

import javax.swing.*;
import java.awt.*;

public class GameClient extends JComponent {
    private int screenWidth;
    private int screenHeight;

    GameClient() {
        this(800, 600);
    }

    public GameClient(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    @Override
    public String toString() {
        return "{" +
                " screenWidth='" + getScreenWidth() + "'" +
                ", screenHeight='" + getScreenHeight() + "'" +
                "}";
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        g.drawImage(new ImageIcon("assets/images/itankU.png").getImage(),
                400, 400, null);
    }

}