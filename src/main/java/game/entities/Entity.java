package game.entities;


import java.awt.*;

public abstract class Entity {
    //Atributos
    protected double x, y;
    protected double width, height;
    protected int damage;
    protected int health;
    protected int maxHealth;
    protected double speed;

    //agregado
    protected boolean flashing = false;
    protected int flashTimer = 0;
    protected final int FLASH_DURATION = 6; // 6 frames ~100ms a 60fps

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

    //Metodos
    public boolean isAlive() {
        return health > 0;
    }

    public abstract Rectangle getBounds();
    public abstract void update();
    public abstract void draw(Graphics g);
    public abstract Rectangle getAttackBounds();
    public abstract void takeDamage(int amount);
    //Getters
    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }
}
