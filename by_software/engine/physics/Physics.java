package by_software.engine.physics;

import by_software.map.Map;
import by_software.mob.Mob;
import by_software.engine.physics.Vec2d;

public class Physics {
	private Map map;
	
	public Physics(Map map)
	{
		this.map = map;
	}
	
	//keep mob size away from walls , doent not work if mob is bigger the 1 map unit
	public Vec2d checkMapCollision(double posX, double posY, double movX, double movY,Mob mob)
	{
		double
        newX = posX + movX,
				newY = posY + movY,
				size = mob.getSize();
			
		
		//get nearest map tile 
		int 
      mapX = (int) Math.round(newX),
			mapY = (int) Math.round(newY);

			//checks collisions in cardinal directions
			if(map.checkCollision(newX + size, posY))
			{
				newX += size;
				newX = Math.round(newX) - size;   
			}
			if(map.checkCollision(posX, newY + size))
			{
				newY += size;
				newY = Math.round(newY) - size;
			}
			if(map.checkCollision(newX - size, posY))
			{
				newX -= size;
				newX = Math.round(newX) + size;   
			}
			if(map.checkCollision(posX, newY - size))
			{
				newY -= size;
				newY = Math.round(newY) + size;
			}
				
			//this test will need to be change to test all map tile with in player size
			//(or test the nears map tile outside size radius and spawn mob at least size away from anything),
			//for it to work with mob bigger the 1 unit (1.2 kinda works, bigger than that acts weird)
			//what it dose not is checks the 4 map squares around the nearest point 
			
			if(map.checkCollision(mapX, mapY) || map.checkCollision(mapX - 1, mapY) ||map.checkCollision(mapX, mapY - 1) ||map.checkCollision(mapX-1, mapY -1) )
			{	
				return this.pointCollision(mapX, mapY, newX, newY, mob);
			}				 
	    return new Vec2d(newX , newY);
	}
	
	
	public Vec2d pointCollision(double pointX, double pointY,double newX, double newY,Mob mob)
	{
		double 	size = mob.getSize();
		
		if( Math.abs((pointX - newX)*(pointX - newX) + (pointY - newY)*(pointY - newY)) < Math.abs(size*size))
		{
			double slope = (pointY - newY) / (pointX - newX);
			double oldX = newX,oldY = newY;
			
			newX = pointX;
			newY = pointY;

		   if( oldX < pointX )
			{ 
			
				newX -= Math.cos(Math.atan(slope)) * size;
				newY -= Math.sin(Math.atan(slope)) * size;
			} 
		    else
			{ 
				newX += Math.cos(Math.atan(slope)) * size;
				newY += Math.sin(Math.atan(slope)) * size;
			} 
		}
		return new Vec2d(newX, newY);
	}
	
}
