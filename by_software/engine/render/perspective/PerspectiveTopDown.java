package by_software.engine.render.perspective;

import java.awt.Graphics;

import by_software.Game;
import by_software.map.Map;
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
    //render each tile of the map
  }
}