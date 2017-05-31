import java.util.Random;

import static java.lang.StrictMath.sqrt;

public class SnakeMechanic {

    public final int RADIUS_SEGMENT = 11;
    private final Random RANDOM = new Random();
    private final int SIZE = 600;
    private final double SPEED = 2.0;
    public  boolean spawnFood = false;
    public  Point[] snakePosition = new Point[SIZE * SIZE];
    public  Point snakeHead = new Point();
    public int lengthSnake = 1;

    //проверка на еду и на поедание самой себя
    public SnakeMechanic(int size, Point[] snakePosition){
        this.snakeHead.setLocation(size / 2, size /2);
        this.snakePosition = snakePosition;
        this.snakePosition[0] = snakeHead;
        for (int i = 1; i < size * size; i++)
            this.snakePosition[i] = new Point();

    }
    public void checkCollision(Point cordFruit){
        Point coordFruit = cordFruit;
        if(sqrt((coordFruit.getX() - snakePosition[0].getX())*(coordFruit.getX() - snakePosition[0].getX()) +
                (coordFruit.getY() - snakePosition[0].getY())*(coordFruit.getY() - snakePosition[0].getY()))<RADIUS_SEGMENT){
            lengthSnake++;
            snakePosition[lengthSnake - 1].setLocation(snakePosition[lengthSnake - 2].getX() - 1,
                    snakePosition[lengthSnake - 2].getY());
            spawnFood = false;
        }
    }
    /*зависит от нажатой клавиши клавиатуры;
    Если нажата кнопка 'q' то голова змеи и каждый отдельный сегмент поворачивает на угол ANGLE
    и смещается по x и y на величину speed*cos(Angle) и speed*sin(Angle)
    */
    public void moveSnake(double dx, double dy){
        //double x = snakeHead.getX();
        //double y = snakeHead.getY();
        //snakeHead.setLocation(x+SPEED * dx, y + SPEED* dy);
        for(int i = 0; i < lengthSnake; i++){
            snakePosition[i].setLocation(snakePosition[i].getX()+SPEED * dx, snakePosition[i].getY() + SPEED* dy);
        }

    }

    public Point spawnFruit(){
        double nextCordX;
        double nextCorfY;
        Point cordFruit = new Point();
        while (!spawnFood){
            nextCordX = RANDOM.nextInt(SIZE + 1) + 20;
            nextCorfY= RANDOM.nextInt(SIZE + 1) + 20 ;
            cordFruit.setLocation(nextCordX,nextCorfY);
            spawnFood = true;
            /*
            for (int i = 0; i < lengthSnake; i++){
                if (!cordFruit.equals(snakePosition[i])){
                    spawnFood = true;
                }
            }
            */
        }
        return cordFruit;
    }

}
