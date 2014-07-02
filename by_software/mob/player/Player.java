package by_software.mob.player;

import by_software.engine.input.Keyboard;
import by_software.map.Map;
import by_software.engine.physics.Vector2f;

public class Player{

  private double pos;
  private double dir = new Vector2f(1d, 0d);
  private double planeX = 0,  planeY = .66; 
  private double speed = 3 ,rotateSpeed = .5;
  private Keyboard input;
  private long timeOld,timeNew;
  private Map map;
  public Player(double x, double y, Keyboard key, Map map)
  {
    pos = new Vector2f(x, y);
    input = key;
    timeOld = System.nanoTime();
    this.map = map;
  }

  public void update()
  {
  
    double x = 0, y = 0;
    
    if( input.up )
    {
      x = -speed;
    }  
    if(input.down)
    {
      x = speed;
    }
    if(input.left)
    {
      y = speed;
   
    }
    if(input.right)
    {
      y = -speed;
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
    double timePerTick = (timeNew - timeOld)/ 1000000000.0;;
    //System.out.println(timePerTick);
  
    x *=  timePerTick;
    y *=  timePerTick;
    this.moveLocal(x,y);
    timeOld = timeNew;

  }

  //will move the player with respect to direction
  public void moveLocal(double x, double y)
  {
   
    double angle = Math.atan(dir.getY()/dir.getX());
    double sin = Math.sin(angle);
    double cos = Math.cos(angle);
   // System.out.println(dirY + "  " + dirX + " " + dirY *dirX);
    //might have to do this for Y also
    
    Vector2f movePos = new Vector2f();
    if( dir.getX() <0)
    {
      movePos.setX(cos * x - sin * y);
      movePos.setY(sin * x + cos * y);
    }  
    else
    {
      movePos.setX( -(cos * x - sin * y) );
      movePos.setY( -(sin * x + cos * y) );
    }
    if(map.checkCollision( pos.getX() + movePos.getX(), pos.getY() ))
    {
      movePosX = 0;
    }
    if(map.checkCollision( pos.getX(), pos.getY() + movePos.getY() ))
    {
      movePosY = 0;
    }
    
    pos.add(movePos);
    
    if(pos.getX() < 0)
    {
      posX += map.getMapSizeX();
    }  
    if(pos.getX() > map.getMapSizeX())
    {
      posX -= map.getMapSizeX();
    }
    
    if(pos.getY() < 0)
    {
      posY += map.getMapSizeY();
    }  
    if(pos.getY() > map.getMapSizeY())
    {
      posY -= map.getMapSizeY();
    }
    
  }

  //move player in world space
  public void moveWorld(double x, double y)
  {
    pos.set(x, y);
  }

  //Shitty matrix multiplaction 
  public void rotate(double angle)
  {
    //[ cos(a) -sin(a) ][x]
    //[ sin(a)  cos(a) ][y]
    //make a (deep) copy of dir using the Vector copy constructor
    Vector2f oldDir = new Vector2f(dir);
    double oldPlaneX = planeX;
    double oldPlaneY = planeY;

    //rotate player direction
    dir.setX( Math.cos(Math.toRadians(angle)) * oldDir.getX() - Math.sin(Math.toRadians(angle)) * oldDir.getY() );
    dir.setY( Math.sin(Math.toRadians(angle)) * oldDir.getX() + Math.cos(Math.toRadians(angle)) * oldDir.getY() );

    //rotate screen plane
    planeX = Math.cos(Math.toRadians(angle)) * oldPlaneX - Math.sin(Math.toRadians(angle)) * oldPlaneY;
    planeY = Math.sin(Math.toRadians(angle)) * oldPlaneX + Math.cos(Math.toRadians(angle)) * oldPlaneY;
  }

  public double getPosX()    {return pos.getX();}
  public double getPosY()    {return pos.getX();}
  public double getDirX()    {return dir.getX();}
  public double getDirY()    {return dir.getY();}
  public double getPlaneX()  {return this.planeX;}
  public double getPlaneY()  {return this.planeY;}

}