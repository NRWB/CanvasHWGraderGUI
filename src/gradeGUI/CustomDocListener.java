package gradeGUI;

import java.io.*;

import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @class CustomDocListener
 * 
 * @description The purpose of this class is to save the opened files in editor.
 * 
 * @author Nick
 *
 */
public class CustomDocListener implements DocumentListener {
	
	/**
	 * Private data member to reference the file path to an opened file in editor.
	 */
	private String filePaf;
	
	/**
	 * @method Constructor
	 * 
	 * @description Default constructor to save the reference to the string passed.
	 * 
	 * @param filePafRef The external string passed.
	 * 
	 */
	public CustomDocListener(String filePafRef) {
		filePaf = filePafRef;
	}
	
	/**
	 * Not implemented in this class.
	 */
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// Plain text components do not fire these events
	}
	
	/**
	 * When an insert is taken place, save the document.
	 */
	@Override
	public void insertUpdate(DocumentEvent de) {
		System.out.println("insertUpdate, file path = " + filePaf);
		saveDoc(de);
	}
	
	/**
	 * When a deletion is taken place, save the document.
	 */
	@Override
	public void removeUpdate(DocumentEvent de) {
		System.out.println("removeUpdate, file path = " + filePaf);
		saveDoc(de);
	}
	
	/**
	 * Sets the string reference for the file path.
	 * Used due to possible null strings being passed to constructor.
	 * 
	 * @param str
	 */
	public void setFilePathOfDoc(String str) {
		filePaf = str;
	}
	
	private void saveDoc(DocumentEvent docEv) {
		HTMLEditorKit kit = new HTMLEditorKit();
		OutputStream bos = null;
		try {
			OutputStream fos = new FileOutputStream(new File (filePaf) );
			bos = new BufferedOutputStream(fos);
			kit.write(bos, docEv.getDocument(), 0, docEv.getDocument().getLength());
		} catch ( IOException | BadLocationException e) {
			e.printStackTrace();
		}
	}
}