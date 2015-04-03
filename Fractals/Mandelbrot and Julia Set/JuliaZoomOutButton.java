package solution;
import java.awt.geom.Rectangle2D;

/**
 * This class is used for zooming in on the Julia Set
 * @author kochelmj
 *
 */
public class JuliaZoomOutButton extends AbstractButton
{

	public JuliaZoomOutButton(MandelbrotSetView mandelView,
			JuliaSetView juliaView, boolean inc)
	{
		super(mandelView, juliaView, inc);
		this.setText("Zoom out Julia Set");
	}

	@Override
	public void specialFunction()
	{
		Rectangle2D rec = this.juliaView.area;
		double newWidth = rec.getWidth() *JuliaSetView.ZOOMFACTOR;
		double newHeight = rec.getHeight() * JuliaSetView.ZOOMFACTOR;
		double newX = rec.getX() - (JuliaSetView.ZOOMFACTOR - 1);
		double newY = rec.getY() - (JuliaSetView.ZOOMFACTOR - 1);
		this.juliaView.area.setRect(newX, newY, newWidth, newHeight);
		this.juliaView.drawFractal();
		String xRange = "[" + this.juliaView.area.getX() + " " + (this.juliaView.area.getX() + this.juliaView.area.getWidth()) + "]";
		String yRange = "[" + this.juliaView.area.getY() + " " + (this.juliaView.area.getY() + this.juliaView.area.getHeight()) + "]";
		this.juliaView.textArea.setText("Julia max iters: " + JuliaSetView.MAXITERS + "\nX range: " + xRange + "\nY range: " + yRange);
	}

}
