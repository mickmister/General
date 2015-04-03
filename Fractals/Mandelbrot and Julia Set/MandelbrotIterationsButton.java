package solution;
import javax.swing.JTextArea;

/**
 * This class is used for adjusting the maximum iterations of the Mandelbrot Set calculationp
 * @author kochelmj
 *
 */
public class MandelbrotIterationsButton extends AbstractButton
{

	private JTextArea textArea;

	public MandelbrotIterationsButton(MandelbrotSetView mandelView,
			JuliaSetView juliaView, boolean inc, JTextArea textArea) 
	{
		super(mandelView, juliaView, inc);
		this.textArea = textArea;
	}

	@Override
	public void specialFunction()
	{
		if(this.inc)
		{
			MandelbrotSetView.MAXITERS += 50;
		}
		else
		{
			if(MandelbrotSetView.MAXITERS > 50)
			MandelbrotSetView.MAXITERS -= 50;
		}
		this.mandelView.drawFractal();
		String xRange = "[" + this.mandelView.area.getX() + " " + (this.mandelView.area.getX() + this.mandelView.area.getWidth()) + "]";
		String yRange = "[" + this.mandelView.area.getY() + " " + (this.mandelView.area.getY() + this.mandelView.area.getHeight()) + "]";
		textArea.setText("Mandelbrot max iters: " + MandelbrotSetView.MAXITERS + "\nX range: " + xRange + "\nY range: " + yRange);
	}
}
