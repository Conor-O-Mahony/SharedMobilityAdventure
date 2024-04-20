package sharedMobilityAdventure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SaveLoadPanel extends JPanel {
	
	private int buttonWidth = 226;
	private int buttonHeight = 65;
	private JPanel game;
	private int noofButtons = 3;
    BufferedImage backgroundImage;

	private static final long serialVersionUID = 8148807433563369470L;

	public SaveLoadPanel(JPanel gameFrame, JFrame saveloadFrame, String mode) {
        setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
        setLayout(null);
        game = gameFrame;
        loadBackgroundImage();
        if (mode=="save") {
        	SavePanel(saveloadFrame);
        } else {
        	LoadPanel(saveloadFrame);
        }
        
        	//add(createButton(saveloadFrame,getButtonXLoc(noofButtons,i),400,"Load from file",String.format("savestate%d.ser",i)));
    }

	private void SavePanel(JFrame saveloadFrame) {
		for (int i=1; i<noofButtons+1; i++) {
        	int xLoc = getButtonXLoc(noofButtons,i);
        	String fileName = String.format("savestate%d.ser",i);
        	
        	if (checkFile(fileName)) {
        		add(createTextBox(xLoc+35, 250, "Overwrite Save"));
        	} else {
        		add(createTextBox(xLoc+35, 250, "New Save"));
        	}
        	add(createSaveButton(saveloadFrame,xLoc,350,"Save to file",fileName));
		}
	}
	
	private void LoadPanel(JFrame saveloadFrame) {
		for (int i=1; i<noofButtons+1; i++) {
        	int xLoc = getButtonXLoc(noofButtons,i);
        	String fileName = String.format("savestate%d.ser",i);
        	
        	if (checkFile(fileName)) {
        		add(createTextBox(xLoc+35, 250, "Load save"));
        		add(createLoadButton(saveloadFrame,xLoc,350,"Load from file",fileName));
        	} else {
        		add(createTextBox(xLoc+35, 250, "No save file"));
        	}
		}
	}
	
	private int getButtonXLoc(int noOfButtons, int buttonNumber) {
		int spacing = (Main.WINDOW_WIDTH - buttonWidth*noOfButtons)/(noOfButtons+1);
		return (spacing+buttonWidth)*buttonNumber - buttonWidth;
	}
	
	private boolean checkFile(String fileName) {
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		} else {
			return false;
		}
	}
 
    private JTextField createTextBox(int x, int y, String text) {
        JTextField Text = new JTextField(text);
        Text.setHorizontalAlignment(SwingConstants.CENTER);
        Text.setBounds(x, y, 144, 64);
        Text.setEditable(false);
        Text.setBackground(Color.BLACK);
        Text.setForeground(Color.RED);
        Text.setFont(new Font("Arial", Font.BOLD, 16));
        Text.setBorder(null);
        return Text;
    }

    private JButton createSaveButton(JFrame frame, int buttonX, int buttonY, String text, String fileName) {

        JButton button = new JButton(text);
        setButtonIcon(button, "images/tiles/savegamebuttondefault.png");
        Rectangle bounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        button.setBounds(bounds);
        //mouse listener for button animation
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setButtonHoverIcon(button, "images/tiles/savegamebuttonhovered.png");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setButtonIcon(button, "images/tiles/savegamebuttondefault.png");
            }
        });
        button.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the current frame
            currentFrame.dispose(); // Dispose the current EndPanel frame
            
            if (text == "Save to file") {
            	try {
					SaveGame(game,fileName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            
            else if (text == "Load from file") {
            	try {
					LoadGame(fileName);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });

        return button;
    }
    
    private JButton createLoadButton(JFrame frame, int buttonX, int buttonY, String text, String fileName) {

        JButton button = new JButton(text);
        setButtonIcon(button, "images/tiles/loadgamebuttondefaultSMALL.png");
        Rectangle bounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        button.setBounds(bounds);
        //mouse listener for button animation
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setButtonHoverIcon(button, "images/tiles/loadgamebuttonhoveredSMALL.png");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setButtonIcon(button, "images/tiles/loadgamebuttondefaultSMALL.png");
            }
        });
        button.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the current frame
            currentFrame.dispose(); // Dispose the current EndPanel frame
            
            if (text == "Save to file") {
            	try {
					SaveGame(game,fileName);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            
            else if (text == "Load from file") {
            	try {
					LoadGame(fileName);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
        });

        return button;
    }
    
    public void SaveGame(JPanel panel,String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(panel);
        out.close();
    }
    
    public void LoadGame(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {

        GamePanel panel = null;

        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);

        panel = (GamePanel) in.readObject();
        
        in.close();
        fileIn.close();
        
        panel.loadImages();
        
        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setTitle("Shared-Mobility Adventure");

        gameFrame.add(panel);

        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }
    //Remove the default button border and fills to make images transparent buttons
    private void setButtonIcon(JButton button, String imagePath) {
    	button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    	button.setBorder(null);
        ImageIcon icon = new ImageIcon(imagePath);
        button.setIcon(icon);
    }

    private void setButtonHoverIcon(JButton button, String hoverImagePath) {
    	button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    	button.setBorder(null);
        ImageIcon icon = new ImageIcon(hoverImagePath);
        button.setRolloverIcon(icon);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        //g.fillRect(x, y, tile, tile);
        //render background image if none present
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("images/tiles/savebg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	JFrame testFrame = new JFrame();
    	testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	testFrame.setResizable(false);
    	testFrame.setTitle("Save/Load from file"); 
        
        SaveLoadPanel test = new SaveLoadPanel(null,testFrame,"save");
        testFrame.add(test);
   
        testFrame.pack();
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
    }

}
