package game.core;

import java.awt.*;

import java.util.List;
import javax.swing.JPanel;

import game.entities.Enemy;
import game.entities.Player;
import game.input.KeyHandler;
import game.logic.WaveManager;

import game.graphics.SpriteLoader;
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

    private final int menu_state = 0;
    private final int playing_state = 1;
    private final int gameover_state = 2;
    private final int victory_state = 3;

   //Constructor
    public GamePanel() {
        //Ventana
        setPreferredSize(new Dimension(1200,700));
        setBackground(Color.BLACK);
        setFocusable(true);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        //Fuentes
        titleFont = SpriteLoader.loadFont("/fonts/BodoniModaSC.ttf", 80f);
        menuFont = titleFont.deriveFont(40f);
        //Fondos
        arenaBackground = SpriteLoader.loadImage("/background/arena.png");
        //Jugador y enemigos
        player = new Player(600, 0.5, 100, 10, 1, 64, 64, 565, keyHandler);
        waveManager = new WaveManager(player);
    }
    //Update por frame
    public void update(){
        //ESTADOS DE JUEGO
        if (currentState == menu_state && keyHandler.enter) {
            currentState = playing_state;
            keyHandler.enter = false;
            player.reset();
            waveManager.reset();

        }
        else if (currentState == gameover_state && keyHandler.enter) {
            currentState = menu_state;
            keyHandler.enter = false;
        }
        else if (currentState == playing_state) {
            player.update();
            waveManager.update();

            if (player.getHealth() <= 0) {
                currentState = gameover_state;
            } else if (waveManager.getAllWavesCompleted()) {
                currentState = victory_state;  // <- nuevo estado
            }
        }
        else if (currentState == victory_state && keyHandler.enter) {
        currentState = menu_state;
        keyHandler.enter = false;
        player.reset();
        waveManager.reset();
        }
        //UPDATE DE COLISIONES EN EL JUEGO
        List<Enemy> enemies = waveManager.getEnemies();
        enemies.removeIf(Enemy::shouldBeRemoved);

        for (Enemy enemy : enemies) {
            //Golpea al enemigo solo una vez por ataque
            if (player.isAttacking() &&
                    player.getAttackBounds().intersects(enemy.getBounds()) &&
                    !enemy.hasBeenHitThisAttack()) {

                enemy.takeDamage(player.getDamage());
                System.out.println("GOLPE AL NENE");
            }
            //El enemigo daña al jugador solo si está vivo y colisiona
            if (enemy.getBounds().intersects(player.getBounds())) {
                if (!enemy.hasDamagedPlayerThisContact()) {
                    player.takeDamage(enemy.getDamage());
                    enemy.setHasDamagedPlayerThisContact(true); // <--- MUY IMPORTANTE

                    System.out.println("AUCH COLISIÓN detectada con enemigo. Vida jugador: " + player.getHealth());
                }
            } else {
                // Cuando ya no colisiona, resetea el flag
                enemy.setHasDamagedPlayerThisContact(false);
            }

        }
        //  Reiniciar el flag cuando el ataque del jugador termina
        if (!player.isAttacking()) {
            for (Enemy enemy : enemies) {
                enemy.resetHitStatus();
            }
        }
        player.resetHitStatus();
    }
    //Metodos de ejecucion e inicio
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
            drawPlaying(g);
        } else if (currentState == gameover_state){
            drawGameOver(g);
        } else if (currentState == victory_state){
            drawVictory(g);
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
        g.drawString("presiona ENTER para comenzar", 460, 470);
    }
    private void drawPlaying(Graphics g) {
        g.drawImage(arenaBackground, 0, 0, getWidth(), getHeight(), null);
        player.draw(g);
        waveManager.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(titleFont.deriveFont(24f));
        g.drawString("Health:  " + player.getHealth(), 20, 40);

        g.setColor(Color.WHITE);
        g.setFont(titleFont.deriveFont(24f));
        g.drawString("Wave:  " +waveManager.getCurrentWave(), 190, 40);

    }
    private void drawGameOver(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED);
        g.setFont(titleFont.deriveFont(100f));
        g.drawString("GAME OVER", 310, 400);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("presiona ENTER para volver al menú", 440, 450);
    }
    private void drawVictory(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.YELLOW);
        g.setFont(titleFont.deriveFont(120f));
        g.drawString("¡VICTORIA!", 250, 400);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Presiona ENTER para volver al menú", 440, 450);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraphics(g);

    }
    //Getters
    public WaveManager getWaveManager() {
        return waveManager;
    }

    public Player getPlayer() {
        return player;
    }
}
