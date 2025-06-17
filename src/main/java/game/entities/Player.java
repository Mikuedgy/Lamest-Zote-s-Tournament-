package game.entities;

public class Player extends Entity{
    //Atributos
    protected int mana, maxMana;


    //Constructor
    public Player(double x, int speed, int maxHealth, int health, int damage, double heigth, double width, double y, int mana, int maxMana) {
        super(x, speed, maxHealth, health, damage, heigth, width, y);
        this.mana = mana;
        this.maxMana = maxMana;
    }
}
