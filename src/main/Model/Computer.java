package main.Model;

import static main.Frame.Constants.POINT_SIZE;

public class Computer extends DefaultSnake {
    private boolean stopped;

    public Computer() {
        init();
    }

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
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public void move() {
        if (!stopped) {
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

    public void updateDirection(int fruitX, int fruitY, int[] obstacleX, int[] obstacleY) {
        // Check if the next move will hit an obstacle
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

    private boolean isObstacleAt(int checkX, int checkY, int[] obstacleX, int[] obstacleY) {
        for (int i = 0; i < obstacleX.length; i++) {
            if (checkX == obstacleX[i] && checkY == obstacleY[i]) {
                return true;
            }
        }
        return false;
    }

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

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
