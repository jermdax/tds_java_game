import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import javax.swing.SwingUtilities;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    canvas.addKeyListener(input);
    canvas.addMouseMotionListener(input);
    frame.add(canvas);

    frame.pack();
    frame.setVisible(true);

    canvas.createBufferStrategy(2);
    bStrat = canvas.getBufferStrategy();
    origin = new Point();
    frame.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e)
      {
        origin.setLocation(frame.getLocationOnScreen());
        origin.translate(frame.getWidth()/2,frame.getHeight()/2);
        input.setOrigin(origin);
      }
    });

  }

  public JFrame getFrame() {return frame;}
  public BufferStrategy getBufferStrategy() {return bStrat;}

  public void resetMousePos()
  {
    robot.mouseMove((int)origin.getX(),(int)origin.getY());
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