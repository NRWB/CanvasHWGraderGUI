package gradeGUI;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ProgramLogger {

	private static Logger logRef;
	
	/*
	public enum levels {
		off,//Integer.MAX_VALUE
		severe,//1000
		warning,//900
		info,//800
		config,//700
		fine,//500
		finer,//400
		finest,//300
		all;//Integer.MIN_VALUE
	}
	*/
	
	public ProgramLogger(Logger l) {
		logRef = l;
		create();
	}
	
	/**
	 * /  The file name separator of the system. Typically either \ or / .
	 * %t The temp directory of the system.
	 * %h The user home directory of the system.
	 * %g The generation number that distinguishes the rotated log files from each other.
	 * %u A unique number to avoid naming conflicts.
	 * %% A single percent sign, in case you want to use that in your file name.
	 */
	public void create() {
		//ConsoleHandler consoleHandler = new ConsoleHandler();
		//logRef.addHandler(consoleHandler);
		FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler("GraderGUI-log.%u.%g.txt");
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		logRef.addHandler(fileHandler);
	}
}
