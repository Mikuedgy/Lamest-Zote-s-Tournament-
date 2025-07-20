package game.entities;

import java.awt.*;
import game.graphics.SpriteLoader;
import java.awt.image.BufferedImage;

public  class EnemyA extends Enemy{
    //Constructor
    public EnemyA(double x, double speed, int maxHealth, int damage,
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
