package by_software.mob;

import by_software.engine.physics.Physics;
import by_software.map.Map;

public class Mob
{
 // FAST("fast", 10, 20, 15, 5),STRONG("strong", 25, 6, 10, 15),TOUGH("tough", 40, 5, 10, 10),AGILE("agile", 10, 20, 25, 5);

  protected double posX,  posY;
  protected double dirX = 1,   dirY = 0;
  protected double planeX = 0,  planeY = .66; 
  protected double speed,rotateSpeed = .5;
  protected long timeOld,timeNew;
  protected double size;
  protected Map map;
  protected Physics physics;
  
  String name;
  int health;
  int acceleration;
  int damage;
  
 
  public Mob(String name, int health, double speed, int acceleration, int damage, double size,double posX ,double posY,Map map,Physics physics){
    this.name = name;
    this.health = health;
    this.speed = speed;
    this.acceleration = acceleration;
    this.damage = damage;
    this.size = size;
    this.posX = posX;
    this.posY = posY;
    this.map = map;
    this.physics = physics;
  }
  
  
  
  public void update()
  {
	  
  }
  
//will move the mob with respect to direction
  public void moveLocal(double x, double y)
  {
   
	  
	  //#######################
	  // Don't remove commented code!
	  // mobs will move slower left and right as is
	  // is may or may not be ok
	  // should speed up the method though
	  //#######################
	  
   // double angle = Math.atan(dirY/dirX);
   // double sin = Math.sin(angle);
   // double cos = Math.cos(angle);
   // System.out.println(dirY + "  " + dirX + " " + dirY *dirX);
  
    
    double movePosX = dirX * x + planeX *y,
    	   movePosY = dirY * x + planeY *y;
   /* if( dirX <0)
    {
      movePosX = cos * x - sin * y;
      movePosY = sin * x + cos * y;
    }  
    else
    {
      movePosX = -(cos * x - sin * y);
      movePosY = -(sin * x + cos * y);
    }*/
   
    double[] pos =  physics.checkMapCollision(posX,posY, movePosX,movePosY,this);
    
    posX = pos[0];
    posY = pos[1];
 
  }
  
  //move player in world space
  public void moveWorld(double x, double y)
  {
    posX = x;
    posY = y;
  }
  
//Shitty matrix multiplaction 
  public void rotate(double angle)
  {
    //[ cos(a) -sin(a) ][x]
    //[ sin(a)  cos(a) ][y]
    double oldDirX = dirX;
    double oldDirY = dirY;
    double oldPlaneX = planeX;
    double oldPlaneY = planeY;

    //rotate player direction
    dirX = Math.cos(Math.toRadians(angle)) * oldDirX - Math.sin(Math.toRadians(angle)) * oldDirY;
    dirY = Math.sin(Math.toRadians(angle)) * oldDirX + Math.cos(Math.toRadians(angle)) * oldDirY;

    //rotate screen plane
    planeX = Math.cos(Math.toRadians(angle)) * oldPlaneX - Math.sin(Math.toRadians(angle)) * oldPlaneY;
    planeY = Math.sin(Math.toRadians(angle)) * oldPlaneX + Math.cos(Math.toRadians(angle)) * oldPlaneY;
  }
  
  public String getClassName(){ return this.name; }
  
  public int getHealth(){ return this.health; }
  
  public double getSpeed(){ return this.speed; }
  
  public double getSize(){ return this.size; }
  
  public int getAcceleration(){ return this.acceleration; }
  
  public int getDamage(){ return this.damage; }
} 
