package by_software;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import by_software.engine.input.Keyboard;
import by_software.engine.render.graphics.GameWindow;
import by_software.engine.render.perspective.*;
import by_software.map.Map;
import by_software.mob.player.Player;


public class Game implements Runnable
{
  private static final Dimension WINDOW_SIZE   = new Dimension(800, 600);
  //the preferred frames per second
  private static final int       DESIRED_FPS   = 120;
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
  //Prespective to render
  private Perspective perspective;
  private Perspective otherPerspective;
  //Player
  private Player player;
  //Map
  private Map map;
  //keyboard input
  public Keyboard key;

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
    key = new Keyboard(.25, this);
    
   
    window = new GameWindow(TITLE, WINDOW_SIZE,key);
    bStrat = window.getBufferStrategy();
    isRunning = true;
    map = new Map("by_software/map/MapTest.map");
    player = new Player(2,2,key,map);
    perspective = new PerspectiveFirstPerson(map);
    otherPerspective = new PerspectiveTopDown(map);
    run();
  }

  public void swapPerspective()
  {
    Perspective tmp = perspective;
    perspective = otherPerspective;
    otherPerspective = tmp;
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
    key.getInput();
  }

  private void update()
  {
    //update the game logic based on input and time (AI?, projectiles)
    player.update();
    key.handleKeys();
    window.resetMousePos();

  }

  private void playSound()
  {

  }

  private void render()
  {
    Graphics graphics = bStrat.getDrawGraphics();
    try{

        perspective.render(graphics,(int)WINDOW_SIZE.getWidth(),(int)WINDOW_SIZE.getHeight(),this,player);

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

  public GameWindow getWindow(){return window;}

  public static void main(String[] args){new Game();}
}
