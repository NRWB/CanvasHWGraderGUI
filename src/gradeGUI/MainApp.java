package gradeGUI;

import java.awt.EventQueue;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainApp {
	
	private static final Logger log = Logger.getLogger(MainApp.class.getSimpleName());
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		ProgramLogger pl = new ProgramLogger(log);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppGUI gui = new AppGUI(log);
					gui.pack();
					gui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
				
		//log.info("info being logged!");
		//log.warning("warning being logged!");
		
	}

}
