package game.entities;

import java.awt.*;
import game.graphics.SpriteLoader;
import java.awt.image.BufferedImage;

public  class EnemyA extends Enemy{
    //Atributos

    //Animaciones
    private boolean facingRight = false;

    private int animationIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 80;
    private BufferedImage[] walkRightSprites;
    private BufferedImage[] walkLeftSprites;

    //Constructor
    public EnemyA(double x, double speed, int maxHealth, int damage,
                  double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, maxHealth, damage, height, width, y, targetPlayer);
        loadSprites();
    }
    //Updates por frame
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
        //Coldown de golpes
        if (isDamaged) {
            damageCooldown--;
            if (damageCooldown <= 0) {
                isDamaged = false;
            }
        }

    }

    //Colliders
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
        walkLeftSprites = new BufferedImage[4];
        walkRightSprites = new BufferedImage[4];
        //Sprites movimiento
        walkRightSprites[0] = SpriteLoader.loadImage("/enemyA/walk0.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/enemyA/walk1.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/enemyA/walk2.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/enemyA/walk3.png");

        walkLeftSprites[0] = SpriteLoader.loadImage("/enemyA/walk0l.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/enemyA/walk1l.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/enemyA/walk2l.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/enemyA/walk3l.png");
    }

}
