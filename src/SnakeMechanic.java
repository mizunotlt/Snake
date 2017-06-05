import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;

public class SnakeMechanic {

    public final int RADIUS_SEGMENT = 10;
    private final Random RANDOM = new Random();
    private  int size ;
    private final double SPEED = 2.0;
    public  boolean spawnFood = false;
    public List<Point> snakePosition = new ArrayList<>();
    public Point snakeHead = new Point();
    public int lengthSnake = 1;

    public SnakeMechanic(int size){
        this.size = size;
        this.snakeHead.setLocation(size / 2, size /2);
        snakePosition.add(snakeHead);
    }

    public void checkCollision(Point cordFruit, double dx, double dy){
        Point coordFruit = cordFruit;
        for (Point snakeSegment : snakePosition){
            if (pow(snakeSegment.getX() - coordFruit.getX(), 2)  +
                    pow(snakeSegment.getY() - coordFruit.getY(), 2)  <= RADIUS_SEGMENT){
                lengthSnake++;
                spawnFood = false;
            }
        }
    }
    public void moveSnake(double dx, double dy) {
        for (Point snakeSegment: snakePosition){
            snakeSegment.setLocation(snakeSegment.getX() + SPEED * dx,
                    snakeSegment.getY() + SPEED * dy);
        }
    }

    public Point spawnFruit(){
        double nextCordX;
        double nextCorfY;
        Point cordFruit = new Point();
        while (!spawnFood){
            nextCordX = RANDOM.nextInt(size - 10) ;
            nextCorfY= RANDOM.nextInt(size -10)  ;
            cordFruit.setLocation(nextCordX,nextCorfY);
            spawnFood = true;
        }
        return cordFruit;
    }
}
