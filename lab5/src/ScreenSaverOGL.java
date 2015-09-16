import java.awt.DisplayMode;
import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.swing.JFrame;

public class ScreenSaverOGL implements GLEventListener{

	/*  Original code from http://www.tutorialspoint.com/jogl/jogl_3d_graphics.htm
	    Modified for COMP4610 labs */
	
	private void makeCube(GLAutoDrawable drawable){
		final GL2 gl = drawable.getGL().getGL2();
		gl.glBegin( GL2.GL_QUADS ); // Start Drawing The Cube
		
		// Top face
		gl.glColor3f( 1f,0f,0f );   //red color
		gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Top)
		gl.glVertex3f( -1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)
		
		// Bottom Face
		gl.glColor3f( 0f,1f,0f ); //green color
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Top Right Of The Quad 
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Top Left Of The Quad 
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
		
		// Side Face
		gl.glColor3f( 0f,0f,1f ); //blue color
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 
		
		
		// Side Face
		gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
		gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
		gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Back)
		
		
		// Side Face
		gl.glColor3f( 1f,0f,1f ); //purple (red + green)
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
		gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 
		
		
		// Side Face
		gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
		gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Left Of The Quad 
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad 
		
		gl.glEnd(); // Done Drawing The Quad
	}
	
	private void makeCube(float r, float g, float b, GLAutoDrawable drawable){
		final GL2 gl = drawable.getGL().getGL2();
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
		
		gl.glBegin( GL2.GL_QUADS ); // Start Drawing The Cube
		
		// Set colour
		gl.glColor3f( r,g,b );   //red color
		
		// Top face
		gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Top)
		gl.glVertex3f( -1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)
		
		// Bottom Face
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Top Right Of The Quad 
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Top Left Of The Quad 
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
		
		// Side Face
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 
		
		// Side Face
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
		gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
		gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Back)
		
		// Side Face
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
		gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 
		
		// Side Face
		gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Left Of The Quad 
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad 
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad 
		
		gl.glEnd(); // Done Drawing The Quad
	}
	
	private void makeToothbrush(GLAutoDrawable drawable){
		final GL2 gl = drawable.getGL().getGL2();
			
			//Draw the bristles
			gl.glPushMatrix();
			  gl.glScalef(1f, 1.5f, 1.7f);
			  makeCube(1f,1f,1f, drawable);
			gl.glPopMatrix();
			
			//Draw the head
			gl.glPushMatrix();
			  gl.glTranslatef(0, -1.5f, 0);
			  gl.glScalef(1.5f, 0.5f, 3f);
			  makeCube(1f,0f,0f, drawable);
			gl.glPopMatrix();
			
			//Draw the handle
			gl.glPushMatrix();
			  gl.glTranslatef(0, -1.5f, 3f);
			  gl.glScalef(1.1f, 0.4f, 6f);
			  makeCube(1f,0f,0f, drawable);
			gl.glPopMatrix();

	}
	
	private void drawCube(float x, float y, float z, GLAutoDrawable drawable){
		final GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glTranslatef( x, y, z ); 
		gl.glRotatef( rquad, 0.0f, 1.0f, 0.0f ); // Rotate the cube on just it's Y axis
		gl.glCallList(1);
		gl.glPopMatrix();
	}
	
	private void drawToothbrush(float x, float y, float z, GLAutoDrawable drawable){
		final GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glTranslatef( x, y, z ); 
		gl.glRotatef( rquad, 1.0f, 1.0f, 1.0f ); // Rotate the cube on just it's Y axis
		gl.glCallList(2);
		gl.glPopMatrix();
	}

	public static DisplayMode dm, dm_old;
	private GLU glu = new GLU();
	private float rquad=0.0f;
	private int texture;
	@Override
	public void display( GLAutoDrawable drawable ) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
		
		float dist = 10f;
		
		drawToothbrush(-dist, -dist, -50f, drawable);
		drawToothbrush(dist, dist, -50f, drawable);
		drawToothbrush(-dist, dist, -50f, drawable);
		drawToothbrush(dist, -dist, -50f, drawable);
		
		/*
		//Draw some rotating cubes
		drawCube( dist,  dist, -20f, drawable);
		drawCube(-dist, -dist, -20f, drawable);
		drawCube( dist, -dist, -20f, drawable);
		drawCube(-dist, dist, -20f, drawable);
		*/
		
		gl.glFlush();
		rquad -=0.90f;
	}
	@Override
	public void dispose( GLAutoDrawable drawable ) {
		// TODO Auto-generated method stub
	}
	@Override
	public void init( GLAutoDrawable drawable ) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel( GL2.GL_SMOOTH );
		gl.glClearColor( 0f, 0f, 0f, 0f );
		gl.glClearDepth( 1.0f );
		gl.glEnable( GL2.GL_DEPTH_TEST );
		gl.glDepthFunc( GL2.GL_LEQUAL );
		gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );
		gl.glEnable(GL2.GL_TEXTURE_2D);
		try
		{
			File im = new File("C:/uni/comp4610/lab5/src/colgate.png");
			Texture t = TextureIO.newTexture(im, true);
			texture = t.getTextureObject(gl);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		//Put the cube into list 1
		gl.glNewList(1, GL2.GL_COMPILE);
		makeCube(drawable);
		gl.glEndList();
		
		//Put the toothbrush into list 2
		gl.glNewList(2, GL2.GL_COMPILE);
		makeToothbrush(drawable);
		gl.glEndList();
		
	}
	@Override
	public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height ) {
		final GL2 gl = drawable.getGL().getGL2();
		if( height <=0 )
			height =1;
		final float h = ( float ) width / ( float ) height;
		gl.glViewport( 0, 0, width, height );
		gl.glMatrixMode( GL2.GL_PROJECTION );
		gl.glLoadIdentity();
		glu.gluPerspective( 45.0f, h, 1.0, 500.0 );
		gl.glMatrixMode( GL2.GL_MODELVIEW );
		gl.glLoadIdentity();
	}
	public static void main( String[] args ) {
		final GLProfile profile = GLProfile.get( GLProfile.GL2 );
		GLCapabilities capabilities = new GLCapabilities( profile );
		// The canvas 
		final GLCanvas glcanvas = new GLCanvas( capabilities );
		ScreenSaverOGL screen = new ScreenSaverOGL();
		glcanvas.addGLEventListener( screen );
		glcanvas.setSize( 800, 800 );
		final JFrame frame = new JFrame ( "COMP4610 Lab 5" );
		frame.getContentPane().add( glcanvas );
		frame.setSize( frame.getContentPane().getPreferredSize() );
		frame.setVisible( true );
		final FPSAnimator animator = new FPSAnimator( glcanvas, 300,true );
		animator.start();
	}
}