package game.core;

import java.util.List;
import java.util.ArrayList;

import game.entities.EnemyA;
import game.graphics.SpriteLoader;
import game.entities.Player;
import game.input.KeyHandler;
import game.logic.WaveManager;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    //Atributos
    private Font titleFont;
    private Font menuFont;

    private Player player;
    private WaveManager waveManager;
    private KeyHandler keyHandler;

    private boolean running;
    private int currentState;
    private Thread gameThread;
    private BufferedImage arenaBackground;

    public static final int menu_state = 0;
    public static final int playing_state = 1;
    public static final int gameover_state = 2;

    private List<EnemyA> enemies = new ArrayList<>();


   //Constructor
    public GamePanel() {
        setPreferredSize(new Dimension(1200,700));
        setBackground(Color.BLACK);
        setFocusable(true);

        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);

        titleFont = SpriteLoader.loadFont("/fonts/BodoniModaSC.ttf", 80f);
        menuFont = titleFont.deriveFont(40f);

        arenaBackground = SpriteLoader.loadImage("/background/arena.png");
        player = new Player(
                100, // x
                0.5,   // speed
                100, // maxHealth
                100, // health
                10,  // damage
                64,  // height
                64,  // width
                565, // y (donde flota por ahora)
                100, // mana
                100, // maxMana
                keyHandler
        );

        waveManager = new WaveManager(player);


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

        if (currentState == menu_state && keyHandler.enter) {
            System.out.println("ENTER puchado");
            currentState = playing_state;
            keyHandler.enter = false;

        } else if (currentState == gameover_state && keyHandler.enter) {
            System.out.println("puchaste enter");
            currentState = menu_state;
            keyHandler.enter = false;

        } else if (currentState == playing_state) {
            player.update();
            waveManager.update();
        }

        for (EnemyA enemy : waveManager.getEnemies()) {
            // Jugador golpea enemigo
            if (player.isAttacking()
                    && player.getAttackBounds().intersects(enemy.getBounds())
                    && !enemy.isDamaged()) {

                enemy.takeDamage(player.getDamage());
                System.out.println("Jugador golpeó al enemigo. Vida enemiga: " + enemy.getHealth());
            }

            // Enemigo golpea jugador
            if (enemy.getBounds().intersects(player.getBounds())
                    && !player.isDamaged()) {

                player.takeDamage(enemy.getDamage());
                System.out.println("Jugador recibió daño. Vida: " + player.getHealth());
            }
        }

        // Puedes poner aquí el cambio a estado de game over, por ejemplo:
        if (player.getHealth() <= 0) {
            currentState = gameover_state;
        }

    }

    public void startGame(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Metodos de dibujo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraphics(g);
    }

    public void drawGraphics(Graphics g){
        if (currentState == menu_state){
            drawMenu(g);
        } else if (currentState == playing_state){
            drawPlaying(g);
        } else if (currentState == gameover_state){
            drawGameOver(g);

        }
    }


    private void drawMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(titleFont);
        g.drawString("Lamest Zote's", 350, 330);
        g.drawString("Tournament", 370, 410);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("presiona ENTER para comenzar", 460, 450);
    }
    private void drawPlaying(Graphics g) {
        g.drawImage(arenaBackground, 0, 0, getWidth(), getHeight(), null);
        player.draw(g);
        waveManager.draw(g);

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
