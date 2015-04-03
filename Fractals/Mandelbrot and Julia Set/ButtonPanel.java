package solution;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This class is used to store all the buttons below the fractal frame.
 * @author kochelmj
 *
 */
public class ButtonPanel extends JPanel
{
	private MandelbrotSetView mandelView;
	private JuliaSetView juliaView;
	private HueButton hueIncButton;
	private HueButton hueDecButton;
	private MandelbrotIterationsButton incMandelbrotIterationsButton;	
	private MandelbrotIterationsButton decMandelbrotIterationsButton;
	private JuliaIterationsButton incJuliaIterationsButton;	
	private JuliaIterationsButton decJuliaIterationsButton;
	private JTextArea mandelItersTextArea;
	private JTextArea juliaItersTextArea;
	private MandelZoomOutButton mandelZoomButton;
	private JuliaZoomOutButton juliaZoomButton;
	
	public ButtonPanel(MandelbrotSetView mandelView, JuliaSetView juliaView)
	{
		GridLayout layout = new GridLayout();
		layout.setColumns(8);
		this.setLayout(layout);
		this.mandelView = mandelView;
		this.juliaView = juliaView;
		this.mandelItersTextArea = this.mandelView.textArea;
		this.juliaItersTextArea = this.juliaView.textArea;
		this.hueIncButton = new HueButton(this.mandelView, this.juliaView, true);
		this.hueIncButton.setText("Cycle color forward");		
		this.hueDecButton = new HueButton(this.mandelView, this.juliaView, false);
		this.hueDecButton.setText("Cycle color backward");
		this.incMandelbrotIterationsButton = new MandelbrotIterationsButton(this.mandelView, this.juliaView, true, this.mandelItersTextArea);
		this.incMandelbrotIterationsButton.setText("Increment max iters");
		this.decMandelbrotIterationsButton = new MandelbrotIterationsButton(this.mandelView, this.juliaView, false, this.mandelItersTextArea);
		this.decMandelbrotIterationsButton.setText("Decrement max iters");
		this.incJuliaIterationsButton = new JuliaIterationsButton(this.mandelView, this.juliaView, true, this.juliaItersTextArea);
		this.incJuliaIterationsButton.setText("Increment max iters");
		this.decJuliaIterationsButton = new JuliaIterationsButton(this.mandelView, this.juliaView, false, this.juliaItersTextArea);
		this.decJuliaIterationsButton.setText("Decrement max iters");
		this.add(this.incMandelbrotIterationsButton);
		this.add(this.decMandelbrotIterationsButton);
		this.mandelZoomButton = new MandelZoomOutButton(this.mandelView, this.juliaView, true);
		this.add(this.mandelZoomButton);
		this.add(this.hueIncButton);
		this.add(this.hueDecButton);
		this.juliaZoomButton = new JuliaZoomOutButton(this.mandelView, this.juliaView, true);
		this.add(this.juliaZoomButton);
		this.add(this.incJuliaIterationsButton);
		this.add(this.decJuliaIterationsButton);		
	}
}
