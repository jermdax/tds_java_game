import java.awt.Graphics;

public interface Perspective
{
	public abstract void render(Graphics gr, int screenWidth, int screenHidth ,Game game, Player player);
}