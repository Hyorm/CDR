
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class Choose extends JFrame implements ActionListener {

	
	private JPanel p;
	
	private JCheckBox pri;
	private JCheckBox ag;
	private JCheckBox ss;
	private JCheckBox gen;
	
	private JTextField from;
	private JLabel l;
	private JTextField to;
	
	private JComboBox<String> ags;
	
	private ButtonGroup group1;
	private JRadioButton spring;
	private JRadioButton summer;
	private JRadioButton fall;
	private JRadioButton winter;
	
	private ButtonGroup group2;
	private JRadioButton man;
	private JRadioButton woman;
	
	private ButtonGroup group3;
	private JRadioButton caff;
	private JRadioButton decaff;
	
	private JPanel priceP;
	private JPanel ageP;
	private JPanel seasonP;
	private JPanel genderP;
	private JPanel caffeineP;
	
	private JButton btn1; 

	private SqlHolder sqlInf;
	
	String ages_str_arr[] = {"10s","20s","30s","40s","50s","60s"};
	String user_id;
	
	public Choose(SqlHolder sqlInf, String user_id) {
		
		this.user_id = user_id;
		this.sqlInf = sqlInf;
		
		//create component
		p = new JPanel();
		
		pri = new JCheckBox("price"); 
		from = new JTextField("1500");
		l = new JLabel(" ~ ");
		to = new JTextField("5000");		

		
		ag = new JCheckBox("age");
		ags = new JComboBox<String> (ages_str_arr);
				
		ss = new JCheckBox("season");
		spring = new JRadioButton("spring");
		summer = new JRadioButton("summer");
		fall = new JRadioButton("fall");
		winter = new JRadioButton("winter");	
		
		
		gen = new JCheckBox("gender");
		man = new JRadioButton("man");
		woman = new JRadioButton("woman");
		
		caff = new JRadioButton("caffeine");
		decaff = new JRadioButton("decaffeine");	
		
		priceP = new JPanel();
		ageP = new JPanel();
		seasonP = new JPanel();
		genderP = new JPanel();
		caffeineP = new JPanel();
		
		btn1 = new JButton("Search");
				
		group1 = new ButtonGroup();
		group2 = new ButtonGroup();
		group3 = new ButtonGroup();
			
		//add actionListener
		pri.addActionListener(this);
		from.addActionListener(this);
		to.addActionListener(this);
		
		ag.addActionListener(this);
		ags.addActionListener(this);
		
		ss.addActionListener(this);
		spring.addActionListener(this);
		summer.addActionListener(this);
		fall.addActionListener(this);
		winter.addActionListener(this);
		
		gen.addActionListener(this);
		man.addActionListener(this);
		woman.addActionListener(this);
		
		caff.addActionListener(this);
		decaff.addActionListener(this);
		
		btn1.addActionListener(this);
		
		//enable 관리 
		from.setEnabled(false);
		to.setEnabled(false);
		ags.setEnabled(false);
		spring.setEnabled(false);
		summer.setEnabled(false);
		fall.setEnabled(false);
		winter.setEnabled(false);
		man.setEnabled(false);
		woman.setEnabled(false);
		
		
		//grouping 
		priceP.add(pri);
		priceP.add(from);
		priceP.add(l);
		priceP.add(to);
		
		ageP.add(ag);
		ageP.add(ags);
		
		//setHorizontalAlignment(SwingConstants.CENTER);
		
		group1.add(spring);
		group1.add(summer);
		group1.add(fall);
		group1.add(winter);		
		seasonP.add(ss);
		seasonP.add(spring);
		seasonP.add(summer);
		seasonP.add(fall);
		seasonP.add(winter);
		
		
		
		group2.add(man);
		group2.add(woman);
		genderP.add(gen);
		genderP.add(man);
		genderP.add(woman);
		
		group3.add(caff);
		group3.add(decaff);
		caffeineP.add(caff);
		caffeineP.add(decaff);
		
		// add to panel
		p.setLayout(new GridLayout(6,1));
		p.add(priceP);
		p.add(ageP);
		p.add(seasonP);
		p.add(genderP);
		p.add(caffeineP);
		p.add(btn1);
		
		this.add(p);
		this.setSize(400, 300);
		this.setVisible(true);
		
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//enable or disable button 
		if(e.getSource() == pri) {
			
			if(pri.isSelected()) {
				from.setEnabled(true);
				to.setEnabled(true);
			}else {
				from.setEnabled(false);
				to.setEnabled(false);
			}
			
		}else if(e.getSource() == ag) {
			if(ag.isSelected()) {
				ags.setEnabled(true);
			}else {
				ags.setEnabled(false);
			}
			
		}else if(e.getSource() == ss) {
			
			if(ss.isSelected()) {
				spring.setEnabled(true);
				summer.setEnabled(true);
				fall.setEnabled(true);
				winter.setEnabled(true);
			}else {
				spring.setEnabled(false);
				summer.setEnabled(false);
				fall.setEnabled(false);
				winter.setEnabled(false);
			}
			
		}else if(e.getSource() == gen) {
			
			if(gen.isSelected()) {
				man.setEnabled(true);
				woman.setEnabled(true);
			}else {
				man.setEnabled(false);
				woman.setEnabled(false);
			}
		
		}else if(e.getSource() == btn1) { //chose option value store and determine query... how to seperate this code from this method....:(
						
			String query = new String();
			int pri_is = 0;
			int ss_is = 0; //season is selected
			int gen_is = 0;
			int ag_is = 0;
			int caf_is = 0;
			
			//**************************query format step1 SELECT********************************// 			
			query += "SELECT * FROM drink ";
		
						
			//**************************query format step2 JOIN**********************************// 
			if(ss.isSelected()) {
				
				ss_is = 1;
				//JOIN
				query += "JOIN recipe ON recipe.drink_id =  drink.drink_id "
						+ "JOIN  ingredientslist ON recipe.ingredient_name = ingredientslist.ingredient_name ";				

			}			
			
			if(gen.isSelected()) {				
				gen_is = 1;		
				//JOIN
				query += "JOIN orderedList ON  orderedList.drink_id = drink.drink_id " + 
					"JOIN  userList ON orderedList.user_id = userList.user_id ";
			}	
			
			if(ag.isSelected()) {				
				ag_is = 1;
				//JOIN
				if(gen_is <1) {
				query += "INNER JOIN  orderedList ON orderedList.drink_id = drink.drink_id " + 
						"INNER JOIN  userList ON orderedList.user_id = userList.user_id ";
				}
			}
			
			if(caff.isSelected() || decaff.isSelected()) {
				
				caf_is = 1;	
				if(ss_is<1) {
					query += "JOIN recipe ON recipe.drink_id =  drink.drink_id "
							+"JOIN  ingredientslist ON recipe.ingredient_name = ingredientslist.ingredient_name ";				
				}	
			}
			
			
			//**********************query format step 3 WHERE************************// 
			query += "WHERE ";
			
			//-------------------------------------saeson----------------------------// 
			if(ss_is > 0) {
				
				query += "season = ";
				if(spring.isSelected()) {
					query += "'Spring'";
				}else if(summer.isSelected()) {
					query += "'Summer'";
				}else if(fall.isSelected()) {
					query += "'Fall'";
				}else if(winter.isSelected()) {
					query += "'Winter'";
				}
				
			}
			
			//---------------------------------------price--------------------------//
			if(pri.isSelected()) {
				pri_is = 1;
				//AND condition 
				if(ss_is > 0) {
					query += " AND ";
				}
				query += " drink.price >=" + from.getText() + " AND drink.price <= " + to.getText();
			}
			
			//---------------------------------------gender-------------------------//
			if(gen_is > 0) {
				
				// AND QUERY;
				if(ss_is > 0 || pri_is >0) {
					query += " AND ";
				}
				
				if(man.isSelected()) {
					query += "userList.gender = 'M'";
				}else if(woman.isSelected()){
					query += "userList.gender = 'F'";
				}
			}
			
			//---------------------------------------age--------------------------------//
			if(ag_is > 0) {
				
				if(ss_is > 0 || pri_is >0 || gen_is > 0) {
					query += " AND ";
				}
				
				
				if(ags.getSelectedItem().equals(ages_str_arr[0])){ //10s
					query += "userList.age >= 10 AND userList.age < 20" ;
				}else if(ags.getSelectedItem().equals(ages_str_arr[1])){ //20s
					query += "userList.age >= 20 AND userList.age < 30" ;
				}else if(ags.getSelectedItem().equals(ages_str_arr[2])) { //30s
					query += "userList.age >= 30 AND userList.age < 40" ;
				}else if(ags.getSelectedItem().equals(ages_str_arr[3])) { //40s
					query += "userList.age >= 40 AND userList.age < 50" ;
				}else if(ags.getSelectedItem().equals(ages_str_arr[4])) { //50s
					query += "userList.age >= 50 AND userList.age < 60" ;
				}else if(ags.getSelectedItem().equals(ages_str_arr[5])) { //60s
					query += "userList.age >= 60 AND userList.age < 70" ;
				}else {
					System.out.println("age is not selected");
				}
			}
			
			//-------------------------caffeine/decaffeine-----------------------------//
			if(caf_is > 0) {
				
				if(ss_is > 0 || pri_is > 0 || gen_is > 0 || ag_is > 0) {
					query += " AND ";
				}
				
				query += "recipe.ingredient_name = 'Espresso'";
			}
			
			
			if( ag_is > 0|| gen_is > 0) {
				query += " GROUP BY orderedList.drink_id " + 
						"ORDER BY count DESC";
			}
				
			new RecommandedDrinksPage(user_id,sqlInf, query);
			this.dispose();
			
		}
		
		
		
		
		
		
		

	}
	
	
	public String determineQuery() {
		String query = new String(); 
		
		return query;
	}
	

}
