package solution;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This view allows you to zoom in and out of a Mandelbrot Set, and view the corresponding Julia Set on the
 * opposite view.
 * @author kochelmj
 *
 */
public class MandelbrotSetView extends JPanel 
{
	BufferedImage image;
	Rectangle2D area;
	JuliaSetView juliaView;
	public static double ZOOMFACTOR = 1.5; //we will divide the widths and heights of the area by this number
	public static final int PANELWIDTH = 800;
	public static final int PANELHEIGHT = 600;
	public static int MAXITERS = 200;
	public static double DIVERGELIMIT = 2 << 16;
	protected static double currentX;
	protected static double currentY;
	public JTextArea textArea;
	
	
	public MandelbrotSetView(JuliaSetView juliaView)
	{
		this.juliaView = juliaView;
		this.area = new Rectangle2D.Double(-2.5, -1.5, 4.0, 3.0);
		this.image = new BufferedImage(PANELWIDTH, PANELHEIGHT, BufferedImage.TYPE_INT_ARGB);
		textArea = new JTextArea();
		textArea.setEditable(false);
		String xRange = "[" + this.area.getX() + " " + (this.area.getX() + this.area.getWidth()) + "]";
		String yRange = "[" + this.area.getY() + " " + (this.area.getY() + this.area.getHeight()) + "]";
		textArea.setText("Mandelbrot max iters: " + MAXITERS + "\nX range: " + xRange + "\nY range: " + yRange);
		this.add(textArea);
	}
	
	public BufferedImage generateFractal()
	{
		double zx = 0.0; //first input always zero for Mandelbrot Set
		double zy = 0.0;
		double px;
		double py;
		for(int y = 0; y < PANELHEIGHT; y++)
		{
			for (int x = 0; x < PANELWIDTH; x++)
			{
				px = getXRelative((double) x);
				py = getYRelative(((double) y));
				double numIters = FractalCalulation.iterate(zx, zy, px, py, MAXITERS, DIVERGELIMIT);
				int color = FractalCalulation.handleColor(numIters, MAXITERS);
				this.image.setRGB(x, y, color);
			}
		}
		return image;
	}
	
	double getXRelative(double x)
	{
		return x / (double) PANELWIDTH * this.area.getWidth() + this.area.getX();
	}
	
	double getYRelative(double y)
	{
		return y / (double) PANELHEIGHT * this.area.getHeight() + this.area.getY();
	}
	
	
	
	public void resetFields()
	{
		ZOOMFACTOR = 1.5;
		MAXITERS = 200;
		DIVERGELIMIT = 2.0;
		this.area = new Rectangle2D.Double(-2.5, -1.5, 4.0, 3.0);
		this.image = new BufferedImage(PANELWIDTH, PANELHEIGHT, BufferedImage.TYPE_INT_ARGB);
	}	
	
	public void drawFractal()
	{
		generateFractal();
		repaint();
	}


}
