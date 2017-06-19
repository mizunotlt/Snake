import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static java.lang.Math.*;


public class SnakeWindow extends JPanel {

    private final int SIZE = 600;
    private SnakeMechanic snake = new SnakeMechanic(SIZE);
    private static boolean GameOver = false;
    private static boolean GameStart = false;
    private double dx = 1;
    private double dy = 0;
    private Point coordFruit = new Point();
    private Image apple;
    private final int IMG_WIDTH = 25;
    private final int IMG_HEIGHT = 25;
    private int lengthSnake;
    private double acceleration = 1.0;

    public  int getSIZE(){
        return SIZE;
    }
    public SnakeWindow(){
        setBackground(Color.decode("#A4D3EE"));
        setFocusable(true);
        // Дискретный поворот змеи
        KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        snake.incAngle();
                        break;
                    case KeyEvent.VK_RIGHT:
                        snake.decAngle();
                        break;
                    case KeyEvent.VK_ENTER:
                        if (!GameStart)
                            GameStart = true;
                        break;
                    case KeyEvent.VK_UP:
                        acceleration = 1.5;
                        break;
                }
            }
            public void keyReleased (KeyEvent e){
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        dx = cos(snake.getAngle());
                        dy = sin(snake.getAngle());
                        break;
                    case KeyEvent.VK_RIGHT:
                        dx = cos(snake.getAngle());
                        dy = sin(snake.getAngle() );
                        break;
                    case KeyEvent.VK_UP:
                        acceleration = 1.0;
                        break;
                }
            }
        };
        ActionListener timerListener = e -> {
            lengthSnake = snake.getLengthSnake();
            List<Point> snakePosition = snake.getSnakePosition();


            if (!GameOver){
                snake.checkCollision(coordFruit, dx, dy);
                snake.moveSnake(dx,dy,acceleration);
                if(!snake.getSpawnFood())
                    coordFruit = snake.spawnFruit();
                repaint();
            }
            if ((snakePosition.get(0).getX() >= SIZE || snakePosition.get(0).getY() >= SIZE ||
                    snakePosition.get(0).getX() <= 0 || snakePosition.get(0).getY() <= 0)){
                GameOver = true;
            }
        };
        this.addKeyListener(keyListener);
        Timer timer1 = new Timer(35, timerListener);
        timer1.start();
        imageDownload();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!GameStart){
            paintStartGame(g);
        }
        else
            if (!GameOver) {
                paintSnake(g, snake);
                if(snake.getSpawnFood())
                    paintFood(g);
            }
            else
                paintEndGame(g);
    }

    private void paintSnake (Graphics g, SnakeMechanic snakes){
        g.setColor(Color.decode("#FFB90F"));
        int radius = snakes.RADIUS_SEGMENT;
        List<Point> snakePosition = snakes.getSnakePosition();
        g.fillOval((int)round(snakePosition.get(0).getX()), (int)round(snakePosition.get(0).getY()),
                2 * radius,  2 * radius);
        g.setColor((Color.GREEN));
        for ( int i = 1; i < snakes.getLengthSnake(); i ++){
            g.fillOval((int)round(snakePosition.get(i).getX()), (int)round(snakePosition.get(i).getY()),
                    2* radius,   2 * radius);
        }
    }

    private  void paintFood(Graphics g){
        g.setColor(Color.decode("#00FF00"));
        int radius = snake.RADIUS_SEGMENT;
        Point fruit = coordFruit;
        g.fillOval((int)round(fruit.getX()) , (int)round(fruit.getY()),
                2 * radius, 2* radius);
        g.drawImage(apple, (int)round(fruit.getX()) - 3, (int)round(fruit.getY() - 1),
                IMG_WIDTH, IMG_HEIGHT, null);
    }

    private  void paintStartGame(Graphics g){
        g.setFont(new Font("Bebas Neue", Font.BOLD, 50));
        g.drawString("Tap to start", 100, 100);
    }

    private  void paintEndGame(Graphics g){
        g.setFont(new Font("Bebas Neue", Font.BOLD, 50));
        g.drawString("Игра окончена ", 100, 100);
        g.drawString("Ваш счет " + snake.getLengthSnake(), 100, 140);
    }

    private void imageDownload(){
        try {
            apple = ImageIO.read(new File("apple.png"));
        }catch (IOException e){

        }
    }
}
