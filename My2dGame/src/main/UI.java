package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font arial_40, arial_65B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
	DecimalFormat dFormat = new DecimalFormat("#0.00", symbols);
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_65B = new Font("Arial", Font.BOLD, 60);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}
	
	// Text notifications
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	// Game Font for UI (Careful not to instantiate things inside the loop)
	public void draw(Graphics2D g2) {
		
		if(gameFinished == true) {
			
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			
			String text;
			int textLength;
			int x;
			int y;
			
			text = "You found the treasure!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 - (gp.tileSize * 3);
			g2.drawString(text, x, y);
			
			text = "Your time is: " + dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 + (gp.tileSize * 4);
			g2.drawString(text, x, y);
			
			g2.setFont(arial_65B);
			g2.setColor(Color.yellow);
			text = "CONGRATULATIONS!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 + (gp.tileSize * 2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
		}
		else {
			
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
			g2.drawString(": " + gp.player.hasKey, 75, 65);
		
			// Time
			playTime +=(double)1/60;
			g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);
			
			// Message
			if(messageOn == true) {
			
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
			
				messageCounter++;
			
				if(messageCounter > 150) {
					messageCounter = 0;
					messageOn = false;
				}
			}	
		}
	}
}
