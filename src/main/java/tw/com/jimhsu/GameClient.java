package tw.com.jimhsu;

import javax.swing.*;

import tw.com.jimhsu.gameobject.Direction;
import tw.com.jimhsu.gameobject.Tank;
import tw.com.jimhsu.gameobject.Wall;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends JComponent {
    private int screenWidth;
    private int screenHeight;

    private Tank playerTank;
    private ArrayList<Tank> enemyTank = new ArrayList<Tank>();
    private ArrayList<Wall> walls = new ArrayList<Wall>();

    GameClient() {
        this(800, 600);
    }

    public GameClient(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        init();
    }

    /**
     * 畫面更新
     */
    public void run() {
        new Thread(() -> {
            while (true) {
                repaint();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 初始化
     */
    public void init() {
        // 牆面
        Image wallImg = new ImageIcon("assets/images/brick.png").getImage();

        playerTank = new Tank(380, 500, Direction.UP);
        playerTank.setSpeed(5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                enemyTank.add(new Tank(200 + j * 60, 50 + i * 60, Direction.DOWN, true));
            }
        }

        walls.add(new Wall(wallImg, 80, 10, false, 15));
        walls.add(new Wall(wallImg, 140, 10, true, 10));
        walls.add(new Wall(wallImg, 640, 10, false, 15));
    }

    /**
     * 按鍵事件(上緣)
     */
    public void keyPressed(KeyEvent e) {
        int dirs = playerTank.getDirs();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dirs |= 0b1000;
                break;
            case KeyEvent.VK_DOWN:
                dirs |= 0b0100;
                break;
            case KeyEvent.VK_LEFT:
                dirs |= 0b0010;
                break;
            case KeyEvent.VK_RIGHT:
                dirs |= 0b0001;
                break;
        }
        playerTank.setDirs(dirs);
        // playerTank.move();
    }

    /**
     * 按鍵事件(下緣)
     */
    public void keyReleased(KeyEvent e) {
        int dirs = playerTank.getDirs();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dirs &= ~0b1000;
                break;
            case KeyEvent.VK_DOWN:
                dirs &= ~0b0100;
                break;
            case KeyEvent.VK_LEFT:
                dirs &= ~0b0010;
                break;
            case KeyEvent.VK_RIGHT:
                dirs &= ~0b0001;
                break;
        }
        playerTank.setDirs(dirs);
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

        // 我方
        playerTank.draw(g);

        // 敵方
        for (Tank tank : enemyTank) {
            tank.draw(g);
        }

        // 牆面
        for (Wall wall : walls) {
            wall.draw(g);
        }
    }
}