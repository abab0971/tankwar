package tw.com.jimhsu;

import java.awt.event.*;

import javax.swing.JFrame;

/**
 * Hello world!
 */
public final class App extends JFrame {
    public static GameClient gameClient;

    private App() {
        setTitle("坦克大戰");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        gameClient = new GameClient();
        add(gameClient);

        pack();
        setVisible(true);

        // 遊戲區域重新繪製
        gameClient.run();

        // 按鍵偵測
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                gameClient.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gameClient.keyReleased(e);
            }
        });
    }

    /**
     * 啟動程式
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        new App();
    }
}
