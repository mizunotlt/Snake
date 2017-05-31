import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;


public class SnakeWindow extends JPanel {

    private final int SIZE = 600;
    private Point[] snakeCord = new Point[SIZE * SIZE];
    private SnakeMechanic snake = new SnakeMechanic(SIZE, snakeCord);
    private static boolean GameOver = false;
    private double dx = 1;
    private double dy = 0;
    private Point coordFruit = new Point();
    private Image apple;
    private final int IMG_WIDTH = 25;
    private final int IMG_HEIGHT = 25;


    public SnakeWindow(){
        setBackground(Color.decode("#A4D3EE"));
        setFocusable(true);
        ActionListener timerListener = e -> {
            if (!GameOver){
                snake.checkCollision(coordFruit);
                snake.moveSnake(dx,dy);
                if(!snake.spawnFood)
                    coordFruit = snake.spawnFruit();
                repaint();

                KeyListener keyListener = new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_DOWN:
                                dy = 1;
                                dx = 0;
                                break;
                            case KeyEvent.VK_UP:
                                dy = -1;
                                dx = 0;
                                break;
                            case KeyEvent.VK_LEFT:
                                dy = 0;
                                dx = -1;
                                break;
                            case KeyEvent.VK_RIGHT:
                                dy = 0;
                                dx = 1;
                                break;
                            //по часовой стрелке
                            case KeyEvent.VK_0:
                                dx = sin(45);
                                dy = -sin(45);
                                break;
                            //против часовой стрелке
                            case KeyEvent.VK_1:
                                dx = -cos(45);
                                dy = sin(45);
                                break;
                        }
                    }
                };
                this.addKeyListener(keyListener);
                System.out.print(snake.spawnFood);
                System.out.println(snake.snakePosition[1]);
            }
            if (snake.snakeHead.getX() == SIZE || snake.snakeHead.getY() == SIZE ||
                    snake.snakeHead.getX() == 0 || snake.snakeHead.getY() == 0){
                GameOver = true;

            }

        };
        Timer timer = new Timer(20, timerListener);
        timer.start();
        imageDownload();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!GameOver){
            paintSnake(g, snake);
            if(snake.spawnFood)
                paintFood(g);
        }
        else
            paintEndGame(g);
    }

    private void paintSnake (Graphics g, SnakeMechanic snakes){
        g.setColor(Color.decode("#FFB90F"));
        int radius = snakes.RADIUS_SEGMENT;
        for (int i = 0; i < snakes.lengthSnake; i ++)
            g.fillOval((int)round(snakes.snakePosition[i].getX()), (int)round(snakes.snakePosition[i].getY()),
                2 * radius, 2* radius);
    }

    private  void paintFood(Graphics g){
        g.setColor(Color.decode("#00FF00"));
        int radius = snake.RADIUS_SEGMENT / 2;
        Point fruit = coordFruit;
        g.fillOval((int)round(fruit.getX()) , (int)round(fruit.getY()),
                2 * radius, 2* radius);
        g.drawImage(apple, (int)round(fruit.getX()) - 3, (int)round(fruit.getY() - 1),
                IMG_WIDTH, IMG_HEIGHT, null);
    }

    private  void paintStartGame(Graphics g){
        g.setFont(new Font("Bebas Neue", Font.BOLD, 50));
        g.drawString("Tap to start", SIZE/2, SIZE/2);
    }

    private  void paintEndGame(Graphics g){
        g.setFont(new Font("Bebas Neue", Font.BOLD, 50));
        g.drawString("Игра окончена ", 100, 100);
        g.drawString("Ваш счет " + snake.lengthSnake, 100, 140);
    }

    private void imageDownload(){
        try {
            apple = ImageIO.read(new File("apple.png"));
        }catch (IOException e){

        }
    }
}
