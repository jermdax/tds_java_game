package by_software.engine.render.graphics;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import by_software.engine.input.Keyboard;

public class GameWindow
{
  private final JFrame          frame;
  private final BufferStrategy  bStrat;
  //used to move mouse
  private       Robot           robot;
  private       Keyboard        input;
  private final Canvas          canvas       = new Canvas();
  private final Point           origin;
  private       boolean         releasedMouse = false;
  private final Cursor          blankCursor  = Toolkit.getDefaultToolkit().createCustomCursor(
                                                new BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB),
                                                new Point(0 ,0),"blank cursor");

  public GameWindow(String title, Dimension windowSize,final  Keyboard input)
  {
     try{
      robot = new Robot();
    } catch(AWTException e) {
      //TODO maybe.
    }
    this.input = input;
    //set up the JFrame
    frame = new JFrame(title);
    frame.setSize(windowSize);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setIgnoreRepaint(true);


    canvas.setPreferredSize(windowSize);
    canvas.setIgnoreRepaint(true);
    
    canvas.setCursor(blankCursor);

    canvas.addKeyListener(input);
    canvas.addMouseMotionListener(input);
    frame.add(canvas);

    frame.pack();
    frame.setVisible(true);
    origin = frame.getLocationOnScreen();
    origin.translate(canvas.getWidth()/2,canvas.getHeight()/2);
    input.setOrigin(origin);

 

    canvas.createBufferStrategy(2);
    bStrat = canvas.getBufferStrategy();

    frame.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e)
      {
        Point frameLoc = frame.getLocationOnScreen();
        origin.setLocation(frameLoc.getX()+canvas.getWidth()/2, frameLoc.getY() + canvas.getHeight()/2);
        input.setOrigin(origin);
      }
    });
    
    frame.addWindowListener(new WindowAdapter() 
    {
      //return mouse control to the user
      public void windowDeactivated(WindowEvent e)
      {
        releaseMouse();
      }
      //get the game to capture the mouse
      public void windowActivated(WindowEvent e)
      {
        captureMouse();
      }
    });
  }

  public JFrame getFrame()                  {return frame; }
  public BufferStrategy getBufferStrategy() {return bStrat;}

  public boolean mouseReleased()
  {
    return releasedMouse;
  }

  public void releaseMouse()
  {
    canvas.setCursor( Cursor.getDefaultCursor() );
    releasedMouse = true;
  }

  public void captureMouse()
  {
    canvas.setCursor(blankCursor);
    releasedMouse = false;
  }

  public void resetMousePos()
  {
    if( ! releasedMouse)
    {
      robot.mouseMove((int)origin.getX(), (int)origin.getY());
    }
  }

  //test
  public static void main(String[] args)
  {
    GameWindow gWind = new GameWindow("test window", new Dimension(800, 600),null);
    BufferStrategy bStrateg = gWind.getBufferStrategy();
    Graphics g = bStrateg.getDrawGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(50, 50, 300, 300);
    g.dispose();
    bStrateg.show();
  }
}
