package by_software.map;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import by_software.engine.input.Keyboard;
import by_software.engine.render.graphics.GameWindow;

public class MapMaker {
  
  private static int sidedBarWidth = 302,tileSize = 32;
  private int numTiles;
  private int perferedHeight = 3000;
  private boolean grid = true;
  private boolean gridOver = true;
  private int sizeX, sizeY;
  private GameWindow gw;
  private Keyboard input;
  private JFrame  frame;
  private boolean paintHover = false;
  private JPanel panel, sidePanel;
  private Canvas canvas;
  
  
  
  private MapTile selectedTile;
  
  private Point   frameLoc;
  private short[][] map;
  public static void main(String[] args)
  {
  
  
  
  MapMaker m = new MapMaker("Map Maker",25,25,true,true);
  
  }
  
  public MapMaker(String title,int x, int y,boolean grid, boolean gridOver)
  {
    this.sizeX = x;
    this.sizeY = y;
    Dimension canvasSize = new Dimension(x * tileSize,y * tileSize); 
    map = new short[x][y];
    this.grid = grid;
    this.gridOver = gridOver;  

  
  //TODO load in tile enums(?) find number of tiles and set perfered hieght of sidepanel 
  
  
    //set up the JFrame
    frame = new JFrame(title);
    frame.setSize(sizeX * tileSize + sidedBarWidth,sizeY * tileSize);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    // frame.setResizable(false);
    frame.setIgnoreRepaint(true);

    panel = new JPanel();
    panel.setLayout(new BorderLayout());
    
    sidePanel = new JPanel();
    sidePanel.setBackground(Color.lightGray);
     
   
    
    
    sidePanel = new JPanel();
    sidePanel.setBackground(Color.lightGray);
    sidePanel.setLayout(new FlowLayout());
    sidePanel.setPreferredSize(new Dimension(sidedBarWidth,perferedHeight));
   
    JScrollPane sideSP =  new JScrollPane(sidePanel);
    sideSP.setPreferredSize(new Dimension(sidedBarWidth + 20,frame.getHeight()));
    sideSP.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  
     
     /// test Shit######################################
     for(int i=0;i < 50 ;i++ )
     {
       SelectButton button1 = new SelectButton(MapTile.Void);
       sidePanel.add(button1);
       SelectButton button2 = new SelectButton(MapTile.Wall);
       sidePanel.add(button2);
       SelectButton button12 = new SelectButton(MapTile.Void);
       sidePanel.add(button12);
       SelectButton button22 = new SelectButton(MapTile.Wall);
       sidePanel.add(button22);
    }
     //#######################################
     
     
     
     
     
     
     ScrollPane sP =  new ScrollPane();
     sP.setPreferredSize(canvasSize);
     
     MapPanel mapPanel = new MapPanel(50,50); 
     
     sP.add(mapPanel);
     
     panel.add(sideSP,BorderLayout.WEST);
     panel.add(sP,BorderLayout.CENTER);
     frame.add(panel);
     frame.pack();
     frame.setVisible(true);
     frameLoc = frame.getLocationOnScreen();

     frame.addComponentListener(new ComponentAdapter() {
       public void componentMoved(ComponentEvent e)
       {
         frameLoc = frame.getLocationOnScreen();
       }
     });

  
  }
  

  private class MapLabel extends JLabel implements MouseListener{
  
  int tileID = 0;
  
  public MapLabel()
  {
    super();
    setPreferredSize(new Dimension(32,32));
    setBackground(Color.WHITE);
    //setIcon(new ImageIcon(MapTile.Void.getSprite()));
    setOpaque(true);
    setEnabled(true);
    setVisible(true);
    addMouseListener(this);
    setBorder(BorderFactory.createLineBorder(Color.black));
    
  }
  
  public void setNewTile(MapTile tile)
  {
    setIcon(new ImageIcon(tile.getSprite()));
    tileID = tile.ordinal();
  }
  
  @Override
  public void mouseClicked(MouseEvent arg0) {}

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
    if(paintHover)
    {
    setNewTile(selectedTile);

    }
  }

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent arg0) {
    // TODO Auto-generated method stub
    setNewTile(selectedTile);
    paintHover = true;
    
  }

  @Override
  public void mouseReleased(MouseEvent arg0) {
    // TODO Auto-generated method stub
    paintHover = false;
  }
  }
  
  private class SelectButton extends JButton implements ActionListener{
  
  MapTile tile;
  public SelectButton(MapTile tile)
  {
  super();
  
  this.tile = tile;
  setVisible(true);
  setPreferredSize(new Dimension(32,32));
  setIcon(new ImageIcon(tile.getSprite()));
  addActionListener(this);
  }
  
  public void actionPerformed(ActionEvent e) 
  {
    selectedTile = tile;
  }
  }
  
  private class MapPanel extends JPanel {
  
  private MapLabel[][] map;
  
  public MapPanel(int x,int y)
  {
    super();
    this.setLayout(new GridLayout(x,y));
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

  
  }
  //TODO implemt a MapTtle Interface so outer enums can be used
  private enum MapTile{
  Void("Void","by_software/map/sprites/01.png"),
  Wall("wall","by_software/map/sprites/02.png");
         
  private BufferedImage sprite;
  
  
  private MapTile(String name, String path)
  {
    try {
    sprite = ImageIO.read(new File(path));
    } catch (IOException e) {
     e.printStackTrace();
    }
 
  }
  
  public BufferedImage getSprite()
  {
    return this.sprite;
    
  }

  }
  
}
