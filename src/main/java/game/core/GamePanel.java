package game.core;

import game.entities.Enemy;
import game.entities.Player;
import game.input.KeyHandler;
import game.logic.WaveManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Atributos
    private Player player;
    private WaveManager waveManager;
    private KeyHandler keyHandler;

    private boolean running;
    private int currentState;
    private Thread gameThread;

    public static final int menu_state = 0;
    public static final int playing_state = 1;
    public static final int gameover_state = 2;


   //Constructor
    public GamePanel() {
        setPreferredSize(new Dimension(1024,900));
        setBackground(Color.BLACK);
        setFocusable(true);

        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
    }

    public void run(){
        final int FPS = 60;
        final double drawInterval = 100000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

    }
    public void update(){


    }
    public void startGame(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Metodos de dibujo
    public void drawGraphics(Graphics g){
        if (currentState == menu_state){
            drawMenu(g);
        } else if (currentState == playing_state){

        } else if (currentState == gameover_state){
            drawGameOver(g);

        }
    }

    private void drawMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Zote Tournament", 100, 100);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Presiona ENTER para comenzar", 100, 200);
    }
    private void drawGameOver(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", 280, 300);
    }
    //Getters
    public WaveManager getWaveManager() {
        return waveManager;
    }

    public Player getPlayer() {
        return player;
    }
}
