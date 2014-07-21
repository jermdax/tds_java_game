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

  public PerspectiveTopDown(Map worldMap)
  {
    this.worldMap = worldMap;
    mapImg = new BufferedImage((int)(worldMap.getMapSizeX() * MapTile.getTopDownSize().getWidth()), 
                               (int)(worldMap.getMapSizeY() * MapTile.getTopDownSize().getHeight()), 
                               BufferedImage.TYPE_INT_ARGB);
    constructMap( this.worldMap.getTiles() );
  }

  private void constructMap(MapTile[][] tiles)
  {
    Graphics g = mapImg.createGraphics();
    int 
      sizeX = (int)MapTile.getTopDownSize().getWidth(), 
      sizeY = (int)MapTile.getTopDownSize().getHeight();
    //draw the map
    for(int x = 0; x < tiles[0].length; x++)
      for(int y = 0; y < tiles.length; y++)
      {
        g.drawImage(tiles[x][y].getTopSprite(), x*sizeX, y*sizeY, Color.BLACK, null);
      }

  }

 public void render(Graphics gr, int screenWidth, int screenHidth ,Game game, Player player)
  {
    gr.drawImage(mapImg, 0, 0, Color.BLACK, null);
    //draw player
    gr.setColor(Color.GREEN);
    gr.fillOval((int)(player.getPosX()*16-4), (int)(player.getPosY()*16-4), 8, 8);
  }
}