package game.entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import game.graphics.SpriteLoader;

public  class EnemyA extends Enemy{
    //Atributos
    private BufferedImage[] walkRightSprites;
    private BufferedImage[] walkLeftSprites;
    private int animationIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 80;

    private boolean facingRight = false;

    private Player targetPlayer;

    //daño
    private boolean isDamaged = false;
    private int damageCooldown = 0;
    private final int damageDelay = 30;

    //Constructor
    public EnemyA(double x, double speed, int maxHealth, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, maxHealth, damage, height, width, y);
        this.targetPlayer = targetPlayer;
        loadSprites();
    }

    private void loadSprites() {
        walkRightSprites = new BufferedImage[4];
        walkLeftSprites = new BufferedImage[4];

        // Caminata derecha
        walkRightSprites[0] = SpriteLoader.loadImage("/enemyA/walk0.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/enemyA/walk1.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/enemyA/walk2.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/enemyA/walk3.png");

        // Caminata izquierda (puedes usar imágenes separadas o reflejadas)
        walkLeftSprites[0] = SpriteLoader.loadImage("/enemyA/walk0l.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/enemyA/walk1l.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/enemyA/walk2l.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/enemyA/walk3l.png");
    }
    @Override
    public boolean isAlive() {
        return health > 0;
    }
    @Override
    public void update() {
        if (!isAlive() || targetPlayer == null) return;

        // Movimiento hacia el jugador
        if (x < targetPlayer.x) {
            x += speed;
            facingRight = true;
        } else if (x > targetPlayer.x) {
            x -= speed;
            facingRight = false;
        }

        // Animación
        animationCounter++;
        if (animationCounter >= animationSpeed) {
            animationIndex = (animationIndex + 1) % walkRightSprites.length;
            animationCounter = 0;
        }

        if (getBounds().intersects(targetPlayer.getBounds())) {
            targetPlayer.takeDamage(damage);
        }

        if (isDamaged) {
            damageCooldown--;
            if (damageCooldown <= 0) {
                isDamaged = false;
            }
        }
    }


    @Override
    public void takeDamage(int amount) {
        if (isDamaged) return;

        health -= amount;
        if (health < 0) health = 0;

        isDamaged = true;
        damageCooldown = damageDelay;
    }



    @Override
    public void draw(Graphics g) {
        if (!isAlive()) return;

        BufferedImage currentFrame = facingRight
                ? walkRightSprites[animationIndex]
                : walkLeftSprites[animationIndex];

        g.drawImage(currentFrame, (int) x, (int) y, (int) width, (int) height, null);
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }
    @Override
    public Rectangle getAttackBounds() {
        return getBounds(); // usa su propio cuerpo como ataque
    }
    public boolean isAttacking() {
        return true; // siempre que toca al jugador
    }

    @Override
    public void attack(){

    }
    public boolean isDamaged() {
        return isDamaged;
    }

}
