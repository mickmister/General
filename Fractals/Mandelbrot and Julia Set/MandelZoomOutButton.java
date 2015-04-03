package solution;
import java.awt.geom.Rectangle2D;


public class MandelZoomOutButton extends AbstractButton
{

	public MandelZoomOutButton(MandelbrotSetView mandelView,
			JuliaSetView juliaView, boolean inc)
	{
		super(mandelView, juliaView, inc);
		this.setText("Zoom out Mandelbrot Set");
	}

	@Override
	public void specialFunction()
	{
		Rectangle2D rec = this.mandelView.area;
		double newWidth = rec.getWidth() * MandelbrotSetView.ZOOMFACTOR;
		double newHeight = rec.getHeight() * MandelbrotSetView.ZOOMFACTOR;
		double newX = rec.getX() - (MandelbrotSetView.ZOOMFACTOR - 1);
		double newY = rec.getY() - (MandelbrotSetView.ZOOMFACTOR - 1);
		this.mandelView.area.setRect(newX, newY, newWidth, newHeight);
		this.mandelView.drawFractal();
		String xRange = "[" + this.mandelView.area.getX() + " " + (this.mandelView.area.getX() + this.mandelView.area.getWidth()) + "]";
		String yRange = "[" + this.mandelView.area.getY() + " " + (this.mandelView.area.getY() + this.mandelView.area.getHeight()) + "]";
		this.mandelView.textArea.setText("Mandelbrot max iters: " + MandelbrotSetView.MAXITERS + "\nX range: " + xRange + "\nY range: " + yRange);
	}

}
