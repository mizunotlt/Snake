import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.*;


public class SnakeWindow extends JPanel {

    private final int SIZE = 600;
    private SnakeMechanic snake = new SnakeMechanic(SIZE);
    private static boolean GameOver = false;
    private double dx = 1;
    private double dy = 0;
    private Point coordFruit = new Point();
    private Image apple;
    private final int IMG_WIDTH = 25;
    private final int IMG_HEIGHT = 25;
    private double angle = 0;
    //private boolean directionFixed = false;

    public SnakeWindow(){
        setBackground(Color.decode("#A4D3EE"));
        setFocusable(true);
        // Дискретный поворот змеи
        KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        angle += PI / 12;
                        break;
                    case KeyEvent.VK_RIGHT:
                        angle -= PI / 12;
                        break;
                }
            }
            public void keyReleased (KeyEvent e){
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        dx = cos(angle );
                        dy = sin(angle );
                        break;
                    case KeyEvent.VK_RIGHT:
                        dx = cos( angle);
                        dy = sin(angle );
                        break;
                }
            }
        };
        ActionListener timerListener = e -> {
            if (!GameOver){
                snake.checkCollision(coordFruit, dx, dy);
                snake.moveSnake(dx,dy);
                if(!snake.spawnFood)
                    coordFruit = snake.spawnFruit();
                repaint();
            }
            if (snake.snakePosition.get(0).getX() >= SIZE || snake.snakePosition.get(0).getY() >= SIZE ||
                    snake.snakePosition.get(0).getX() <= 0 || snake.snakePosition.get(0).getY() <= 0){
                GameOver = true;
            }
        };
        this.addKeyListener(keyListener);
        Timer timer1 = new Timer(20, timerListener);
        timer1.start();
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
        for (Point snakeSegment : snakes.snakePosition)
            g.fillOval((int)round(snakeSegment.getX()), (int)round(snakeSegment.getY()),
                 2 * radius,  2 * radius);
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
