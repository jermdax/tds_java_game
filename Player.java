

public class Player{

	private double posX = 2 ,	posY = 2;
	private double dirX = 1,	dirY = 0;
	private double planeX = 0,	planeY = .66; 

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

	public double getPosX()		{return this.posX;}
	public double getPosY()		{return this.posY;}
	public double getDirX()		{return this.dirX;}
	public double getDirY()		{return this.dirY;}
	public double getPlaneX()	{return this.planeX;}
	public double getPlaneY()	{return this.planeY;}

}