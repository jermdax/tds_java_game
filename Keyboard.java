import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
 

public class Keyboard implements KeyListener, MouseMotionListener  {

  //this number mught need to be increased.
  private boolean[] keys = new boolean[255];
  
  public boolean up, down, left, right, space, esc, q, e;
  public  double mouseX = 0 , mouseY = 0;

  private double mouseOldX = 0, mouseOldY = 0;
  private double sensitivity, sensitivityReset;
  private boolean ignoreMouseMovement = false;
  private Point origin = new Point();

  public Keyboard(double sensitivity)
  {
    this.sensitivity = sensitivity;
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
  
  public void reset()
  { 
    mouseX = 0;
    mouseY = 0;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    
    keys[e.getKeyCode()] = true;
    
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
    
  }

  @Override
  public void keyTyped(KeyEvent e) {
    
  }

  @Override
  public void mouseMoved(MouseEvent e)
  {
     
    PointerInfo a = MouseInfo.getPointerInfo();
    Point point = new Point(a.getLocation());

    double mouseNewX = point.getX();
    double mouseNewY = point.getY();

    System.out.println("mouseNewX = " + mouseNewX + "; originX = " + origin.getX() + " boolean result " + (mouseNewX != origin.getX()));
    if(mouseNewX != origin.getX())
    {
      System.out.println("difference between newMouse and origin");
      mouseX = (mouseNewX - mouseOldX) * sensitivity;
      mouseY = (mouseNewY - mouseOldY) * sensitivity;
    }
    else{System.out.println("new mouse equals origin location");}
    mouseOldX = mouseNewX;
    mouseOldY = mouseNewY;
  System.out.println(mouseX);
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

  public double getMouseX(){ return mouseX;}
  public double getMouseY(){ return mouseY;}

  public void setOrigin(Point origin)
  {
    this.origin = origin;
  }




}