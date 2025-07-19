package game.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    //Atributos basicos
    protected int damage;
    protected int health;
    protected double x, y;
    protected double speed;
    protected double width;
    protected double height;
    protected int maxHealth;
    //Animacion
    protected int flashTimer = 0;
    protected boolean flashing = false;
    protected final int FLASH_DURATION = 20; // 6 frames ~100ms a 60fps
    //Damage y ataques
    protected int damageDelay = 30;
    protected int damageCooldown = 0;
    protected boolean isDamaged = false;
    protected boolean hasBeenHitThisAttack = false;
    protected boolean hasDamagedPlayerThisContact = false;

    //Constructor
    public Entity(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y) {
        this.x = x;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.health = health;
        this.damage = damage;
        this.height = height;
        this.width = width;
        this.y = y;
    }
    //Metodos abstractos
    public abstract void draw(Graphics g);
    protected abstract void updateEntity();
    public abstract Rectangle getBounds();
    public abstract Rectangle getAttackBounds();
    //Update por frames
    public void update() {
        updateEntity();      // lógica propia de cada tipo
        updateFlashing();    // lógica compartida: destello
    }
    //Damage y animacion
    public void takeDamage(int amount) {
        if (hasBeenHitThisAttack || isDamaged) return;

        health -= amount;
        if (health < 0) health = 0;

        isDamaged = true;
        damageCooldown = damageDelay;
        hasBeenHitThisAttack = true;

        // Activar parpadeo blanco
        flashing = true;
        flashTimer = FLASH_DURATION;

    }
    protected void updateFlashing() {
        if (flashing) {
            flashTimer--;
            if (flashTimer <= 0) {
                flashing = false;
            }
        }
    }
    protected BufferedImage makeWhiteImage(BufferedImage src) {
        BufferedImage whiteImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int rgba = src.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xff;
                if (alpha > 0) {
                    whiteImg.setRGB(x, y, (alpha << 24) | 0xffffff); // blanco con misma opacidad
                }
            }
        }
        return whiteImg;
    }
    //Otros
    public boolean isAlive() {
        return health > 0;
    }
    //Getters
    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }
    public void resetHitStatus() {
        hasBeenHitThisAttack = false;
    }
    public boolean hasBeenHitThisAttack() {
        return hasBeenHitThisAttack;
    }
    public boolean hasDamagedPlayerThisContact() {
        return hasDamagedPlayerThisContact;
    }
    public void setHasDamagedPlayerThisContact(boolean value) {
        hasDamagedPlayerThisContact = value;
    }
}
