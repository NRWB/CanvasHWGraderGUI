package gradeGUI;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TreeDemoProject/src/components/TreeDemo.java
 * @author Nick
 *
 */

@SuppressWarnings("serial")
public class FileViewTree extends JPanel implements TreeSelectionListener {
    
	private JTree tree;
	private JEditorPane overviewComponent;
    private String openedSelectedFolder;
    private JScrollPane treeView;
    private String fileBeingViewed;
    private CustomDocListener docListenerRefs;
    private String currParent;
    
    private JTextArea textAreaRef;
    private CustomDocListener docListenerRefs2;
    
    private JLabel currViewFileTitleLabelRef;
    
    private static FlowLayout lay = new FlowLayout();
        
    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";

    public FileViewTree(String fileRef, CustomDocListener docRef) {
    	super(lay);
    }
    
    public void setFolder(String f) {
    	openedSelectedFolder = new String(f);
    }
    
    public void setCommentTextArea(JTextArea textArea) {
    	this.textAreaRef = textArea;
    }
    
    public void setViewedFileTitleLabel(JLabel jl) {
    	this.currViewFileTitleLabelRef = jl;
    }
    
    public void updateView() {
    	if ( openedSelectedFolder == null || openedSelectedFolder.compareTo("") == 0 ) {
    		System.out.println("FileViewTree - updateView(): please use appropiate setFolder method before calling this method.");
    		return;
    	}
    	
    	if ( this.tree != null )
    		deleteCurrentTree();
    	
    	DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(openedSelectedFolder);
    	createNodes(newRoot);
    	
    	this.removeAll();//JPanel remove old components
    	
        tree = new JTree(newRoot);

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        tree.addTreeSelectionListener(this);
        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }

        treeView = new JScrollPane(tree);
        treeView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        treeView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        this.add(treeView); // JPanel add the scroll pane
        
        setVisible(true);
    }
    
    private void deleteCurrentTree() {
    	tree.removeAll();
    }

    public void valueChanged(TreeSelectionEvent e) {
    	
    	createNodes(new DefaultMutableTreeNode(openedSelectedFolder));
    	
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        
        if (node == null) {
        	System.out.println("Node is null");
        	return;
        }
        
        String parent = node.isLeaf() ? node.getParent().toString() : node.toString();
        
        if (parent == null)
        	throw new NullPointerException("Null parent folder");
        
        if (node.isLeaf()) {
        	this.fileBeingViewed = new String(parent + System.getProperty("file.separator") + node.toString());
        	System.out.println("The file being viewed: " + this.fileBeingViewed);
        	
        	if (Files.exists(Paths.get(this.fileBeingViewed), LinkOption.NOFOLLOW_LINKS)) {
        		
        		String previewName = parsePathName(this.fileBeingViewed);
        		
        		this.currViewFileTitleLabelRef.setText("Source Code: (File name = " + previewName + ")");
        		
	            try {
	            	FileReader reader = new FileReader(fileBeingViewed);
	            	overviewComponent.read(reader, fileBeingViewed);
	            	docListenerRefs.setFilePathOfDoc(fileBeingViewed);
	            	overviewComponent.getDocument().addDocumentListener(docListenerRefs);
		        } catch (IOException ex) {
					ex.printStackTrace();
				}
        	}
        }
        
        currParent = parent;
        
        // Try to load a notes file for the student's homework folder, if one exists
        String sentinel = currParent + System.getProperty("file.separator") + "notes.txt";
        
    	Path target = Paths.get(sentinel);
    	
    	if (Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
    		// try to set the text of the comment area here:
    		try {
    			FileReader reader = new FileReader( sentinel );
    			textAreaRef.read(reader, sentinel);
    			docListenerRefs2.setFilePathOfDoc(sentinel);
    			textAreaRef.getDocument().addDocumentListener(docListenerRefs2);
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    	} else {
    		// reset the contents of the text area to nothing here:

    		// Don't set the text of the component to "" (nothing) because
    		// it will auto-save over the contents, thereby deleting all text in the file!
    	}
                
        treeView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        treeView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        treeView.setMinimumSize(new Dimension(0, 0));
        setVisible(true);
        setMinimumSize(new Dimension(0, 0));
        
        validate();
    }
    
    private String parsePathName(String src) {
    	final int len = src.length();
		int i = len - 1, count = 0;
		for ( ; i >= 0; i--) {
			char c = src.charAt(i);
			if (c == '/' || c == '\\')
				count++;
			if (count == 2)
				break;
		}
		return src.substring(i, len);
    }
    
    public void setDisplayArea(JEditorPane c) {
    	overviewComponent = c;
    	overviewComponent.setMinimumSize(new Dimension(0, 0));
    }
    
    public void setDocumentUpdateRef(CustomDocListener cdl) {
    	docListenerRefs = cdl;
    }
    
    
    public void setDocumentUpdateRef2(CustomDocListener cdl) {
    	docListenerRefs2 = cdl;
    }

    private void createNodes(DefaultMutableTreeNode top) {
    	
        List<Path> queried = null;
		try {
			Stream<Path> pafStream = Files.list(Paths.get(top.toString() + System.getProperty("file.separator")));
			queried = new LinkedList<Path>(pafStream.collect(Collectors.toList()));
			pafStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> files = new LinkedList<String>();
        for ( Path p : queried )
        	files.add( p.toString() );
        
		Set<String> dirLevels = new HashSet<String>();
		for ( String s : files )
			if ( Files.isDirectory(Paths.get(s)) )
				dirLevels.add(s);
		
		for ( String s : dirLevels ) {
			File dir = new File(s);
			
			if ( dir.list().length > 0 ) {
				DefaultMutableTreeNode subCategory = new DefaultMutableTreeNode(s);
				
				// recursive call here to continue digging down in folders...
				// would need to clean code leading to this code block, for this given method. (code between method signature and this code block)
				
				String[] children = dir.list();
				for (String str : children) {
					subCategory.add(new DefaultMutableTreeNode(str));
				}
				
				top.add(subCategory);
			} else {
				top.add(new DefaultMutableTreeNode(s));
			}
		}
    }
}
