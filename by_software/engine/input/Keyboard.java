package by_software.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import by_software.Game;
import by_software.engine.render.graphics.GameWindow;
 

public class Keyboard implements KeyListener, MouseMotionListener  {

  //this number mught need to be increased.
  private boolean[] keys = new boolean[255];
  
  public boolean up, down, left, right, space, esc, q, e;
  public  double mouseX = 0 , mouseY = 0;

  private double      mouseOldX = 0, mouseOldY = 0;
  private double      sensitivity, sensitivityReset;
  private boolean     ignoreMouseMovement = false;
  private Point       origin = new Point();
  private Game        game;
  private GameWindow  gameWindow;

  public Keyboard(double sensitivity, Game game)
  {
    this.sensitivity  = sensitivity;
    this.game         = game;
    this.gameWindow   = game.getWindow();
  }



  public void getInput()
  {
    up = keys[KeyEvent.VK_UP] ||  keys[KeyEvent.VK_W];
    down = keys[KeyEvent.VK_DOWN] ||  keys[KeyEvent.VK_S];
    right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
    left = keys[KeyEvent.VK_LEFT] ||  keys[KeyEvent.VK_A];
    q = keys[KeyEvent.VK_Q];
    e = keys[KeyEvent.VK_E];
    space = keys[KeyEvent.VK_SPACE];
    esc = keys[KeyEvent.VK_ESCAPE];
  }

  public void handleKeys()
  {
    //maybe handle keyinput in here instead of in each individual class?
    //this would make it easier to find and change key behaviour
  }
  
  public void reset()
  { 
    mouseX = 0;
    mouseY = 0;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      if( game.getWindow().mouseReleased() )
      {
        game.getWindow().captureMouse();
      }
      else
      {
        game.getWindow().releaseMouse();
      }
    }
    else
    {
      keys[e.getKeyCode()] = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    keys[e.getKeyCode()] = false;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    
  }

  @Override
  public void mouseMoved(MouseEvent e)
  {

  }

  @Override
  public void mouseDragged(MouseEvent e)
  {
   // mouseMoved(e);
  }

  public void ignoreMouseMove(boolean ignore)
  {
      ignoreMouseMovement = ignore;

  }

  public double getMouseX(){ return  (MouseInfo.getPointerInfo().getLocation().getX() - origin.getX())* sensitivity;}
  public double getMouseY(){ return  (MouseInfo.getPointerInfo().getLocation().getY() - origin.getY())* sensitivity;}

  public void setOrigin(Point origin)
  {
    this.origin = origin;
  }




}
