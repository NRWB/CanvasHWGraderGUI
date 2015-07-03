package gradeGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

// https://code.google.com/p/jsyntaxpane/wiki/UsingNew
import jsyntaxpane.DefaultSyntaxKit;

public class AppGUI extends JFrame {

	private CustomDocListener currViewedDoc = null;
	private String currentTargetedFolder = null;
	private String currFilePathInEditor = null;
	private static final long serialVersionUID = 7689207307895492510L;
	private final JSplitPane splitPane = new JSplitPane();
	
	private final JPanel panel = new JPanel();

	
	private final JPanel panel_1 = new JPanel();
	private final JButton btnNewButton = new JButton("Run All");
	
	private FileViewTree list = new FileViewTree(currFilePathInEditor, currViewedDoc);
	
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private final JSplitPane splitPane_1 = new JSplitPane();
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	private final JLabel lblSourceCodefile = new JLabel("Source Code: (File name = x)");
	private final JLabel lblNewLabel = new JLabel("Student HW Comments:");
	private final JScrollPane scrollPane = new JScrollPane();
	
	private final JTextArea textArea = new JTextArea();
	private CustomDocListener cuuViewedDoc2 = null;
	private String currCommentsOpened;
	
	private String currentTargetedScript;
	
	private final JPanel srcCodePanel = new JPanel();
	private final JLabel lblSelectedScript = new JLabel("Selected Script:");
	private final JTextField textField = new JTextField();
	private final JButton btnOpenScriptFile = new JButton("Open Script File");
	private final JButton btnCompile = new JButton("Compile");
	private final JButton btnCompileAll = new JButton("Compile All");
	private final JButton btnOpen = new JButton("Open");
	private final JFileChooser fileSelectorPopUp = new JFileChooser();
	private final JButton btnRenamer = new JButton("Rename");
	private final JMenuItem mntmExit = new JMenuItem("Exit");
	private final JCheckBox chckbxViewScriptOutput = new JCheckBox("View Script Output");
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnNewMenu = new JMenu("File");
	private final JMenuItem mntmNewMenuItem = new JMenuItem("Open");
	private static Logger logRef;

	public AppGUI(Logger l) {
		logRef = l;
		init();
	}

