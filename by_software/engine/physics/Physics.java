package by_software.engine.physics;

import by_software.map.Map;
import by_software.mob.Mob;

public class Physics {
	private Map map;
	
	public Physics(Map map)
	{
		this.map = map;
	}
	
	public double[] checkCollision(double posX, double posY, double movX, double movY,Mob mob)
	{
		double  newX = posX + movX,
				newY = posY + movY,
				size = mob.getSize(),
				sizeX = size,
				sizeY = size;
		
		
		int mapX = (int) Math.round(newX),
			mapY = (int) Math.round(newY);

			if(movX <0)
			{
				sizeX = - sizeX;
			}
			if(movY <0)
			{
				sizeY = - sizeY;
			}
			

			//double mapX = 4;
			//double mapY = 5;
			
		
		
			if(map.checkCollision(newX + sizeX, posY))
			{
				newX += sizeX;
				newX = Math.round(newX) - sizeX;   
			}
			if(map.checkCollision(posX, newY + sizeY))
			{
				newY += sizeY;
				newY = Math.round(newY) - sizeY;
			}
			if(map.checkCollision(newX - sizeX, posY))
			{
				newX -= sizeX;
				newX = Math.round(newX) + sizeX;   
			}
			if(map.checkCollision(posX, newY - sizeY))
			{
				newY -= sizeY;
				newY = Math.round(newY) + sizeY;
			}
				
		
			if(map.checkCollision(mapX,mapY) || map.checkCollision(mapX - 1,mapY) ||map.checkCollision(mapX,mapY - 1) ||map.checkCollision(mapX-1 ,mapY -1) )
			{	if( Math.abs((mapX - newX)*(mapX - newX) + (mapY - newY)*(mapY - newY)) < Math.abs(size*size))
				{
				System.out.println("yep");
			
				double slope = (mapY - newY) / (mapX - newX);
				double oldX = newX,oldY = newY;
				// System.out.println(newX +" " + newY+ " " +Math.toDegrees(Math.atan(slope)));
				   
				   newX = mapX;
				   newY = mapY;
				   
				   
				   
				 //  System.out.println(newX +" " + newY+ " " +Math.toDegrees(Math.atan(slope)));
			
				   if( oldX < mapX )
					{ 
					 //  System.out.println("1");
						newX -= Math.cos(Math.atan(slope)) * size;
						newY -= Math.sin(Math.atan(slope)) * size;
					} 
				   else
					{ 
						newX += Math.cos(Math.atan(slope)) * size;
						newY += Math.sin(Math.atan(slope)) * size;
					} 

				 //  System.out.println(newX +" " + newY+ " " +Math.toDegrees(Math.atan(slope)));
				
				}
			
			}
			
		
	
		
		
		
		
		
		
				 
	    double[] pos = {newX , newY};
	    return pos;
	}
}
