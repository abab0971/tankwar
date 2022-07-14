package tw.com.jimhsu;

import javax.swing.*;

import tw.com.jimhsu.gameobject.Direction;
import tw.com.jimhsu.gameobject.GameObject;
import tw.com.jimhsu.gameobject.Tank;
import tw.com.jimhsu.gameobject.Wall;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameClient extends JComponent {
    private int screenWidth;
    private int screenHeight;

    private Tank playerTank;
    private CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<GameObject>();

    private Image[] bulletImage;

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
                    // System.out.println(gameObjects.size());
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

        // String[] ext = { "U", "D", "L", "R", "LU", "RU", "LD", "RD" };
        String[] ext = { "U", "RU", "R", "RD", "D", "LD", "L", "LU" };
        Image[] iTankImg = new Image[ext.length];
        Image[] eTankImg = new Image[ext.length];
        bulletImage = new Image[ext.length];

        // UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
        for (int i = 0; i < ext.length; i++) {
            iTankImg[i] = new ImageIcon("assets/images/itank" + ext[i] + ".png").getImage();
            eTankImg[i] = new ImageIcon("assets/images/etank" + ext[i] + ".png").getImage();
            bulletImage[i] = new ImageIcon("assets/images/missile" + ext[i] + ".png").getImage();
        }

        // 玩家物件
        playerTank = new Tank(iTankImg, 380, 500, Direction.UP, false);
        playerTank.setSpeed(5);

        gameObjects.add(playerTank);
        // 產生敵方
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                gameObjects.add(new Tank(eTankImg, 200 + j * 100, 50 + i * (eTankImg[0].getHeight(null) + 47),
                        Direction.DOWN, true));
            }
        }

        Image[] wallImg = { new ImageIcon("assets/images/brick.png").getImage() };
        gameObjects.add(new Wall(wallImg, 80, 10, false, 15));
        gameObjects.add(new Wall(wallImg, 140, 10, true, 10));
        gameObjects.add(new Wall(wallImg, 640, 10, false, 15));
    }

    /**
     * 按鍵事件(上緣)
     * 1000: 上
     * 1100: 右上
     * 0100: 右
     * 0110: 右下
     * 0010: 下
     * 0011: 左下
     * 0001: 左
     * 1001: 左上
     */
    public void keyPressed(KeyEvent e) {
        int dirs = playerTank.getDirs();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dirs |= 0b1000;
                break;
            case KeyEvent.VK_RIGHT:
                dirs |= 0b0100;
                break;
            case KeyEvent.VK_DOWN:
                dirs |= 0b0010;
                break;
            case KeyEvent.VK_LEFT:
                dirs |= 0b0001;
                break;
            case KeyEvent.VK_CONTROL:
                playerTank.fire();
                break;
        }
        playerTank.setDirs(dirs);
        // playerTank.move();
    }

    /**
     * 按鍵事件(下緣)
     * 1000: 上
     * 1100: 右上
     * 0100: 右
     * 0110: 右下
     * 0010: 下
     * 0011: 左下
     * 0001: 左
     * 1001: 左上
     */
    public void keyReleased(KeyEvent e) {
        int dirs = playerTank.getDirs();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dirs &= ~0b1000;
                break;
            case KeyEvent.VK_RIGHT:
                dirs &= ~0b0100;
                break;
            case KeyEvent.VK_DOWN:
                dirs &= ~0b0010;
                break;
            case KeyEvent.VK_LEFT:
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

    public CopyOnWriteArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Image[] getBulletImage() {
        return bulletImage;
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

        // 多型
        for (GameObject object : gameObjects) {
            object.draw(g);
        }

        // 生命週期結束，釋放物件
        for (GameObject object : gameObjects) {
            if (!object.getAlive()) {
                gameObjects.remove(object);
            }
        }
    }
}