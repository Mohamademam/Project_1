package game;
import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){
        ImageIcon icon = new ImageIcon("logo.png");
        this.add(new GamePanel());
        this.setIconImage(icon.getImage());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);

    }
}
