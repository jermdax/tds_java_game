package by_software.map;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import by_software.engine.render.graphics.TexturePack;

public enum MapTile{
	
	Void("","",false,false),
	Wall("wall","",false,false),
	Grass("a","",false,false),
	Floor("adasd","",false,false),
	Fuck("fuck","",false,false);
	
	
	
	private static final int	TOP_DOWN = 0,
								XSIDE_FPS = 1,
								YSIDE_FPS = 2;	

    private static       Dimension FPS_SIZE; 
    private static       Dimension TOP_DOWN_SIZE; 
	
    private static 		 TexturePack texturePack;
    
    //[0] = topdown
    //[1] = fps x side
    //[2] = fps y side
    private BufferedImage[] textures;

    private String name, message; 
    private boolean wall, solid;
    
    
    private MapTile(String name,String message, boolean wall, boolean solid)
    {
      this.name 	= name;
      this.message 	= message;
      this.wall 	= wall;
      this.solid 	= solid;
  
    }
    
    //have to call this before useing any textures 
    public static void setTexturePack(TexturePack t)
    {
        MapTile.FPS_SIZE = t.getTopDownSize();
        System.out.println(t.getTopDownSize() +" " + MapTile.FPS_SIZE);
    	texturePack = t;
    }
    
    //have to call this before useing any textures 
    public static void loadAllTiles()
    {
    	for(int i = 0; i < MapTile.values().length;i++)
    	{
    		
    		MapTile.values()[i].textures = texturePack.getTextureSet(i);
    	}
    }
    
    public static void loadAllTiles(TexturePack t)
    {	
    	setTexturePack(t);
  
    	for(int i = 0; i < MapTile.values().length;i++)
    	{
    		MapTile.values()[i].textures = texturePack.getTextureSet(i);
    	}
    }
  
    public BufferedImage getTopSprite()
    {
      return this.textures[TOP_DOWN];
    }

    public BufferedImage getXSideSprite()
    {
      return this.textures[XSIDE_FPS];
    }
    
    public BufferedImage getYSideSprite()
    {
      return this.textures[YSIDE_FPS];
    }
    
    public BufferedImage getXSideSprite(int x)
    {
      return this.textures[XSIDE_FPS].getSubimage(x, 0, 1, FPS_SIZE.height );
    }
    
    public BufferedImage getYSideSprite(int x)
    {
      return this.textures[YSIDE_FPS].getSubimage(x, 0, 1, FPS_SIZE.height );
    }
    
  
    
    
    public String  getName()	     {return this.name;}
    public String  getMessage()      {return this.message;}
    public boolean isSolid() 	     {return this.solid;}
    public boolean isWall() 	     {return this.wall;}
    public static Dimension getTopDownSize(){return TOP_DOWN_SIZE;}
    public static Dimension getFpsSize()    {return FPS_SIZE;}
}