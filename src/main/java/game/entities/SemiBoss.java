package game.entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import game.graphics.SpriteLoader;

public  class SemiBoss extends Boss{
    //Constructor
    public SemiBoss(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, health, damage, height, width, y, targetPlayer);
        loadSprites();
    }
    //Collider
    @Override
    public Rectangle getBounds() {
        if (!isAlive() || playingDeathAnimation || fullyDead) {
            return new Rectangle(0, 0, 0, 0); // Sin colisión mientras muere
        }
        int colliderWidth = (int) (width * 0.9);
        int colliderHeight = (int) (height * 0.8);
        int offsetX = (int) ((width - colliderWidth) / 2);
        int offsetY = (int) ((height - colliderHeight) / 2);

        return new Rectangle((int) x + offsetX, (int) y + offsetY, colliderWidth, colliderHeight);
    }
    //Sprites
    private void loadSprites() {
        walkLeftSprites = new BufferedImage[4];
        walkRightSprites = new BufferedImage[4];
        exploteSprites = new BufferedImage[8];
        //Sprites movimiento
        walkRightSprites[0] = SpriteLoader.loadImage("/semiBoss/walk0.png");
        walkRightSprites[1] = SpriteLoader.loadImage("/semiBoss/walk1.png");
        walkRightSprites[2] = SpriteLoader.loadImage("/semiBoss/walk2.png");
        walkRightSprites[3] = SpriteLoader.loadImage("/semiBoss/walk3.png");

        walkLeftSprites[0] = SpriteLoader.loadImage("/semiBoss/walk0l.png");
        walkLeftSprites[1] = SpriteLoader.loadImage("/semiBoss/walk1l.png");
        walkLeftSprites[2] = SpriteLoader.loadImage("/semiBoss/walk2l.png");
        walkLeftSprites[3] = SpriteLoader.loadImage("/semiBoss/walk3l.png");
        //Sprites derrota
        exploteSprites [0] = SpriteLoader.loadImage("/semiBoss/plop0.png");
        exploteSprites [1] = SpriteLoader.loadImage("/semiBoss/plop1.png");
        exploteSprites [2] = SpriteLoader.loadImage("/semiBoss/plop2.png");
        exploteSprites [3] = SpriteLoader.loadImage("/semiBoss/plop3.png");
        exploteSprites [4] = SpriteLoader.loadImage("/semiBoss/plop4.png");
        exploteSprites [5] = SpriteLoader.loadImage("/semiBoss/plop5.png");
        exploteSprites [6] = SpriteLoader.loadImage("/semiBoss/plop6.png");
        exploteSprites [7] = SpriteLoader.loadImage("/semiBoss/plop7.png");
    }
}
