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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SaveLoadPanel extends JPanel {
	
	private int buttonWidth = 16*9;
	private int buttonHeight = 16 * 4;
	private JPanel game;
	private int noofButtons = 3;

	private static final long serialVersionUID = 8148807433563369470L;

	public SaveLoadPanel(JPanel gameFrame, JFrame saveloadFrame, String mode) {
        setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
        setLayout(null);
        game = gameFrame;
        
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
        		add(createTextBox(xLoc, 250, "Overwrite Save"));
        	} else {
        		add(createTextBox(xLoc, 250, "New Save"));
        	}
        	add(createButton(saveloadFrame,xLoc,350,"Save to file",fileName));
		}
	}
	
	private void LoadPanel(JFrame saveloadFrame) {
		for (int i=1; i<noofButtons+1; i++) {
        	int xLoc = getButtonXLoc(noofButtons,i);
        	String fileName = String.format("savestate%d.ser",i);
        	
        	if (checkFile(fileName)) {
        		add(createTextBox(xLoc, 250, "Load save"));
        		add(createButton(saveloadFrame,xLoc,350,"Load from file",fileName));
        	} else {
        		add(createTextBox(xLoc, 250, "No save file"));
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

    private JButton createButton(JFrame frame, int buttonX, int buttonY, String text, String fileName) {
        JButton button = new JButton(text);
        Rectangle bounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        button.setBounds(bounds);

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        //g.fillRect(x, y, tile, tile);
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
