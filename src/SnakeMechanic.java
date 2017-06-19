import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SnakeMechanic {

    private final int RADIUS_SEGMENT = 10;
    private final Random RANDOM = new Random();
    private int size ;
    private boolean spawnFood = false;
    private List<Point> snakePosition = new ArrayList<>();
    private Point snakeHead = new Point();
    private int lengthSnake = 1;

    public SnakeMechanic(int size){
        this.size = size;
        this.snakeHead.setLocation(size / 2, size /2);
        snakePosition.add(snakeHead);
    }

    public int getRADIUS_SEGMENT(){
        return  RADIUS_SEGMENT;
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

    public void checkCollision(Point cordFruit, double dx, double dy){
        Point coordFruit = cordFruit;
        if (sqrt(pow(snakePosition.get(0).getX() - coordFruit.getX(), 2)  +
                pow(snakePosition.get(0).getY() - coordFruit.getY(), 2))  < RADIUS_SEGMENT){
            snakePosition.add(coordFruit);
            lengthSnake++;
            spawnFood = false;
        }

    }

    public void moveSnake(double speed, double dx, double dy,
                          double acceleration) {

        snakeHead.setLocation(snakeHead.getX() + speed * acceleration * dx,
                snakeHead.getY() + speed * acceleration * dy);
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
