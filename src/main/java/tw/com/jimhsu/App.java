package tw.com.jimhsu;

import javax.swing.JFrame;

/**
 * Hello world!
 */
public final class App extends JFrame {
    private App() {
        setTitle("坦克大戰");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GameClient gameClient = new GameClient();
        add(gameClient);

        pack();
        setVisible(true);
    }

    /**
     * 啟動程式
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        new App();
    }
}
