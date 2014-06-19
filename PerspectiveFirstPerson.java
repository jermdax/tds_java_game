import java.awt.Graphics;
import java.awt.Color;

public class PerspectiveFirstPerson implements Perspective
{

  private static Map worldMap;

  public PerspectiveFirstPerson(Map worldMap)
  {
    this.worldMap = worldMap;
  }

  public void render(Graphics gr, int screenWidth, int screenHeight ,Game game, Player player)
  {
    

      gr.setColor(Color.BLACK);//the screen would not reset
      gr.fillRect(0,0,screenWidth,screenHeight);

    for(int i = 0; i <= screenWidth; i++)
    { 

      //cameraX is the relitive position of i int the screen where the left side = -1 and right = 1
      double cameraX = 2 * i / ((double)screenWidth) -1;
      
      //ray starts at player
      double rayPosX = player.getPosX(); 
      double rayPosY = player.getPosY();

      //get slope of ray
      double rayDirX = player.getDirX() + player.getPlaneX() * cameraX;
      double rayDirY = player.getDirY() + player.getPlaneY() * cameraX;
      //System.out.println(rayPosX + "   " + rayPosY + "   \n" + rayDirX + "   " + rayDirY + "   \n" );

      //map coords of ray
      int mapX = (int)rayPosX, mapY = (int)rayPosY;

      //length of ray from current position to next x or y-side
      double sideDistX, sideDistY;

      //length of ray from one x or y-side to next x or y-side
      double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
      double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
      double perpWallDist;
       
      //what direction to step in x or y-direction (either +1 or -1)
      int stepX;
      int stepY;

      int hit = 0; //was there a wall hit?
      int side = 0; //was a NS or a EW wall hit?

         //calculate step and initial sideDist
      if (rayDirX < 0)
      {
        stepX = -1;
        sideDistX = (rayPosX - mapX) * deltaDistX;
      }
      else
      {
        stepX = 1;
        sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
      }
      if (rayDirY < 0)
      {
        stepY = -1;
        sideDistY = (rayPosY - mapY) * deltaDistY;
      }
      else
      {
        stepY = 1;
        sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
      }



      //perform DDA
      while (hit == 0)
      {
        //jump to next map square, OR in x-direction, OR in y-direction
        if (sideDistX < sideDistY)
        {
          sideDistX += deltaDistX;
          mapX += stepX;
          side = 0;
        }
        else
        {
          sideDistY += deltaDistY;
          mapY += stepY;
          side = 1;
        }
        //Check if ray has hit a wall
        if (worldMap.getMapTile(mapX,mapY) > 0) hit = 1;
      } 


        //Calculate distance projected on camera direction (oblique distance will give fisheye effect!)
      if (side == 0)
      {
        perpWallDist = Math.abs((mapX - rayPosX + (1 - stepX) / 2) / rayDirX);
      }
      else
      {
        perpWallDist = Math.abs((mapY - rayPosY + (1 - stepY) / 2) / rayDirY);
      }
      
      int lineHeight = Math.abs((int)(screenHeight /perpWallDist));

      int drawStart = -lineHeight/2 + screenHeight/2;
      if(drawStart < 0)
      {
        drawStart = 0;
      }

      int drawEnd = lineHeight/2 + screenHeight/2;
      if(drawEnd > screenHeight)
      {
        drawEnd = screenHeight;
      }
      

      Color color;
      switch(worldMap.getMapTile(mapX,mapY))
      {
        case 1 : 
          color = Color.RED;
          break; 
        case 2 : 
          color = Color.GREEN;
          break;
        case 3 : 
          color = Color.YELLOW;
          break;
        case 4 : 
          color = Color.ORANGE;
          break;
        default: color = Color.BLUE;


      }
      gr.setColor(color);
      gr.drawLine(i,drawEnd,i,drawStart);


    }


  }



 
}