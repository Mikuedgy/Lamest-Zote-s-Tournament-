package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    //Atributos
    public boolean up, down;
    public boolean left, right;
    public boolean attack;
    public boolean charge;
    public boolean enter;
    public enum Direction { NONE, LEFT, RIGHT }

    public Direction lastDirectionPressed = Direction.NONE;

    //Metodos
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            enter = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            up = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            down = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_X){
            attack = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_Z){
            charge = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
            lastDirectionPressed = Direction.LEFT;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
            lastDirectionPressed = Direction.RIGHT;
        }
    }

    public void keyReleased(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            enter = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            up = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_X){
            attack = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_Z){
            charge = false;
        }
    }

    public void keyTyped(KeyEvent e){
    }
}
