package game.entities;

public abstract class Enemy extends Entity {
    //Atributos

    protected boolean hasBeenHitThisAttack = false;
    protected boolean isDamaged = false;
    protected int damageCooldown = 0;
    protected int damageDelay = 30;
    protected Player targetPlayer;

    protected boolean hasDamagedPlayerThisContact = false;

    //Constructor

    public Enemy(double x, double speed, int maxHealth, int health, int damage, double height, double width, double y, Player targetPlayer) {
        super(x, speed, maxHealth, health, damage, height, width, y);
        this.targetPlayer = targetPlayer;
    }

    public boolean shouldBeRemoved() {
        return !isAlive(); // comportamiento por defecto para enemigos simples
    }

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
