package by_software.engine.physics;

import java.util.ArrayList;

import by_software.entity.Entity;
import by_software.map.Map;

public class Physics {
	private Map map;
	private ArrayList<Entity> entitys;
	
	public Physics(Map map, ArrayList<Entity> entitys)
	{
		this.map = map;
		this.entitys = entitys;
	}
	
	//keep mob size away from walls , doent not work if mob is bigger the 1 map unit
	
	
	public void checkEntityCollision( double movX, double movY,Entity entity)
	{
		for(Entity e: entitys)
		{
			if(e != entity)
			{
				
				 entity.setPos(pointCollision(e.getSize(),e.getPosX(), e.getPosY(),entity.getPosX(), entity.getPosY(),entity) );
			}
		}
	}
	
	public Vec2d checkMapCollision(double posX, double posY, double movX, double movY,Entity entity)
	{
		double
        newX = posX + movX,
				newY = posY + movY,
				size = entity.getSize();
			
		
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
				return this.pointCollision(0,mapX, mapY, newX, newY, entity);
			}				 
	    return new Vec2d(newX , newY);
	}
	
	
	public Vec2d pointCollision(double radius ,double pointX, double pointY,double newX, double newY,Entity entity)
	{
		double 	size = entity.getSize();
		
		if( Math.abs((pointX - newX)*(pointX - newX) + (pointY - newY)*(pointY - newY)) < Math.abs((size+ radius)*(size + radius)))
		{
			double slope = (pointY - newY) / (pointX - newX);
			double oldX = newX,oldY = newY;
			
			newX = pointX;
			newY = pointY;

		   if( oldX < pointX )
			{ 
			
				newX -= Math.cos(Math.atan(slope)) * (size+ radius);
				newY -= Math.sin(Math.atan(slope)) * (size+ radius);
			} 
		    else
			{ 
				newX += Math.cos(Math.atan(slope)) * (size+ radius);
				newY += Math.sin(Math.atan(slope)) * (size+ radius);
			} 
		}
		return new Vec2d(newX, newY);
	}
	
}
