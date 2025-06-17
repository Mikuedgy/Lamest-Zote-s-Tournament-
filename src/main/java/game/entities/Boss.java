package game.entities;

public abstract class Boss extends Enemy {
    //Atributos

    //Constructor
    public Boss(double x, int speed, int maxHealth, int health, int damage, double heigth, double width, double y) {
        super(x, speed, maxHealth, health, damage, heigth, width, y);
    }
}
