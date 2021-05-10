import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CafeDrinkDetailPage extends JFrame implements ActionListener {
	
	private JPanel panel;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	
	private String user_id;
	private SqlHolder sqlInf;
	
	//constructor
	public CafeDrinkDetailPage(SqlHolder sqlInf, String user_id) {
		
		this.sqlInf = sqlInf;
		this.user_id = user_id;

		//create object
		btn1 = new JButton("EDIYA");
		btn2 = new JButton("Hisbeans");
		btn3 = new JButton("Mom's cafe");
		btn4 = new JButton("Apple In the Tree");
		panel = new JPanel();
		
		//action Listener
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		
		//add 
		panel.setLayout(new GridLayout(2,2));
		panel.add(btn1);
		panel.add(btn2);
		panel.add(btn3);	
		panel.add(btn4);	
		
		this.add(panel);	
		this.setSize(400, 600);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btn1) {
			new CafeDrinksPage(sqlInf,user_id,"EDIYA coffee", 2);
			this.dispose();
		}
		else if(e.getSource() == btn2) {
			new CafeDrinksPage(sqlInf,user_id,"Hisbeans coffee", 1);
			this.dispose();
		}
		else if(e.getSource() == btn3) {
			new CafeDrinksPage(sqlInf,user_id,"Moms coffee",3);
			this.dispose();
		}
		else if(e.getSource() == btn4) {
			new CafeDrinksPage(sqlInf,user_id,"AppleintheTree coffee", 4);
			this.dispose();
		}
		
	}
}		

	
	


