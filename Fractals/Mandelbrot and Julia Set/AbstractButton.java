package solution;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This is an abstract class for using the MandelbrotSetView and JuliaSetView classes
 * @author kochelmj
 *
 */
public abstract class AbstractButton extends JButton
{
	public MandelbrotSetView mandelView;
	public JuliaSetView juliaView;
	public boolean inc;
	
	public AbstractButton(MandelbrotSetView mandelView, JuliaSetView juliaView, boolean inc)
	{
		this.mandelView = mandelView;
		this.juliaView = juliaView;
		this.inc = inc;
		this.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				specialFunction();				
			}});			
	}	
	
	public abstract void specialFunction();
}
