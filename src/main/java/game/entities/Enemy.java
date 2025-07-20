package game.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {
    //Atributos personaje
    protected Player targetPlayer;
    //Sprites y animacion
    protected int freezeFrames = 0;

    protected int animationIndex = 0;
    protected int animationCounter = 0;
    protected int animationSpeed = 80;

    protected boolean facingRight = false;
    protected BufferedImage[] walkRightSprites;
    protected BufferedImage[] walkLeftSprites;

    //Constructor
    public Enemy(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, health, damage, height, width, y);
        this.targetPlayer = targetPlayer;
    }

    //Update por frames
    protected void updateEntity() {
        if (!isAlive() || targetPlayer == null) return;
        if (freezeFrames > 0) {
            freezeFrames--;
            return; // Saltamos la lógica de movimiento este frame
        }
        //Persecucion del jugador
        if (x < targetPlayer.x) {
            x += speed;
            facingRight = true;
        } else if (x > targetPlayer.x) {
            x -= speed;
            facingRight = false;
        }
        //Animacion
        animationCounter++;
        if (animationCounter >= animationSpeed) {
            animationIndex = (animationIndex + 1) % walkRightSprites.length;
            animationCounter = 0;
        }
        //Cooldown damage
        if (isDamaged) {
            damageCooldown--;
            if (damageCooldown <= 0) {
                isDamaged = false;
            }
        }

    }
    @Override
    public void takeDamage(int amount) {
        if (hasBeenHitThisAttack || isDamaged) return;

        health -= amount;
        if (health < 0) health = 0;

        isDamaged = true;
        damageCooldown = damageDelay;
        hasBeenHitThisAttack = true;

        // Activar parpadeo blanco
        flashing = true;
        flashTimer = FLASH_DURATION;
        freezeFrames = 150;

    }
    //Sprites y dibujo
    @Override
    public void draw(Graphics g) {
        // Debug del colisionador
        //g.setColor(Color.BLUE);
        //Rectangle bounds = getBounds();
        //g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        drawSprite(g, walkRightSprites, walkLeftSprites);
    }
    protected void drawSprite(Graphics g, BufferedImage[] spritesRight, BufferedImage[] spritesLeft) {
        BufferedImage baseFrame = facingRight
                ? spritesRight[animationIndex]
                : spritesLeft[animationIndex];

        BufferedImage currentFrame = flashing
                ? makeWhiteImage(baseFrame)
                : baseFrame;

        g.drawImage(currentFrame, (int) x, (int) y, (int) width, (int) height, null);
    }
    //Colliders y otros
    @Override
    public Rectangle getAttackBounds() {
        return getBounds();
    }
    public boolean shouldBeRemoved() {
        return !isAlive(); //comportamiento por defecto para enemigos simples
    }
}
