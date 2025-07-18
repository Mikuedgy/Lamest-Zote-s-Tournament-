package game.entities;

public abstract class Boss extends Enemy {
    //Atributos

    //Constructor
    public Boss(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, health, damage, height, width, y, targetPlayer);
    }
}
