


package by_software.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import by_software.engine.physics.Physics;
import by_software.engine.physics.Vec2d;
import by_software.map.Map;

public class EntityTestPillar extends Entity{

	//TODO fix or delete this class
	//thrown together to get fps to work
	
	public EntityTestPillar( Vec2d pos, Map map,Physics physics)
	{
		super("pillar", .25, pos, map, physics);
		
		try {
			sprite = ImageIO.read(new File("by_software/map/sprites/pillar.png"));
			System.out.println("asdajhd ;" + sprite.getRGB(0, 0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		@Override
		public int getHeight()
		{
			return sprite.getHeight();
		}
		@Override
		public int getWidth()
		{
			return sprite.getWidth();
		}
}
