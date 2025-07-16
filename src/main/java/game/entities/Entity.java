package game.entities;

import java.awt.*;

public abstract class Entity {
    //Atributos
    protected double x, y;
    protected double width, height;
    protected int damage, health, maxHealth;
    protected double speed;


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
    public abstract Rectangle getBounds();
    public abstract void update();
    public abstract void draw(Graphics g);
    public abstract void attack();




}
