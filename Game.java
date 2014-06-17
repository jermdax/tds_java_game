import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game implements Runnable
{
  private static final Dimension WINDOW_SIZE   = new Dimension(800, 600);
  //the preferred frames per second
  private static final int       DESIRED_FPS   = 60;
  //game name
  public static  final String    TITLE         = "Some_Game";
  //nanoSecs in a sec
  private static final long      NANO_PER_SEC  = 1000000000;
  //nanoSecs in a tick
  private static final long      NANO_PER_TICK = NANO_PER_SEC / DESIRED_FPS;

  //create the window that will be rendered to
  private GameWindow     window;
  //BufferStrategy
  private BufferStrategy bStrat;
  //the game is running
  private boolean        isRunning        = false;
  //the background image of the screen
  private BufferedImage  background;

  //loop timing stuff
  private int  fps       = 0;
  private int  frames    = 0;
  private long totalTime = 0;
  private long lastTime  = 0;
  private long curTime   = System.currentTimeMillis();
  private long lastNano  = 0;
  private long curNano   = 0;


  public Game()
  {
    window = new GameWindow(TITLE, WINDOW_SIZE);
    bStrat = window.getBufferStrategy();
    isRunning = true;
    run();
  }

  public void run()
  {
    while(isRunning)
    {
      //get the nanoTime of before the loop execution 
      lastNano = System.nanoTime();
      countFps();
      getInput();
      update();
      playSound();
      render();
      try{
        //get how long the loop took to execute
        curNano = System.nanoTime();
        //sleep for that much time (sleep is in mili not nano)
        long sleepTime = (NANO_PER_TICK - (curNano - lastNano)) / 1000000;
        //only speep if wee arent behind schedule
        if(sleepTime > 0)
        {
          Thread.sleep(sleepTime);
        }
        else 
        {
          System.out.println("not sleeping");
        }
      }catch(Exception e){
        System.err.println("Problem with the thread sleep");
        e.printStackTrace();
        System.exit(1);
      }
    }
  }

  private void getInput()
  {
    //readKeyboard
  }

  private void update()
  {
    //update the game logic based on input and time (AI?, projectiles)
  }

  private void playSound()
  {

  }

  private void render()
  {
    Graphics graphics = bStrat.getDrawGraphics();
    try{

      //TODO remove this
      Random rand = new Random(5);
      for(int i = 0; i < WINDOW_SIZE.getWidth(); i+=10)
        for(int j = 0; j < WINDOW_SIZE.getHeight(); j+=10){
          graphics.setColor(new Color(rand.nextInt()));
          graphics.fillRect(i, j, 10, 10);
                }



      if( ! bStrat.contentsLost())
        bStrat.show();
    }finally{
      if(graphics != null)
        graphics.dispose();
    }
  }

  private void countFps()
  {
    lastTime = curTime;
    curTime = System.currentTimeMillis();
    totalTime += curTime - lastTime;
    if(totalTime > 1000)
    {
      totalTime -= 1000;
      fps = frames;
      frames = 0;
      //TODO reomve below print
      System.out.println("fps = " + fps);
    }
    ++frames;
  }

  public static void main(String[] args){new Game();}
}
