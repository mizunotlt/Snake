import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;

public class SnakeMechanic {

    private final int RADIUS_SEGMENT = 10;
    private final Random RANDOM = new Random();
    private int size ;
    private final double SPEED = 2.0;
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
        if (pow(snakePosition.get(0).getX() - coordFruit.getX(), 2)  +
                pow(snakePosition.get(0).getY() - coordFruit.getY(), 2)  <= RADIUS_SEGMENT){
            snakePosition.add(new Point(snakePosition.get(lengthSnake - 1).getX()  -  2 * RADIUS_SEGMENT * dx,
                    snakePosition.get(lengthSnake - 1).getY() - 2 * RADIUS_SEGMENT * dy));
            lengthSnake++;
            spawnFood = false;
        }

    }

    public void moveSnake(double dx, double dy,
                          double acceleration) {
        /*
        snakePosition.get(0).setLocation(snakePosition.get(0).getX() + SPEED * dx,
                snakePosition.get(0).getY() + SPEED * dy);
        for (int i = 1 ; i <= lengthSnake - 1 ; i++){
            snakePosition.get(i).setLocation( snakePosition.get(i - 1).getX()  -  2 * RADIUS_SEGMENT * dx ,
                    snakePosition.get(i - 1).getY() - 2 * RADIUS_SEGMENT * dy  );
        }
        */
        snakePosition.get(0).setLocation(snakePosition.get(0).getX() + SPEED * acceleration * dx,
                snakePosition.get(0).getY() + SPEED * acceleration * dy);
        for (int i = 1 ; i <= lengthSnake - 1 ; i++){
            double prevX  = snakePosition.get(i).getX();
            double prevY = snakePosition.get(i).getY();
            snakePosition.get(i).setLocation( snakePosition.get(i - 1).getX(),
                    snakePosition.get(i - 1).getY() );
            //коррекция по направлениям
            if ((prevX != snakePosition.get(i).getX()) && (prevY != snakePosition.get(i).getY())){
                snakePosition.get(i).setLocation( snakePosition.get(i).getX()  -  2 * RADIUS_SEGMENT * dx ,
                        snakePosition.get(i).getY() - 2 * RADIUS_SEGMENT * dy  );
            }
            if ((prevX == snakePosition.get(i).getX()) && (prevY < snakePosition.get(i).getY())){
                snakePosition.get(i).setLocation( snakePosition.get(i).getX(),
                        snakePosition.get(i).getY() + 2 * RADIUS_SEGMENT * dy  );
            }
            if ((prevX == snakePosition.get(i).getX()) && (prevY > snakePosition.get(i).getY())){
                snakePosition.get(i).setLocation( snakePosition.get(i).getX(),
                        snakePosition.get(i).getY() - 2 * RADIUS_SEGMENT * dy  );
            }
            if ((prevX < snakePosition.get(i).getX()) && (prevY == snakePosition.get(i).getY())){
                snakePosition.get(i).setLocation( snakePosition.get(i).getX()  +  2 * RADIUS_SEGMENT * dx ,
                        snakePosition.get(i).getY());
            }
            if ((prevX > snakePosition.get(i).getX()) && (prevY == snakePosition.get(i).getY())){
                snakePosition.get(i).setLocation( snakePosition.get(i).getX()  -  2 * RADIUS_SEGMENT * dx ,
                        snakePosition.get(i ).getY());
            }

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
