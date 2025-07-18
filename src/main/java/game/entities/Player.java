package game.entities;

import java.awt.*;
import game.input.KeyHandler;
import java.awt.image.BufferedImage;
import game.graphics.SpriteLoader;

public class Player extends Entity{
    //Atributos

    private KeyHandler keyHandler;

    //Sprites
    private BufferedImage[] walkRightSprites;
    private BufferedImage[] walkLeftSprites;
    private BufferedImage standRightSprite;
    private BufferedImage standLeftSprite;
    private BufferedImage[] attackRightSprites;
    private BufferedImage[] attackLeftSprites;
    //Ataque
    private boolean attacking = false;
    private int attackIndex = 0;
    private int attackCounter = 0;
    private int attackSpeed = 30;

    private final int ATTACK_DURATION = 5; //cantidad de sprites
    //Animacion movimiento
    private int animationIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 45; //velocidad sprites

    private boolean facingRight = true;
    private boolean moving = false;

    //Damage
    private boolean isDamaged = false;
    private int damageCooldown = 0;
    private final int damageDelay = 30;

    //Constructor
    public Player(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, KeyHandler keyHandler) {
        super(x, speed, maxHealth, health, damage, height, width, y);
        this.keyHandler = keyHandler;
        loadSprites();
    }

    @Override
    public void update() {
        //Ataque
        if (keyHandler.attack && !attacking) {
            attack();
        }
        if (attacking) {
            attackCounter++;
            if (attackCounter >= attackSpeed) {
                attackIndex++;
                attackCounter = 0;
                if (attackIndex >= ATTACK_DURATION) {
                    attacking = false;
                    attackIndex = 0;
                }
            }
            return;
        }
        //Movimiento
        moving = false;
        if (keyHandler.left && keyHandler.right) {
            if (keyHandler.lastDirectionPressed == KeyHandler.Direction.LEFT) {
                x -= speed;
                facingRight = false;
                moving = true;
            } else if (keyHandler.lastDirectionPressed == KeyHandler.Direction.RIGHT) {
                x += speed;
                facingRight = true;
                moving = true;
            }
        } else if (keyHandler.left) {
            x -= speed;
            facingRight = false;
            moving = true;
        } else if (keyHandler.right) {
            x += speed;
            facingRight = true;
            moving = true;
        }
        //Animacion sprites
        if (moving) {
            animationCounter++;
            if (animationCounter >= animationSpeed) {
                animationIndex = (animationIndex + 1) % walkRightSprites.length;
                animationCounter = 0;
            }
        } else {
            animationIndex = 0;
        }
        //Coldown golpes
        if (isDamaged) {
            damageCooldown--;
            if (damageCooldown <= 0) {
                isDamaged = false;
            }
        }

    }

   //Colliders y damage
    @Override
    public Rectangle getAttackBounds() {
        if (!attacking) return new Rectangle(0, 0, 0, 0); // No atacar = sin hitbox

        int attackWidth = 35; // Más grueso (ajústalo según lo que se vea bien)
        int attackHeight = (int) height;

        int offset = 35; // Pegado más al cuerpo

        if (facingRight) {
            return new Rectangle((int)(x + width - offset), (int)y, attackWidth, attackHeight);
        } else {
            return new Rectangle((int)(x - attackWidth + offset), (int)y, attackWidth, attackHeight);
        }
    }

    @Override
    public Rectangle getBounds() {
        int colliderWidth = (int) (width * 0.3);   // 60% del ancho
        int colliderHeight = (int) (height * 0.9); // 90% del alto
        int offsetX = (int) ((width - colliderWidth) / 2);
        int offsetY = (int) ((height - colliderHeight) / 2);

        return new Rectangle((int) x + offsetX, (int) y + offsetY, colliderWidth, colliderHeight);
    }
    @Override
    public void takeDamage(int amount) {
        if (isDamaged) return;

        health -= amount;
        if (health < 0) health = 0;

        isDamaged = true;
        damageCooldown = damageDelay;
    }
    public void attack() {
        if (!attacking) {
            attacking = true;
            attackIndex = 0;
            attackCounter = 0;
        }
    }
    public boolean isAttacking() {
        return attacking;
    }
    //Sprites y dibujado
    @Override
    public void draw(Graphics g) {
        BufferedImage currentFrame;

        g.setColor(Color.RED);
        Rectangle bounds = getBounds();
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        Rectangle attackbounds =getAttackBounds();
        g.drawRect(attackbounds.x, attackbounds.y,attackbounds.width, attackbounds.height);

        if (attacking) {
            currentFrame = facingRight ? attackRightSprites[attackIndex] : attackLeftSprites[attackIndex];
        } else if (moving) {
            currentFrame = facingRight ? walkRightSprites[animationIndex] : walkLeftSprites[animationIndex];
        } else {
            currentFrame = facingRight ? standRightSprite : standLeftSprite;
        }

        g.drawImage(currentFrame, (int) x, (int) y, (int) width, (int) height, null);
    }

    private void loadSprites() {
        walkRightSprites = new BufferedImage[7];
        walkLeftSprites = new BufferedImage[7];

        attackRightSprites = new BufferedImage[5];
        attackLeftSprites = new BufferedImage[5];

        //sprites lado derecho
        standRightSprite = SpriteLoader.loadImage("/player/standr.png");

        walkRightSprites[0] = SpriteLoader.loadImage("/player/walk0.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/player/walk1.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/player/walk2.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/player/walk3.png");
        walkRightSprites[4] = SpriteLoader.loadImage("/player/walk4.png");
        walkRightSprites[5] = SpriteLoader.loadImage("/player/walk5.png");
        walkRightSprites[6] = SpriteLoader.loadImage("/player/walk6.png");

        attackRightSprites[0] = SpriteLoader.loadImage("/player/attack0.png");
        attackRightSprites[1] = SpriteLoader.loadImage("/player/attack1.png");
        attackRightSprites[2] = SpriteLoader.loadImage("/player/attack2.png");
        attackRightSprites[3] = SpriteLoader.loadImage("/player/attack3.png");
        attackRightSprites[4] = SpriteLoader.loadImage("/player/attack0.png");

        //Sprites lado izquierdo
        standLeftSprite = SpriteLoader.loadImage("/player/standl.png");

        walkLeftSprites[0] = SpriteLoader.loadImage("/player/walk0l.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/player/walk1l.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/player/walk2l.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/player/walk3l.png");
        walkLeftSprites[4] = SpriteLoader.loadImage("/player/walk4l.png");
        walkLeftSprites[5] = SpriteLoader.loadImage("/player/walk5l.png");
        walkLeftSprites[6] = SpriteLoader.loadImage("/player/walk6l.png");

        attackLeftSprites[0] = SpriteLoader.loadImage("/player/attack0l.png");
        attackLeftSprites[1] = SpriteLoader.loadImage("/player/attack1l.png");
        attackLeftSprites[2] = SpriteLoader.loadImage("/player/attack2l.png");
        attackLeftSprites[3] = SpriteLoader.loadImage("/player/attack3l.png");
        attackLeftSprites[4] = SpriteLoader.loadImage("/player/attack0l.png");
    }
}
