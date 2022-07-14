package tw.com.jimhsu;

import javax.swing.*;

import tw.com.jimhsu.gameobject.Direction;
import tw.com.jimhsu.gameobject.GameObject;
import tw.com.jimhsu.gameobject.PlayerTank;
import tw.com.jimhsu.gameobject.Tank;
import tw.com.jimhsu.gameobject.Wall;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameClient extends JComponent {
    private int screenWidth;
    private int screenHeight;

    private PlayerTank playerTank;
    private CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<GameObject>();

    private Image[] bulletImage;
    private Image[] wallImg;
    private Image[] iTankImg;
    private Image[] eTankImg;
    private Image[] explosionImg;

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
        // String[] ext = { "U", "D", "L", "R", "LU", "RU", "LD", "RD" };
        String[] ext = { "U", "RU", "R", "RD", "D", "LD", "L", "LU" };

        // 牆面
        wallImg = new Image[] { new ImageIcon("assets/images/brick.png").getImage() };

        iTankImg = new Image[ext.length];
        eTankImg = new Image[ext.length];
        bulletImage = new Image[ext.length];

        explosionImg = new Image[11];
        for (int i = 0; i < explosionImg.length; i++) {
            explosionImg[i] = new ImageIcon("assets/images/" + i + ".png").getImage();
        }

        // UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
        for (int i = 0; i < ext.length; i++) {
            iTankImg[i] = new ImageIcon("assets/images/itank" + ext[i] + ".png").getImage();
            eTankImg[i] = new ImageIcon("assets/images/etank" + ext[i] + ".png").getImage();
            bulletImage[i] = new ImageIcon("assets/images/missile" + ext[i] + ".png").getImage();
        }

        initGame();
    }

    // 重置遊戲
    void initGame() {
        // 釋放遊戲資源
        for (GameObject object : gameObjects) {
            gameObjects.remove(object);
        }

        // 玩家物件
        playerTank = new PlayerTank(iTankImg, 380, 500, Direction.UP);
        // playerTank.setEnemy(true);
        playerTank.setSpeed(5);
        gameObjects.add(playerTank);

        geneEnemy(1);
        geneWall(10);
    }

    /**
     * 生成敵方
     * 
     * @param nums
     */
    public void geneEnemy(int nums) {
        Random random = new Random();

        for (int i = 0; i < nums; i++) {
            int x = random.nextInt(screenWidth - eTankImg[0].getWidth(null));
            int y = random.nextInt(screenHeight - eTankImg[0].getHeight(null));
            Tank enemyTank = new Tank(eTankImg, x, y,
                    Direction.values()[random.nextInt(Direction.values().length)], true);

            enemyTank.setSpeed(5);
            gameObjects.add(enemyTank);
        }
    }

    /**
     * 生成牆壁
     * 
     * @param nums
     */
    public void geneWall(int nums) {
        Random random = new Random();

        for (int i = 0; i < nums; i++) {
            int x = random.nextInt(screenWidth - wallImg[0].getWidth(null));
            int y = random.nextInt(screenHeight - wallImg[0].getHeight(null));

            gameObjects.add(new Wall(wallImg, x, y, false, 1));
        }
    }

    /**
     * 檢查遊戲狀態
     */
    public void checkGameState() {
        boolean gameOver = true;

        for (GameObject object : gameObjects) {
            if (object instanceof Tank && ((Tank) object).isEnemy()) {
                gameOver = false;
                break;
            }
        }

        if (!playerTank.getAlive()) {
            gameOver = true;
        }

        if (gameOver) {
            initGame();
        }

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
            case KeyEvent.VK_S:
                playerTank.superFire();
                break;
            case KeyEvent.VK_Z:
                initGame();
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

    public Image[] getExplosionImg() {
        return explosionImg;
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
        // 判斷遊戲是否結束
        checkGameState();
    }
}