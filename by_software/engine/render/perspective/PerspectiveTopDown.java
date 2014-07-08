package by_software.engine.render.perspective;

import java.awt.Graphics;
import java.awt.Color;

import by_software.Game;
import by_software.map.Map;
import by_software.mob.player.Player;
import by_software.engine.render.graphics.Tile;

public class PerspectiveTopDown implements Perspective
{
  private static Map worldMap;

  public PerspectiveTopDown(Map worldMap)
  {
    this.worldMap = worldMap;

  }

 public void render(Graphics gr, int screenWidth, int screenHidth ,Game game, Player player)
  {
    Tile[][] mapTiles = worldMap.getTiles();
    for(int x = 0; x < mapTiles.length; x++)
    {
      for(int y = 0; y < mapTiles[x].length; y++)
      {
        if( mapTiles[x][y].isWall() )
          gr.setColor(Color.RED);
        else
          gr.setColor(Color.GRAY);
        gr.fillRect(x*Tile.SIZE, y*Tile.SIZE, Tile.SIZE, Tile.SIZE);
        
        //drawGrid
        gr.setColor(Color.CYAN);
        gr.drawRect(x*Tile.SIZE, y*Tile.SIZE, Tile.SIZE, Tile.SIZE);
      }
      gr.setColor(Color.GREEN);
      gr.fillOval((int)(player.getPosX()*Tile.SIZE)-4, (int)(player.getPosY()*Tile.SIZE)-4, 18, 18);
    }
  }

}