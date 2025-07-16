package game.entities;

public abstract class Enemy extends Entity {
    //Atributos
    //Constructor
    public Enemy(double x, double speed, int maxHealth, int health, int damage, double heigth, double width, double y) {
        super(x, speed, maxHealth, health, damage, heigth, width, y);
    }
}
