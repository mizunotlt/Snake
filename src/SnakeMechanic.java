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
        if (pow(snakePosition.get(0).getX() - coordFruit.getX(), 2)  +
                pow(snakePosition.get(0).getY() - coordFruit.getY(), 2)  <= RADIUS_SEGMENT){
            snakePosition.add(new Point(snakePosition.get(lengthSnake - 1).getX()  -  2 * RADIUS_SEGMENT * dx,
                    snakePosition.get(lengthSnake - 1).getY() - 2 * RADIUS_SEGMENT * dy));
            lengthSnake++;
            spawnFood = false;
        }

    }
    public void moveSnake(double dx, double dy) {
        snakePosition.get(0).setLocation(snakePosition.get(0).getX() + SPEED * dx,
                snakePosition.get(0).getY() + SPEED * dy);
        for (int i = 1 ; i <= lengthSnake - 1 ; i++){
            snakePosition.get(i).setLocation( snakePosition.get(i - 1).getX()  -  2 * RADIUS_SEGMENT * dx,
                    snakePosition.get(i - 1).getY() - 2 * RADIUS_SEGMENT * dy);
        }
    }

    public Point spawnFruit(){
        double nextCordX;
        double nextCorfY;
        Point cordFruit = new Point();
        while (!spawnFood){
            nextCordX = RANDOM.nextInt(size - 10) + 1 ;
            nextCorfY= RANDOM.nextInt(size -10) + 1  ;
            cordFruit.setLocation(nextCordX,nextCorfY);
            spawnFood = true;
        }
        return cordFruit;
    }
}
