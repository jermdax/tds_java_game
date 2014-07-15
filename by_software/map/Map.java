package by_software.map;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Map{

  private               short[][] map;
  private               int       sizeX, sizeY;    
  private               Dimension size;   
  private static final  String    ERROR_LOADING_MAP_DEF       = "Failed to load map definition file!";
  private               MapTile[][] tiles;
  
  /**
  *  load a map from a file.
  **/
  public Map(String path)
  {
      readMap(path);
      tiles = toTiles();
  }

  /**
  * Create a map of size  sizeX * sizeY,
  * fills edges with tile id 1.
  **/
  public Map(int sizeX, int sizeY)
  {
    map = new short[sizeX][sizeY];
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    size = new Dimension(sizeX,sizeY);

    for(int i = 0; i < sizeX; i++)
    {
      map[i][0] = map[i][sizeY - 1] = 1;
      map[0][i] = map[sizeY - 1][i] = 1;
    }
  }

  /**
  *  read a map from file.
  **/
  public void readMap(String path)
  {
    //used scanner probably shit.
    try
    {
      File file = new File(path);
      Scanner scanner = new Scanner(file);

      sizeX = scanner.nextInt();
      sizeY = scanner.nextInt();


      this.map = new short[sizeX][sizeY];

      for(int j = 0; j < sizeY;  j++)
      {
        for(int i = 0; i < sizeX; i++)
        {
          map[i][j] = scanner.nextShort();
        }      
      }
    }
    catch(IOException e)
    {
        System.out.print(ERROR_LOADING_MAP_DEF);
    }
  }

  /**
  *  procedural generation a map.
  **/
  public void generateMap()
  {

  }

  /**
  * procedural generation a symmetrical map.
  *  rise and run = andle of symmetry(slope).
  **/
  public void generateMap(int rise, int run)
  {

  }

  //convert the numbers describing the map to actual tiles
  public MapTile[][] toTiles()
  {
    int x = 0, y = 0;
    MapTile[][] tiles = new MapTile[map.length][map[0].length];
    for(short[] row : map)
    {
      for(short tile : row)
      {
        tiles[x][y] = MapTile.getMapTile(map[x][y]);
        ++y;
      }
      ++x;
      y = 0;
    }
    return tiles;
  }
  
  
  public String toString()
  {
    String result = new String();

    for(int i = 0;i < sizeY;i++)
    {
        for(int j = 0;j < sizeX;j++)
      {
          result += String.format("%4d", map[j][i]);
      }
      result += "\n";
    }
    return result;
  }

  public void printMap()
  {
    System.out.println(this);
  }

  public boolean checkCollision(int x, int y)
  {
    return map[x][y] != 0;
    
  }

  public boolean checkCollision(double x, double y)
  {
    return checkCollision((int) x, (int) y);
  }

  public short[][] getMap()             {  return this.map;       }
  public short getMapTile(int x,int y)  {  return this.map[x][y]; }
  public MapTile[][] getTiles()         {  return tiles;          }
  public int getMapSizeX()              {  return this.sizeX;     }
  public int getMapSizeY()              {  return this.sizeY;     }
  public int getMapSize()               {  return this.sizeX;     }
}