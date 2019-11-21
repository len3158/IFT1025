import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

class Surligneur extends JFrame
{
	JTextArea textComp;

	Surligneur()
	{
	textComp = new JTextArea("It is known that public health problems are important.", 50, 20);
	textComp.setFont(new Font("Arial Black", Font.PLAIN, 20));
	add(textComp);
	setVisible(true);
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}	

	// Creates highlights around all occurrences of pattern in textComp
	public void highlight(JTextComponent textComp, String pattern) {
		// First remove all old highlights
		//removeHighlights(textComp);
		String[] aTester = {"known", "health", "important"};
		Boolean isNotEqual = true;
		try {
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;
			for(int i=0; i<aTester.length;i++){
				if(pattern.equalsIgnoreCase(aTester[i])){
					isNotEqual = false;
					System.out.println(aTester[i]+" "+pattern);
					break;
					}
				}
					while (((pos = text.indexOf(pattern, pos)) >= 0) && isNotEqual) {
						// Create highlighter using private painter and apply around pattern
						hilite.addHighlight(pos, pos+pattern.length(), myHighlightPainter);
						pos += pattern.length();
					}
					
					//hilite.addHighlight(pos, pos+pattern.length(), myHighlightPainter);
					//pos += pattern.length();
						// Search for pattern
			/*while ((pos = text.indexOf(pattern, pos)) >= 0) {
				// Create highlighter using private painter and apply around pattern
				hilite.addHighlight(pos, pos+pattern.length(), myHighlightPainter);
				pos += pattern.length();
			}*/
		} catch (BadLocationException e) {
			
		}
	}
	
	/*// Removes only our private highlights
	public void removeHighlights(JTextComponent textComp) {
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();
	
		for (int i=0; i<hilites.length; i++) {
			if (hilites[i].getPainter() instanceof MyHighlightPainter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}*/
	
	// An instance of the private subclass of the default highlight painter
	Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.yellow);
	
	// A private subclass of the default highlight painter
	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public MyHighlightPainter(Color color) {
			super(color);
		}
	}
	
	public static void main(String[] args)
	{
	Surligneur h = new Surligneur();


	// Highlight the occurrences of the word "public"
	h.highlight(h.textComp, "It");
	h.highlight(h.textComp, "is");
	h.highlight(h.textComp, "known");
	h.highlight(h.textComp, "that");
	h.highlight(h.textComp, "public");
	h.highlight(h.textComp, "health");
	h.highlight(h.textComp, "important");

	}
}