	private void init() {
		setTitle("GraderGUI");
		setLayout(new BorderLayout());
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chckbxViewScriptOutput.setToolTipText("Enable to view the script output during runtime.");
		btnCompileAll.setEnabled(false);
		this.btnCompile.setEnabled(false);
		this.btnRenamer.setEnabled(false);
		this.textField.setColumns(10);
		this.splitPane_1.setResizeWeight(0.5);
		this.splitPane_1.setPreferredSize(new Dimension(10, 10));
		this.splitPane_1.setMinimumSize(new Dimension(10, 10));
		this.splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.splitPane.setBorder(null);
		this.splitPane.setDividerSize(2);
		this.splitPane.setPreferredSize(new Dimension(10, 10));
		this.splitPane.setMinimumSize(new Dimension(10, 10));
		this.splitPane.setResizeWeight(0.25);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		
		btnCompile.setToolTipText("Compiles this individual java file");
		btnCompileAll.setToolTipText("Compiles all java files in this current folder");
		
		list.setViewedFileTitleLabel(lblSourceCodefile);
		
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(this.splitPane, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(this.splitPane, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE));
				
		JScrollPane extraPane = new JScrollPane(list);
		extraPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		extraPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.panel.add(extraPane);

		
		this.splitPane.setLeftComponent(this.panel);
		GroupLayout gl_panel = new GroupLayout(this.panel);
		
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(this.list,GroupLayout.DEFAULT_SIZE,10, Short.MAX_VALUE).addGroup(gl_panel.createSequentialGroup().addComponent(this.btnNewButton).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.btnOpen).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.btnRenamer))).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(this.list,GroupLayout.DEFAULT_SIZE, 480,Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(this.btnNewButton).addComponent(this.btnOpen).addComponent(this.btnRenamer)).addContainerGap()));
		
		this.btnNewButton.setEnabled(false);
		this.panel.setLayout(gl_panel);
		
		this.splitPane.setRightComponent(this.panel_1);
		GroupLayout gl_panel_1 = new GroupLayout(this.panel_1);
		
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(this.tabbedPane,GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE).addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(this.tabbedPane,GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE).addContainerGap()));
		this.tabbedPane.addTab("Current Student", null, this.panel_2, null);
		GroupLayout gl_panel_2 = new GroupLayout(this.panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addComponent(this.splitPane_1,Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 10,Short.MAX_VALUE));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addComponent(this.splitPane_1,Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 483,Short.MAX_VALUE));
		this.splitPane_1.setLeftComponent(this.panel_4);
		GroupLayout gl_panel_4 = new GroupLayout(this.panel_4);
		gl_panel_4.setHorizontalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_4.createSequentialGroup().addContainerGap().addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING).addComponent(this.srcCodePanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE).addGroup(gl_panel_4.createSequentialGroup().addComponent(this.lblSourceCodefile).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.btnCompile).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.btnCompileAll))).addContainerGap()));
		gl_panel_4.setVerticalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_4.createSequentialGroup().addContainerGap().addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE).addComponent(this.lblSourceCodefile).addComponent(this.btnCompile).addComponent(this.btnCompileAll)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.srcCodePanel,GroupLayout.DEFAULT_SIZE, 193,Short.MAX_VALUE).addContainerGap()));
		this.lblSourceCodefile.setToolTipText("Actively saves every 100 milliseconds.");
		this.panel_4.setLayout(gl_panel_4);

		

		this.srcCodePanel.setLayout(new BorderLayout());
		DefaultSyntaxKit.initKit();
		final JEditorPane codeEditor = new JEditorPane();
		JScrollPane scrPane = new JScrollPane(codeEditor);
		this.srcCodePanel.add(scrPane, BorderLayout.CENTER);
		this.srcCodePanel.doLayout();
		codeEditor.setContentType("text/java");
		codeEditor.setText("public static void main(String[] args) {\n}");
		
		

		
		// maintain only one document listener, switching it's associations as files come in/out of view panel of single file
		currViewedDoc = new CustomDocListener(currFilePathInEditor);

		list.setDisplayArea(codeEditor);
		
		list.setDocumentUpdateRef(currViewedDoc);
		
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileSelectorPopUp.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (currentTargetedFolder != null) {
					File f = new File(currentTargetedFolder);
					if (f.isDirectory())
						fileSelectorPopUp.setCurrentDirectory(f);
					else
						fileSelectorPopUp.setCurrentDirectory(new File(f.getParent()));
				} else
					fileSelectorPopUp.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileSelectorPopUp.setMultiSelectionEnabled(false);
				int returnVal = fileSelectorPopUp.showOpenDialog(null);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					System.out.println("Error @ JFileChooser; option not approved.");
					return;
				}
				currentTargetedFolder = new String(fileSelectorPopUp.getSelectedFile().getAbsolutePath());
				// Update:
				list.setFolder(currentTargetedFolder);
				list.updateView();
				repaint();
				revalidate();
				// list.setDisplayArea(codeEditor);
			}
		});
		
		btnOpenScriptFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (currentTargetedFolder != null) {
					File f = new File(currentTargetedFolder);
					if (f.isDirectory())
						jfc.setCurrentDirectory(f);
					else
						jfc.setCurrentDirectory(new File(f.getParent()));
				} else
					jfc.setCurrentDirectory(new File(System.getProperty("user.home")));
				jfc.setMultiSelectionEnabled(false);
				int returnVal = jfc.showOpenDialog(null);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					System.out.println("Error @ JFileChooser [script selection]; option not approved.");
					return;
				}
				currentTargetedScript = new String(jfc.getSelectedFile().getAbsolutePath());
				textField.setText(currentTargetedScript);
				btnNewButton.setEnabled(true);
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ProcessBuilder pb = new ProcessBuilder(currentTargetedScript);
					
					btnNewButton.setEnabled(false);
					Process p = pb.start();
					
					int rc = p.waitFor();
					btnNewButton.setEnabled(true);
					
					System.out.println("Script executed with return code: " + rc);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				btnNewButton.setEnabled(true);
			}
		});
		
		this.splitPane_1.setRightComponent(this.panel_5);
		GroupLayout gl_panel_5 = new GroupLayout(this.panel_5);
		gl_panel_5.setHorizontalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_5.createSequentialGroup().addContainerGap().addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addComponent(this.scrollPane, GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE).addComponent(this.lblNewLabel)).addContainerGap()));
		this.lblNewLabel.setToolTipText("Actively saves every 100 milliseconds.");
		gl_panel_5.setVerticalGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup().addContainerGap().addComponent(this.lblNewLabel).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.scrollPane, GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE).addContainerGap()));
		this.scrollPane.setViewportView(this.textArea);
		list.setCommentTextArea(this.textArea);
		cuuViewedDoc2 = new CustomDocListener(currCommentsOpened);
		list.setDocumentUpdateRef2(cuuViewedDoc2);
		this.panel_5.setLayout(gl_panel_5);
		this.panel_2.setLayout(gl_panel_2);
		this.tabbedPane.addTab("Script", null, this.panel_3, null);
		GroupLayout gl_panel_3 = new GroupLayout(this.panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_3.createSequentialGroup().addContainerGap().addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addComponent(this.textField, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE).addComponent(this.lblSelectedScript).addComponent(this.btnOpenScriptFile).addComponent(this.chckbxViewScriptOutput)).addContainerGap()));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_3.createSequentialGroup().addContainerGap().addComponent(this.lblSelectedScript).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,	GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.btnOpenScriptFile).addGap(18).addComponent(this.chckbxViewScriptOutput).addContainerGap(360, Short.MAX_VALUE)));
		this.panel_3.setLayout(gl_panel_3);
		this.panel_1.setLayout(gl_panel_1);
		getContentPane().setLayout(groupLayout);
		setJMenuBar(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(mntmNewMenuItem);
		mnNewMenu.add(mntmExit);
	}
}
