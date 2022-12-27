import java.awt.Graphics;

import javax.swing.JPanel;



public class render extends JPanel
{
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);	
		
		dinosaur.dino.repaint(g);
	}
}
