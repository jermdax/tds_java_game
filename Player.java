public class Player{

  private double posX = 2 ,  posY = 2;
  private double dirX = 1,  dirY = 0;
  private double planeX = 0,  planeY = .66; 
  private double speed = .01 ,rotateSpeed = .5;
  private Keyboard input;

  public Player(double x, double y, Keyboard key)
  {
    posX = x;
    posY = y;
    input = key;
  }

  public void update()
  {

    double x = 0, y = 0, r = 0;

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
    //rotate controls till mouse is done
   /* if(input.q)
    {
      r = -speed;
    }
    if(input.e)
    {
     r = speed;
    }
    */
    /*normalsied for movement where x and y != 0
    if x and y are both 1 the distance traveled it 40% more then in one axis
    only works for 45 degrees*/

    if(x != 0 && y !=0)
    {
        x *= .707;
        y *= .707;

    }

    this.rotate(input.mouseX);
    input.reset();
    this.moveLocal(x,y);

  }

  //will move the player with respect to direction
  public void moveLocal(double x, double y)
  {
   
    double angle = Math.atan(dirY/dirX);
    double sin = Math.sin(angle);
    double cos = Math.cos(angle);
   // System.out.println(dirY + "  " + dirX + " " + dirY *dirX);
    //might have to do this for Y also
    if( dirX <0)
    {
      posX += cos * x - sin * y;
      posY += sin * x + cos * y;
    }  
    else
    {
       posX -= cos * x - sin * y;
       posY -= sin * x + cos * y;
    }
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

  public double getPosX()    {return this.posX;}
  public double getPosY()    {return this.posY;}
  public double getDirX()    {return this.dirX;}
  public double getDirY()    {return this.dirY;}
  public double getPlaneX()  {return this.planeX;}
  public double getPlaneY()  {return this.planeY;}

}