import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PickList extends JFrame {

	private static final long serialVersionUID = -8116599055133624395L;
	private static ColorFactory colorFactory = new ColorFactory();
	private ArrayList<String> drinks = new ArrayList<String>(); // drink_id, drink_name
	JLabel jldrnkID, jldrnkName;
	JLabel valdrnkID, valdrnkName;

	public PickList(JFrame pre_frame, SqlHolder sqlInf, String user_name) {
		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
		setBounds(200, 100, 800, 800);
		int numTple = 0;

		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			String sql;

			sql = "SELECT b.drink_id, a.drink_name FROM drink a inner join pickedList b on a.drink_id=b.drink_id"
				+ " inner join userList c on c.user_id=b.user_id WHERE (c.user_name='"
				+ user_name 
				+ "')";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				/*
				String drinkID = rs.getString("drink_id");
				String drinkName = rs.getString("drink_name");

				drinks.add(drinkID);
				drinks.add(drinkName);
				*/
				String drinkID = rs.getString("drink_id");
				String drinkName = rs.getString("drink_name");

				add(valdrnkID = new JLabel(drinkID));
				valdrnkID.setBounds(50, 10 + (numTple + 1) * 30, 150, 30);

				add(valdrnkName = new JLabel(drinkName));
				valdrnkName.setBounds(200, 10 + (numTple + 1) * 30, 200, 30);

				numTple++;

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException sel) {
			sel.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.setLayout(null);

		setVisible(true);
	}

	/*
	void showPckList() {
		// Drink ID Label
		add(jldrnkID = new JLabel("Drink ID", JLabel.CENTER));
		jldrnkID.setBorder(BorderFactory.createBevelBorder(0));
		jldrnkID.setBounds(200, 10, 100, 50);

		// Drink Name Label
		add(jldrnkID = new JLabel("Drink Name", JLabel.CENTER));
		jldrnkID.setBorder(BorderFactory.createBevelBorder(0));
		jldrnkID.setBounds(400, 10, 250, 50);

		for (int i = 0; i < drinks.size(); i += 2) {
			// Drink ID Value
			add(valdrnkID = new JLabel(drinks.get(i), JLabel.CENTER));
			valdrnkID.setHorizontalAlignment(JTextField.CENTER);
			valdrnkID.setBounds(200, 10 + 50 * (i + 1), 100, 50);
			valdrnkID.setText(drinks.get(0));

			// Drink Name Value
			add(valdrnkName = new JLabel(drinks.get(i + 1), JLabel.CENTER));
			valdrnkName.setHorizontalAlignment(JTextField.CENTER);
			valdrnkName.setBounds(400, 10 + 50 * (i + 1), 250, 50);
			valdrnkName.setText(drinks.get(1));
		}
	}
	*/
}
