import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.Color;

public class Game implements Runnable
{
	private static final Dimension WINDOW_SIZE = new Dimension(800, 600);
	/*
	//the player that belongs to the client of this class
	private final Player myPlayer;
	//The player being tracked (can watch other player)
	private Player curPlayer;*/
	//the game is running
	boolean isRunning = false;
	//game name
	public static final String TITLE = "Some_Game";
	//create the window that will be rendered to
	private GameWindow window;
	//graphics obj to draw to
	Graphics graphics;
	//BufferStrategy
	BufferStrategy bStrat;

	public Game()
	{
		window = new GameWindow(TITLE, WINDOW_SIZE);
		bStrat = window.getBufferStrategy();
		isRunning = true;
		run();
	}

	public void run()
	{
		while(isRunning)
		{
			getInput();
			update();
			playSound();
			render();
		}
	}

	void getInput()
	{

	}

	void update()
	{

	}

	void playSound()
	{

	}

	void render()
	{
		try{
			graphics = bStrat.getDrawGraphics();
			graphics.setColor(Color.BLACK);
			graphics.fillRect(50, 50, 300, 300);
			if( ! bStrat.contentsLost())
				bStrat.show();
		}finally{
			if(graphics != null)
				graphics.dispose();
		}System.out.println("render");
	}

	public static void main(String[] args){new Game();}
}