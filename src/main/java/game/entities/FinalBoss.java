package game.entities;

public abstract class FinalBoss extends Boss {
    //Atributos

    //Constructor

    public FinalBoss(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, health, damage, height, width, y, targetPlayer);
    }
}
