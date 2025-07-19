package game.entities;

import java.awt.*;
import game.graphics.SpriteLoader;
import java.awt.image.BufferedImage;

public  class EnemyC extends Enemy{
    //Constructor
    public EnemyC(double x, double speed, int maxHealth, int damage,
                  double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, maxHealth, damage, height, width, y, targetPlayer);
        loadSprites();
    }
    //Collider
    @Override
    public Rectangle getBounds() {
        int colliderWidth = (int) (width * 0.9);   // Reducir ancho (60% del original)
        int colliderHeight = (int) (height * 0.8); // Reducir alto (80% del original)
        int offsetX = (int) ((width - colliderWidth) / 2);  // Centrar horizontalmente
        int offsetY = (int) ((height - colliderHeight) / 2); // Centrar verticalmente

        return new Rectangle((int) x + offsetX, (int) y + offsetY, colliderWidth, colliderHeight);
    }
    //Sprites
    private void loadSprites() {
        walkRightSprites = new BufferedImage[5];
        walkLeftSprites = new BufferedImage[5];
        // Sprites movimiento
        walkRightSprites[0] = SpriteLoader.loadImage("/enemyC/fly0.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/enemyC/fly1.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/enemyC/fly2.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/enemyC/fly3.png");
        walkRightSprites[4] = SpriteLoader.loadImage("/enemyC/fly4.png");

        walkLeftSprites[0] = SpriteLoader.loadImage("/enemyC/fly0l.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/enemyC/fly1l.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/enemyC/fly2l.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/enemyC/fly3l.png");
        walkLeftSprites[4] = SpriteLoader.loadImage("/enemyC/fly4l.png");
    }
}
