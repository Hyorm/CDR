import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class OrderList extends JFrame {

	private static final long serialVersionUID = 8402379533485231903L;
	private static ColorFactory colorFactory = new ColorFactory();

	SqlHolder sqlInf;
	JLabel topWords, valDrink, valOption1, valOption2, valOption3, valCnt;
	String userID;

	public OrderList(SqlHolder _sqlInf, String user_name) {
		super("Show Ordered List");
		sqlInf = _sqlInf;

		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
		setBounds(300, 100, 1000, 600);

		this.setLayout(null);

		setVisible(true);
	}

	public void setUserID(String user_name) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			String sql = "SELECT user_id FROM userList WHERE user_name='" + user_name + "';";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
				userID = rs.getString("user_id");

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException sel) {
			sel.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setTPles() {
		int numTple = 0;
		ArrayList<String> tmp_options = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			String sql = "SELECT b.drink_name, c.option1, c.option2, c.option3, d.count FROM drink b inner join orderedList d on b.drink_id=d.drink_id" 
					+ " inner join chooseList c on c.choose_id=d.choose_id WHERE d.user_id='" + userID + "';";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				String drinkName = rs.getString("drink_name");
				String option1 = rs.getString("option1");
				String option2 = rs.getString("option2");
				String option3 = rs.getString("option3");
				String count = rs.getString("count");

				add(valDrink = new JLabel(drinkName));
				valDrink.setBounds(50, 10 + (numTple + 1) * 30, 150, 30);

				add(valOption1 = new JLabel(option1));
				valOption1.setBounds(200, 10 + (numTple + 1) * 30, 200, 30);

				add(valOption2 = new JLabel(option2));
				valOption2.setBounds(400, 10 + (numTple + 1) * 30, 200, 30);

				add(valOption3 = new JLabel(option3));
				valOption3.setBounds(650, 10 + (numTple + 1) * 30, 200, 30);

				add(valCnt = new JLabel(count));
				valCnt.setBounds(850, 10 + (numTple + 1) * 30, 50, 30);

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
	}
}
