package by_software.engine.render.graphics;

import java.awt.image.BufferedImage;

public class Tile
{
  private final         BufferedImage  graphic;
  public static final   int            SIZE = 32;
  private final         boolean        wall;

  public Tile(boolean wall)
  {
    graphic = new BufferedImage(1, 1, 1);
    this.wall = wall;
  }

  public boolean isWall()
  {
    return wall;
  }
}