package game;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class window extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public window(){
		
		super("snake v003.2");
		
		JPanel panel = new mainGame();
		add(panel);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		((mainGame) panel).run();
		
	}
}
