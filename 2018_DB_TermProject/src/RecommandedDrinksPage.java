import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class RecommandedDrinksPage extends JFrame implements ActionListener {

	private final int width = 400;
	private final int height = 600;	
	private JList<String> drinklist; // drink object list 필요
	private DefaultListModel<String> listModel;
	private JScrollPane spane; 
	private JButton btn1; 
	private JButton btn2; 
	private JPanel panel1;
	private JPanel panel2;
	
	private String user_id;
	
	private ArrayList<String> drinkname_list = new ArrayList<String>();
	private ArrayList<Integer> drinkid_list = new ArrayList<Integer>();
	
	private SqlHolder sqlInf;

	//constructor
	public RecommandedDrinksPage(String user_id, SqlHolder sqlInf,String query){
		
		this.user_id = user_id;
		this.sqlInf = sqlInf;
		//query statement decision algorithm..
		cafeDrinkQuery(query);
		
		drinklist = new JList<>(listModel);	
		drinklist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		spane = new JScrollPane(drinklist);
		panel1 = new JPanel();
		panel2 = new JPanel();
		btn1 = new JButton("Order");
		btn2 = new JButton("Pick");
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		panel1.setLayout(new GridLayout(2,1));
		panel2.setLayout(new GridLayout(1,2));
		
		spane.setSize(400,500);
		
		panel2.add(btn1);
		panel2.add(btn2);
		
		panel1.add(spane);
		panel1.add(panel2);
		
		
		//this.add(drinklist);
		//this.add(new JScrollPane(drinklist));
		this.add(panel1);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width,height);
		this.setVisible(true);
		
	}
	
	//ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn1) {
			
			try {				
				String choosed_drinkname = drinkname_list.get((drinklist.getSelectedIndex()));
				System.out.println(choosed_drinkname);
				new OrderPage(user_id, sqlInf , choosed_drinkname,0); // OrderList(SqlHolder _sqlInf, String user_name)
			}catch(IndexOutOfBoundsException ex) {
				   
				ex.printStackTrace();	
				JOptionPane.showMessageDialog(null, "Choose drink", "Wrong", JOptionPane.WARNING_MESSAGE);
              	
			}}
		
		if(e.getSource() == btn2) {
			
			try {				
				int choosed_drinkid = drinkid_list.get((drinklist.getSelectedIndex()));
				System.out.println(choosed_drinkid);
				insertPickedListQuery(user_id, String.valueOf(choosed_drinkid));
			}catch(IndexOutOfBoundsException ex) {
				   
				ex.printStackTrace();	
				JOptionPane.showMessageDialog(null, "Choose drink", "Wrong", JOptionPane.WARNING_MESSAGE);
			}    	
			
		}
	}
	
	
	public void cafeDrinkQuery(String query) {
			
			
			System.out.println(query);
			listModel = new DefaultListModel<>();
			
			
			Connection conn = null;
			Statement stmt = null;
			
	
			try{ 
			      Class.forName(SSqlHolder.getInstance().getDriver());
			      conn = DriverManager.getConnection(SSqlHolder.getInstance().getUrl(),  SSqlHolder.getInstance().getName(), SSqlHolder.getInstance().getPWD());
			      stmt = conn.createStatement();
	
	
			      ResultSet rs = stmt.executeQuery(query);
	
				   while(rs.next()) { // or if 
				      //rs가 쿼리문 만족하는 tuple 가져옴
				      //filed 별로 getString(attribute명) 정보 가져옴
				      String drinkId = rs.getString("drink_name") + "		" +rs.getString("price") + "		" +rs.getString("temparature") ;      
				      drinkname_list.add(rs.getString("drink_name"));
				      drinkid_list.add(Integer.parseInt(rs.getString("drink_id")));
				      listModel.addElement(drinkId);
				   }
	
				   rs.close();
				   stmt.close();
				   conn.close();
				   
			}catch(SQLException sel) {
			   
				sel.printStackTrace();
			
			}catch(Exception ex) {
			   
				ex.printStackTrace();
			
			}finally {
			
				try {
			      
					if(stmt != null)
			         stmt.close();
			    
				}catch(SQLException se2) {
			   
				}
			   
				try {
			     
					if(conn != null)
			         conn.close();
			   
				}catch(SQLException se) {
			      
					se.printStackTrace();
			  
				}
			}		
		} 
	
	
	public void insertPickedListQuery(String user_id, String drink_id) {
		
		//INSERT INTO pickedList (user_id, drink_id);
		
		Connection conn = null;
		Statement stmt = null;
		

		try{ 
		      Class.forName(SSqlHolder.getInstance().getDriver());
		      conn = DriverManager.getConnection(SSqlHolder.getInstance().getUrl(),  SSqlHolder.getInstance().getName(), SSqlHolder.getInstance().getPWD());
		      stmt = conn.createStatement();


		   
		     int result = stmt.executeUpdate("INSERT INTO pickedList VALUES (" +user_id + ", " + drink_id + ")");

		     
		     if (result != 0) {
					JOptionPane.showMessageDialog(null, "Succeed to Insert!");
				}
			  
			   stmt.close();
			   conn.close();
			   
		}catch(SQLException sel) {
		   
			sel.printStackTrace();
		
		}catch(Exception ex) {
		   
			ex.printStackTrace();
		
		}finally {
		
			try {
		      
				if(stmt != null)
		         stmt.close();
		    
			}catch(SQLException se2) {
		   
			}
		   
			try {
		     
				if(conn != null)
		         conn.close();
		   
			}catch(SQLException se) {
		      
				se.printStackTrace();
		  
			}
		}
		
	}
		
}

	
	

