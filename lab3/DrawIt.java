import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/*
 * DrawIt - 
 * Eric McCreath 2009
 */

public class DrawIt  implements Runnable {

	static final Dimension dim = new Dimension(800,600);
	
	public enum Mode {
	    PEN, SMUDGE, SPRAY, FILL, ERASE
	}
	
	Mode mode = Mode.PEN;
	
	JFrame jf;
	DrawArea da;
	JMenuBar bar;
	JMenu jmfile, jmoptions, jmtools;
	JMenuItem jmiquit, jmiexport, jmthick, jmalpha, jmpen, jmsmudge, jmspray, jmfill, jmerase;
	ToolBar colorToolbar;
	
	int alpha = 255;
	float thickness = 5.0f;
	
	public DrawIt() {
		SwingUtilities.invokeLater(this);
	}
	
	public void run() {
		jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		da = new DrawArea(dim,this);
		//da.setFocusable(true);
		jf.getContentPane().add(da,BorderLayout.CENTER);
		
		// create a toolbar
		colorToolbar = new ToolBar(BoxLayout.Y_AXIS);
		colorToolbar.addbutton("Red", Color.RED);
		colorToolbar.addbutton("Blue", Color.BLUE);
		colorToolbar.addbutton("Green", Color.GREEN);
		jf.getContentPane().add(colorToolbar,BorderLayout.LINE_END);
		
		// create some menus
		bar = new JMenuBar();
		jmfile = new JMenu("File");
		jmoptions = new JMenu("Options");
		jmtools = new JMenu("Tools");
		jmiexport = new JMenuItem("Export");
		jmfile.add(jmiexport);
		jmiexport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				da.export(new File("export.png"));
			}});
		
		//Add thickness menu options
		jmthick = new JMenuItem("Thickness");
		jmoptions.add(jmthick);
		jmthick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thickness = Float.parseFloat(JOptionPane.showInputDialog(jf, "Enter a thickness", null));
			}});
		
		//Add alpha menu options
		jmalpha = new JMenuItem("Alpha");
		jmoptions.add(jmalpha);
		jmalpha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alpha = Integer.parseInt(JOptionPane.showInputDialog(jf, "Enter an alpha between 0 and 255", null));
			}});
		
		//Add pen tool option
		jmpen = new JMenuItem("Pen");
		jmtools.add(jmpen);
		jmpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Mode.PEN;
			}});
		
		//Add smudge tool option
		jmsmudge = new JMenuItem("Smudge");
		jmtools.add(jmsmudge);
		jmsmudge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Mode.SMUDGE;
			}});
		
		//Add spray tool option
		jmspray = new JMenuItem("Spray");
		jmtools.add(jmspray);
		jmspray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Mode.SPRAY;
			}});
		
		//Add fill tool option
		jmfill = new JMenuItem("Fill");
		jmtools.add(jmfill);
		jmfill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Mode.FILL;
			}});
		
		//Add erase tool option
		jmerase = new JMenuItem("Erase");
		jmtools.add(jmerase);
		jmerase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = Mode.ERASE;
			}});
		
		jmiquit = new JMenuItem("Quit");
		jmfile.add(jmiquit);
		jmiquit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}});
		
		bar.add(jmfile);
		bar.add(jmoptions);
		bar.add(jmtools);
		jf.setJMenuBar(bar);
		
		jf.pack();
		jf.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		DrawIt sc = new DrawIt();
	}
}
