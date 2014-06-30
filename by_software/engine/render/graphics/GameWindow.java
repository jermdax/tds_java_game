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
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import by_software.engine.input.Keyboard;

public class GameWindow
{
  private final JFrame frame;
  private BufferStrategy bStrat;
  //used to move mouse
  private Robot robot;
  private final Keyboard input;
  private Canvas canvas;
  private final Point origin;

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


    canvas = new Canvas();
    canvas.setPreferredSize(windowSize);
    canvas.setIgnoreRepaint(true);
     Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB),new Point(0 ,0),"blank cursor");

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
   ;
    frame.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e)
      {
        Point frameLoc = frame.getLocationOnScreen();
        origin.setLocation(frameLoc.getX()+canvas.getWidth()/2, frameLoc.getY() + canvas.getHeight()/2);
        input.setOrigin(origin);
      }
    });

  }

  public JFrame getFrame() {return frame;}
  public BufferStrategy getBufferStrategy() {return bStrat;}

  public void resetMousePos()
  {
    robot.mouseMove((int)origin.getX(), (int)origin.getY());
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