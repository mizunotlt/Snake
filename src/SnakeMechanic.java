import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SnakeMechanic {

    public final int RADIUS_SEGMENT = 10;
    private final Random RANDOM = new Random();
    private int size ;
    private double speed = 2.0;
    private boolean spawnFood = false;
    private double angle = 0.0;
    public  final double dangle = PI / 12;
    private List<Point> snakePosition = new ArrayList<>();
    private Point snakeHead = new Point();
    private int lengthSnake = 1;
    private final double acceleration = 1.5;
    static boolean checkAccel = false;

    public SnakeMechanic(int size){
        this.size = size;
        this.snakeHead.setLocation(size / 2, size /2);
        snakePosition.add(snakeHead);
    }

    public double getAngle(){
        return angle;
    }
    public boolean getSpawnFood(){
        return spawnFood;
    }
    public int getLengthSnake(){
        return lengthSnake;
    }
    public List<Point> getSnakePosition(){
        return snakePosition;
    }

    public double incAngle(){
        return angle += dangle;
    }
    public double decAngle(){
        return  angle -= dangle;
    }

    public void checkCollision(Point cordFruit, double dx, double dy){
        Point coordFruit = cordFruit;
        if (sqrt(pow(snakePosition.get(0).getX() - coordFruit.getX(), 2)  +
                pow(snakePosition.get(0).getY() - coordFruit.getY(), 2))  < RADIUS_SEGMENT){
            snakePosition.add(coordFruit);
            lengthSnake++;
            spawnFood = false;
        }

    }

    public void moveSnake(double dx, double dy) {
        if (lengthSnake % 5 == 0){
            speed += 0.01;
        }
        if (checkAccel)
            snakeHead.setLocation(snakeHead.getX() + speed * acceleration * dx,
                snakeHead.getY() + speed * acceleration * dy);
        else
            snakeHead.setLocation(snakeHead.getX() + speed * dx,
                    snakeHead.getY() + speed * dy);
        for (int i = lengthSnake - 1 ; i > 0 ; i--){
            snakePosition.get(i).setLocation( snakePosition.get(i - 1).getX() - dx * RADIUS_SEGMENT,
                    snakePosition.get(i - 1).getY() - RADIUS_SEGMENT* dy);
        }
        snakePosition.set(0, snakeHead);
    }

    public Point spawnFruit(){
        double nextCordX;
        double nextCorfY;
        Point cordFruit = new Point();
        while (!spawnFood){
            nextCordX = RANDOM.nextInt(size - 3 * RADIUS_SEGMENT) + 2 * RADIUS_SEGMENT;
            nextCorfY= RANDOM.nextInt( size - 3 * RADIUS_SEGMENT) + 2 * RADIUS_SEGMENT;
            cordFruit.setLocation(nextCordX,nextCorfY);
            spawnFood = true;
        }
        return cordFruit;
    }
}
