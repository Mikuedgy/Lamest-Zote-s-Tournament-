package game.logic;

import java.util.List;
import java.util.ArrayList;
import game.entities.Enemy;
import game.entities.EnemyA;
import game.entities.EnemyB;
import game.entities.EnemyC;
import game.entities.SemiBoss;

import game.entities.Player;

import java.awt.*;



public class WaveManager {
    //Atributos
    private List <Enemy> enemies;
    private Player player;

    private int currentWave;
    private int enemiesRemaining;

    //Constructor
    public WaveManager(Player player) {
        this.player = player;
        enemies = new ArrayList<>();
        spawnWave(); // Primera oleada
    }
    //Metodos

    public void spawnWave() {
        enemies.clear(); // O simplemente añade más si quieres acumular
        //enemies.add(new EnemyA(1203, 0.1, 5, 10, 30, 30, 595, player));
        //enemies.add(new EnemyB(-3, 0.1, 8, 12, 44, 44, 580, player));
        //enemies.add(new EnemyC(-3, 0.2, 8, 12, 30, 30, 570,player));
        enemies.add(new SemiBoss(10, 0.2, 8, 12, 30, 100, 130,523,player));

        // Puedes añadir más enemigos aquí con otras posiciones
    }

    public void update() {
        for (Enemy e : enemies) {
            e.update();
        }
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            e.draw(g);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

}
