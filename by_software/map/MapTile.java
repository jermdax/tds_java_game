package by_software.map;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.text.Format;

import by_software.engine.render.graphics.TexturePack;

public class MapTile{
 
  
  
  private static MapTile[] tiles; 
  
  public static final MapTile 
    WALL_TOP_LEFT       = new MapTile("wall","",true,true,true,true,0,0),
    WALL_TOP_CENTER     = new MapTile("wall","",true,true,true,true,1,0),
    WALL_TOP_RIGHT      = new MapTile("wall","",true,true,true,true,2,0), 

    WALL_CENTER_LEFT    = new MapTile("wall","",true,true,true,true,0,1),
    WALL_CENTER_CENTER  = new MapTile("wall","",true,true,true,true,1,1),
    WALL_CENTER_RIGHT   = new MapTile("wall","",true,true,true,true,2,1), 

    WALL_BOTTOM_LEFT    = new MapTile("wall","",true,true,true,true,0,2),
    WALL_BOTTOM_CENTER  = new MapTile("wall","",true,true,true,true,1,2),
    WALL_BOTTOM_RIGHT   = new MapTile("wall","",true,true,true,true,2,2), 

    WALL_END_LEFT       = new MapTile("wall","",true,true,true,true,3,2),
    WALL_END_RIGHT      = new MapTile("wall","",true,true,true,true,4,2),

    WALL_END_TOP        = new MapTile("wall","",true,true,true,true,3,3),
    WALL_END_BOTTOM     = new MapTile("wall","",true,true,true,true,4,3),

    WALL_PILLAR         = new MapTile("wall","",true,true,true,true,0,3),
    WALL_VERTICAL       = new MapTile("wall","",true,true,true,true,1,3),
    WALL_HORIZONTAL     = new MapTile("wall","",true,true,true,true,2,3),
    WALL_4_CONNECTOR    = new MapTile("wall","",true,true,true,true,7,0),

    WALL_BLOCK          = new MapTile("wall","",true,true,true,false,0,7),
    WALL_BLOCK_BLUE     = new MapTile("wall","",true,true,true,false,1,7),
    WALL_BLOCK_RED      = new MapTile("wall","",true,true,true,false,2,7),



    WALL_7_CONNECTOR_0  = new MapTile("wall","7",true,true,true,true,3,0), 
    WALL_7_CONNECTOR_90 = new MapTile("wall","7",true,true,true,true,4,0),  
    WALL_7_CONNECTOR_180= new MapTile("wall","7",true,true,true,true,4,1), 
    WALL_7_CONNECTOR_270= new MapTile("wall","7",true,true,true,true,3,1), 

    WALL_L_CONNECTOR_0  = new MapTile("wall","L",true,true,true,true,5,0), 
    WALL_L_CONNECTOR_90 = new MapTile("wall","L",true,true,true,true,6,0),  
    WALL_L_CONNECTOR_180= new MapTile("wall","L",true,true,true,true,6,1),
    WALL_L_CONNECTOR_270= new MapTile("wall","L",true,true,true,true,5,1),

    WALL_T_CONNECTOR_0  = new MapTile("wall","T",true,true,true,true,5,2), 
    WALL_T_CONNECTOR_90 = new MapTile("wall","T",true,true,true,true,5,3),
    WALL_T_CONNECTOR_180= new MapTile("wall","T",true,true,true,true,6,2),
    WALL_T_CONNECTOR_270= new MapTile("wall","T",true,true,true,true,6,3),


    FLOOR               = new MapTile("","",false,false,false,false,0,4),
    FLOOR_DONT_KNOW     = new MapTile("","",false,false,false,false,0,6),

    HILLMARKER_OFF      = new MapTile("HillMarker","",false,false,false,false,1,4),
    HILLMARKER_BLUE     = new MapTile("","",false,false,false,false,0,5),
    HILLMARKER_RED      = new MapTile("","",false,false,false,false,1,5);
    
  private static Integer nextId;
  
  private static final int  
    TOP_DOWN = 0,
    XSIDE_FPS = 1,
    YSIDE_FPS = 2;  

    private static       Dimension FPS_SIZE; 
    private static       Dimension TOP_DOWN_SIZE; 
  
    private static       TexturePack texturePack;
    
    //[0] = topdown
    //[1] = fps x side
    //[2] = fps y side
    private BufferedImage[] textures;

    
    private int 
      id, 
      xLocation,//sprite sheet location
      yLocation;//sprite sheet location

    private String 
      name,
      message; 
    
    private boolean 
      wall,
      solid,
      connectFromOther,
      connectToOther;

    private static final String FORMAT =  "Name:  %20s\n"
                                        + "Wall:  %20s\n"
                                        + "Solid: %20s\n"
                                        + "%s";
   
    
    protected MapTile(String name,String message, boolean wall, boolean solid,boolean connectFromOther, boolean connectToOther, int xLocation, int yLocation)
    {
      if(nextId == null)
      {
        nextId = 0;
        tiles = new MapTile[0];
      }
      
      this.id        = nextId++;
      this.name      = name;
      this.message   = message; 
      this.wall      = wall;
      this.solid     = solid;
      this.connectFromOther = connectFromOther;
      this.connectToOther = connectToOther;
      this.xLocation = xLocation;
      this.yLocation = yLocation;
      
      
      tiles =  extendArraySize(tiles);// expands array  by one array has to be full for values() to not get null point excepton
      tiles[this.id] = this;
    }
    
    //have to call this before useing any textures 
    public static void setTexturePack(TexturePack t)
    {
        MapTile.FPS_SIZE      = t.getFpsSize();
        MapTile.TOP_DOWN_SIZE = t.getTopDownSize();
        texturePack = t;
    }
    
    //have to call this before using any textures 
    public static void loadAllTiles()
    {
      for(short i = 0; i < MapTile.values().length; i++)
      {
        MapTile tile    = MapTile.getMapTile(i);
        tile.textures = texturePack.getTextureSet(tile.xLocation, tile.yLocation);
      }
    }
    
    public static void loadAllTiles(TexturePack t)
    {  
      setTexturePack(t);
      loadAllTiles();
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
    
    public String  getName()                {return this.name;            }
    public String  getMessage()             {return this.message;         }
    public boolean isSolid()                {return this.solid;           }
    public boolean isWall()                 {return this.wall;            }
    public boolean getConnectFromOther()    {return this.connectFromOther;}
    public boolean getConnectToOther()      {return this.connectToOther;  }
    
    public static Dimension getTopDownSize(){return TOP_DOWN_SIZE;}
    public static Dimension getFpsSize()    {return FPS_SIZE;     }
    
    private static MapTile[] extendArraySize(MapTile[] array){
        MapTile[] temp = array.clone();
        array          = new MapTile[array.length + 1];
        System.arraycopy(temp, 0, array, 0, temp.length);
        return array;
    }
    
    @Override
    public String toString()
    {
      return String.format(FORMAT, name, wall, solid, message);
    }
    
}


