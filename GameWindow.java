import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

public class GameWindow
{
	private JFrame frame;
	private BufferStrategy bStrat;

	public GameWindow(String title, Dimension windowSize)
	{
		//set up the JFrame
		frame = new JFrame(title);
		frame.setSize(windowSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setIgnoreRepaint(true);

		Canvas canvas = new Canvas();
		canvas.setPreferredSize(windowSize);
		canvas.setIgnoreRepaint(true);
		frame.add(canvas);

		frame.pack();
		frame.setVisible(true);

		canvas.createBufferStrategy(2);
		bStrat = canvas.getBufferStrategy();
	}

	public JFrame getFrame() {return frame;}
	public BufferStrategy getBufferStrategy() {return bStrat;}

	//test
	public static void main(String[] args)
	{
		GameWindow gWind = new GameWindow("test window", new Dimension(800, 600));
		BufferStrategy bStrateg = gWind.getBufferStrategy();
		Graphics g = bStrateg.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(50, 50, 300, 300);
		g.dispose();
		bStrateg.show();
	}
}