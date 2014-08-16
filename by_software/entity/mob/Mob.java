package by_software.entity.mob;



import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import by_software.Util;
import by_software.engine.physics.Physics;
import by_software.engine.physics.Vec2d;
import by_software.entity.Entity;
import by_software.map.Map;

public class Mob extends Entity
{
 // FAST("fast", 10, 20, 15, 5),STRONG("strong", 25, 6, 10, 15),TOUGH("tough", 40, 5, 10, 10),AGILE("agile", 10, 20, 25, 5);

  protected Vec2d dir = new Vec2d(1d, 0d);
  protected Vec2d plane  = new Vec2d(0d, .66d);
  protected Point2D.Double planeEnd1 = new Point2D.Double(dir.x , -plane.y); 
  protected Point2D.Double planeEnd2 = new Point2D.Double(dir.x , plane.y); 
   
  protected double fieldOfView = 45;
  protected double speed,
  				   rotateSpeed = .5;

  protected int health;
  protected int acceleration;
  protected int damage;
  
 
  public Mob(String name, int health, double speed, int acceleration, int damage, double size, Vec2d pos, Map map, Physics physics)
  {
    super(name, size, pos, map,  physics);

    this.health = health;
    this.speed = speed;
    this.acceleration = acceleration;
    this.damage = damage;
  }
  
  public void update()
  {
	  
  }
  
//will move the mob with respect to direction
  @Override
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
    physics.checkEntityCollision( movePos.x, movePos.y, this);
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
    Point2D.Double oldPlaneEnd1 = new Point2D.Double(planeEnd1.x, planeEnd1.y);
    Point2D.Double oldPlaneEnd2 = new Point2D.Double(planeEnd2.x, planeEnd2.y);
    //rotate player direction
    dir.set( Math.cos(Math.toRadians(angle)) * oldDir.x - Math.sin(Math.toRadians(angle)) * oldDir.y,
             Math.sin(Math.toRadians(angle)) * oldDir.x + Math.cos(Math.toRadians(angle)) * oldDir.y);

    //rotate screen plane
    plane.set( Math.cos(Math.toRadians(angle)) * oldPlane.x - Math.sin(Math.toRadians(angle)) * oldPlane.y,
               Math.sin(Math.toRadians(angle)) * oldPlane.x + Math.cos(Math.toRadians(angle)) * oldPlane.y);
  
    planeEnd1.setLocation( Math.cos(Math.toRadians(angle)) * oldPlaneEnd1.x - Math.sin(Math.toRadians(angle)) * oldPlaneEnd1.y,
               			   Math.sin(Math.toRadians(angle)) * oldPlaneEnd1.x + Math.cos(Math.toRadians(angle)) * oldPlaneEnd1.y);
    planeEnd2.setLocation( Math.cos(Math.toRadians(angle)) * oldPlaneEnd2.x - Math.sin(Math.toRadians(angle)) * oldPlaneEnd2.y,
   						   Math.sin(Math.toRadians(angle)) * oldPlaneEnd2.x + Math.cos(Math.toRadians(angle)) * oldPlaneEnd2.y);
  
  }

  public Point2D.Double insideFieldOfView(Entity that)
  {
	  //draw a line from player to entity and  checks if it  intersect viewPlane
	  
	  //if an Entity pos is outside the cone of vision doent not render it at all
	  //TODO fix ^
	  Line2D.Double viewPlane = new Line2D.Double(planeEnd1.getX() + this.getPosX(), planeEnd1.getY() + this.getPosY(),planeEnd2.getX() + this.getPosX(),planeEnd2.getY() + this.getPosY());
	  Line2D.Double mobLine = new Line2D.Double(this.getPosX(), this.getPosY(), that.getPosX(), that.getPosY());
	  if(viewPlane.intersectsLine(mobLine))
	  {
		  return Util.get_line_intersection(viewPlane, mobLine);  
	  }
	  return null ;
  }
  
  public double calculateScreenX(Point2D.Double pointOnScreen)
  {
	  //translates a point on the plane to point a line on screen 
	  double sx = Util.distance(pointOnScreen.x, pointOnScreen.y, this.getPlaneEnd1().x + this.getPosX(), this.getPlaneEnd1().y + this.getPosY()    ) ;
	  return sx /= Util.distance(this.getPlaneEnd1(), this.getPlaneEnd2());
  }
  
  public double perpendicularDistance(Entity that)
  {
	  Line2D.Double viewPlane = new Line2D.Double(planeEnd1.getX() + this.getPosX(), planeEnd1.getY() + this.getPosY(),planeEnd2.getX() + this.getPosX(),planeEnd2.getY() + this.getPosY());
	  return  viewPlane.ptLineDist(that.getPosX(),that.getPosY()) + plane.length();
  }
  public double distance(Entity that)
  {
	 return Util.distance(this, that);
  }
  
  public double angle(Entity that)
  {
	  double m1 = (0-dir.y) / (0-dir.x);
	  double m2 = (that.getPosY()- this.getPosY())/(that.getPosX() - this.getPosX());

	  double angle  =  Math.atan((m1 - m2) / (1 + m1*m2));
	 // System.out.println(Math.toDegrees(angle));
	  return angle;
  }
  
  public Vec2d getLocalCoord(Entity that)
  {
	  //translates entitys to a point on screen and distance so perspective fps can render it 
	  
	  //NOT USED ANYMORE, Might be useful ? 
	  //Not sure if it works properly
	  
	  double distance = Util.distance(this, that);//depth (y value?)
	  double angle = angle(that);
	  
	  double xValue = Math.sin(angle) * distance;
	  //System.out.println(xValue);
	  return new Vec2d(xValue,distance);
  }
  

  
  public int getHealth()      { return health;       }
  public double getSpeed()    { return speed;        }
  public double getSize()     { return size;         }
  public int getAcceleration(){ return acceleration; }
  public int getDamage()      { return damage;       }

  public Point2D.Double  getPlaneEnd1()     {return planeEnd1;    }
  public Point2D.Double  getPlaneEnd2()     {return planeEnd2;    }
  
  
  public Vec2d  getDir()     {return dir;    }
  public double getDirX()    {return dir.x;  }
  public double getDirY()    {return dir.y;  }
  public Vec2d  getPlane()   {return plane;  }
  public double getPlaneX()  {return plane.x;}
  public double getPlaneY()  {return plane.y;}
} 
