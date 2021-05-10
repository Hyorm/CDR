import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

/**
 * 9번 -> orderedDrinkList에서 choose_id 가져와 sorting해서 보여주기.
 * 
 * @author hanbokyoung
 *
 */
public class OptionRecommend extends JFrame implements ActionListener {
	private JLabel jlOptions[];
	private JRadioButton jrOptions[];
	private ButtonGroup bgRadioGroup;
	private JPanel jpLabel;
	private JPanel jpRadio;
	private JLabel jlDrinkName;
	private JButton jbSelect;
	private ColorFactory colorFactory = new ColorFactory();
	private ArrayList<String> choose_idList = new ArrayList<String>(); // orderedList로부터 choose_id를 가져와 저장해 두기 위한 배열.

	/* DB */
	private Connection conn = null;
	private Statement stmt = null;
	private String sql = "";
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	//private String DRINK_ID = "3"; // TODO 효림이 것으로 바꾸기.
	private String DRINK_ID = OrderPage.orderDrink_id;
	private SqlHolder sqlInf;

	public OptionRecommend(SqlHolder sqlInf_) {

		sqlInf = sqlInf_;

		initComponents();

		try {
			// DB connection
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			if (!OptionSelection.jcConsiderDisease.isSelected()) {
				// 개수 제한하기 -> 5개로.
				sql = "select choose_id from (select * from orderedList order by count desc)t where drink_id="
						+ DRINK_ID + " limit 5";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					if(!choose_idList.contains(rs.getString("choose_id")))
						choose_idList.add(rs.getString("choose_id"));
				}

			} else {
				// crate view descOrderedListView -> Sorting OrderedList by descending order
				sql = "create or replace view descOrderedListView as select * from orderedList order by count desc;";
				stmt.executeUpdate(sql);

				// create view chooseIdVIew -> filtering view by drink_id and make new one
				sql = "create or replace view chooseIdView as select choose_id from descOrderedListView where drink_id ="
						+ DRINK_ID;
				stmt.executeUpdate(sql);

				ArrayList<String> chooseIDList = new ArrayList<String>();
				sql = "select choose_id from chooseIdView";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					if(!chooseIDList.contains(rs.getString("choose_id")))
						chooseIDList.add(rs.getString("choose_id"));
				}
				for (int i = 0; i < chooseIDList.size(); i++) {
					System.out.println("chooseIDList " + chooseIDList.get(i));
				}

				/* ------------------------------ */
				ArrayList<String> diseaseIDList = new ArrayList<String>();
				ArrayList<String> avoidOptionList = new ArrayList<String>();

				//String userID = "1"; // TODO 실제 로그인한 userID로 바꾸기.
				String userID = OrderPage.user_id;
				sql = "create or replace view myDisease as select disease_id from sufferList where user_id=" + userID; // create
																														// view
				stmt.executeUpdate(sql);

				sql = "select disease_id from myDisease";
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					if(!diseaseIDList.contains(rs.getString("disease_id")))
						diseaseIDList.add(rs.getString("disease_id"));
				}
				for (int i = 0; i < diseaseIDList.size(); i++) {
					System.out.println("diseaseIDList " + diseaseIDList.get(i));
				}

