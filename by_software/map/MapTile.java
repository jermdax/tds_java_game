package by_software.map;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import by_software.engine.render.graphics.TexturePack;

public class MapTile{
  /*
  Void("","",false,false),
  Wall("wall","",false,false),
  Grass("a","",false,false),
  Floor("adasd","",false,false),
  Fuck("fuck","",false,false);
  */
  
  
  private static MapTile[] tiles; 
  
  public static final MapTile VOID = new MapTile("","",false,false),    
                              WALL = new MapTile("wall","",false,false),
                              GRASS = new MapTile("a","",false,false),
                              FLOOR = new MapTile("adasd","",false,false),
                              FUCK =  new MapTile("fuck","",false,false);
  
  
  private static Integer nextId;
  
  private static final int  TOP_DOWN = 0,
                            XSIDE_FPS = 1,
                            YSIDE_FPS = 2;  

    private static       Dimension FPS_SIZE; 
    private static       Dimension TOP_DOWN_SIZE; 
  
    private static      TexturePack texturePack;
    
    //[0] = topdown
    //[1] = fps x side
    //[2] = fps y side
    private BufferedImage[] textures;

    
    private int id;
    private String name, message; 
    private boolean wall, solid;

    protected MapTile(String name,String message, boolean wall, boolean solid)
    {
      if(nextId == null)
      {//need here to not reset to 0 when a maptile is created
        nextId = 0;
        tiles = new MapTile[0];
      }
      
      this.id = nextId++;
      this.name   = name;
      this.message   = message;
      this.wall   = wall;
      this.solid   = solid;
      tiles =  extendArraySize(tiles);// expands array  by one array has to be full for values() to not get null point excepton
      tiles[this.id] = this;
    }
    
    //have to call this before useing any textures 
    public static void setTexturePack(TexturePack t)
    {
        MapTile.FPS_SIZE = t.getFpsSize();
        MapTile.TOP_DOWN_SIZE = t.getTopDownSize();
        System.out.println(t.getTopDownSize() +" " + MapTile.FPS_SIZE);
        texturePack = t;
    }
    
    //have to call this before useing any textures 
    public static void loadAllTiles()
    {
      for(short i = 0; i < MapTile.values().length; i++)
      {
        
        MapTile.getMapTile(i).textures = texturePack.getTextureSet(i);
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
    
    //add to keep enum methods 
    public static MapTile[] values()
    {
      return tiles;
    }
    
    public static MapTile getMapTile(short id)
    {
      return tiles[id];
    }
    
    public int ordinal()
    {
      return this.id;
    }
    
    public String  getName()                {return this.name;    }
    public String  getMessage()             {return this.message; }
    public boolean isSolid()                {return this.solid;   }
    public boolean isWall()                 {return this.wall;    }
    public static Dimension getTopDownSize(){return TOP_DOWN_SIZE;}
    public static Dimension getFpsSize()    {return FPS_SIZE;     }
    
    private static MapTile[] extendArraySize(MapTile[] array){
        MapTile[] temp = array.clone();
        array = new MapTile[array.length + 1];
        System.arraycopy(temp, 0, array, 0, temp.length);
        return array;
    }
    
}


