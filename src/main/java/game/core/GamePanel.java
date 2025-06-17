package game.core;

import game.entities.Enemy;
import game.entities.Player;
import game.input.KeyHandler;
import game.logic.WaveManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    //Atributos
    private Player player;
    private WaveManager waveManager;
    private KeyHandler keyHandler;

    private boolean running;
    private int currentState;
    private Thread gameThread;


   //Constructor
    public GamePanel() {
    }
}
