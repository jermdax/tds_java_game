package by_software.map;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import by_software.engine.render.graphics.TexturePack;

public class MapMaker {
  
  private static int sideBarWidth = 302;
  private int numTiles;
  private Dimension tileSize;
  private int sideScrollHeight = 3000;

 
  private int sizeX, sizeY;
  private JFrame  frame;
  private JPanel panel, sidePanel;
  private Canvas canvas;
  private OptionPanel optionPanel;
  private MapTile selectedTile;
  private MapPanel mapPanel;
  private ScrollPane sP;
  
  
  private MapTile defaultTile;
  private TexturePack texturePack;
  private SelectButton[] selectButtons;
  private static String path = "";

  static int  maxX  =100,maxY=100 ; 

  public static void main(String[] args)
  {
    TexturePack t =  new TexturePack("D:/work/by_software/src/by_software/map/sprites/",new Dimension(16,16),new Dimension(8,16)); 
    
  //TexturePack t =  new TexturePack("D:/work/by_software/src/by_software/map/sprites/",new Dimension(16,16),new Dimension(8,16)); 
    MapMaker m = new MapMaker("Map Maker","D:/work/by_software/src/",40,40,t);
  }
  

  public MapMaker(String title, String path, int x, int y, TexturePack texturePack)
  {
    this.sizeX = x;
    this.sizeY = y;
    this.defaultTile = MapTile.FLOOR;
    this.selectedTile = MapTile.FLOOR;
    this.tileSize = texturePack.getTopDownSize();
    this.texturePack = texturePack;
    MapTile.loadAllTiles(texturePack);
    
    Dimension scrollMapSize =new Dimension(x > maxX?maxX * tileSize.width :x * (tileSize.height),
                      y>maxY?maxY * tileSize.height :y * (tileSize.height) );
    //map = new short[x][y];
  
    //TODO load in tile enums(?) find number of tiles and set perfered hieght of sidepanel 
   
    sideScrollHeight = (MapTile.values().length * tileSize.height) /  sideBarWidth /tileSize.width;
    //set up the JFrame
    frame = new JFrame(title);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setIgnoreRepaint(true);
    
    
    panel = new JPanel();
    panel.setLayout(new BorderLayout());

    
    sidePanel = new JPanel();
    sidePanel.setBackground(Color.lightGray);
    sidePanel.setLayout(new FlowLayout());
    sidePanel.setPreferredSize(new Dimension(sideBarWidth,sideScrollHeight));
   
    JScrollPane buttonPane =  new JScrollPane(sidePanel);
    buttonPane.setPreferredSize(new Dimension(sideBarWidth + 20,frame.getHeight()));
    buttonPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    selectButtons = new SelectButton[MapTile.values().length]; 
    for(int i=0;i < MapTile.values().length ;i++ )
    {   
       selectButtons[i]  = new SelectButton(MapTile.values()[i]);
       sidePanel.add(selectButtons[i]);
    }     
     
     JPanel sidePanel = new JPanel();
     sidePanel.setLayout(new BorderLayout());
     sidePanel.add(buttonPane,BorderLayout.CENTER);
     
     optionPanel = new OptionPanel();
     sidePanel.add(optionPanel,BorderLayout.NORTH);
     
     
     sP =  new ScrollPane();
     sP.setPreferredSize(scrollMapSize);
     
     mapPanel = new MapPanel(x,y,MapTile.getTopDownSize(),defaultTile); 
     
     sP.add(mapPanel);
     panel.add(sidePanel,BorderLayout.WEST);
     panel.add(sP,BorderLayout.CENTER);
     frame.add(panel);
     frame.pack();
     frame.setVisible(true);
     frame.repaint();

  }

  public void importMap(String name)
  {
    mapPanel.loadMap(name);
    mapPanel.revalidate();
  }
  
  public void exportMap(String name)
  {
    try {
    PrintWriter writer = new PrintWriter(name,"UTF-8");

    
    writer.print(mapPanel.toString());
    
    writer.close();
  } catch (FileNotFoundException e) {  
    e.printStackTrace();
  } catch (UnsupportedEncodingException e) {
    e.printStackTrace();
  }
  }

  private class SelectButton extends JButton implements ActionListener{
  
    MapTile tile;
    public SelectButton(MapTile tile)
    {
    super();
    
      this.tile = tile;
      setVisible(true);
      setPreferredSize(MapTile.getTopDownSize());
      setIcon(new ImageIcon(tile.getTopSprite()));
      addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
   
      mapPanel.setSelectedTile(tile);
      optionPanel.update(tile);
    }
  }
  
  public class OptionPanel extends JPanel{

    MapTile tile;
    JLabel tileLabel;
    JTextArea textArea;
    JPanel selectedPanel;
    
    private OptionPanel()
    {
     
      setLayout(new BorderLayout());

      add(new ExportPanel(),BorderLayout.SOUTH);

      
      selectedPanel = new JPanel();
      selectedPanel.setLayout(new BorderLayout());
     
      textArea = new  JTextArea();
      textArea.setText(selectedTile.toString());
      textArea.setVisible(true);

      tileLabel = new JLabel();
      tileLabel.setIcon(new ImageIcon(selectedTile.getTopSprite()));
      tileLabel.setVisible(true);
      
      selectedPanel.add(textArea,BorderLayout.CENTER);
      selectedPanel.add(tileLabel,BorderLayout.WEST);
   
      add(selectedPanel,BorderLayout.NORTH);
    }
    
    public void update(MapTile tile)
    {
      tileLabel.setIcon(new ImageIcon(tile.getTopSprite()));
      textArea.setText(tile.toString());
    }
  }


  private class ExportPanel extends JPanel{
  
    
    JTextField mapName;
    
    public ExportPanel()
    {
    
      mapName = new JTextField(12);
      
      add(new JButton(new AbstractAction("Export") {


          public void actionPerformed(ActionEvent e) 
          {
             exportMap(mapName.getText());
          }
      }));
      
      add(new JButton(new AbstractAction("Import") {


            public void actionPerformed(ActionEvent e) 
            {
               importMap(mapName.getText());
            }
        }));
      add(mapName);
    }
  }
}
