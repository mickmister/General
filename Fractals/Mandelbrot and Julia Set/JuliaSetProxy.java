package solution;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class JuliaSetProxy extends JuliaSetView
{
	Thread retrievalThread;
	boolean retrieving = false;
	Rectangle2D rec;
	public JuliaSetProxy()
	{
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
	
	private MouseListener getMouseListener() {
		return new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e)
			{
				Point point = e.getPoint();
				double newWidth = area.getWidth() / ZOOMFACTOR;
				double newHeight = area.getHeight() / ZOOMFACTOR;
				
				double px = getXRelative(point.x);
				double py = getYRelative(point.y);
				
				//Calculated so mouse stays on same point
				double newX = px - newWidth / 2;
				double newY = py - newHeight / 2;
				
				area.setRect(newX, newY, newWidth, newHeight);
				
				
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
								generateFractal(currentCX, currentCY);
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
				textArea.setText("Julia max iters: " + JuliaSetView.MAXITERS + "\nX range: " + xRange + "\nY range: " + yRange);
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
