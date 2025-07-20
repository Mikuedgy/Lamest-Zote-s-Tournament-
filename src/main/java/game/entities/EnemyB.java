package game.entities;

import java.awt.*;
import game.graphics.SpriteLoader;
import java.awt.image.BufferedImage;

public  class EnemyB extends Enemy{
    //Constructor
    public EnemyB(double x, double speed, int maxHealth, int damage,
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
    @Override
    public void loadSprites() {
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
