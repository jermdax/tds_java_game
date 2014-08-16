package by_software.entity.mob.player;



import by_software.engine.physics.Physics;
import by_software.map.Map;
import by_software.engine.physics.Vec2d;
import by_software.entity.mob.Mob;

public class PlayerInternet extends Mob{

 
  private long timeOld,timeNew;

  
  public PlayerInternet(String name, int health, double speed, int acceleration, int damage, double size, Vec2d pos, Map map, Physics physics)
  {
	  super(name, health, speed, acceleration, damage, size, pos, map, physics);
      
      timeOld = System.nanoTime();
  }
  @Override
  public void update()
  {
   

  }
  
}