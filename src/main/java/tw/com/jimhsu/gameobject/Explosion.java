package tw.com.jimhsu.gameobject;

import java.awt.*;;

public class Explosion extends GameObject {
    public Explosion(Image[] image, int x, int y) {
        super(image, x, y);
        run();
    }

    void run() {
        new Thread(() -> {
            while (alive) {
                try {
                    Thread.sleep(50);
                    if (++frame >= image.length) {
                        alive = false;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void draw(Graphics g) {
        if (!alive) {
            return;
        }

        g.drawImage(image[frame], x, y, null);

    }

    @Override
    public void ai() {

    }
}