				for (int i = 0; i < diseaseIDList.size(); i++) {
					sql = "select option_name from optionCauseList where disease_id=" + diseaseIDList.get(i);
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						if(!avoidOptionList.contains(rs.getString("option_name")))
							avoidOptionList.add(rs.getString("option_name"));
					}
				}
				
				for(int i=0; i<avoidOptionList.size(); i++) {
					System.out.println("avoidOptionList "+avoidOptionList.get(i));
				}
				/* ------------------------------ */

				sql = "";
				for (int i = 0; i < chooseIDList.size(); i++) {
					if (i == 0) {
						sql += "create or replace view tempChooseListView as select choose_id,option1,option2,option3 "
								+ "from chooseList where choose_id=" + chooseIDList.get(i);
					} else {
						sql += " or choose_id=" + chooseIDList.get(i);
					}
				}
				if(!sql.equals("")) {
					stmt.executeUpdate(sql);
					System.out.println(sql);	
				
				

				sql = "";
				//avoidOptionList.add("javaChip");
				for (int i = 0; i < avoidOptionList.size(); i++) {
					sql = "select choose_id from tempChooseListView where ";
					for(int j=0; j<avoidOptionList.size(); j++)
						sql += "option1<>'" + avoidOptionList.get(j)+"' and ";
					for(int j=0; j<avoidOptionList.size(); j++)
						sql += "option2<>'" + avoidOptionList.get(j)+"' and ";
					for(int j=0; j<avoidOptionList.size(); j++)
						sql += "option3<>'" + avoidOptionList.get(j)+"' and ";
				}
				}
				if(!sql.equals("")) {
					String finalSQL = sql.substring(0, sql.length()-4);	
					System.out.println(finalSQL);
					rs = stmt.executeQuery(finalSQL);
					while (rs.next()) {
						if (!choose_idList.contains(rs.getString("choose_id")))
							choose_idList.add(rs.getString("choose_id"));
					}
				}
				

				
			}
		
				//System.out.println("ming");
				for (int i = 0; i < choose_idList.size(); i++) {
					System.out.println(choose_idList.get(i));
				}
				//System.out.println("ming2");

			jlOptions = new JLabel[choose_idList.size()];
			for (int i = 0; i < choose_idList.size(); i++) {
				jlOptions[i] = new JLabel();
				jlOptions[i].setHorizontalAlignment(SwingConstants.CENTER);
				jlOptions[i].setOpaque(true);
				jlOptions[i].setBackground(colorFactory.getBackgroundColor());
			}

			jrOptions = new JRadioButton[choose_idList.size()];
			for (int i = 0; i < choose_idList.size(); i++) {
				jrOptions[i] = new JRadioButton();
				jrOptions[i].setHorizontalAlignment(SwingConstants.CENTER);
			}
			bgRadioGroup = new ButtonGroup();
			for (int i = 0; i < choose_idList.size(); i++) {
				bgRadioGroup.add(jrOptions[i]);
			}

			// add text to labels
			rs = null;
			String myChoice;
			for (int i = 0; i < choose_idList.size(); i++) {
				String q = "select option1, option2, option3 from chooseList where choose_id = ?";
				pstmt = conn.prepareStatement(q);
				pstmt.setString(1, choose_idList.get(i));

				rs = pstmt.executeQuery();
				myChoice = "";
				while (rs.next()) {
					if (!rs.getString("option1").equals("null"))
						myChoice += rs.getString("option1");
					if (!rs.getString("option2").equals("null"))
						myChoice += (", " + rs.getString("option2"));
					if (!rs.getString("option3").equals("null"))
						myChoice += (", " + rs.getString("option3"));
				}
				if (myChoice.equals("")) {
					jlOptions[i].setText("No option");
				} else {
					jlOptions[i].setText(myChoice);
				}

			}

			// get drink name which is selected by user
			sql = "select drink_name from drink where drink_id =" + DRINK_ID;
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				jlDrinkName.setText(rs.getString("drink_name"));
			}
			// initialize JLabel

			// setChooseID();

			// initialize JPaenl and add components
			jpLabel = new JPanel(new GridLayout(5, 1, 10, 10));
			for (int i = 0; i < choose_idList.size(); i++) {
				jpLabel.add(jlOptions[i]);
			}
			jpRadio = new JPanel(new GridLayout(5, 1));
			for (int i = 0; i < choose_idList.size(); i++) {
				jpRadio.add(jrOptions[i]);
			}

			//modify error
			if(choose_idList.size()==0) {
				jlOptions = new JLabel[1];
				jlOptions[0] = new JLabel();
				jlOptions[0].setHorizontalAlignment(SwingConstants.CENTER);
				jlOptions[0].setOpaque(true);
				jlOptions[0].setText("No option recommended");
				jlOptions[0].setFont(new Font("arial",Font.BOLD,15));
				jlOptions[0].setBackground(colorFactory.getBackgroundColor());
				jpLabel = new JPanel(new GridLayout(1, 1, 10, 10));
				jpLabel.add(jlOptions[0]);
			}
			
			jbSelect = new JButton("select");
			jbSelect.addActionListener(this);

			this.setLayout(new BorderLayout());

			JPanel jpCenter = new JPanel();
			jpCenter.setLayout(new GridLayout(1, 3, 20, 20));

			jpLabel.setBackground(colorFactory.getBackgroundColor());
			jpRadio.setBackground(colorFactory.getBackgroundColor());

			jbSelect.setOpaque(true);
			jbSelect.setBackground(new Color(255, 255, 255));
			jbSelect.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

			jpCenter.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
			jpCenter.setBackground(colorFactory.getBackgroundColor());
			jpCenter.add(jpLabel);
			jpCenter.add(jpRadio);
			jpCenter.add(jbSelect);

			// JPanel jpLeft = new JPanel(new GridLayout(2,1,20,20));
			// jpLeft.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
			// jpLeft.setBackground(colorFactory.getBackgroundColor());
			// jpLeft.add(jcConsiderDisease);
			// jpLeft.add(jbSelect);

			this.add(jlDrinkName, "North");
			this.add(jpCenter, "Center");
			// this.add(jpLeft, "East");
			// this.add(jbSelect, "East");

			this.setTitle("Best option list of " + jlDrinkName.getText());
			setSize(1000, 400);
			this.setLocation(100, 100);
			setVisible(true);
			/* initialize JRadioButton and grouping */

			rs.close();
			stmt.close();
			conn.close();
		}catch(

	SQLException sel)
	{
		sel.printStackTrace();
	}catch(
	Exception ex)
	{
		ex.printStackTrace();
	}finally
	{
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException se2) {

		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbSelect) {

			this.setVisible(false);
			int index = -1;
			for (int i = 0; i < choose_idList.size(); i++) {
				if (jrOptions[i].isSelected()) {
					index = i;
					//System.out.println("Final choose_id : "+choose_idList.get(i));
					break;
				}
			}

			if (index == -1) { // if no option selected
				OptionSelection.jlChoice.setText("No option");
				//set OptionSelection.chooseID
				OptionSelection.chooseID = 1;
			} else {
				OptionSelection.jlChoice.setText(jlOptions[index].getText());
				//set OptionSelection.chooseID
				OptionSelection.chooseID = Integer.parseInt(choose_idList.get(index));
				
			}
			
			System.out.println(OptionSelection.chooseID);

		}

	}

	public void initComponents() {
		// set drink name : which is on the top of the panel
		jlDrinkName = new JLabel();
		jlDrinkName.setHorizontalAlignment(SwingConstants.CENTER);
		jlDrinkName.setFont(new Font("arial", Font.BOLD, 30));
		jlDrinkName.setOpaque(true);
		jlDrinkName.setBackground(colorFactory.getBackgroundColor());

	}

	public void setChooseID() {

	}

}
