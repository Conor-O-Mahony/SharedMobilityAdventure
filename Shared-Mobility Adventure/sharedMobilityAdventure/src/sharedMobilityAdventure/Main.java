package sharedMobilityAdventure;

import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

      JFrame menuFrame = new JFrame();
      menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      menuFrame.setResizable(false);
      menuFrame.setTitle("Shared-Mobility Adventure");  
      
      MenuPanel menuPanel = new MenuPanel(menuFrame);
      menuFrame.add(menuPanel);
 
      menuFrame.pack();
      menuFrame.setLocationRelativeTo(null);
      menuFrame.setVisible(true); 
      
  }

  public static void openGameWindow(MenuPanel menuPanel, JFrame menuFrame) {  	
  	
	  JFrame gameFrame = new JFrame();
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.setResizable(false);
      gameFrame.setTitle("Shared-Mobility Adventure");

      GamePanel gamePanel = new GamePanel();
      gameFrame.add(gamePanel);

      gameFrame.pack();
      gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
    	
      menuFrame.dispose();
      
  }
}