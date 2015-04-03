package solution;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class MandelbrotSetProxy extends MandelbrotSetView
{
	Thread retrievalThread;
	boolean retrieving = false;
	Rectangle2D rec;
	public MandelbrotSetProxy(JuliaSetView juliaView) 
	{
		super(juliaView);
		this.addMouseListener(getMouseListener());
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(this.image, 0, 0, PANELWIDTH, PANELHEIGHT, this);		
		if(retrieving)
		{
			((Graphics2D) g).draw(rec);
		}
	}	
	
	private void drawRectangle(Point point)
	{
		double height = PANELHEIGHT / ZOOMFACTOR;
		double width = PANELWIDTH / ZOOMFACTOR;
		rec = new Rectangle2D.Double(point.x - width / 2, point.y - height / 2, width, height);	
	}
	
	private MouseListener getMouseListener()
	{
		return new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e)
			{
				Point point = e.getPoint();
				double newWidth = area.getWidth() / ZOOMFACTOR;
				double newHeight = area.getHeight() / ZOOMFACTOR;
				
				final double px = getXRelative(point.x);
				final double py = getYRelative(point.y);
				
				//Calculated so mouse stays on same point
				double newX = px - (((double) point.x / PANELWIDTH) * newWidth);
				double newY = py - (((double) point.y / PANELHEIGHT) * newHeight);
				
				area.setRect(newX, newY, newWidth, newHeight);
				
				currentX = px;
				currentY = py;
				
				if (!retrieving) {
					retrieving = true;
					drawRectangle(point);
					repaint();
					retrievalThread = new Thread(new Runnable() 
					{
						public void run() 
						{
							try 
							{
								new Thread(new Runnable()
								{

									@Override
									public void run() 
									{
										juliaView.resetFields();
										juliaView.generateFractal(px, py);
										juliaView.repaint();
									}
									
								}).start();
								
								image = generateFractal();
								retrieving = false;
								repaint();
								
									
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					retrievalThread.start();
				}
				
				
				
				
				
				String xRange = "[" + area.getX() + " " + (area.getX() + area.getWidth()) + "]";
				String yRange = "[" + area.getY() + " " + (area.getY() + area.getHeight()) + "]";
				textArea.setText("Mandelbrot max iters: " + MandelbrotSetView.MAXITERS + "\nX range: " + xRange + "\nY range: " + yRange);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}			
		};
	}

}
