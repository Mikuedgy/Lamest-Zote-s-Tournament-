package game.logic;

import java.awt.*;
import java.util.List;
import game.entities.*;
import java.util.ArrayList;

public class WaveManager {
    //Atributos
    private Player player;
    private List<Enemy> enemies;
    private int currentWave = 1;
    private boolean allWavesCompleted = false;
    //Constructor
    public WaveManager(Player player) {
        this.player = player;
        enemies = new ArrayList<>();
        spawnWave();
    }
    //Update por frame
    public void update() {
        if (allWavesCompleted) return;
        //UPDATE DE COLISIONES EN EL JUEGO
        for (Enemy e : enemies) {
            e.update();
            for (Enemy enemy : enemies) {
                //Golpea al enemigo solo una vez por ataque
                if (player.isAttacking() &&
                        player.getAttackBounds().intersects(enemy.getBounds()) &&
                        !enemy.hasBeenHitThisAttack()) {

                    enemy.takeDamage(player.getDamage());

                }
                //El enemigo daña al jugador solo si está vivo y colisiona
                if (enemy.getBounds().intersects(player.getBounds())) {
                    if (!enemy.hasDamagedPlayerThisContact()) {
                        player.takeDamage(enemy.getDamage());
                        enemy.setHasDamagedPlayerThisContact(true);
                    }
                } else {
                    //Cuando ya no colisiona, resetea el flag
                    enemy.setHasDamagedPlayerThisContact(false);
                }

            }
            //Reiniciar el flag cuando el ataque del jugador termina
            if (!player.isAttacking()) {
                for (Enemy enemy : enemies) {
                    enemy.resetHitStatus();
                }
            }
            player.resetHitStatus();
        }
        enemies.removeIf(Enemy::shouldBeRemoved);

        if (enemies.isEmpty()) {
            if (currentWave < 3) {
                currentWave++;
                spawnWave();
            } else {
                allWavesCompleted = true;
            }
        }
    }
    //Dibujado y reset
    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            e.draw(g);
        }
    }
    public void reset() {
        enemies.clear();
        currentWave = 1;
        allWavesCompleted = false;
        spawnWave();
    }
    //Carga de oleadas
    public void spawnWave() {
        enemies.clear();
        switch (currentWave) {
            case 1:
                enemies.add(new EnemyA(1300, 0.1, 2, 1, 30, 30, 595, player));
                enemies.add(new EnemyA(-200, 0.1, 2, 1, 30, 30, 595, player));
                enemies.add(new EnemyC(2000, 0.2, 2, 1, 30, 30, 570,player));
                enemies.add(new EnemyC(-1000, 0.2, 2, 1, 30, 30, 570, player));

                enemies.add(new EnemyC(2500, 0.2, 2, 1, 30, 30, 570,player));
                enemies.add(new EnemyC(-1500, 0.2, 2, 1, 30, 30, 570, player));

                enemies.add(new EnemyA(2800, 0.2, 2, 1, 30, 30, 595, player));
                enemies.add(new EnemyC(-1800, 0.2, 2, 1, 30, 30, 570,player));


                enemies.add(new EnemyA( 3100, 0.3, 2, 1, 30, 30, 595, player));
                enemies.add(new EnemyC(-2000, 0.3, 2, 1, 30, 30, 570,player));

                break;

            case 2:
                enemies.add(new EnemyB(1300, 0.1, 3, 1, 44, 44, 580, player));
                enemies.add(new EnemyC(-200, 0.2, 2, 1, 30, 30, 570,player));
                enemies.add(new EnemyC(1600, 0.2, 2, 1, 30, 30, 570,player));


                enemies.add(new EnemyB(2500, 0.1, 3, 1, 44, 44, 580, player));
                enemies.add(new EnemyB(-1000, 0.1, 3, 1, 44, 44, 580, player));
                enemies.add(new EnemyB(2900, 0.2, 3, 1, 44, 44, 580, player));
                enemies.add(new EnemyB(-1500, 0.2, 3, 1, 44, 44, 580, player));

                enemies.add(new EnemyA(3400, 0.2, 2, 2, 30, 30, 595, player));
                enemies.add(new EnemyA(-2100, 0.2, 2, 2, 30, 30, 595, player));

                enemies.add(new EnemyC(-2000, 0.3, 2, 1, 30, 30, 570,player));
                enemies.add(new EnemyC(-2000, 0.3, 2, 1, 30, 30, 570,player));

                enemies.add(new SemiBoss(-4000, 0.2, 4, 4, 2, 100, 130,523,player));
                enemies.add(new SemiBoss(7000, 0.3, 4, 4, 2, 100, 130,523,player));


                break;
            case 3:
                enemies.add(new EnemyB(1400, 0.1, 3, 1, 44, 44, 580, player));
                enemies.add(new EnemyB(-300, 0.1, 3, 1, 44, 44, 580, player));

                enemies.add(new FinalBoss(2000,0.1, 5, 5, 2, 80, 100,545,player));
                break;
        }
    }
    //Getters
    public boolean getAllWavesCompleted() {
        return allWavesCompleted;
    }
    public int getCurrentWave() {
        return currentWave;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
