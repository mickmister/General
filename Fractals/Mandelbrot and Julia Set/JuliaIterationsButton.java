package solution;
import javax.swing.JTextArea;

/**
 * This class is used for adjusting the maximium iterations of the Julia Set calcuation
 * @author kochelmj
 *
 */
public class JuliaIterationsButton extends AbstractButton
{
	private JTextArea textArea;

	public JuliaIterationsButton(MandelbrotSetView mandelView,
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
			JuliaSetView.MAXITERS += 50;
		}
		else
		{
			if(JuliaSetView.MAXITERS > 50)
			JuliaSetView.MAXITERS -= 50;
		}
		this.juliaView.drawFractal();
		String xRange = "[" + this.juliaView.area.getX() + " " + (this.juliaView.area.getX() + this.juliaView.area.getWidth()) + "]";
		String yRange = "[" + this.juliaView.area.getY() + " " + (this.juliaView.area.getY() + this.juliaView.area.getHeight()) + "]";
		textArea.setText("Julia max iters: " + JuliaSetView.MAXITERS + "\nX range: " + xRange + "\nY range: " + yRange);
	}
}
