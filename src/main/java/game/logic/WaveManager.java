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
        spawnWave(); // Primera oleada
    }
    //Update por frame
    public void update() {
        if (allWavesCompleted) return;

        for (Enemy e : enemies) {
            e.update();
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
        currentWave = 0;
        spawnWave();
    }
    //Carga de oleadas
    public void spawnWave() {
        enemies.clear();
        switch (currentWave) {
            case 1:
                enemies.add(new EnemyA(1200, 0.4, 2, 1, 30, 30, 595, player));
                enemies.add(new EnemyC(1800, 0.4, 2, 1, 30, 30, 570,player));

                break;

            case 2:
                enemies.add(new EnemyB(2000, 0.3, 3, 1, 44, 44, 580, player));
                enemies.add(new SemiBoss(-800, 0.3, 4, 4, 1, 100, 130,523,player));
                break;

            case 3:

                enemies.add(new FinalBoss(1300,0.3, 5, 6, 1, 80, 100,545,player));
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
