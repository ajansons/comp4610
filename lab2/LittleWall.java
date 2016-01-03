import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class LittleWall implements ActionListener {

	/**
	 * Little Wall Rock Climbing Copyright 2009 Eric McCreath GNU LGPL
	 */

	final static Dimension dim = new Dimension(800, 600);
	final static XYPoint wallsize = new XYPoint(8.0, 6.0);

	JFrame jframe;
	GameComponent canvas;
	Wall wall;
	PlayerSpring player;
	Timer timer;

	public LittleWall() {
		jframe = new JFrame("Little Wall");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new GameComponent(dim);
		jframe.getContentPane().add(canvas);
		jframe.pack();
		jframe.setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		LittleWall lw = new LittleWall();
		lw.drawTitleScreen();
		lw.startRunningGame();
	}

	private void startRunningGame() {
		wall = new Wall(dim, wallsize);
		wall.draw(canvas.getBackgroundGraphics());
		player = new PlayerSpring(wall);
		canvas.addMouseMotionListener(player);
		canvas.addKeyListener(player);
		
		timer = new Timer(1000 / 15, this);
		timer.start();
	}

	private void drawTitleScreen() throws InterruptedException {
		Graphics2D bg = canvas.getBackgroundGraphics();
		
		int x = 400;
	    int y = 0;
		
		GradientPaint cliffFaceGradient = new GradientPaint(x, y, new Color(153, 102, 0), x, 600, new Color(64, 43, 0));
		
		bg.setPaint(cliffFaceGradient);
		
		bg.fillRect(0, 0, dim.width, dim.height);
		canvas.clearOffscreen();

		Graphics2D os = canvas.getOffscreenGraphics();
		os.setColor(Color.red);
		os.setFont(new Font("Droid Sans", Font.PLAIN, 80));
		os.drawString("Little              ", 100, 100);
		os.setColor(Color.orange);
		os.drawString("       Wall         ", 100, 150);
		os.setColor(Color.yellow);
		os.drawString("            Climbing", 100, 200);

		// add your code here to make the title screen more interesting.
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("littleWallLogo.png"));
		} catch (IOException e) {
			
		}
		
		AffineTransform tx = new AffineTransform();
		
		tx.translate(50, 220);
		tx.scale(2, 1.5);
		
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		img = op.filter(img, null);
		
		os.drawImage(img, 0, 0, null);
		
		canvas.drawOffscreen();
		Thread.sleep(1000);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			canvas.clearOffscreen();
			Graphics2D of = canvas.getOffscreenGraphics();
			player.draw(of);
			player.update(canvas, wall);
			canvas.drawOffscreen();
		}
	}
}
