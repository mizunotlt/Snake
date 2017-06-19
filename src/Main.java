import javax.swing.*;

public class Main extends JFrame{

    private static SnakeWindow snakeWindow;
    private final int Window_with = snakeWindow.getSIZE();
    private final int Window_hight = snakeWindow.getSIZE();

    private Main(String s){
        super(s);
        setSize(Window_with, Window_hight);
        snakeWindow = new  SnakeWindow();
        add(snakeWindow);
        this.setContentPane(snakeWindow);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main("Snake"));
    }
}
