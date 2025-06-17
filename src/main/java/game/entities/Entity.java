package game.entities;

public abstract class Entity {
    //Atributos
    protected double x, y;
    protected double width, heigth;
    protected int damage, health, maxHealth;
    protected int speed;


    //Constructor
    public Entity(double x, int speed, int maxHealth, int health, int damage, double heigth, double width, double y) {
        this.x = x;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.health = health;
        this.damage = damage;
        this.heigth = heigth;
        this.width = width;
        this.y = y;
    }

    //Metodos


}
