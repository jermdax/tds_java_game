package by_software.entity.mob.player;

import by_software.engine.input.Keyboard;
import by_software.engine.physics.Physics;
import by_software.map.Map;
import by_software.engine.physics.Vec2d;
import by_software.entity.mob.Mob;

public class Player extends Mob{

 
  private Keyboard input;
  private long timeOld,timeNew;

  
  public Player(String name, int health, double speed, int acceleration, int damage, double size, Vec2d pos, Keyboard key, Map map, Physics physics)
  {
	super(name, health, speed, acceleration, damage, size, pos, map, physics);
    input = key;
    timeOld = System.nanoTime();
  }
  @Override
  public void update()
  {
    double x = 0, y = 0, r = 0;
    
    if(input.up   ){x =  speed;}  
    if(input.down ){x = -speed;}
    if(input.left ){y = -speed;}
    if(input.right){y =  speed;}

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
    //TODO should be in a Timer class? or use a method from Game? 
    timeNew = System.nanoTime();  
    double delta = (timeNew - timeOld)/ 1000000000.0;
    //System.out.println(timePerTick);
  
    x *=  delta;
    y *=  delta;
    this.moveLocal(x, y);
    timeOld = timeNew;

  }
  
}