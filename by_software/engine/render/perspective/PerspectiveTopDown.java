package by_software.engine.render.perspective;

import java.awt.Color;
import java.awt.Graphics;

import by_software.Game;
import by_software.map.Map;
import by_software.map.MapTile;
import by_software.mob.player.Player;

public class PerspectiveTopDown implements Perspective
{
  private static Map worldMap;

  public PerspectiveTopDown(Map worldMap)
  {
    this.worldMap = worldMap;

  }

 public void render(Graphics gr, int screenWidth, int screenHidth ,Game game, Player player)
  {
    MapTile[][] mapTiles = worldMap.getTiles();
    for(int x = 0; x < mapTiles.length; x++)
    {
      for(int y = 0; y < mapTiles[x].length; y++)
      {
        if( mapTiles[x][y].isWall() )
          gr.setColor(Color.RED);
        else
          gr.setColor(Color.GRAY);
        gr.fillRect(x*MapTile.getTopDownSize().width, y*MapTile.getTopDownSize().height,MapTile.getTopDownSize().width, MapTile.getTopDownSize().height);
        
        //drawGrid
        gr.setColor(Color.CYAN);
        gr.drawRect(x*MapTile.getTopDownSize().width, y*MapTile.getTopDownSize().height, MapTile.getTopDownSize().width, MapTile.getTopDownSize().height);
      }
      gr.setColor(Color.GREEN);
      gr.fillOval((int)(player.getPosX()*MapTile.getTopDownSize().width)-4, (int)(player.getPosY()*MapTile.getTopDownSize().height)-4, 18, 18);
    }
  }

}