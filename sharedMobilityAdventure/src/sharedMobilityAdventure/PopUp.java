
package sharedMobilityAdventure;

import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

public class PopUp implements Serializable{
		
	private static final long serialVersionUID = 2L;
	protected int popupX;
    protected int popupY;
    static transient BufferedImage image;
    int panelWidth = Main.DEFAULT_BOARD_SIZE;
    int panelHeight = Main.DEFAULT_BOARD_SIZE;
    protected static final int WIDTH = 32;
    protected static final int HEIGHT = 32;
    protected boolean visible;
    
    private static String[] informationOptions = {
    "Carbon emissions primarily consist of carbon dioxide (CO2)\n and other greenhouse gases released into the atmosphere.",
    "The burning of fossil fuels like coal, oil, and natural gas\n is the largest source of carbon emissions.",
    "The burning of coal for electricity generation is one of the\n largest sources of carbon emissions globally.",
    "The United States, China, and the European Union are among the largest emitters of carbon dioxide globally.",
    "Ireland has committed to achieving net-zero carbon emissions\n by 2050, in line with the goals of the Paris Agreement.",
    "Ireland has a carbon tax in place to incentivize emission reductions\n and encourage the transition to cleaner energy sources.",
    "The Irish government has established the Climate Action Plan, which outlines\n measures to achieve emissions reductions across various sectors.",
    "The Irish forestry sector contributes to carbon sequestration by planting\n and managing forests, which absorb CO2 from the atmosphere.",
    "The residential and commercial sectors contribute to carbon emissions\n through energy consumption for heating, cooling, and electricity use.",
    "The transportation sector is one of the largest sources of carbon emissions\n in Ireland, accounting for approximately 20% of total emissions.",
    "The Irish government offers incentives to promote the adoption of electric\n vehicles, such as grants for purchasing EVs and subsidies for installing charging infrastructure.",
    "Ireland has implemented measures to improve the fuel efficiency of vehicles,\n such as emissions standards and mandatory vehicle inspections.",
    "Ireland's National Transport Authority promotes sustainable transport options\n and invests in infrastructure to support walking, cycling, and public transit.",
    "The Irish Rail network operates electric and diesel trains, with efforts underway\n to electrify more routes to further reduce carbon emissions.",
    "Public transportation, including buses and trains, plays a significant role in reducing\n carbon emissions by providing an alternative to private car use.",
    "Sustainable transportation initiatives, such as carpooling, ride-sharing, and telecommuting,\n help reduce carbon emissions by optimizing vehicle occupancy and reducing travel demand.",
    "Ireland's Climate Action Plan includes measures to reduce carbon emissions from transportation,\n such as increasing the availability of electric vehicle charging stations and investing in public transit infrastructure.",
    "The transportation of goods, including shipping and freight transport,\n contributes to carbon emissions through the burning of diesel fuel.",
    "Carbon offsets can take various forms, including reforestation projects,\n renewable energy initiatives, and investments in energy efficiency.",
    "The aviation industry is responsible for around 2% of global carbon emissions,\n and efforts are underway to develop sustainable aviation fuels and improve fuel efficiency."
    };
 
    public PopUp() {
    	this.visible = true;
    	Random random = new Random();

    	int randomNumberX = random.nextInt(panelWidth);
        int randomNumberY = random.nextInt(panelHeight);

        int oddNumberX = randomNumberX * 2 + 1;
        int oddNumberY = randomNumberY * 2 + 1;

        popupX = Main.TILE_SIZE / 2 * oddNumberX;
        popupY = Main.TILE_SIZE / 2 * oddNumberY; 
        
        image = Main.popupImage;   
  }
  
    public boolean getVisibility() {
        return this.visible;
    }

    public void draw(Graphics g) {
    	int adjustedX = popupX - (WIDTH / 2);
	    int adjustedY = popupY - (HEIGHT / 2);
	    g.drawImage(image, adjustedX, adjustedY, WIDTH, HEIGHT, null);
	}

	public void setVisibility(boolean visibleUpdated) {
	    this.visible = visibleUpdated;
	}

	public void displayPopup() {
	    Random randomObj = new Random();
	    int randomNumber = randomObj.nextInt(20);
		JOptionPane.showMessageDialog(null, "Did you know: " + informationOptions[randomNumber]);	
	} 
}
