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
    private MapTile selectedTile = MapTile.FLOOR;
    
    
  
    
    public MapPanel(String name, Dimension tileSize, MapTile defaultTile)
    {
      
      //super();
      this.defaultTile = defaultTile;
      this.tileSize = tileSize;
      this.loadMap(name);  
    }
    
    public MapPanel(int x,int y, Dimension tileSize, MapTile defaultTile)
    {
      //super();
      this.defaultTile = defaultTile;
      this.tileSize = tileSize;
      this.setLayout(new GridLayout(y,x));
      this.sizeX = x;
      this.sizeY = y;
      map = new MapLabel[x][y];
      for(int j = 0 ; j < y; j++)
      {
        for(int i = 0; i < x; i++ )
        {
        
          map[i][j] = new MapLabel(i,j);
          this.add(map[i][j]);
        }
      }
        
    }
    
    public void updateSurroundingTiles(int x, int y)
    {
      MapLabel currentLabel;
      try{
        currentLabel = map[x][y-1];
        currentLabel.setNewTile(currentLabel.getTile());
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
      try{
        currentLabel = map[x+1][y-1];
        currentLabel.setNewTile(currentLabel.getTile());
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
      try{
        currentLabel = map[x+1][y];
        currentLabel.setNewTile(currentLabel.getTile());
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
      try{
        currentLabel = map[x+1][y+1];
        currentLabel.setNewTile(currentLabel.getTile());
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
      try{
        currentLabel = map[x][y+1];
        currentLabel.setNewTile(currentLabel.getTile());
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
      try{
        currentLabel = map[x-1][y+1];
        currentLabel.setNewTile(currentLabel.getTile());
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
      try{
        currentLabel = map[x-1][y];
        currentLabel.setNewTile(currentLabel.getTile());
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
      try{
        currentLabel = map[x-1][y-1];
        currentLabel.setNewTile(currentLabel.getTile());
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
    }
    
    public byte checkSurroundingTiles(int x, int y)
    {
      byte code = 0;
      try{
        if(map[x][y-1].getTile().getConnectFromOther()) {code++;}
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{code <<= 1;}
      
      try{
        if(map[x+1][y-1].getTile().getConnectFromOther()) {code++;}
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{code <<= 1;}
      
      try{
        if(map[x+1][y].getTile().getConnectFromOther()) {code++;}
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{code <<= 1;}
      
      try{
        if(map[x+1][y+1].getTile().getConnectFromOther()) {code++;}
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{code <<= 1;}
      
      try{
        if(map[x][y+1].getTile().getConnectFromOther()) {code++;}
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{code <<= 1;}
      
      try{
        if(map[x-1][y+1].getTile().getConnectFromOther()) {code++;}
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{code <<= 1;}
      
      try{
        if(map[x-1][y].getTile().getConnectFromOther()) {code++;}
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{code <<= 1;}
      
      try{
        if(map[x-1][y-1].getTile().getConnectFromOther()) {code++;}
      }catch(ArrayIndexOutOfBoundsException e){}
      finally{}
      
      return code;
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
              map[i][j] = new MapLabel(scanner.nextShort(),i,j);
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
      int y,x;
        int tileID = 0;
        private Dimension size;
       
        public MapLabel(int x ,int y)
        {
          //super();
          this.x = x;
          this.y = y;
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
        
        public MapLabel(short id, int x ,int y)
        {
          this.x = x;
          this.y = y;
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
          this.tile = checkTile(tile);
          setIcon(new ImageIcon(this.tile.getTopSprite()));
          tileID = this.tile.ordinal();
        }  
        
        public void setNewTile(MouseEvent e)
        {
          if(mouseButton == 1)
          {
            setNewTile(selectedTile);
          }
          if(mouseButton == 3)
          {
            setNewTile(defaultTile);
          }   
          updateSurroundingTiles(this.x,this.y);
        }  
        
        public MapTile checkTile(MapTile tile)
        {
          if(tile.getConnectToOther())
          {
            //System.out.println(Integer.toBinaryString(((Integer)(int)checkSurroundingTiles(this.x,this.y))));
            return MapTileSet.getMapTile(checkSurroundingTiles(this.x,this.y));
          }
          return tile;
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