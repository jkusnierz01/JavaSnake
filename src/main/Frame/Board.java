package main.Frame;

import main.Model.Computer;
import main.Model.Frog;
import main.Model.Fruit;
import main.Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static main.Frame.Constants.*;

/**
 * Board class represents the game board for the snake game.
 * It extends JPanel and implements ActionListener to handle game updates.
 */
public class Board extends JPanel implements ActionListener {
    /**
     * Indicates whether the game is currently active.
     */
    public boolean inGame = true;

    /**
     * Player object representing the human player.
     */
    private Player player;

    /**
     * Fruit object representing the fruit to be collected.
     */
    private Fruit myFruit;

    /**
     * Frog object representing the frog to be collected.
     */
    private Frog myFrong;

    /**
     * List of Computer objects representing AI-controlled players.
     */
    private ArrayList<Computer> computers;

    /**
     * Arrays to hold the x and y coordinates of obstacles.
     */
    private int obstacleX[] = new int[4];
    private int obstacleY[] = new int[4];

    /**
     * Timer object to control game updates.
     */
    private Timer timer;

    /**
     * Images for various game elements.
     */
    private Image ball, fruit, head, frog, compball;

    /**
     * Constructor initializes the game board.
     */
    public Board() {
        buildBoard();
    }

    /**
     * Sets up the game board.
     */
    public void buildBoard() {
        addKeyListener(new MyAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        loadImages();
        gameInitialize();
    }

    /**
     * Loads images for the game elements.
     */
    public void loadImages() {
        ImageIcon _ball = new ImageIcon("src/images/ball.png");
        ball = _ball.getImage();

        ImageIcon comp_ball = new ImageIcon("src/images/comp_ball.png");
        compball = comp_ball.getImage();

        ImageIcon _fruit = new ImageIcon("src/images/fruit.png");
        fruit = _fruit.getImage();

        ImageIcon _head = new ImageIcon("src/images/head.png");
        head = _head.getImage();

        ImageIcon _frog = new ImageIcon("src/images/frog.png");
        frog = _frog.getImage();
    }

    /**
     * Initializes the game elements and starts the game timer.
     */
    public void gameInitialize() {
        this.player = new Player();
        this.computers = new ArrayList<>();
        computers.add(new Computer());
        locateFruit();
        locateFrog();
        locateObstacle();
        timer = new Timer(Constants.DELAY, this);
        timer.start();
    }

    /**
     * Randomly positions the fruit on the board.
     */
    public void locateFruit() {
        this.myFruit = new Fruit();
        int r = (int) (Math.random() * RAND_POS);
        this.myFruit.setFruitX(r * POINT_SIZE);
        r = (int) (Math.random() * RAND_POS);
        this.myFruit.setFruitY(r * POINT_SIZE);
    }

    /**
     * Randomly positions the frog on the board.
     */
    public void locateFrog() {
        this.myFrong = new Frog();
        int r = (int) (Math.random() * RAND_POS);
        this.myFrong.setFrogX(r * POINT_SIZE);
        r = (int) (Math.random() * RAND_POS);
        this.myFrong.setFrogY(r * POINT_SIZE);
    }

    /**
     * Positions obstacles on the board.
     */
    public void locateObstacle() {
        int r = (int) (Math.random() * RAND_POS);
        obstacleX[0] = r * POINT_SIZE;
        obstacleY[0] = r * POINT_SIZE;
        for (int i = 1; i < 4; i++) {
            obstacleX[i] = obstacleX[i - 1] + POINT_SIZE;
            obstacleY[i] = obstacleY[0];
        }
    }

    /**
     * Paints the game elements on the board.
     * @param g the Graphics object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * Draws the game elements.
     * @param g the Graphics object to protect
     */
    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(fruit, myFruit.getFruitX(), myFruit.getFruitY(), this);
            g.drawImage(frog, myFrong.getFrogX(), myFrong.getFrogY(), this);
            g.setColor(Color.red);
            for (int i = 0; i < 4; i++) {
                g.fillRect(obstacleX[i], obstacleY[i], POINT_SIZE, POINT_SIZE);
            }

            for (int i = 0; i < player.dots; ++i) {
                if (i == 0) {
                    g.drawImage(head, player.getX()[i], player.getY()[i], this);
                } else {
                    g.drawImage(ball, player.getX()[i], player.getY()[i], this);
                }
            }

            for (Computer computer : computers) {
                for (int i = 0; i < computer.dots; ++i) {
                    if (i == 0) {
                        g.drawImage(head, computer.getX()[i], computer.getY()[i], this);
                    } else {
                        g.drawImage(compball, computer.getX()[i], computer.getY()[i], this);
                    }
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    /**
     * Displays the game over message.
     * @param g the Graphics object to protect
     */
    public void gameOver(Graphics g) {
        final String message = "Game Over";
        Font messageFont = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(messageFont);
        g.setColor(Color.white);
        g.setFont(messageFont);
        g.drawString(message, (FIELD_WIDTH - metr.stringWidth((message))) / 2, FIELD_HEIGHT / 2);
        ResultingFrame addResult = new ResultingFrame(player.dots - 3);
        addResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addResult.setVisible(true);
    }

    /**
     * Checks if the player or a computer has collected a fruit.
     */
    public void checkFruit() {
        if ((player.getX()[0] == myFruit.getFruitX()) && (player.getY()[0] == myFruit.getFruitY())) {
            player.dots++;
            locateFruit();
        }

        for (Computer computer : computers) {
            if ((computer.getX()[0] == myFruit.getFruitX()) && (computer.getY()[0] == myFruit.getFruitY())) {
                computer.dots++;
                locateFruit();
            }
        }
    }

    /**
     * Checks if the player or a computer has collected a frog.
     */
    public void checkFrog() {
        if ((player.getX()[0] == myFrong.getFrogX()) && (player.getY()[0] == myFrong.getFrogY())) {
            ++player.dots;
            locateFrog();
        }
        for (Computer computer : computers) {
            if ((computer.getX()[0] == myFrong.getFrogX()) && (computer.getY()[0] == myFrong.getFrogY())) {
                ++computer.dots;
                locateFrog();
            }
        }
    }

    /**
     * Checks if there is a collision involving the player.
     */
    private void checkIfCollision() {
        for (int i = player.dots; i > 0; --i) {
            if ((i > 4) && (player.getX()[0] == player.getX()[i]) && (player.getY()[0] == player.getY()[i])) {
                inGame = false;
            }
        }
        if ((player.getY()[0] >= FIELD_HEIGHT) || (player.getY()[0] < 0) || (player.getX()[0] >= FIELD_WIDTH) || (player.getX()[0] < 0)) {
            inGame = false;
        }
        for (int i = 0; i < 4; i++) {
            if ((player.getX()[0] == obstacleX[i]) && (player.getY()[0] == obstacleY[i])) {
                inGame = false;
                break;
            }
        }

        for (Computer computer : computers) {
            for (int i = 0; i < computer.dots; i++) {
                if ((player.getX()[0] == computer.getX()[i]) && (player.getY()[0] == computer.getY()[i])) {
                    inGame = false;
                }
            }
        }

        for (int i = 0; i < computers.size(); ++i) {
            Computer c1 = computers.get(i);
            for (int j = 0; j < computers.size(); ++j) {
                if (i != j) {
                    Computer c2 = computers.get(j);
                    for (int k = 0; k < c1.dots; k++) {
                        for (int l = 0; l < c2.dots; l++) {
                            if ((c1.getX()[k] == c2.getX()[l]) && (c1.getY()[k] == c2.getY()[l])) {
                                c1.setStopped(true);
                                c2.setStopped(true);
                                inGame = false;
                            }
                        }
                    }
                }
            }
        }

        if (!inGame) {
            timer.stop();
        }
    }

    /**
     * Checks for collisions involving the computers.
     */
    private void checkComputerCollision() {
        for (int i = 0; i < computers.size(); ++i) {
            Computer c1 = computers.get(i);
            for (int j = i + 1; j < computers.size(); ++j) {
                Computer c2 = computers.get(j);
                if ((c1.getX()[0] == c2.getX()[0]) && (c1.getY()[0] == c2.getY()[0])) {
                    c1.setStopped(true);
                    c2.setStopped(true);
                }
            }
        }

        for (Computer computer : computers) {
            if ((computer.getY()[0] >= FIELD_HEIGHT) || (computer.getY()[0] < 0) || (computer.getX()[0] >= FIELD_WIDTH) || (computer.getX()[0] < 0)) {
                computer.reverseDirection();
            }
        }

        for (Computer computer : computers) {
            for (int i = 0; i < 4; i++) {
                if ((computer.getX()[0] == obstacleX[i]) && (computer.getY()[0] == obstacleY[i])) {
                    computer.setStopped(true);
                    break;
                }
            }
        }
    }

    /**
     * Called every DELAY milliseconds to update the game state.
     * @param e the ActionEvent triggered by the Timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            Thread fruitThread = new Thread(this::checkFruit);
            Thread playerThread = new Thread(() -> {
                checkIfCollision();
                this.player.move();
            });
            Thread frogThread = new Thread(() -> {
                checkFrog();
                this.myFrong.move();
            });
            Thread computerThread = new Thread(() -> {
                checkComputerCollision();
                for (Computer computer : computers) {
                    computer.updateDirection(myFruit.getFruitX(), myFruit.getFruitY(), obstacleX, obstacleY);
                    computer.move();
                    computer.checkIfCollisionWithOtherSnakes(player, computers);
                }
            });

            fruitThread.start();
            playerThread.start();
            frogThread.start();
            computerThread.start();
            try {
                fruitThread.join();
                playerThread.join();
                frogThread.join();
                computerThread.join();
            } catch (InterruptedException interruptedException) {
                System.out.println(interruptedException.getMessage());
            }
        }
        repaint();
    }

    /**
     * Handles keyboard input to control the player's direction.
     */
    private class MyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!player.rightDirection)) {
                player.leftDirection = true;
                player.upDirection = false;
                player.downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!player.leftDirection)) {
                player.rightDirection = true;
                player.upDirection = false;
                player.downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!player.downDirection)) {
                player.upDirection = true;
                player.rightDirection = false;
                player.leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!player.upDirection)) {
                player.downDirection = true;
                player.rightDirection = false;
                player.leftDirection = false;
            }
        }
    }
}
