package game.entities;

import java.awt.*;
import game.graphics.SpriteLoader;
import java.awt.image.BufferedImage;

public  class EnemyB extends Enemy{
    //Atributos

    //Animaciones
    private boolean facingRight = false;

    private int animationIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 80;
    private BufferedImage[] walkRightSprites;
    private BufferedImage[] walkLeftSprites;

    //Constructor
    public EnemyB(double x, double speed, int maxHealth, int damage,
                  double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, maxHealth, damage, height, width, y, targetPlayer);
        loadSprites();
    }
    //Update por frame
    @Override
    public void update() {

        if (!isAlive() || targetPlayer == null) return;
        //Persecucion hacia el jugador
        if (x < targetPlayer.x) {
            x += speed;
            facingRight = true;
        } else if (x > targetPlayer.x) {
            x -= speed;
            facingRight = false;
        }
        //Animacion de sprites
        animationCounter++;
        if (animationCounter >= animationSpeed) {
            animationIndex = (animationIndex + 1) % walkRightSprites.length;
            animationCounter = 0;
        }
        //Coldown golpes
        if (isDamaged) {
            damageCooldown--;
            if (damageCooldown <= 0) {
                isDamaged = false;
            }
        }
    }

    //Coliders
    @Override
    public Rectangle getBounds() {
        int colliderWidth = (int) (width * 0.9);   // Reducir ancho (60% del original)
        int colliderHeight = (int) (height * 0.8); // Reducir alto (80% del original)
        int offsetX = (int) ((width - colliderWidth) / 2);  // Centrar horizontalmente
        int offsetY = (int) ((height - colliderHeight) / 2); // Centrar verticalmente

        return new Rectangle((int) x + offsetX, (int) y + offsetY, colliderWidth, colliderHeight);
    }

    @Override
    public Rectangle getAttackBounds() {
        return getBounds();
    }

    //Sprites y dibujado
    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLUE);
        Rectangle bounds = getBounds();
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        if (!isAlive()) return;

        BufferedImage currentFrame = facingRight
                ? walkRightSprites[animationIndex]
                : walkLeftSprites[animationIndex];

        g.drawImage(currentFrame, (int) x, (int) y, (int) width, (int) height, null);
    }

    private void loadSprites() {
        walkRightSprites = new BufferedImage[6];
        walkLeftSprites = new BufferedImage[6];
        //Sprites movimiento
        walkRightSprites[0] = SpriteLoader.loadImage("/enemyB/walk0l.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/enemyB/walk1l.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/enemyB/walk2l.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/enemyB/walk3l.png");
        walkRightSprites[4] = SpriteLoader.loadImage("/enemyB/walk4l.png");
        walkRightSprites[5] = SpriteLoader.loadImage("/enemyB/walk5l.png");

        walkLeftSprites[0] = SpriteLoader.loadImage("/enemyB/walk0.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/enemyB/walk1.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/enemyB/walk2.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/enemyB/walk3.png");
        walkLeftSprites[4] = SpriteLoader.loadImage("/enemyB/walk4.png");
        walkLeftSprites[5] = SpriteLoader.loadImage("/enemyB/walk5.png");
    }
}
