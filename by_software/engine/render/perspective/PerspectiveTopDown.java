package by_software.engine.render.perspective;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import by_software.Game;
import by_software.map.Map;
import by_software.map.MapTile;
import by_software.mob.player.Player;

public class PerspectiveTopDown implements Perspective
{
  private static Map worldMap;
  //TODO dont hardcode in the size
  private static BufferedImage mapImg;
  //scale by which the sprites are scaled when loaded
  public static final int SCALE = 2;

  private static final  int 
    SIZE_X = (int)MapTile.getTopDownSize().getWidth(), 
    SIZE_Y = (int)MapTile.getTopDownSize().getHeight();

  public PerspectiveTopDown(Map worldMap)
  {
    this.worldMap = worldMap;
    mapImg = new BufferedImage((int)(worldMap.getMapSizeX() * MapTile.getTopDownSize().getWidth()*SCALE), 
                               (int)(worldMap.getMapSizeY() * MapTile.getTopDownSize().getHeight()*SCALE), 
                               BufferedImage.TYPE_INT_ARGB);
    constructMap( this.worldMap.getTiles() );
  }

  private void constructMap(MapTile[][] tiles)
  {
    Graphics g = mapImg.createGraphics();
    //draw the map
    for(int x = 0; x < tiles[0].length; x++)
      for(int y = 0; y < tiles.length; y++)
      {
        g.drawImage(tiles[x][y].getTopSprite(), x*(SIZE_X*SCALE), y*(SIZE_Y*SCALE), x*SIZE_X*SCALE+SIZE_X*SCALE, y*SIZE_Y*SCALE+SIZE_Y*SCALE, 0, 0, SIZE_X, SIZE_Y, Color.BLACK, null);
      }

  }
private int startX = 300, startY = 300;
 public void render(Graphics gr, int screenWidth, int screenHidth ,Game game, Player player)
  {
    //draw the map with the top left corner at the player pos - half the gamewindow size
    int
      halfWindX = (int)(Game.getWindowSize().getWidth() /2),
      halfWindY = (int)(Game.getWindowSize().getHeight()/2),
      startX    = (int)(player.getPosX()*SCALE*SIZE_X),
      startY    = (int)(player.getPosY()*SCALE*SIZE_X);
    //the line below is doing the rendering and the one i think has a issue
    //it has some hardcoded values, they are just while debugging
    gr.drawImage(mapImg, 0, 0, halfWindX*2, halfWindY*2, startX-halfWindX, startY-halfWindY, startX+halfWindX, startY+halfWindY, Color.RED, null);
    //draw player
    gr.setColor(Color.GREEN);
    //TODO dont use hardcoded values
    gr.fillOval(halfWindX-8, halfWindY-8, 16, 16);
  }
}