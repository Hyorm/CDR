import java.awt.Color;
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
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OptionChoose extends JFrame implements ActionListener {
	// String options[] = { "None", "caramelSyrup", "cinnamonSyrup",
	// "hazelnutsyrup", "javaChip", "shot",
	// "whipping cream" };

	String optionList[];
	public static JComboBox<String> option1;
	public static JComboBox<String> option2;
	public static JComboBox<String> option3;
	private JButton jbSelect;
	private JPanel jpP1;

	private Connection conn = null;
	private Statement stmt = null;
	private String chooseID;
	private SqlHolder sqlInf;
	private ColorFactory colorFactory = new ColorFactory();

	public OptionChoose(SqlHolder sqlInf_) {
		sqlInf = sqlInf_;

		setOptionList();

		option1 = new JComboBox<String>(optionList);
		option2 = new JComboBox<String>(optionList);
		option3 = new JComboBox<String>(optionList);
		jbSelect = new JButton("Set Option");

		option1.addActionListener(this);
		option2.addActionListener(this);
		option3.addActionListener(this);
		jbSelect.addActionListener(this);

		jpP1 = new JPanel(new GridLayout(4, 1));
		jpP1.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		jpP1.setBackground(colorFactory.getBackgroundColor());

		jpP1.add(option1);
		jpP1.add(option2);
		jpP1.add(option3);
		jpP1.add(jbSelect);

		this.add(jpP1);

		this.setSize(400, 400);
		this.setLocation(500, 100);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbSelect) {
			if (option1.getSelectedIndex() == 0 && option2.getSelectedIndex() == 0 && option3.getSelectedIndex() == 0) {
				OptionSelection.jlChoice.setText("No option");
			} else {

				ArrayList<String> array = new ArrayList<String>();

				if (option1.getSelectedIndex() != 0)
					array.add(option1.getSelectedItem() + "");
				if (option2.getSelectedIndex() != 0)
					array.add(option2.getSelectedItem() + "");
				if (option3.getSelectedIndex() != 0)
					array.add(option3.getSelectedItem() + "");

				// TODO array sorting
				Collections.sort(array);
				
//				for(String i : array) {
//					System.out.println("array"+i);
//				}

				String selectedOption = "";
				if (array.get(0) != null)
					selectedOption += array.get(0);
				for (int i = 1; i < array.size(); i++) {
					selectedOption += (", " + array.get(i));
				}

				OptionSelection.jlChoice.setText(selectedOption);

				String sql = "";
				ResultSet rs = null;
				PreparedStatement pstmt = null;

				/* get choose_id from DB */
				try {
					Class.forName(sqlInf.getDriver());
					conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
					stmt = conn.createStatement();

					// System.out.println("array size : " + array.size());
					switch (array.size()) {
					case 0:
						OptionSelection.setChooseID(1); // no option
						break;
					case 1:
						sql = "SELECT choose_id FROM chooseList WHERE option1 = ? AND option2 ='null' AND option3 ='null'";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, array.get(0));
						break;
					case 2:
						sql = "SELECT choose_id FROM chooseList WHERE option1 = ? AND option2 = ? AND option3 ='null'";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, array.get(0));
						pstmt.setString(2, array.get(1));
						break;
					case 3:
						sql = "SELECT choose_id FROM chooseList WHERE option1 = ? AND option2 = ? AND option3 = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, array.get(0));
						pstmt.setString(2, array.get(1));
						pstmt.setString(3, array.get(2));
						break;
					}

					rs = pstmt.executeQuery();

					if (rs.next()) {
						this.chooseID = rs.getString("choose_id");
						OptionSelection.chooseID = Integer.parseInt(this.chooseID);
						// hooseIDList.add(rs.getString("choose_id"));
					} else {
						OptionSelection.chooseID = 1;
					}

					// finish!
					System.out.println("choose_id : " + chooseID);

					rs.close();

					stmt.close();
					conn.close();
				} catch (SQLException sel) {
					sel.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
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
			this.setVisible(false);

		}

	}

	private void setOptionList() {
		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();
			ResultSet rs = null;
			String sql = "";

			ArrayList<String> array = new ArrayList<String>();
			array.add("None");

			if (!OptionSelection.jcConsiderDisease.isSelected()) { // do not consider disease

				sql = "select option_name from optionList";

				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					array.add(rs.getString("option_name"));
				}

			} else { // consider disease
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
					diseaseIDList.add(rs.getString("disease_id"));
				}
				for(int i=0; i<diseaseIDList.size(); i++) {
					System.out.println(diseaseIDList.get(i));
				}

				for (int i = 0; i < diseaseIDList.size(); i++) {
					sql = "select option_name from optionCauseList where disease_id=" + diseaseIDList.get(i);
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						avoidOptionList.add(rs.getString("option_name"));
					}
				}
				
				sql ="select * from optionList ";
				for(int i=0; i<avoidOptionList.size(); i++) {
//					System.out.println(avoidOptionList.get(i));
					if(i==0) sql += "where option_name <> '"+avoidOptionList.get(i)+"'";
					else sql += " AND option_name <> '"+avoidOptionList.get(i)+"'";
				}
				System.out.println(sql);
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					array.add(rs.getString("option_name"));
				}
				
			}

			optionList = array.toArray(new String[array.size()]);

			rs.close();

			stmt.close();
			conn.close();
		} catch (SQLException sel) {
			sel.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
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
}
