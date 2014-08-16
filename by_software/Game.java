package by_software;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import by_software.engine.input.Keyboard;
import by_software.engine.physics.Physics;
import by_software.engine.physics.Vec2d;
import by_software.engine.render.graphics.GameWindow;
import by_software.engine.render.graphics.TexturePack;
import by_software.engine.render.perspective.Perspective;
import by_software.engine.render.perspective.PerspectiveFirstPerson;
import by_software.engine.render.perspective.PerspectiveTopDown;
import by_software.entity.Entity;
import by_software.entity.EntityTestPillar;
import by_software.entity.mob.player.Player;
import by_software.map.Map;
import by_software.map.MapTile;


public class Game implements Runnable
{
  private static final Dimension WINDOW_SIZE   = new Dimension(800, 450);
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
  //game texturepack, both TDS and FPS textures
  private TexturePack texPack = new TexturePack("by_software/map/sprites/", 
                                                new Dimension(16, 16), new Dimension(128, 128));
  
  //physics 
  private Physics physics;
  //Prespective to render
  private Perspective perspective;
  private Perspective otherPerspective;
  //Player
  private Player player;
  private EntityTestPillar testp;
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

 
  private ArrayList<Entity> entitys;
  
  public Game()
  {
    key = new Keyboard(.25, this);
    window = new GameWindow(TITLE, WINDOW_SIZE,key);
    bStrat = window.getBufferStrategy();
    isRunning = true;
  
    entitys = new ArrayList<>();
    
    map = new Map("by_software/map/MapTest.map");
    physics = new Physics(map,entitys);

    //String name, int health, double speed, int acceleration, int damage, double size, Vec2d pos, Keyboard key, Map map, Physics physics)
    //TODO dont use hard coded values
    player = new Player("Player", 100, 3, 1, 10, .4 , new Vec2d(7,8), key, map, physics);
    testp = new EntityTestPillar(  new Vec2d(5, 3), map, physics);
    
    //load the map
    MapTile.loadAllTiles(texPack);
    
    entitys.add(player);
    entitys.add(testp);
    entitys.add( new EntityTestPillar(  new Vec2d(6, 3), map, physics));
    entitys.add( new EntityTestPillar(  new Vec2d(7, 3), map, physics));
    entitys.add( new EntityTestPillar(  new Vec2d(8, 3), map, physics));
    entitys.add( new EntityTestPillar(  new Vec2d(5, 5), map, physics));
    entitys.add( new EntityTestPillar(  new Vec2d(6, 5), map, physics));
    entitys.add( new EntityTestPillar(  new Vec2d(7, 5), map, physics));
    entitys.add( new EntityTestPillar(  new Vec2d(8, 5), map, physics));
    perspective = new PerspectiveFirstPerson(map,entitys,(int)WINDOW_SIZE.getWidth());
    otherPerspective = new PerspectiveTopDown(map,entitys);
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
       /* else 
        {
          System.out.println("sleep time = " + sleepTime);
        }*/
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
	  key.handleKeys();
	  for(Entity e:entitys)
	  {
		  e.update(); 
	  }
   
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

  public        GameWindow getWindow()     {return window;     }
  public static Dimension  getWindowSize() {return WINDOW_SIZE;}

  public static void main(String[] args){new Game();}
}
