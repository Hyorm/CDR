import java.awt.Color;

public class ColorFactory {
	
	private Color backgroundColor;
	
	public ColorFactory() {
		//backgroundColor = new Color(248,203,186); //pink
		//backgroundColor = new Color(199,214,191); //mint
		//backgroundColor = new Color(205,163,141); //dark beige
		//backgroundColor = new Color(231,212,198);
		backgroundColor = new Color(225,191,189);
		
		
		
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
}
