package by_software.mob.player;

import by_software.engine.input.Keyboard;
import by_software.engine.physics.Physics;
import by_software.map.Map;
import by_software.mob.Mob;

public class Player extends Mob{

  //private double posX = 2 ,  posY = 2;
 // private double dirX = 1,  dirY = 0;
//  private double planeX = 0,  planeY = .66; 
 // private double speed = 3 ,rotateSpeed = .5;
  private Keyboard input;
  private long timeOld,timeNew;
 // private double size = .5;
  //private Map map;
  
  public Player(String name,int health, double speed, int acceleration, int damage,double size,double posX, double posY, Keyboard key, Map map,Physics physics)
  {
	  super(name, health, speed, acceleration, damage, size, posX ,posY, map, physics);
	
    //posX = x;
   // posY = y;
    input = key;
    timeOld = System.nanoTime();
    ;
  }
  @Override
  public void update()
  {
  
    double x = 0, y = 0, r = 0;
    
    if( input.up )
    {
      x = speed;
    }  
    if(input.down)
    {
      x = -speed;
    }
    if(input.left)
    {
      y = -speed;
   
    }
    if(input.right)
    {
      y = speed;
    }

    /*normalsied for movement where x and y != 0
    if x and y are both 1 the distance traveled it 40% more then in one axis
    only works for 45 degrees*/

    if(x != 0 && y !=0)
    {
        x *= .707;
        y *= .707;

    }
  
    this.rotate(input.getMouseX());
    input.reset();
    timeNew = System.nanoTime();  
    double timePerTick = (timeNew - timeOld)/ 1000000000.0;
    //System.out.println(timePerTick);
  
    x *=  timePerTick;
    y *=  timePerTick;
    this.moveLocal(x, y);
    timeOld = timeNew;

  }
}