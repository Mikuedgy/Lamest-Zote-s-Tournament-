package game.entities;

public abstract class EnemyC extends Enemy{
    //Atributos

    //Constructor
    public EnemyC(double x, int speed, int maxHealth, int health, int damage, double heigth, double width, double y) {
        super(x, speed, maxHealth, health, damage, heigth, width, y);
    }
}
