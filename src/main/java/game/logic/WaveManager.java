package game.logic;

import java.util.List;
import java.util.ArrayList;

import game.entities.EnemyA;
import game.entities.Player;

import java.awt.*;



public class WaveManager {
    //Atributos
    private List <EnemyA> enemies;
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
        enemies.add(new EnemyA(900, 0.2, 100, 10, 30, 30, 595, player));
        // Puedes añadir más enemigos aquí con otras posiciones
    }

    public void update() {
        for (EnemyA e : enemies) {
            e.update();
        }
    }

    public void draw(Graphics g) {
        for (EnemyA e : enemies) {
            e.draw(g);
        }
    }

    public List<EnemyA> getEnemies() {
        return enemies;
    }

}
