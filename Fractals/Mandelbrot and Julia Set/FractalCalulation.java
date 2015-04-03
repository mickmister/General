package solution;
import java.awt.Color;

/**
 * This class is used for iteration and color handling
 * @author kochelmj
 *
 */
public class FractalCalulation 
{

	public static float HUE_CONSTANT = 3.8f;
	
	//Iteration using complex number math
	public static double iterate(double zx, double zy, double cx, double cy, int maxiters, double limit) 
	{
		double newZx, newZy;
		for(int i = 0; i < maxiters; i++)
		{
			newZx = zx * zx - zy * zy;
			newZy = 2 * zx * zy;			
			zx = newZx + cx;
			zy = newZy + cy;		
			
			if(mag(zx, zy) > limit)
			{
				double result = Math.log(Math.log(mag(zx, zy))/ Math.log(2)) / Math.log(2);
				return i + 1 - result;
			}
		}
		return maxiters;
	}
	
	// Magnitude
	public static double mag(double x, double y) 
	{
		return Math.sqrt(x * x + y * y);
	}

	public static int handleColor(double numIters, int maxIters)
	{
		double hueOffset = (double) numIters / (double) maxIters;
		return Color.HSBtoRGB((float) (HUE_CONSTANT *  hueOffset), 1f, 1f);
	}
	

}
