package by_software.entity;


import java.awt.Color;
import java.awt.image.BufferedImage;

import by_software.engine.physics.Physics;
import by_software.engine.physics.Vec2d;
import by_software.map.Map;

public class Entity
{
 // FAST("fast", 10, 20, 15, 5),STRONG("strong", 25, 6, 10, 15),TOUGH("tough", 40, 5, 10, 10),AGILE("agile", 10, 20, 25, 5);

  protected Vec2d pos;
  protected long timeOld,timeNew;
  protected double size;
  protected Map map;
  protected Physics physics;
  protected int width,height;
  protected BufferedImage sprite;
  protected String name;

  
 
  public Entity(String name, double size, Vec2d pos, Map map, Physics physics)
  {
    this.name = name; 
    this.size = size;
    this.pos = pos;
    this.map = map;
    this.physics = physics;
   
  }
  
  public void update()
  {
	  
  }
  
  //will move the mob with respect to pos
  public void moveLocal(double x, double y)
  {
	 pos.translate(x, y);
  }
  
  //move player in world space
  public void moveWorld(double x, double y)
  {
    pos.set(pos.x + x, pos.y + y);
  }
  public void updateCollisonPlane()
  {
	  
  }
  public int getHeight()
  {
	  
	  return height;
  }
  public int getWidth()
  {
	  
	  return width;
  }
  public int getColor(int x, int y)
  {
	  //System.out.println(x +" " +y);
	  if(sprite == null)
	  return 0;
	  
	 // System.out.println(x +" " +y);
	  return sprite.getRGB(x,y);
  }

  public String getClassName(){ return name; }
  public double getSize()     { return size; }
  public Vec2d  getPos()     {return pos;    }
  public double getPosX()    {return pos.x;  }
  public double getPosY()    {return pos.y;  }

  public void setPos(Vec2d pos)
  {
	  this.pos = pos;  
  }
} 

