//import java.awt.BorderLayout;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Scanner;
//import javax.swing.JButton;
//import javax.swing.JColorChooser;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JList;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//
//
//public class HWGUI extends JFrame implements ListSelectionListener, ActionListener, KeyListener{ 
//
//	
//	//TODO: doesn't work with ".java" files that have a bad/unmappable character
//	
//	private File path = new File(".");
//	private File scriptPath = new File("./a");  
//	
//	private JTextArea codeArea = new JTextArea(10,10);
//	private JTextArea notesArea = new JTextArea(10,25);
//	private JTextArea shellResultsArea = new JTextArea(20,25);
//	private JList foo;
//	
//	private JButton runOne = new JButton("Run One Dir");
//	private JButton runAll = new JButton("Run All");
//	private JButton javaCompile = new JButton("javac & java");
//	private JButton checkComments = new JButton("Check Comments");
//	
//	private JTextField scriptPathField = new JTextField(10);
//	private JButton setScript = new JButton("Set script:");
//	
//	//todo: remove this upDir hack one day
//	private static final String upDir = ".................................................";
//	
//	private File targetFile = null;
//	
//	
//	public HWGUI() {
//		super(".");
//		//add a filechooser
//		//add a textarea to east top
//		//add a textarea to east bottom
//		
//		setLayout(new BorderLayout());
//		
//	
//		
//		//File foo = new File();
//		
//		File[] filesAndDirs = Arrays.copyOf(path.listFiles(), path.listFiles().length+1);
//		filesAndDirs[filesAndDirs.length-1] = new File(upDir);  //go up one directory; many dots are a hack for spacing
//		
//		foo = new JList(filesAndDirs);
//		foo.addListSelectionListener(this);
//	
//		scriptPathField.setText(scriptPath.toString());
//		runOne.addActionListener(this);
//		
//		notesArea.addKeyListener(this);
//		codeArea.addKeyListener(this);
//		
//		//JColorChooser fc = new JColorChooser();
//		add(new JScrollPane(foo), BorderLayout.WEST);
//		
//		//malcom gladwell - outliers
//		
//		
//	//	Thread b = new Thread(new Runnable() { public void run(){fc.showOpenDialog(null);}} );
//		//b.start();
//		//fc.show();
//		
//		
//		shellResultsArea.setText("***Console Output*************");
//		
//		Container c = new Container();
//		c.setLayout(new BorderLayout());
//		
//		c.add(new JScrollPane(codeArea), BorderLayout.CENTER);
//		Container d = new Container();
//		d.setLayout(new BorderLayout());
//		d.add(new JScrollPane(notesArea), BorderLayout.SOUTH);
//		d.add(new JScrollPane(shellResultsArea), BorderLayout.CENTER);
//		
//		c.add(d,BorderLayout.EAST);
//		add(c, BorderLayout.CENTER);
//		
//		Container e = new Container();
//		e.setLayout(new FlowLayout());
//		
//		
//		e.add(setScript);
//		e.add(scriptPathField);
//		e.add(runOne);
//		e.add(runAll);
//		e.add(javaCompile);
//		e.add(checkComments);
//		add(e, BorderLayout.SOUTH);
//		
//		javaCompile.addActionListener(this);
//		
//		
//		
//		setSize(1000,800);
//		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
//	
//	
//	public static void main(String[] args) {
//		HWGUI app = new HWGUI();
//	}
//
//	
//	//if the codeArea loses focus or this app loses focus (minmimized/alt tabbed) or this app is closed - SAVE/WRITE the codeArea to file
//	//if the notesArea loses focus or this app '' '' '' '' AND there are chars in the notesArea (ie, length > 0) - SAVE/WRITE the notesArea to notes.txt in that dir
//	
//	//also, have a timer that every 10 seconds checks for changes in the code and notes areas and writes the changes to disk if so
//	//have this timer reset every time a key is typed or changed, so it doesnt flush during edits but around them
//	
//	//note that loses focus above happens before the new selection could occur in the JList, so is a superior hook 
//	
//	
//	
//	@Override
//	public void valueChanged(ListSelectionEvent e) {
//		//if making a selection, change the path and our UI accordingly
//		//if(e.)
//		
//		
//		//path = foo.getSelectedValue() != null ? foo.getSelectedValue().toString() : path;
//		
//		/*while(e.getValueIsAdjusting()) {
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				//e1.printStackTrace();
//			}
//		}*/
//		
//		//SwingUtilities.invoke
//		if(e.getValueIsAdjusting()==false){
//			JList src = (JList)e.getSource();
//			Object target = src.getSelectedValue();
//			if(target != null) {
//				System.out.println("Target not null:"+ target.toString());
//				targetFile = new File(target.toString());
//				if(targetFile.isDirectory() || target.toString().contains(upDir)) {
//				
//					if(target.toString().contains(upDir)) {
//						String txt = path.getParent() == null ? ".." : path.getParent();
//						path = new File(txt);
//						System.out.println("Going up");
//						codeArea.setText("");
//						targetFile = null;
//					} else {
//						path = new File(target.toString());
//					}	
//				} else if(targetFile.toString().contains(".java") || targetFile.toString().contains(".txt")){  //a file, so load the codeArea
//					
//					//ROB!! HERE!!
//					String program = getTextFromFile(target.toString());
//					codeArea.setText(program);
//					
//					//using tempFile
//					System.out.println("Loading text/java file");
//				}
//				if(targetFile != null) {
//					super.setTitle(targetFile.toString());
//				} else {
//					super.setTitle(upDir);
//				}
//				//call method to see if there is a notes.txt in this dir, and if so, load its text into the notes textarea
//				File notesFile = new File(path.toString() + "/notes.txt");
//				if(notesFile.exists() && notesFile.isFile()) {
//					String text = getTextFromFile(notesFile.toString());
//					notesArea.setText(text);
//				} else {
//					//do i need to write the old notes to disk here?
//					notesArea.setText("");
//				}
//				
//				
//				
//			} else {
//				System.out.println("target is null path is:" + path.toString());
//			}
//			
//			
//			refreshDirectoryView();
//			
//		}
//		
//	}
//		
//	private void refreshDirectoryView() {
//		File[] filesAndDirs = Arrays.copyOf(path.listFiles(), path.listFiles().length+1);
//		filesAndDirs[filesAndDirs.length-1] = new File(path.toString() + "//"+upDir);  //go up one directory
//		foo.setListData(filesAndDirs);
//		repaint();
//	}
//	
//	
//	private String getTextFromFile(String fname) {
//		StringBuffer retVal = new StringBuffer();
//		Scanner foo = null;
//		
//		try {
//			foo = new Scanner(new File(fname));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Code target file not found");
//			System.exit(-1);
//		}
//		
//		while (foo.hasNextLine()) {
//			retVal.append(foo.nextLine() + "\n");
//		}
//		
//		return retVal.toString();
//	}
//
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		System.out.println("Button was pressed.");
//		
//		JButton src = (JButton)e.getSource();
//		
//		if(src == runOne) {
//			System.out.println("Gonna copy and run the " + scriptPath + " script");
//			
//			try {
//				
//				//TODO: BUG!! change "cp a" to "cp " + scriptPath.toString 
//				Runtime.getRuntime().exec("cp a " + path.toString()); //copy the script first
//				//todo:switch to run and report?
//			} catch (IOException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//				System.exit(-1);
//			}
//			String[] params = {scriptPath.toString()};
//			runAndReport(params);
//					
//			refreshDirectoryView();
//		} else if (src == runAll) {
//			//go through each directory calling runOne on it
//			File[] list = path.listFiles();
//			for(File f : list) {
//				
//			}
//			
//		} else if(src == setScript) {
//			
//		} else if(src == javaCompile) {	
//			if(targetFile.isFile()) {
//				String[] params = {"javac", removePath(targetFile.toString())};
//				runAndReport(params, true);
//			
//				String shortName = targetFile.toString();
//				int end = shortName.lastIndexOf(".");
//				shortName = removePath(shortName.substring(0, end));
//				
//				params[0] = "java";
//				params[1] = shortName;
//				runAndReport( params, true, true );  //!!! ROB!!
//			} else{
//				JOptionPane.showMessageDialog(null, "Select a .java file to compile!");
//			}
//		} else if(src == checkComments) {
//			
//		}
//	}
//	private String removePath(String str) {
//		int start = str.lastIndexOf("/") + 1;
//		int end = str.length();
//		return str.substring(start, end);
//	}
//	
//	private void runAndReport(String[] params) {
//		runAndReport(params, false, false);
//	}
//	private void runAndReport(String[] params, boolean writeResults) {
//		runAndReport(params, writeResults, false);
//	}
//	private void runAndReport(String[] params, boolean writeResults, boolean append) {
//		System.out.println("cmd:" + Arrays.toString(params));
//		ProcessBuilder pb = new ProcessBuilder(params);
//		Map<String, String> env = pb.environment();
//		//env.put("PATH", "/Library/Java/JavaVirtualMachines/jdk1.7.0_21.jdk/Contents/Home/bin");
//		
//		pb.directory(path);
//		//pb.directory(new File("/Users/robnash/Documents/workspace/HWGUI"));
//		System.out.println("Working dir:"+ path.toString());
//		
//		try {
//			shellResultsArea.append("\n"+Arrays.toString(params));
//			
//			PrintWriter results = null;
//			if(writeResults) {
//				if(append) {
//					results = new PrintWriter(new BufferedWriter( new FileWriter(path.toString()+ "/results.txt", true)));
//				} else {
//					results = new PrintWriter(new File(path.toString()+ "/results.txt"));
//				}
//				results.println(Arrays.toString(params) + "\n");
//			}
//			
//			
//			Process p = pb.start();
//			try {
//				p.waitFor();
//			} catch (InterruptedException e1) {
//				//e1.printStackTrace();
//			}
//				
//			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
//			String line = "";
//			StringBuffer output = new StringBuffer();
//			while ((line = reader.readLine())!= null) {
//				output.append(line + "\n");
//			}
//			reader.close();
//			shellResultsArea.append("\n+++(stdOut)++++++++++\n"+ output +"\n\n");
//			if(writeResults) results.println("\n+++(stdOut)++++++++++\n"+ output +"\n\n");
//			
//			reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//			line = "";
//			output = new StringBuffer();
//			while ((line = reader.readLine())!= null) {
//				output.append(line + "\n");
//			}
//			reader.close();
//			shellResultsArea.append("\n---(stdErr)-----------\n"+ output +"\n\n");
//			if(writeResults) {
//				results.println("\n---(stdErr)-----------\n"+ output +"\n\n");
//				results.close();
//			}
//	
//			
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			System.out.println("Excepted while running " + Arrays.toString(params) + " on " + path);
//			System.exit(-1);
//		}
//	}
//
//	@Override
//	public void keyPressed(KeyEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		JTextArea src = (JTextArea) e.getSource();
//		
//		if(src == notesArea) {
//			writeTextToDisk(new File(path+"/notes.txt"), notesArea.getText());
//		} else if(src == codeArea) {
//			writeTextToDisk(targetFile, codeArea.getText());
//		}
//		
//		/*if(saveThreadActive == false) {
//			Thread a = new Thread(new Runnable() {
//				public void run() {
//					
//					
//					
//					saveThreadActive = false;
//				}
//			});
//			saveThreadActive = true; //race condition here between this and next line?
//			a.start();
//		}*/
//	}
//	
//	private void writeTextToDisk(File toWrite, String text) {
//		System.out.println("Writing text to :" +toWrite.toString());
//		try {
//			PrintWriter foo = new PrintWriter( toWrite );
//			foo.println(text);
//			foo.close();
//		}  catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.exit(-1);
//		}
//	}
//
//
//	@Override
//	public void keyTyped(KeyEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//
//
//
//	
//
//}
