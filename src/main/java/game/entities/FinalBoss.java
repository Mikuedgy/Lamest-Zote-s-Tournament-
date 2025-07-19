package game.entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import game.graphics.SpriteLoader;

public class FinalBoss extends Boss {
    //Atributos
    //Sprite y animacion
    private BufferedImage[] attackLeftSprites;
    private BufferedImage[] attackRightSprites;

    private boolean attacking = false;
    private boolean attackInProgress = false;
    private int attackAnimationIndex = 0;
    private int attackAnimationCounter = 0;
    private final int attackAnimationSpeed = 70;
    //Constructor
    public FinalBoss(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, health, damage, height, width, y, targetPlayer);
        loadSprites();
    }
    //Update por frames
    @Override
    public void updateEntity() {
        // Si está atacando, solo reproducir animación y aplicar daño
        if (attackInProgress) {
            attackAnimationCounter++;
            if (attackAnimationCounter >= attackAnimationSpeed) {
                attackAnimationCounter = 0;
                attackAnimationIndex++;

                if (attackAnimationIndex == 4) {
                    Rectangle hitbox = getAttackBounds(); // hitbox real del golpe
                    if (hitbox.intersects(targetPlayer.getBounds())) {
                        targetPlayer.takeDamage(damage);
                    }
                }
                //Fin de ataque
                if (attackAnimationIndex >= attackRightSprites.length) {
                    attacking = false;
                    attackInProgress = false;
                    attackAnimationIndex = 0;
                }
            }
            return; //NO movimiento durante ataque
        }
        //Si el jugador está en zona de detección, inicia el ataque
        if (getAttackDetectionZone().intersects(targetPlayer.getBounds())) {
            attackInProgress = true;
            attacking = true;
            attackAnimationCounter = 0;
            attackAnimationIndex = 0;
            return;
        }
        // Solo si no está atacando, se mueve normalmente
        attacking = false;
        super.updateEntity();
    }
    //Colliders
    public Rectangle getAttackDetectionZone() {
        int rangeWidth = (int) (width * 0.8);
        int rangeHeight = (int) (height * 0.9);
        int offsetY = (int) (height * 0.05);//Ajustes vertical

        int offsetX;
        if (facingRight) {
            offsetX = (int) (width * 0.3); //Frente a la derecha
        } else {
            offsetX = -(int) (rangeWidth - width * 0.6); //Frente a la izquierda
        }

        return new Rectangle((int) x + offsetX, (int) y + offsetY, rangeWidth, rangeHeight);
    }

    @Override
    public Rectangle getAttackBounds() {
        if (!attacking || !isAlive()) return new Rectangle(0, 0, 0, 0);

        int attackWidth = (int) (width * 0.8);
        int attackHeight = (int) (height * 0.9);
        int offsetY = (int) (height * 0.1); //Vertical

        int offsetX;
        if (facingRight) {
            offsetX = (int) (width * 0.4);//Frente a la derecha
        } else {
            offsetX = -(int) (attackWidth - width * 0.6);//Frente a la izquierda
        }

        return new Rectangle((int) x + offsetX, (int) y + offsetY, attackWidth, attackHeight);
    }
    @Override
    public Rectangle getBounds() {
        if (!isAlive() || playingDeathAnimation || fullyDead) {
            return new Rectangle(0, 0, 0, 0);
        }
        int colliderWidth = (int) (width * 0.9);
        int colliderHeight = (int) (height * 0.8);
        int offsetX = (int) ((width - colliderWidth) / 2);
        int offsetY = (int) ((height - colliderHeight) / 2);

        return new Rectangle((int) x + offsetX, (int) y + offsetY, colliderWidth, colliderHeight);
    }

    //Sprites y dibujado
    @Override
    public void draw(Graphics g) {

        if (fullyDead) {
            BufferedImage deathFrame = facingRight
                    ? deathRightSprites[Math.min(deathAnimationIndex, deathRightSprites.length - 1)]
                    : deathLeftSprites[Math.min(deathAnimationIndex, deathLeftSprites.length - 1)];
            g.drawImage(deathFrame, (int) x, (int) y, (int) width, (int) height, null);
            return;
        }
        if (playingDeathAnimation) {
            BufferedImage deathFrame = facingRight
                    ? deathRightSprites[Math.min(deathAnimationIndex, deathRightSprites.length - 1)]
                    : deathLeftSprites[Math.min(deathAnimationIndex, deathLeftSprites.length - 1)];

            int offsetVerticalFix = -10;
            double scaleX = 0.8; //
            double scaleY = 0.8;

            int scaledWidth = (int) (width * scaleX);
            int scaledHeight = (int) (height * scaleY);
            int offsetX = (scaledWidth - (int) width) / 2;
            int offsetY = (scaledHeight - (int) height) / 2;

            g.drawImage(deathFrame, (int) x - offsetX, (int) y - offsetY - offsetVerticalFix, scaledWidth, scaledHeight, null);
            return;
        }
        if (attacking) {
            BufferedImage attackFrame = facingRight
                    ? attackRightSprites[attackAnimationIndex]
                    : attackLeftSprites[attackAnimationIndex];

            int offsetVerticalFix = 10;
            double scaleX = 1.40; // más ancho
            double scaleY = 1.45; // más alto

            int scaledWidth = (int) (width * scaleX);
            int scaledHeight = (int) (height * scaleY);
            int offsetX = (scaledWidth - (int) width) / 2;
            int offsetY = (scaledHeight - (int) height) / 2;

            g.drawImage(attackFrame, (int) x - offsetX, (int) y - offsetY - offsetVerticalFix, scaledWidth, scaledHeight, null);
            //Collider de damage
            if (attackInProgress) {
                Rectangle hit = getAttackBounds();

                g.setColor(Color.RED);
                g.drawRect(hit.x, hit.y, hit.width, hit.height);
            }
            return;
        }
        super.draw(g);
        //Colider de zona de peligro
        if (isAlive() && !playingDeathAnimation && !fullyDead) {
            Rectangle detectZone = getAttackDetectionZone();
            g.setColor(Color.GREEN);
            g.drawRect(detectZone.x, detectZone.y, detectZone.width, detectZone.height);
        }

    }
    private void loadSprites() {
        walkRightSprites = new BufferedImage[10];
        walkLeftSprites = new BufferedImage[10];

        attackRightSprites = new BufferedImage[10];
        attackLeftSprites = new BufferedImage[10];

        deathRightSprites = new BufferedImage[9];
        deathLeftSprites = new BufferedImage[9];

        //sprites lado derecho
        walkRightSprites[0] = SpriteLoader.loadImage("/finalBoss/walk0.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/finalBoss/walk1.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/finalBoss/walk2.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/finalBoss/walk3.png");
        walkRightSprites[4] = SpriteLoader.loadImage("/finalBoss/walk4.png");
        walkRightSprites[5] = SpriteLoader.loadImage("/finalBoss/walk5.png");
        walkRightSprites[6] = SpriteLoader.loadImage("/finalBoss/walk6.png");
        walkRightSprites[7] = SpriteLoader.loadImage("/finalBoss/walk7.png");
        walkRightSprites[8] = SpriteLoader.loadImage("/finalBoss/walk8.png");
        walkRightSprites[9] = SpriteLoader.loadImage("/finalBoss/walk9.png");

        attackRightSprites[0] = SpriteLoader.loadImage("/finalBoss/attack0.png");
        attackRightSprites[1] = SpriteLoader.loadImage("/finalBoss/attack1.png");
        attackRightSprites[2] = SpriteLoader.loadImage("/finalBoss/attack2.png");
        attackRightSprites[3] = SpriteLoader.loadImage("/finalBoss/attack3.png");
        attackRightSprites[4] = SpriteLoader.loadImage("/finalBoss/attack4.png");
        attackRightSprites[5] = SpriteLoader.loadImage("/finalBoss/attack5.png");
        attackRightSprites[6] = SpriteLoader.loadImage("/finalBoss/attack6.png");
        attackRightSprites[7] = SpriteLoader.loadImage("/finalBoss/attack7.png");
        attackRightSprites[8] = SpriteLoader.loadImage("/finalBoss/attack8.png");
        attackRightSprites[9] = SpriteLoader.loadImage("/finalBoss/attack9.png");

        deathRightSprites[0] = SpriteLoader.loadImage("/finalBoss/plop0.png");
        deathRightSprites[1] = SpriteLoader.loadImage("/finalBoss/plop1.png");
        deathRightSprites[2] = SpriteLoader.loadImage("/finalBoss/plop2.png");
        deathRightSprites[3] = SpriteLoader.loadImage("/finalBoss/plop3.png");
        deathRightSprites[4] = SpriteLoader.loadImage("/finalBoss/plop4.png");
        deathRightSprites[5] = SpriteLoader.loadImage("/finalBoss/plop5.png");
        deathRightSprites[6] = SpriteLoader.loadImage("/finalBoss/plop6.png");
        deathRightSprites[7] = SpriteLoader.loadImage("/finalBoss/plop7.png");
        deathRightSprites[8] = SpriteLoader.loadImage("/finalBoss/plop8.png");

        //Sprites lado izquierdo
        walkLeftSprites[0] = SpriteLoader.loadImage("/finalBoss/walk0l.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/finalBoss/walk1l.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/finalBoss/walk2l.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/finalBoss/walk3l.png");
        walkLeftSprites[4] = SpriteLoader.loadImage("/finalBoss/walk4l.png");
        walkLeftSprites[5] = SpriteLoader.loadImage("/finalBoss/walk5l.png");
        walkLeftSprites[6] = SpriteLoader.loadImage("/finalBoss/walk6l.png");
        walkLeftSprites[7] = SpriteLoader.loadImage("/finalBoss/walk7l.png");
        walkLeftSprites[8] = SpriteLoader.loadImage("/finalBoss/walk8l.png");
        walkLeftSprites[9] = SpriteLoader.loadImage("/finalBoss/walk9l.png");

        attackLeftSprites[0] = SpriteLoader.loadImage("/finalBoss/attack0l.png");
        attackLeftSprites[1] = SpriteLoader.loadImage("/finalBoss/attack1l.png");
        attackLeftSprites[2] = SpriteLoader.loadImage("/finalBoss/attack2l.png");
        attackLeftSprites[3] = SpriteLoader.loadImage("/finalBoss/attack3l.png");
        attackLeftSprites[4] = SpriteLoader.loadImage("/finalBoss/attack4l.png");
        attackLeftSprites[5] = SpriteLoader.loadImage("/finalBoss/attack5l.png");
        attackLeftSprites[6] = SpriteLoader.loadImage("/finalBoss/attack6l.png");
        attackLeftSprites[7] = SpriteLoader.loadImage("/finalBoss/attack7l.png");
        attackLeftSprites[8] = SpriteLoader.loadImage("/finalBoss/attack8l.png");
        attackLeftSprites[9] = SpriteLoader.loadImage("/finalBoss/attack9l.png");

        deathLeftSprites[0] = SpriteLoader.loadImage("/finalBoss/plop0l.png");
        deathLeftSprites[1] = SpriteLoader.loadImage("/finalBoss/plop1l.png");
        deathLeftSprites[2] = SpriteLoader.loadImage("/finalBoss/plop2l.png");
        deathLeftSprites[3] = SpriteLoader.loadImage("/finalBoss/plop3l.png");
        deathLeftSprites[4] = SpriteLoader.loadImage("/finalBoss/plop4l.png");
        deathLeftSprites[5] = SpriteLoader.loadImage("/finalBoss/plop5l.png");
        deathLeftSprites[6] = SpriteLoader.loadImage("/finalBoss/plop6l.png");
        deathLeftSprites[7] = SpriteLoader.loadImage("/finalBoss/plop7l.png");
        deathLeftSprites[8] = SpriteLoader.loadImage("/finalBoss/plop8l.png");
    }
}
