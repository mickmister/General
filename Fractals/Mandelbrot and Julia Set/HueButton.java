package solution;

/**
 * This button cycles through different hue values for the iteration divergence
 * @author kochelmj
 *
 */
public class HueButton extends AbstractButton
{
	
	public HueButton(MandelbrotSetView view1, JuliaSetView view2, boolean inc)
	{
		super(view1, view2, inc);
	}

	@Override
	public void specialFunction()
	{
		if(this.inc)
		{
			FractalCalulation.HUE_CONSTANT++;
		}
		else
		{
			FractalCalulation.HUE_CONSTANT--;
		}
		this.mandelView.drawFractal();
		this.juliaView.drawFractal();
	}
	

}
