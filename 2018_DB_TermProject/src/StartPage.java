import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

// main page class 
public class StartPage extends JFrame implements ActionListener {
	
	private JPanel panel;
	private JButton btn_cafe;
	private JButton btn_new;
	private JButton btn_choose;
	private String user_id;
	private SqlHolder sqlInf;
	
	//constructor
	public StartPage(SqlHolder sqlInf, String user_id) {
		
		this.user_id = user_id;
		this.sqlInf = sqlInf;
		
		//create object
		btn_cafe = new JButton("Cafe");
		btn_new = new JButton("New");
		btn_choose = new JButton("Drink Recommand");
		panel = new JPanel();
		
		
		//action Listener
		btn_cafe.addActionListener(this);
		btn_new.addActionListener(this);
		btn_choose.addActionListener(this);
		
		//add 
		panel.setLayout(new GridLayout(3,1));
		panel.add(btn_cafe);
		panel.add(btn_new);
		panel.add(btn_choose);	
		this.add(panel);
		
		this.setSize(400, 600);
		this.setVisible(true);
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btn_choose) {
			new Choose(sqlInf,user_id);
		}
		else if(e.getSource() == btn_cafe) {
			new CafeDrinkDetailPage(sqlInf,user_id);
		}
		else if(e.getSource() == btn_new) {
			new NewDrinksPage(sqlInf,user_id);
		}
		
		
	}
	

}
