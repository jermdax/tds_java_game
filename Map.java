import java.util.*;
import java.io.File;
import java.io.IOException;

public class Map{

  private       short[][] map;
  private       int sizeX, sizeY;    


  private final String errorLoading = "Failed to load map!";
  
  /**
  *  load a map from a file.
  **/
  public Map(String path)
  {
      readMap(path);
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
        System.out.print(errorLoading);
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

  public short[][] getMap()  {  return this.map;  }  
  public short getMapTile(int x,int y)  {  return this.map[x][y];  }

  public void printMap()
  {
    //System.out.print(Arrays.deepToString(map));
    for(int i = 0;i < sizeY;i++)
    {
        for(int j = 0;j < sizeX;j++)
      {
          System.out.print(String.format("%3d", map[j][i]));
      }
      System.out.println();
    }

  }

  public static void main(String[] args)
  {  

    Map map = new Map("MapTest");
    map.printMap();

   }

}