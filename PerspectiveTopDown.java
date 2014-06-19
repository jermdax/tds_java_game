import java.awt.Graphics;

public class PerspectiveTopDown implements Perspective
{
  private static Map worldMap;

  public PerspectiveTopDown(Map worldMap)
  {
    this.worldMap = worldMap;

  }

 public void render(Graphics gr, int screenWidth, int screenHidth ,Game game, Player player)
  {
    //TODO call methods to render Top down
  }
}