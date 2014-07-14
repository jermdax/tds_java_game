package by_software.engine.render.graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import by_software.map.MapTile;

public class TexturePack
{
	
  private static        BufferedImage allTdsTiles;
  private static        BufferedImage allFpsTextures;
  private static        BufferedImage allMobs;
  private static        BufferedImage allWeps;
  private static final  String        ERROR_LOADING_MAP_TEXTURES  = "Failed to load map texure!";
  
  private static        String topDownFileName = "TopDownSprites.png";
  private static        String fpsFileName     = "FpsTextures.png";
  private static        String mobFileName     = "";
  private static        String weaponFileName  = "";
  
  private 				int    numTilesX;
  private 				int    numTilesY;
  
  private 				int    numTexturesX;
  private 				int    numTexturesY;
  
  private 				Dimension topDownSize;
  private 				Dimension fpsSize;
  
 
  public static void main(String[] args)
  {
	  TexturePack t =  new TexturePack("D:/work/by_software/src/by_software/map/sprites/",new Dimension(32,32),new Dimension(16,16));
	  
	  
	  MapTile.loadAllTiles(t);
	  
	  
	  
	    JFrame frame = new JFrame();
		JLabel panel = new JLabel();
		panel.setIcon(new ImageIcon(MapTile.Void.getTopSprite()));
		frame.add(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	
  }
  
  public TexturePack(String path,Dimension topDownSize,Dimension fpsSize)
  {
    //take the paths (defaults) and then load the textures into memory
	  
	  this.topDownSize = topDownSize;
	  this.fpsSize = fpsSize;
	  
	  try {
		allTdsTiles = ImageIO.read(new File(path + topDownFileName));
	
	  //allFpsTextures = ImageIO.read(new File(path + fpsFileName));
	  //allMobs = ImageIO.read(new File(path + mobFileName));
	  //allWeps = ImageIO.read(new File(path + weaponFileName));
		
		numTilesX = (allTdsTiles.getWidth() / topDownSize.width);
		numTilesY = (allTdsTiles.getHeight() / topDownSize.height);
	
	  } catch (IOException e) {
		  	System.out.println(ERROR_LOADING_MAP_TEXTURES);
			e.printStackTrace();
	  }
  }
  
  
  public BufferedImage getSprite(int id)
  {
	  int x,y;
	
	  x = (id % numTilesX) * topDownSize.width;
	  y = (id / numTilesX) * topDownSize.height;
	  return allTdsTiles.getSubimage(x, y, topDownSize.width, topDownSize.height);
  }
  
  
  //fps textures will be arrange x face then y face of the same block
  
  public BufferedImage getFpsXTexture(int id)
  {
	 
	  id *= 2;// texture is 2 wide
	  int x,y;
		
	  x = (id % numTilesX) * fpsSize.width;
	  y = (id / numTilesX) * fpsSize.height;
	  
	  return allFpsTextures.getSubimage(x, y, fpsSize.width, fpsSize.width);
	  
	  
	
  }
  
  public BufferedImage getFpsYTexture(int id)
  {
		  id *= 2;
		  id++;
		  int x,y;
			
		  x = (id % numTilesX) * fpsSize.width;
		  y = (id / numTilesX) * fpsSize.height;
		  
		  return allFpsTextures.getSubimage(x, y, fpsSize.width, fpsSize.width);
  }
  
  public BufferedImage[] getTextureSet(int id)
  {
	  
	  BufferedImage[] set = {getSprite(id)};//,getFpsXTexture(id),getFpsYTexture(id)};
	  return set;
  }
  public Dimension getTopDownSize(){return topDownSize;}
  public Dimension getFpsSize(){return fpsSize;}
}