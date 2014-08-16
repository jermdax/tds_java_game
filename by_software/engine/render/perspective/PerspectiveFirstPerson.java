package by_software.engine.render.perspective;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import by_software.Game;
import by_software.Util;
import by_software.engine.physics.Vec2d;
import by_software.entity.Entity;
import by_software.entity.mob.player.Player;
import by_software.map.Map;
import by_software.map.MapTile;

public class PerspectiveFirstPerson implements Perspective
{

  private static Map worldMap;
  private BufferedImage image;
  private ArrayList<Entity> entitys;
  private int [] pixels;
  private double[] depthBuffer; 
  public static int transparentColor = -65281;
  
  public PerspectiveFirstPerson(Map worldMap, ArrayList<Entity> entitys, int screenWidth)
  {
    this.worldMap = worldMap;
    this.entitys = entitys;
    this.depthBuffer = new double[screenWidth];
    
    
  }

  public void render(Graphics gr, int screenWidth, int screenHeight ,Game game, Player player)
  {
	  if(image == null)
	  {
		  image = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
		  pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	  }

	 
    gr.setColor(Color.BLACK);//the screen would not reset
    gr.fillRect(0,0,screenWidth,screenHeight);

    
    for(int x = 0; x < screenWidth; x++)
    { 

      //cameraX is the relitive position of i int the screen where the left side = -1 and right = 1
      double cameraX = 2 * x / ((double)screenWidth) -1;
      
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
        if (worldMap.getMapTile(mapX,mapY).isWall()) hit = 1;
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
      depthBuffer[x] = perpWallDist;
      
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
      
      //test//
      
      double wallX; //where exactly the wall was hit
      if (side == 1) {wallX = rayPosX + ((mapY - rayPosY + (1 - stepY) / 2) / rayDirY) * rayDirX;}
      else      {wallX = rayPosY + ((mapX - rayPosX + (1 - stepX) / 2) / rayDirX) * rayDirY;}
      wallX -= ((int)(wallX));
       
      //x coordinate on the texture
      int texX = (int)(wallX * MapTile.getFpsSize().width );
      if(side == 0 && rayDirX > 0) texX = MapTile.getFpsSize().width - texX - 1;
      if(side == 1 && rayDirY < 0) texX = MapTile.getFpsSize().width - texX - 1;
      //texX = MapTile.getFpsSize().width - texX - 1;
      //Color color;
      BufferedImage ImageStrip;
      if(side == 0)
      {
        ImageStrip = this.worldMap.getMapTile(mapX, mapY).getXSideSprite(texX);
      }
      else
      {
    	ImageStrip = this.worldMap.getMapTile(mapX, mapY).getYSideSprite(texX);
      }
      
      
      
     for(int y = drawStart; y<drawEnd; y++)
      {
    	 
    	 int d = y * 256 - screenHeight * 128 + lineHeight * 128;  //256 and 128 factors to avoid floats
         int texY = (((d * MapTile.getFpsSize().height) / lineHeight) / 256);
    	if(texY < MapTile.getFpsSize().width && texY >= 0)
    	{
    	
         
	   
	        int color = ImageStrip.getRGB(0, texY);
	        //make color darker for y-sides: R, G and B byte each divided through two with a "shift" and an "and"
	        if((x + y*screenWidth) >= pixels.length)
	        {
	        	
	        	break;
	        }
	        else
	        {
	        	pixels[x + y*screenWidth] = color;
	        }
	     } 
      }
    }
    
    ///////
    //draw entity
    //lot of math that barely works 
    for(Entity e: entitys)
    {
    
    	if(e != player)
    	{
    		
    		Point2D.Double entityLocal = player.insideFieldOfView(e);
    		if(entityLocal != null)
    		{
    			
    			double sx = player.calculateScreenX(entityLocal) * screenWidth;
    			double distance =   player.perpendicularDistance(e);
    			
        		

				int lineHeight = Math.abs((int)(screenHeight / distance));
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
		      
	  			int width = (int)( (double)lineHeight/(double)e.getHeight() *(double) e.getWidth()); 
	  			double texX = 0;
	  			
	  			for(int x = (int)(sx - width/2); x <sx + width/2;x++)
				{	
	  				
	  				if(x >=0 && x < screenWidth)
					{
	  					if(texX >= e.getWidth())
	  					{
	  						texX = e.getWidth()-1;
	  					}
	  					
	  					if(distance < depthBuffer[x])
	  					{
				  			for(int y = drawStart; y < drawEnd;y++)
		    				{	
				  				int d = y * 256 - screenHeight * 128 + lineHeight * 128;  //256 and 128 factors to avoid floats
				  		        int texY = (((d * e.getHeight()) / lineHeight) / 256);
				  		       
				  		        if(texY < e.getHeight() && texY >= 0)
				  		    	{
					  		        int color = e.getColor((int)texX, texY);
					  		         
					  		        if(color != transparentColor)
					  		        {
					  		        	pixels[(int)x + y*screenWidth] = color;
					  		        }
				  		    	}
	    	    			}
    					}
    				}
	  			
	  				texX += (double)e.getWidth()/(double)width ;
				}
    		
    		}
    	}
    	
    	
    }
    //////
    
    gr.drawImage(image, 0, 0, null);

    
    clear();
    

    }


  	public void clear()
  	{
  		for(int i = 0; i < pixels.length  ;i++)
  	    {
  	    		pixels[i] = 0;
  	    	
  	    }
  	}
  }



 
