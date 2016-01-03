import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/*
 * DrawArea - a simple JComponent for drawing.  The "offscreen" BufferedImage is 
 * used to draw to,  this image is then used to paint the component.
 * Eric McCreath 2009 2015
 */

public class DrawArea extends JComponent implements MouseMotionListener,
		MouseListener {

	private BufferedImage offscreen;
	Dimension dim;
	DrawIt drawit;

	double mouseX;
	double mouseY;
	
	int smudgeColour;
	
	int alf = 255;
	
	public DrawArea(Dimension dim, DrawIt drawit) {
		this.setPreferredSize(dim);
		offscreen = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_INT_RGB);
		this.dim = dim;
   this.drawit = drawit;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);

		clearOffscreen();
	}

	public void clearOffscreen() {
		Graphics2D g = offscreen.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, dim.width, dim.height);
		repaint();
	}

	public Graphics2D getOffscreenGraphics() {
		return offscreen.createGraphics();
	}

	public void drawOffscreen() {
		repaint();
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(offscreen, 0, 0, null);
	}
	
	public void mouseDragged(MouseEvent m) {
		draw(m);
	}

	public void mouseMoved(MouseEvent m) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if(drawit.mode == DrawIt.Mode.SMUDGE){
			smudgeColour = offscreen.getRGB(e.getX(), e.getY());
		}
		
		
	}

	public void mouseReleased(MouseEvent e) {
		
		
		/*
		Graphics2D g = offscreen.createGraphics();
		
		Color col = (Color) drawit.colorToolbar.getSelectCommand();
		
		g.setColor(col);

		switch (drawit.mode) {
		case PEN: 	g.draw(new Line2D.Double(mouseX, mouseY, e.getX(), e.getY()));
			 		break;
		case SMUDGE:
					break;
		case SPRAY: Random generator = new Random(); 
					for(int i = 0; i < 50; i++){
						
						int x = (int) ((double)generator.nextInt(50) * Math.cos(generator.nextDouble()*6.23) + e.getX());
						int y = (int) ((double)generator.nextInt(50) * Math.sin(generator.nextDouble()*6.23) + e.getY());
						
						if ( x < dim.width-1 && y < dim.height-1 && y > 0 && x > 0){
						offscreen.setRGB(x, y, col.getRGB());
						}
					}			
	    			break;
		case FILL:  
					break;
		case ERASE: g.setColor(Color.WHITE);
					g.draw(new Line2D.Double(mouseX, mouseY, e.getX(), e.getY()));
	    			break;
		default:	System.out.println("You got problems! This shouldn't happen!");
					break;
		}
		
		drawOffscreen();
		*/
		
		draw(e);
		
		
	}
	
	public void draw(MouseEvent m){
		Graphics2D g = offscreen.createGraphics();
		Color colour = (Color) drawit.colorToolbar.getSelectCommand();
		colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), drawit.alpha);
		g.setColor(colour);
		g.setStroke(new BasicStroke(drawit.thickness));
		
		switch (drawit.mode) {
		
		case PEN: 	g.draw(new Line2D.Double(mouseX, mouseY, m.getX(), m.getY()));
			 		break;
			 		
		case SMUDGE:
					int s = 10; //amount to smudge
					Color smudgeCol = new Color(smudgeColour);
					System.out.println(smudgeCol.getAlpha());
					g.setColor(smudgeCol);
					g.draw(new Line2D.Double(mouseX, mouseY, m.getX(), m.getY()));
					smudgeColour = smudgeCol.getRGB();
					smudgeCol = new Color(Math.min(smudgeCol.getRed()+5, 255), Math.min(smudgeCol.getGreen()+5, 255), Math.min(smudgeCol.getBlue()+5, 255), Math.max(0, (smudgeCol.getAlpha() - s)));
					smudgeColour = smudgeCol.getRGB();
					
					break;
					
		case SPRAY: Random generator = new Random(); 
					for(int i = 0; i < 50; i++){
						
						int x = (int) ((double)generator.nextInt(50) * Math.cos(generator.nextDouble()*6.23) + m.getX());
						int y = (int) ((double)generator.nextInt(50) * Math.sin(generator.nextDouble()*6.23) + m.getY());
						
						if ( x < dim.width-1 && y < dim.height-1 && y > 0 && x > 0){
							offscreen.setRGB(x, y, colour.getRGB());
						}
					}			
					break;
					
		case FILL:  if ( m.getX() < dim.width-1 && m.getY() < dim.height-1 && m.getY() > 0 && m.getX() > 0){
						flood(new boolean[(int) dim.getHeight()][(int) dim.getWidth()], m.getX(), m.getY(), new Color(offscreen.getRGB(m.getX(), m.getY())), colour);
					}	
					break;
					
		case ERASE: g.setColor(Color.WHITE);
					g.draw(new Line2D.Double(mouseX, mouseY, m.getX(), m.getY()));
	    			break;
	    			
		default:
					System.out.println("You got problems! This shouldn't happen!");
					break;
		}
		
		drawOffscreen();
		mouseX = m.getX();
		mouseY = m.getY();
	}
	
	
	//Algorithm adapted from http://www.cis.upenn.edu/~cis110/14fa/hw/hw09/FloodFill.java
	public void flood(boolean[][] mark, int row, int col, Color srcColor, Color tgtColor) {
		
	Color colour = (Color) drawit.colorToolbar.getSelectCommand();
	colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), drawit.alpha);
		
	// make sure row and col are inside the image
	if (!(col < dim.width-1 && row < dim.height-1 && row > 0 && col > 0)){
		return;
	}
	
	// make sure this pixel hasn't been visited yet
	if (mark[row][col]) return;
	
	// make sure this pixel is the right color to fill
	if (offscreen.getRGB(col, row) != srcColor.getRGB()) return;
	
	// fill pixel with target color and mark it as visited
	offscreen.setRGB(col, row, tgtColor.getRGB());
	mark[row][col] = true;
	
	// recursively fill surrounding pixels
	// (this is equivelant to depth-first search)
	flood(mark, row - 1, col, srcColor, tgtColor);
	flood(mark, row + 1, col, srcColor, tgtColor);
	flood(mark, row, col - 1, srcColor, tgtColor);
	flood(mark, row, col + 1, srcColor, tgtColor);
	}

	public void export(File file) {
		try {
			ImageIO.write(offscreen, "png", file);
		} catch (IOException e) {
			System.out.println("problem saving file");
		}
	}
}
