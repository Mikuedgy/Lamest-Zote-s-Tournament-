package game.entities;



import game.input.KeyHandler;
import game.graphics.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Player extends Entity{
    //Atributos
    protected int mana, maxMana;
    private KeyHandler keyHandler;

    private BufferedImage[] walkRightSprites;
    private BufferedImage[] walkLeftSprites;
    private BufferedImage standRightSprite;
    private BufferedImage standLeftSprite;
    private BufferedImage[] attackRightSprites;
    private BufferedImage[] attackLeftSprites;

    private boolean attacking = false;
    private int attackIndex = 0;
    private int attackCounter = 0;
    private int attackSpeed = 30; //velocidad sprites

    private final int ATTACK_DURATION = 5; //cantidad de sprites

    private int animationIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 45; //velocidad sprites

    private boolean facingRight = true;
    private boolean moving = false;

    //Constructor
    public Player(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, int mana, int maxMana, KeyHandler keyHandler) {
        super(x, speed, maxHealth, health, damage, height, width, y);
        this.mana = mana;
        this.maxMana = maxMana;
        this.keyHandler = keyHandler;

        loadSprites();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void attack() {
        if (!attacking) {
            attacking = true;
            attackIndex = 0;
            attackCounter = 0;
        }
    }

    @Override
    public void update() {

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
            return; // No moverse mientras ataca
        }

        moving = false;

        if (keyHandler.right) {
            x += speed;
            facingRight = true;
            moving = true;
        } else if (keyHandler.left) {
            x -= speed;
            facingRight = false;
            moving = true;
        }

        if (moving) {
            animationCounter++;
            if (animationCounter >= animationSpeed) {
                animationIndex = (animationIndex + 1) % walkRightSprites.length;
                animationCounter = 0;
            }
        } else {
            animationIndex = 0;
        }

    }

    @Override
    public void draw(Graphics g) {
        BufferedImage currentFrame;

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

        //sprites lado izq
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

    //Getters y Setters
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
}
