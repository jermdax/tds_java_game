package by_software.map;

public class MapTileSet {

  
   
    private static final MapTileTriple[] WALLSET = {
        
         // new MapTileTriple(MapTile.WALL_TOP_LEFT, Integer.parseInt("00111000",2), Integer.parseInt("10111011",2)),
         // new MapTileTriple(MapTile.WALL_TOP_CENTER, Integer.parseInt("00111110",2), Integer.parseInt("10111110",2)),
         // new MapTileTriple(MapTile.WALL_TOP_RIGHT, Integer.parseInt("00001110",2), Integer.parseInt("10101110",2)),
      
      
         // new MapTileTriple(MapTile.WALL_CENTER_CENTER, Integer.parseInt("11111111",2), Integer.parseInt("11111111",2)),
      
        new MapTileTriple(MapTile.WALL_END_BOTTOM, Integer.parseInt("10000000",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_END_LEFT, Integer.parseInt("00100000",2), Integer.parseInt("10101010",2)),
        
        new MapTileTriple(MapTile.WALL_END_TOP, Integer.parseInt("00001000",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_END_RIGHT, Integer.parseInt("00000010",2), Integer.parseInt("10101010",2)),
        
        
        new MapTileTriple(MapTile.WALL_VERTICAL, Integer.parseInt("10001000",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_HORIZONTAL, Integer.parseInt("00100010",2), Integer.parseInt("10101010",2)),
        
      
        new MapTileTriple(MapTile.WALL_4_CONNECTOR, Integer.parseInt("10101010",2), Integer.parseInt("10101010",2)),
       
        
        new MapTileTriple(MapTile.WALL_L_CONNECTOR_0, Integer.parseInt("00101000",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_L_CONNECTOR_90, Integer.parseInt("00001010",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_L_CONNECTOR_180, Integer.parseInt("10000010",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_L_CONNECTOR_270, Integer.parseInt("10100000",2), Integer.parseInt("10101010",2)),
        
        new MapTileTriple(MapTile.WALL_T_CONNECTOR_0, Integer.parseInt("10001010",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_T_CONNECTOR_90, Integer.parseInt("10100010",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_T_CONNECTOR_180, Integer.parseInt("10101000",2), Integer.parseInt("10101010",2)),
        new MapTileTriple(MapTile.WALL_T_CONNECTOR_270, Integer.parseInt("00101010",2), Integer.parseInt("10101010",2)),
      
        
    }; 
      
  
  public static MapTile getMapTile(int code)
  {
    for(MapTileTriple triple: WALLSET)
    {
      if(triple.isMatch((byte)code))
      {
        return triple.getTile();
      }
    }
    return MapTile.WALL_PILLAR;
  }
  

      //WALL_TOP_LEFT   = O011,10O0 = 124,120,56,60
      //WALL_TOP_CENTER   = O0O0,1110
    //WALL_TOP_RIGHT    = O0O0,1110 
    
  
      //WALL_CENTER_LEFT   = 1111,10O0
        //WALL_CENTER_CENTER = 1111,1111
        //WALL_CENTER_RIGHT  = 1000,1111
            
  
    //WALL_BOTTOM_Right  = 10o0,o011
      //WALL_BOTTOM_CENTER = 1110,O011
    //WALL_BOTTOM_LEFT   = 1110,O0O0
  /*
     WALL_END_LEFT       = OO1O,OOOO
     WALL_END_RIGHT      = OOOO,OO1O
     
     WALL_END_TOP        = OOOO,1OOO
     WALL_END_BOTTOM     = 1OOO,OOOO 
     
     WALL_PILLER      = O0O0,O0O0 
     WALL_VERTICAL       = 10O0,10O0
     WALL_HORIZONTAL     = O010,O010
     WALL_4_CONNECTOR    = 1O1O,1O1O // MAYBE 1010,1010
     
     WALL_BLOCK //TODO NOT PART OF CONTINUS WALL NEED TO ADD A BOOLE TO JOINABLE ?
     WALL_BLOCK_BLUE 
     WALL_BLOCK_RED 
     
     
     
     WALL_7_CONNECTOR_0   = 111O,1111
     WALL_7_CONNECTOR_90  = 1111,1O11
     WALL_7_CONNECTOR_180 = 1111,111O
     WALL_7_CONNECTOR_270 = 1O11,1111
     
    WALL_L_CONNECTOR_0   = O01O,10O0
    WALL_L_CONNECTOR_90  = O0O0,1O10  
    WALL_L_CONNECTOR_180 = 10o0,o01O
    WALL_L_CONNECTOR_270 = 1O10,O0O0
     
    WALL_T_CONNECTOR_0   = 10O0,1O1O
    WALL_T_CONNECTOR_90  = 1O10,O01O
    WALL_T_CONNECTOR_180 = 1O1O,10O0
     WALL_T_CONNECTOR_270 = O01O,1O10
*/
  private static class MapTileTriple{
    
    byte code;
    byte mask;
    MapTile tile;
    
    public MapTileTriple( MapTile tile, int code, int mask)
    {
      this.code = (byte) code;
      this.mask = (byte)mask;
      this.tile = tile;
    }
    
    public boolean isMatch(byte code)
    {
      return this.code == (code & mask);
    }
    public MapTile getTile()
    {
      return this.tile;
    }
    
  }
}
