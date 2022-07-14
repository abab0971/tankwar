package tw.com.jimhsu.gameobject;

/**
 * 方向
 * 1000: 上
 * 1100: 右上
 * 0100: 右
 * 0110: 右下
 * 0010: 下
 * 0011: 左下
 * 0001: 左
 * 1001: 左上
 */
public enum Direction {
    // UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
    UP,
    UP_RIGHT,
    RIGHT,
    DOWN_RIGHT,
    DOWN,
    DOWN_LEFT,
    LEFT,
    UP_LEFT,
}
