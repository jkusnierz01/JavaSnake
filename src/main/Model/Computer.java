package main.Model;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static main.Frame.Constants.POINT_SIZE;

/**
 * The Computer class represents an AI-controlled snake in the game.
 */
public class Computer extends DefaultSnake {
    private boolean stopped;
    private boolean frozen;
    private Timer freezeTimer;

    /**
     * Constructs a Computer object and initializes it.
     */
    public Computer() {
        init();
    }

    /**
     * Initializes the computer snake with default values.
     */
    public void init() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 200 - i * POINT_SIZE;
            y[i] = 10;
        }
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;
        stopped = false;
        frozen = false;
    }

    /**
     * Returns the x coordinates of the computer snake.
     *
     * @return an array of x coordinates
     */
    public int[] getX() {
        return x;
    }

    /**
     * Returns the y coordinates of the computer snake.
     *
     * @return an array of y coordinates
     */
    public int[] getY() {
        return y;
    }

    /**
     * Moves the computer snake if it is not stopped or frozen.
     */
    public void move() {
        if (!stopped && !frozen) {
            for (int i = dots; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            if (leftDirection) {
                x[0] -= POINT_SIZE;
            }

            if (rightDirection) {
                x[0] += POINT_SIZE;
            }

            if (upDirection) {
                y[0] -= POINT_SIZE;
            }

            if (downDirection) {
                y[0] += POINT_SIZE;
            }
        }
    }

    /**
     * Updates the direction of the computer snake towards the fruit while avoiding obstacles.
     *
     * @param fruitX     the x coordinate of the fruit
     * @param fruitY     the y coordinate of the fruit
     * @param obstacleX  the x coordinates of the obstacles
     * @param obstacleY  the y coordinates of the obstacles
     */
    public void updateDirection(int fruitX, int fruitY, int[] obstacleX, int[] obstacleY) {
        if (willHitObstacle(obstacleX, obstacleY)) {
            avoidObstacle(obstacleX, obstacleY);
        } else {
            if (x[0] < fruitX) {
                rightDirection = true;
                leftDirection = false;
                upDirection = false;
                downDirection = false;
            } else if (x[0] > fruitX) {
                leftDirection = true;
                rightDirection = false;
                upDirection = false;
                downDirection = false;
            } else if (y[0] < fruitY) {
                downDirection = true;
                upDirection = false;
                leftDirection = false;
                rightDirection = false;
            } else if (y[0] > fruitY) {
                upDirection = true;
                downDirection = false;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }

    /**
     * Checks if the computer snake will hit an obstacle in its next move.
     *
     * @param obstacleX the x coordinates of the obstacles
     * @param obstacleY the y coordinates of the obstacles
     * @return true if the computer snake will hit an obstacle, false otherwise
     */
    private boolean willHitObstacle(int[] obstacleX, int[] obstacleY) {
        int nextX = x[0];
        int nextY = y[0];

        if (leftDirection) {
            nextX -= POINT_SIZE;
        } else if (rightDirection) {
            nextX += POINT_SIZE;
        } else if (upDirection) {
            nextY -= POINT_SIZE;
        } else if (downDirection) {
            nextY += POINT_SIZE;
        }

        for (int i = 0; i < obstacleX.length; i++) {
            if (nextX == obstacleX[i] && nextY == obstacleY[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adjusts the direction of the computer snake to avoid obstacles.
     *
     * @param obstacleX the x coordinates of the obstacles
     * @param obstacleY the y coordinates of the obstacles
     */
    private void avoidObstacle(int[] obstacleX, int[] obstacleY) {
        if (leftDirection || rightDirection) {
            if (!isObstacleAt(x[0], y[0] - POINT_SIZE, obstacleX, obstacleY)) {
                upDirection = true;
                downDirection = false;
            } else {
                downDirection = true;
                upDirection = false;
            }
            leftDirection = false;
            rightDirection = false;
        } else if (upDirection || downDirection) {
            if (!isObstacleAt(x[0] - POINT_SIZE, y[0], obstacleX, obstacleY)) {
                leftDirection = true;
                rightDirection = false;
            } else {
                rightDirection = true;
                leftDirection = false;
            }
            upDirection = false;
            downDirection = false;
        }
    }

    /**
     * Checks if there is an obstacle at the specified coordinates.
     *
     * @param checkX     the x coordinate to check
     * @param checkY     the y coordinate to check
     * @param obstacleX  the x coordinates of the obstacles
     * @param obstacleY  the y coordinates of the obstacles
     * @return true if there is an obstacle at the specified coordinates, false otherwise
     */
    private boolean isObstacleAt(int checkX, int checkY, int[] obstacleX, int[] obstacleY) {
        for (int i = 0; i < obstacleX.length; i++) {
            if (checkX == obstacleX[i] && checkY == obstacleY[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reverses the direction of the computer snake.
     */
    public void reverseDirection() {
        if (leftDirection) {
            leftDirection = false;
            rightDirection = true;
        } else if (rightDirection) {
            rightDirection = false;
            leftDirection = true;
        } else if (upDirection) {
            upDirection = false;
            downDirection = true;
        } else if (downDirection) {
            downDirection = false;
            upDirection = true;
        }
    }

    /**
     * Checks if the computer snake is stopped.
     *
     * @return true if the computer snake is stopped, false otherwise
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * Sets the stopped state of the computer snake.
     *
     * @param stopped the new stopped state
     */
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    /**
     * Freezes the computer snake for a specified duration.
     */
    public void freeze() {
        frozen = true;
        freezeTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frozen = false;
                freezeTimer.stop();
            }
        });
        freezeTimer.start();
    }

    /**
     * Checks if the computer snake collides with the player or other computer snakes.
     *
     * @param player    the player snake
     * @param computers the list of computer snakes
     */
    public void checkIfCollisionWithOtherSnakes(Player player, ArrayList<Computer> computers) {
        // Sprawdzanie kolizji z graczem
        for (int i = 0; i < player.dots; i++) {
            if ((x[0] == player.getX()[i]) && (y[0] == player.getY()[i])) {
                freeze();
                break;
            }
        }

        // Sprawdzanie kolizji z innymi komputerowymi wężami
        for (Computer other : computers) {
            if (other != this) {
                for (int i = 0; i < other.dots; i++) {
                    if ((x[0] == other.getX()[i]) && (y[0] == other.getY()[i])) {
                        freeze();
                        other.freeze();
                        break;
                    }
                }
            }
        }
    }
}
