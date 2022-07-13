package tw.com.jimhsu;

import javax.swing.*;

import tw.com.jimhsu.gameobject.Direction;
import tw.com.jimhsu.gameobject.Tank;

import java.awt.*;
import java.awt.event.*;

public class GameClient extends JComponent {
    private int screenWidth;
    private int screenHeight;

    private Tank playerTank;

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
     * 初始化我方坦克方向
     */
    public void init() {
        playerTank = new Tank(250, 250, Direction.UP);
        playerTank.setSpeed(5);
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

        playerTank.draw(g);
    }
}