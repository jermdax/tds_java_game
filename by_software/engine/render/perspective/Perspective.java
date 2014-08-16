package by_software.engine.render.perspective;

import java.awt.Graphics;

import by_software.Game;
import by_software.entity.mob.player.Player;

public interface Perspective
{
	public abstract void render(Graphics gr, int screenWidth, int screenHidth ,Game game, Player player);
}