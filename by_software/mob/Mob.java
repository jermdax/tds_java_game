package by_software.mob;

import by_software.engine.physics.Physics;
import by_software.map.Map;
import by_software.engine.physics.Vec2d;

public class Mob
{
 // FAST("fast", 10, 20, 15, 5),STRONG("strong", 25, 6, 10, 15),TOUGH("tough", 40, 5, 10, 10),AGILE("agile", 10, 20, 25, 5);

  protected Vec2d pos;
  protected Vec2d dir = new Vec2d(1d, 0d);
  protected Vec2d plane  = new Vec2d(0d, .66d);
  protected double speed,rotateSpeed = .5;
  protected long timeOld,timeNew;
  protected double size;
  protected Map map;
  protected Physics physics;
  
  String name;
  int health;
  int acceleration;
  int damage;
  
 
  public Mob(String name, int health, double speed, int acceleration, int damage, double size, Vec2d pos, Map map, Physics physics){
    this.name = name;
    this.health = health;
    this.speed = speed;
    this.acceleration = acceleration;
    this.damage = damage;
    this.size = size;
    this.pos = pos;
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
  
    Vec2d movePos = new Vec2d(dir.x * x + plane.x * y, dir.y * x + plane.y *y);
    //comment below will mean strafe and walk speed are the same
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

    pos =  physics.checkMapCollision(this.pos.x, this.pos.y, movePos.x, movePos.y, this);
  }
  
  //move player in world space
  public void moveWorld(double x, double y)
  {
    pos.set(pos.x + x, pos.y + y);
  }
  
//Shitty matrix multiplaction 
  public void rotate(double angle)
  {
    //[ cos(a) -sin(a) ][x]
    //[ sin(a)  cos(a) ][y]
    Vec2d oldDir   = new Vec2d(dir.x, dir.y);
    Vec2d oldPlane = new Vec2d(plane.x, plane.y);

    //rotate player direction
    dir.set( Math.cos(Math.toRadians(angle)) * oldDir.x - Math.sin(Math.toRadians(angle)) * oldDir.y,
             Math.sin(Math.toRadians(angle)) * oldDir.x + Math.cos(Math.toRadians(angle)) * oldDir.y);

    //rotate screen plane
    plane.set( Math.cos(Math.toRadians(angle)) * oldPlane.x - Math.sin(Math.toRadians(angle)) * oldPlane.y,
               Math.sin(Math.toRadians(angle)) * oldPlane.x + Math.cos(Math.toRadians(angle)) * oldPlane.y);
  }
  
  public String getClassName(){ return name;         }
  public int getHealth()      { return health;       }
  public double getSpeed()    { return speed;        }
  public double getSize()     { return size;         }
  public int getAcceleration(){ return acceleration; }
  public int getDamage()      { return damage;       }

  public Vec2d  getPos()     {return pos;    }
  public double getPosX()    {return pos.x;  }
  public double getPosY()    {return pos.y;  }
  public Vec2d  getDir()     {return dir;    }
  public double getDirX()    {return dir.x;  }
  public double getDirY()    {return dir.y;  }
  public Vec2d  getPlane()   {return plane;  }
  public double getPlaneX()  {return plane.x;}
  public double getPlaneY()  {return plane.y;}
} 
