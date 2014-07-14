package by_software.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapPanel extends JPanel {
	  private boolean paintHover = false;
	  private int mouseButton = 0;
	  private MapLabel[][] map;
	  private int sizeX,sizeY;
	  private Dimension tileSize;
	  private MapTile defaultTile;
	  private MapTile selectedTile = MapTile.Void;
	  
	  public MapPanel(String name, Dimension tileSize, MapTile defaultTile)
	  {
		  
		  super();
		  this.defaultTile = defaultTile;
		  this.tileSize = tileSize;
		  this.loadMap(name);  
	  }
	  
	  public MapPanel(int x,int y, Dimension tileSize, MapTile defaultTile)
	  {
	    super();
	    this.defaultTile = defaultTile;
		this.tileSize = tileSize;
	    this.setLayout(new GridLayout(y,x));
	    map = new MapLabel[x][y];
	    for(int i = 0; i < x; i++ )
	    {
	      for(int j = 0 ; j < y; j++)
	      {
	        map[i][j] = new MapLabel();
	        this.add(map[i][j]);
	      }
	    }
	      
	  }
	  
	  
	  public void loadMap(String name)
	  {
		  
		  this.removeAll();
		  
		  try
		    {
		      File file = new File(name);
		      Scanner scanner = new Scanner(file);

		      sizeX = scanner.nextInt();
		      sizeY = scanner.nextInt();

		      this.setLayout(new GridLayout(sizeY,sizeX));
		      this.map = new MapLabel[sizeX][sizeY];
		     
		      for(int j = 0; j < sizeY;  j++)
		      {
		        for(int i = 0; i < sizeX; i++)
		        {
		          map[i][j] = new MapLabel(scanner.nextShort());
		          this.add(map[i][j]);
		        }      
		      }
		    }
		    catch(IOException e)
		    {
		        System.out.print("Error Importing Map");
		        e.printStackTrace();
		    }
		  
	  }
	  
	  public String toString()
	  {
		  String string = "";
		  string += sizeX +" "+ sizeY + " \n";
		  for(int i = 0;i < sizeY; i++)
			{
				for(int j = 0;j<sizeX;j++)
				{
					string += map[j][i].getTile().ordinal() + " ";
								
				}
			
				string += "\n";
			}	
		  return string;
	  }
	  
	  public void setSelectedTile(MapTile tile)
	  {
		  this.selectedTile = tile;
	  }
	  
	  
	  
	  
	  private class MapLabel extends JLabel implements MouseListener{
			private MapTile tile;
		    int tileID = 0;
		    private Dimension size;
		   
		    public MapLabel()
		    {
		      super();
		      
		      size = tileSize;
		      setBackground(Color.WHITE);
		      setOpaque(true);
		      setEnabled(true);
		      setVisible(true);
		      addMouseListener(this);
		  
		      setBorder(BorderFactory.createLineBorder(Color.black));
		  	  tile = defaultTile;
		  	  tileID = tile.ordinal();
			
		      setIcon(new ImageIcon(tile.getTopSprite()));
		      
		    }
		    
		    public MapLabel(short id)
		    {
		    	size = tileSize;
		        setPreferredSize(size);
		        setBackground(Color.WHITE);
		        setOpaque(true);
		        setEnabled(true);
		        setVisible(true);
		        addMouseListener(this);
		    
		        setBorder(BorderFactory.createLineBorder(Color.black));
		        
		    	tile = MapTile.values()[id];//TODO
		    	tileID = id;
		    	
		    	setIcon(new ImageIcon(tile.getTopSprite()));
		    }
		    
		    public void setNewTile(MapTile tile)
		    {
		    	this.tile = tile;
		    	setIcon(new ImageIcon(tile.getTopSprite()));
		    	tileID = tile.ordinal();
		    }  
		    
		    public void setNewTile(MouseEvent e)
		    {
		    	if(mouseButton == 1)
		    	{
		    		this.tile = selectedTile;
		    	}
		    	if(mouseButton == 3)
		    	{
		    		this.tile = defaultTile;
		    	}
		    	setIcon(new ImageIcon(tile.getTopSprite()));
		    	tileID = tile.ordinal();
		    }  
		    
		    
		    
		    
		    public MapTile getTile()
		    {
		    	return this.tile;
		    }
		    
		    @Override
		    public void mouseClicked(MouseEvent arg0) {}

		    @Override
		    public void mouseEntered(MouseEvent e) {
		      // TODO Auto-generated method stub
		      if(paintHover)
		      {
		    	  setNewTile(e);
		      }
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {}

		    @Override
		    public void mousePressed(MouseEvent arg0) {
		      // TODO Auto-generated method stub
		    	mouseButton = arg0.getButton();
		    	setNewTile(arg0);
		    	paintHover = true;
		      
		    }

		    @Override
		    public void mouseReleased(MouseEvent arg0) {
		      // TODO Auto-generated method stub
		      mouseButton = 0;
		      paintHover = false;
		    }
		  }
	  
	  
}