package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int ScreenWidth = 600;
    static final int ScreenHeight = 600;
    static final int UnitSize = 25;
    static final int GameUnit = (ScreenWidth * ScreenHeight) / UnitSize;
    static final int Delay = 75;
    final int x[] = new int[GameUnit];
    final int y[] = new int[GameUnit];
    int bodyPart = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
        this.setBackground(new Color(20,20,40));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(Delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
       if(running){
           for(int i=0; i<ScreenHeight/UnitSize;i++){
               g.drawLine(i*UnitSize,0,i*UnitSize,ScreenHeight);
               g.drawLine(0,i*UnitSize,ScreenWidth,i*UnitSize);
           }
           g.setColor(new Color(195,52,41));
           g.fillOval(appleX,appleY,UnitSize,UnitSize);
           for(int i=0; i< bodyPart;i++){
               if(i==0){
                   g.setColor(Color.green);
                   g.fillRect(x[i],y[i],UnitSize,UnitSize);
               }
               else{
                   g.setColor(new Color(122,178,152));
                   g.fillRect(x[i],y[i],UnitSize,UnitSize);
               }
               g.setColor(new Color(122,178,152));
               g.setFont(new Font("Ink Free",Font.BOLD,40));

               FontMetrics metrics = getFontMetrics(g.getFont());
               g.drawString("Score: " + applesEaten ,(metrics.stringWidth("Score:" + applesEaten))/2,g.getFont().getSize());
           }
       }
       else{
           gameOver(g);
       }
    }
    public void move(){
        for(int i=bodyPart;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UnitSize;
                break;
            case 'D':
                y[0] = y[0] + UnitSize;
                break;
            case 'L':
                x[0] = x[0] - UnitSize;
                break;
            case 'R':
                x[0] = x[0] + UnitSize;
                break;
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(ScreenWidth/UnitSize))*UnitSize;
        appleY = random.nextInt((int)(ScreenHeight/UnitSize))*UnitSize;
    }
    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            bodyPart++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollisions(){
        //check if head collides with body
        for(int i=bodyPart;i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
            //check if head touches left border
            if (x[0] < 0) {
                running = true;
            }
            //check if head touches right border
            if (x[0] > ScreenWidth)
                running = false;
            //check if head touches top border
            if (y[0] < 0)
                running = false;
            //check if head touches bottom border
            if (y[0] > ScreenHeight)
                running = false;

            if (!running)
                timer.stop();
        }


    }
    JButton button;
    public void gameOver(Graphics g){
        //Game over Text
        g.setColor(new Color(122,178,152));
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(ScreenWidth - metrics.stringWidth("Game Over"))/2,(ScreenHeight/3));
        g.drawString("Score:" + applesEaten,(ScreenWidth - metrics.stringWidth("Score" + applesEaten))/2,ScreenHeight/2);



        button = new JButton("TRY AGAIN");
        button.setBounds(200,350,200,70);
        button.setFocusable(false);
        button.addActionListener(this);
        button.setBackground(new Color(220,230,230));
        button.setFont(new Font("Ink Free",Font.BOLD,25));
        button.setForeground(Color.black);
        this.add(button);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
        if(e.getSource() == button)
        {
            new GameFrame();
        }
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }

}
