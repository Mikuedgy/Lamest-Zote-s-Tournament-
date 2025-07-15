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

    private int animationIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 45;

    private boolean facingRight = true;
    private boolean moving = false;


    // Constructor
    public Player(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, int mana, int maxMana, KeyHandler keyHandler) {
        super(x, speed, maxHealth, health, damage, height, width, y);
        this.mana = mana;
        this.maxMana = maxMana;
        this.keyHandler = keyHandler;

        loadSprites();
    }

    private void loadSprites() {
        walkRightSprites = new BufferedImage[7];
        walkLeftSprites = new BufferedImage[7];

        // Sprites derecha
        walkRightSprites[0] = SpriteLoader.loadImage("/sprites/walk0.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/sprites/walk1.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/sprites/walk2.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/sprites/walk3.png");
        walkRightSprites[4] = SpriteLoader.loadImage("/sprites/walk4.png");
        walkRightSprites[5] = SpriteLoader.loadImage("/sprites/walk5.png");
        walkRightSprites[6] = SpriteLoader.loadImage("/sprites/walk6.png");

        // Sprites izquierda
        walkLeftSprites[0] = SpriteLoader.loadImage("/sprites/walkl0.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/sprites/walkl1.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/sprites/walkl2.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/sprites/walkl3.png");
        walkLeftSprites[4] = SpriteLoader.loadImage("/sprites/walkl4.png");
        walkLeftSprites[5] = SpriteLoader.loadImage("/sprites/walkl5.png");
        walkLeftSprites[6] = SpriteLoader.loadImage("/sprites/walkl6.png");

        standRightSprite = SpriteLoader.loadImage("/sprites/standr.png");
        standLeftSprite = SpriteLoader.loadImage("/sprites/standl.png");
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void update() {
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

        if (moving) {
            currentFrame = facingRight ? walkRightSprites[animationIndex] : walkLeftSprites[animationIndex];
        } else {
            currentFrame = facingRight ? standRightSprite : standLeftSprite;
        }

        g.drawImage(currentFrame, (int) x, (int) y, (int) width, (int) height, null);
    }

    // Getters y Setters
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
