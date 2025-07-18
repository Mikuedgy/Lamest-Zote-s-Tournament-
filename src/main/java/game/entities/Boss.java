package game.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Boss extends Enemy {
    //Atributos
    //Sprites y animacion
    protected boolean fullyDead = false;
    protected int deathAnimationIndex = 0;
    protected int deathAnimationCounter = 0;
    protected final int deathAnimationSpeed = 50;
    protected boolean playingDeathAnimation = false;

    protected BufferedImage[] exploteSprites;
    //Constructor
    public Boss(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, health, damage, height, width, y, targetPlayer);
    }
    //Update por frame
    @Override
    public void updateEntity() {
        if (fullyDead) return;

        if (!isAlive()) {
            handleDeathAnimation();
            return;
        }

        super.updateEntity(); // Movimiento, animaciones normales, etc.
    }
    //Animaciones y dibujado
    @Override
    public void draw(Graphics g) {
        if (fullyDead) return;
        // Animación de muerte
        if (playingDeathAnimation && deathAnimationIndex < exploteSprites.length) {
            g.drawImage(exploteSprites[deathAnimationIndex], (int) x, (int) y, (int) width, (int) height, null);
            return;
        }
        super.draw(g);
    }
    protected void handleDeathAnimation() {
        if (!playingDeathAnimation) {
            playingDeathAnimation = true;
            deathAnimationIndex = 0;
            deathAnimationCounter = 0;
        } else {
            deathAnimationCounter++;
            if (deathAnimationCounter >= deathAnimationSpeed) {
                deathAnimationCounter = 0;
                deathAnimationIndex++;

                if (deathAnimationIndex >= exploteSprites.length) {
                    fullyDead = true;
                }
            }
        }
    }
    //Banderas
    @Override
    public boolean shouldBeRemoved() {
        return fullyDead;
    }
}
