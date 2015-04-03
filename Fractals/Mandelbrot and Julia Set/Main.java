package solution;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * This program calculates the Mandelbrot Set, allows you to click and zoom on the set, then calculates the corresponding
 * Julia Set, and allows you to click and zoom on that set
 * @author Michael Kochell
 *
 */
public class Main 
{	
	
	public static void main(String[] fractalsRule)
	{
		new Main();
	}	
	
	public Main()
	{		
		JFrame frame1 = new JFrame();	//frame for two sets
		frame1.setSize(1605, 600);
		frame1.setLocation(200, 0);
		JFrame frame2 = new JFrame();	//frame for buttons
		frame2.setSize(1600, 200);
		frame2.setLocation(200, 600);
		JuliaSetView juliaView = new JuliaSetProxy();
		MandelbrotSetView mandelbrotView = new MandelbrotSetProxy(juliaView);		
		mandelbrotView.drawFractal();
		juliaView.generateFractal(MandelbrotSetView.currentX, MandelbrotSetView.currentY);
		juliaView.repaint();
		GridLayout layout = new GridLayout();
		layout.setColumns(2);
		layout.setHgap(5);
		ButtonPanel panel = new ButtonPanel(mandelbrotView, juliaView);
		frame1.setLayout(layout);
		frame1.add(mandelbrotView);
		frame1.add(juliaView);
		frame2.add(panel);
		frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Graphics g = juliaView.getGraphics();
		g.fill3DRect(10, 10, 200, 200, true);
	}
}
