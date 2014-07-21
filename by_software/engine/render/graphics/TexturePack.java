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
	  TexturePack t =  new TexturePack("D:/work/by_software/src/by_software/map/sprites/",new Dimension(16,16),new Dimension(8,16));
	  MapTile.setTexturePack(t);
	  MapTile.loadAllTiles();
	  
	  JFrame frame = new JFrame();
	  JLabel panel = new JLabel();
	  panel.setIcon(new ImageIcon(MapTile.FLOOR.getTopSprite()));
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
	
		allFpsTextures = ImageIO.read(new File(path + fpsFileName));
	  //allMobs = ImageIO.read(new File(path + mobFileName));
	  //allWeps = ImageIO.read(new File(path + weaponFileName));
		
		numTilesX = (allTdsTiles.getWidth() / topDownSize.width);
		numTilesY = (allTdsTiles.getHeight() / topDownSize.height);
	
	  } catch (IOException e) {
		  	System.out.println(ERROR_LOADING_MAP_TEXTURES);
			e.printStackTrace();
	  }
  }
  
  
  public BufferedImage getSprite(int x, int y)
  {
	return allTdsTiles.getSubimage(x * topDownSize.width, y * topDownSize.height, topDownSize.width, topDownSize.height);
  }
  
  
  //fps textures will be arrange x face then y face of the same block
  
  public BufferedImage getFpsXTexture(int x, int y)
  {
    return allFpsTextures.getSubimage(x * fpsSize.width, y * fpsSize.height, fpsSize.width, fpsSize.width);
  }
  
  public BufferedImage getFpsYTexture(int x, int y)
  {
    x++;
    System.out.println(x * fpsSize.width);
    return allFpsTextures.getSubimage(x * fpsSize.width, y * fpsSize.height, fpsSize.width, fpsSize.width);
  }
  
  public BufferedImage[] getTextureSet(int x, int y)
  {
	  
	  BufferedImage[] set = {getSprite(x,y), getFpsXTexture(x,y), getFpsYTexture(x,y)};
	  return set;
  }
  public Dimension getTopDownSize(){return topDownSize;}
  public Dimension getFpsSize(){return fpsSize;}
}