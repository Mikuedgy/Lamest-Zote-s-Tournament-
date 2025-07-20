package game.main;

import game.core.GamePanel;
import javax.swing.JFrame;

//Aqui simplemente llamare a Gamepanel y ajustare algunas cosas de ventana
public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame("Lamest Zote's Tournament");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGame();
    }
}
