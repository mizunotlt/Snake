import java.util.Objects;


class Point  {
    private double x;
    private double y;

    public Point(){
        this.x = 0.0;
        this.y = 0.0;

    }
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }


    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        Point point = (Point) o;
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        return ((Objects.equals(this.x, point.x)) && (Objects.equals(this.y, point.y)));
    }

    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y +")";
    }
}
