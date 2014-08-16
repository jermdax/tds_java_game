package by_software;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import by_software.engine.physics.Vec2d;
import by_software.entity.Entity;

public class Util {
	
	
	public static double distance( double x1,double y1,double x2,double y2 )
	{
		double result = (x2 - x1)*(x2 - x1);
		  result += (y2 - y1) * (y2 - y1);
		  return Math.sqrt(result);	 
	}
	
	public static double distance(Entity a, Entity b)
	{
		return distance(a.getPos(),b.getPos());
	}
	public static double distance(Point2D a, Point2D b)
	{
		return distance(a.getX(), a.getY(), b.getX(), b.getY());
	}
	
	public static double distance(Vec2d a, Vec2d b)
	{
		  
		// double result = (b.x - a.x)*(b.x - a.x);
		  //result += (b.y - a.y) * (b.y - a.y);
		  return distance(a.x, a.y, b.x, b.y);	  
	}
	
	public static Point2D.Double get_line_intersection(Line2D.Double pLine1, Line2D.Double pLine2)
	{
	    Point2D.Double
	        result = null;

	    double
	        s1_x = pLine1.x2 - pLine1.x1,
	        s1_y = pLine1.y2 - pLine1.y1,

	        s2_x = pLine2.x2 - pLine2.x1,
	        s2_y = pLine2.y2 - pLine2.y1,

	        s = (-s1_y * (pLine1.x1 - pLine2.x1) + s1_x * (pLine1.y1 - pLine2.y1)) / (-s2_x * s1_y + s1_x * s2_y),
	        t = ( s2_x * (pLine1.y1 - pLine2.y1) - s2_y * (pLine1.x1 - pLine2.x1)) / (-s2_x * s1_y + s1_x * s2_y);

	    if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
	    {
	        // Collision detected
	        result = new Point2D.Double(
	             (pLine1.x1 + (t * s1_x)),
	             (pLine1.y1 + (t * s1_y)));
	    }   // end if

	    return result;
	}
}
